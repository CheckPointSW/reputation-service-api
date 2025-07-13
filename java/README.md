# Reputation Service - Java Example

Java example for Reputation Service REST API usage.  
The jar prints to screen the result of the resource.  
The example uses **Java 11**.

## Install

```
mvn install
```

The *jar* will be in `target/reputation-service-api-x.y.z.jar` where `x.y.z` is the version number.

## Usage

```
# java -jar reputation-service-api-1.0.0.jar --help
Usage: <main class> [options]
  Options:
  * -ck, --client-key
      The client-key for your requests
    --help
      Show this message and exit.
  * -r, --resource
      The resource you want to query
  * -s, --service
      The service you want to query
      Possible Values: [url, domain, ip]
    -v, --verbose
      More logs and prints the full response
      Default: false
```

## Examples

### Simple use

```
# java -jar reputation-service-api-1.0.0.jar -s ip -r 8.8.8.8 -ck <client-Key>
8.8.8.8 is Benign with risk 0/100
```

```
# java -jar reputation-service-api-1.0.0.jar -s url -r https://m.swiki.playmarket-googles.com -ck <client-Key>
  https://m.swiki.playmarket-googles.com is Infecting URL with risk 100/100
```

### With `--verbose`

```
# java -jar reputation-service-api-1.0.0.jar -s ip -r 8.8.8.8 -ck <client-key> -v
first, let's get token from rep-auth
success!
now, let's query reputation
success!
your response is:
{
  "response" : [ {
    "status" : {
      "code" : 2001,
      "label" : "SUCCESS",
      "message" : "Succeeded to generate reputation"
    },
    "resource" : "8.8.8.8",
    "reputation" : {
      "classification" : "Benign",
      "severity" : "N/A",
      "confidence" : "High"
    },
    "risk" : 0,
    "context" : {
      "location" : {
        "countryCode" : "US",
        "countryName" : "United States",
        "region" : null,
        "city" : null,
        "postalCode" : null,
        "latitude" : 37.751007,
        "longitude" : -97.822,
        "dma_code" : 0,
        "area_code" : 0,
        "metro_code" : 0
      },
      "asn" : 15169,
      "as_owner" : "Google LLC"
    }
  } ]
}
8.8.8.8 is Benign with risk 0/100
```
```
# java -jar reputation-service-api-1.0.0.jar -s url -r https://m.swiki.playmarket-googles.com -ck <client-Key> -v
  first, let's get token from rep-auth
  success!
  now, let's query reputation
  success!
  your response is:
  {
    "response": [
      {
        "status": {
          "code": 2001,
          "label": "SUCCESS",
          "message": "Succeeded to generate reputation"
        },
        "resource": "https://m.swiki.playmarket-googles.com",
        "reputation": {
          "classification": "Infecting URL",
          "severity": "High",
          "confidence": "High"
        },
        "risk": 100,
        "context": {
          "categories": [
            {
                "id": 31,
                "name": "Phishing"
            }
          ],
          "protection_name": "Phishing.TC.twxqx",
          "indications": [
              "Known compromised domain",
              "Known malicious website"
          ]
        }
      }
    ]
  }
  https://m.swiki.playmarket-googles.com is Infecting URL with risk 100/100
```
