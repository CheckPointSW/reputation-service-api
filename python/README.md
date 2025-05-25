# Reputation Service - Python Example

Python example for Reputation Service REST API usage.  
The script prints to screen the result of the resource.

## Install

```
python setup.py install
```

## Usage

```
> reputation-api

Usage: reputation-api [OPTIONS]

Options:
  -s, --service [url|file|ip]  The service you want to query  [required]
  -r, --resource TEXT          The resource you want to query  [required]
  -ck, --client-key TEXT       The client-key for your requests  [required]
  -v, --verbose                More logs and prints the full response
  --help                       Show this message and exit.
```

## Examples

### Simple use

```
# reputation-api -s url -r https://m.swiki.playmarket-googles.com -ck <client-key>
https://m.swiki.playmarket-googles.com is Phishing with risk 100/100
```

### With `--verbose`

```
# reputation-api -s url -r https://m.swiki.playmarket-googles.com -ck <client-key> -v
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
        "vt_categories": [
            "phishing and fraud",
            "phishing and other frauds",
            "phishing (alphamountain.ai)"
        ],
        "protection_name": "Phishing.TC.twxqx",
        "indications": [
            "Known compromised domain",
            "Known malicious website"
        ]
        "vt_positives": 17,
        "registrant": "b20dfac919550647s@",
        "creation_date": "2024-03-01"
      }
    }
  ]
}

https://m.swiki.playmarket-googles.com is Phishing with risk 100/100
```
