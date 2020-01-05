package com.checkpoint.reputation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.*;

/**
 * @author aviadl
 */
public class ReputationClient {
    private final Logger logger = Logger.getLogger("reputation-client");
    private final static ObjectMapper mapper = new ObjectMapper();
    private final AppArguments args;
    private final HttpClient client;

    public ReputationClient(AppArguments arguments) {
        this.args = arguments;
        this.client = HttpClient.newHttpClient();
        initLog();
    }

    private void initLog() {
        logger.setUseParentHandlers(false);
        logger.setLevel(this.args.isVerbose() ? Level.ALL : Level.INFO);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(this.args.isVerbose() ? Level.ALL : Level.INFO);
        handler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                return record.getMessage() + '\n';
            }
        });
        logger.addHandler(handler);
    }

    public void run() {
        logger.log(Level.FINE, "first, let's get token fro rep-auth");
        // first let's get the token
        client.sendAsync(HttpRequest.newBuilder()
                                    .GET()
                                    .uri(URI.create("https://rep.checkpoint.com/rep-auth/service/v1.0/request"))
                                    .header("Client-Key", this.args.getClientKey())
                                    .build(), HttpResponse.BodyHandlers.ofString())
              .thenApply(this::handleResponse)
              // then send to reputation (with token)
              .thenApplyAsync(this::sendToReputation)
              .thenApply(this::handleResponse)
              .thenApply(HttpResponse::body)
              .thenAccept(this::handleReputationResponse)
              .join();
    }

    private void handleReputationResponse(String s) {
        try {
            JsonNode jsonNode = mapper.readTree(s);
            logger.log(Level.FINE,
                       "your response is:\n" + mapper.writerWithDefaultPrettyPrinter()
                                                     .writeValueAsString(jsonNode));
            JsonNode response = jsonNode.get("response").get(0);
            String classification = response.get("reputation").get("classification").asText();
            int risk = response.get("risk").asInt();
            logger.log(Level.INFO, String.format("%s is %s with risk %s/100", this.args.getResource(), classification,
                                                 risk));
        } catch (JsonProcessingException ignored) {
        }
    }

    private HttpResponse<String> sendToReputation(HttpResponse<String> res) {
        logger.log(Level.FINE, "now, let's query reputation");
        try {
            return client.send(HttpRequest.newBuilder()
                                          .POST(HttpRequest.BodyPublishers
                                                        .ofString(String.format("{ \"request\": [{\"resource\": " +
                                                                                "\"%s\"}]}", this.args.getResource())))
                                          .uri(URI.create(String.format(
                                                  "https://rep.checkpoint.com/%s-rep/service/v2.0/query?" +
                                                  "resource=%s",
                                                  this.args.getService(), this.args.getResource())))
                                          .header("Client-Key", this.args.getClientKey())
                                          .header("token", res.body())
                                          .build(), HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
            System.exit(-1);
        }
        return null;
    }

    private HttpResponse<String> handleResponse(HttpResponse<String> res) {
        if (res.statusCode() != 200) {
            System.out.println(String.format("Failed requesting %s with status code %s", res.uri(), res.statusCode()));
            System.exit(-1);
        }
        logger.log(Level.FINE, "success!");
        return res;
    }
}
