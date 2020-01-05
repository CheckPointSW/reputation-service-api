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
# reputation-api -s url -r https://secure08c-chase.000webhostapp.com/web/auth/enrollment/ -ck <client-key>
https://secure08c-chase.000webhostapp.com/web/auth/enrollment/ is Phishing with risk 100/100
```

### With `--verbose`

```
# reputation-api -s url -r https://secure08c-chase.000webhostapp.com/web/auth/enrollment/ -ck <client-key> -v
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
      "resource": "https://secure08c-chase.000webhostapp.com/web/auth/enrollment/",
      "reputation": {
        "classification": "Phishing",
        "severity": "High",
        "confidence": "High"
      },
      "risk": 100,
      "context": {
        "categories": [
          {
            "id": 1,
            "name": "Computers / Internet"
          }
        ],
        "google_safe_browsing_categories": [
          "SOCIAL_ENGINEERING"
        ],
        "phishing": {
          "brand": "Chase",
          "type": "Banking",
          "domain": "chase.com"
        },
        "protection_name": "Phishing.TC.twxqx",
        "indications": [
          "12 vendors detected this URL in VirusTotal",
          "Google found social engineering activity on this url",
          "The domain name resembles the chase official website",
          "The URL is malicious by Check Point's Threat Cloud",
          "VirusTotal vendors detected malicious URLs of the domain",
          "The IP address is involved with malicious activity",
          "The domain is hosted on IP address associated with malicious activity",
          "URL path is involved with malicious activity",
          "Found a phishing file downloaded from domain"
        ],
        "vt_positives": 12,
        "alexa_rank": 7397,
        "registrant": "hostmaster@hostinger.com",
        "creation_date": "2016:05:11 00:00:00"
      }
    }
  ]
}

https://secure08c-chase.000webhostapp.com/web/auth/enrollment/ is Phishing with risk 100/100
```
