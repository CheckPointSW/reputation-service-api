package com.checkpoint.reputation;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import lombok.Getter;
import lombok.ToString;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author aviadl
 */
@Getter
@ToString
public class AppArguments {
    @Parameter(names = {"-s", "--service"}, description = "The service you want to query", required = true)
    private Service service;
    @Parameter(names = {"-r", "--resource"}, description = "The resource you want to query", required = true)
    private String resource;
    @Parameter(names = {"-ck", "--client-key"}, description = "The client-key for your requests", required = true)
    private String clientKey;
    @Parameter(names = {"-v", "--verbose"}, description = "More logs and prints the full response")
    private boolean verbose = false;
    @Parameter(names = {"--help"}, help = true, description = "Show this message and exit.")
    private boolean help;

    public AppArguments(String[] args) {
        JCommander cmd = JCommander
                .newBuilder()
                .addObject(this)
                .build();
        try {
            cmd.parse(args);
        } catch (ParameterException e) {
            System.err.println(e.getLocalizedMessage());
            cmd.usage();
            System.exit(-1);
        }


        if (this.isHelp()) {
            cmd.usage();
            System.exit(0);
        }
    }

    enum Service {
        url,
        domain,
        ip
    }
}
