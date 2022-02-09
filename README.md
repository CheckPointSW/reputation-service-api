# Check Point Reputation Service API

  - [Overview](#overview)  
  - [Getting Started with the Reputation Service API](#getting-started-with-the-reputation-service-api)
    - [Get your API Key](#get-your-api-key)
  - [Swagger](#swagger)
  - [APIs](#apis)
    - [Rep-Auth Service](#rep-auth-service)
        - [How to generate a token?](#how-to-generate-a-token)
    - [URL Reputation Service](#url-reputation-service)
        - [Request](#request)
        - [URL classifications](#url-classifications)
    - [File Reputation Service](#file-reputation-service)
        - [Request](#request-1)
        - [File classifications](#file-classifications)
    - [IP Reputation Service](#ip-reputation-service)
        - [Request](#request-2)
        - [IP classifications](#ip-classifications)
    - [Response](#response)
        - [Response Status Codes](#response-status-codes)
    - [Risk Threshold Guide](#risk-threshold-guide)

## **Overview**

Leverage the Check Point’s threat intelligence to enrich your SIEM and SOAR solutions and to secure your business applications and websites by using simple RESTful APIs.

Check Point Reputation Service API provides the following functionalities:

- [**URL Reputation**](#url-reputation-service) - for a domain/URL returns the classification and risk in accessing the resource  
- [**File Reputation**](#file-reputation-service) - for a file digest (md5/sha1/sha256) returns the risk in downloading the file without the need to scan it  
- [**IP Reputation**](#ip-reputation-service) - for an IP address returns it’s classification and risk in accessing a resource hosted on it

## Getting Started with the Reputation Service API  

### Get your API Key  

To get started with using the APIs, you must first [contact us](mailto:TCAPI_SUPPORT@checkpoint.com) .  
We'll provide you a trial API key for you to use. You'll be granted a daily quota. When you quota exceeded you'll receive `429` response status code.

## Swagger

Checkout our [Swagger UI](https://app.swaggerhub.com/apis-docs/Check-Point/reputation-service/) to explore the API

## APIs

### **Rep-Auth Service**

Authentication to the reputation service  acquires using a token generated from the rep-auth service.

The token will expire after a week, to renew the authentication - send a new token request.

The token should look like this: `exp=1578566241~acl=/*~hmac=95add7c04faa2e7831b451fd45503e4a2ac0598c7e84a5ace7dd611d7b483e5f`

#### **How to generate a token**?

Send an HTTPS GET request: <https://rep.checkpoint.com/rep-auth/service/v1.0/request>

Use the "Client-Key" header. (otherwise you will get HTTP status 401)

**How do I know that the token expired?**

Service respond status code 403 Forbidden

### **URL Reputation Service**

#### Request

Send an HTTPS POST request: https://rep.checkpoint.com/url-rep/service/v2.0/query?resource={url}

Request headers: 

  - "Client-Key":  You authorization id.
  - "token": the token from the rep-auth service.

Request body, use JSON format:
 
```js
{
    "request": [{
        "resource": "{url}"
    }]
}
```

| **Parameter Name** | **Type** | **is Optional** | **Description**                |
| ------------------ | -------- | --------------- | ------------------------------ |
| resource           | String   | No              | the URL to query about         |

#### **URL classifications**

| **Classification**  | **Description**                                                                                                                                                                                                                                                                                                                                                                                                                                            | **Severity** |
| ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------ |
| Unclassified        | The service couldn't classify the domain. there is no enough data about this resource.                                                                                                                                                                                                                                                                                                                                                                     | N/A          |
| Adware              | Website operating in the gray areas of the law collecting private data on the users and display unwanted content, or website which contains sub-application to download.                                                                                                                                                                                                                                                                                   | Low          |
| Volatile Website    | Website that contains malicious software, for example: hacking websites.                                                                                                                                                                                                                                                                                                                                                                                   | Medium       |
| Benign              | Legit website, which aren't serve any malicious purpose.                                                                                                                                                                                                                                                                                                                                                                                                   | N/A          |
| CnC Server          | Command and controller of malware.                                                                                                                                                                                                                                                                                                                                                                                                                         | Critical     |
| Compromised Website | Legit website that was hacked and now serve a malicious purpose.                                                                                                                                                                                                                                                                                                                                                                                           | High         |
| Phishing            | Websites that attempt to obtain [sensitive information](https://en.wikipedia.org/wiki/Information_sensitivity) such as usernames, passwords, and [credit card](https://en.wikipedia.org/wiki/Credit_card) details (and sometimes, indirectly, [money](https://en.wikipedia.org/wiki/Money)), often for malicious reasons, by masquerading as a trustworthy entity in an [electronic communication](https://en.wikipedia.org/wiki/Electronic_communication) | High         |
| Infecting Website   | Website that may infect it’s visitors with malware.                                                                                                                                                                                                                                                                                                                                                                                                        | High         |
| Infecting URL       | URL that may infect it’s visitors with malware.                                                                                                                                                                                                                                                                                                                                                                                                            |              |
| Web Hosting         | Websites that allows to rent out space for websites to have your business in.                                                                                                                                                                                                                                                                                                                                                                              | Medium       |
| File Hosting        | Websites that allows to rent out space for storage to have your business in.                                                                                                                                                                                                                                                                                                                                                                               | Medium       |
| Parked              | Website which permanently does not have a content. it may contains advertising content on pages that have been registered but do not yet have original content                                                                                                                                                                                                                                                                                             | Medium       |
| Spam                | The url is used for spam.                                                                                                                                                                                                                                                                                                                                                                                                                                  | High         |
| Cryptominer         | The url is used for cryptomining.                                                                                                                                                                                                                                                                                                                                                                                                                          | High         |

### **File Reputation Service**

#### Request  

Send an HTTPS POST request: <https://rep.checkpoint.com/file-rep/service/v2.0/query?resource={file-hash}>

request headers:  

- "Client-Key":  You authorization id.
- "token": the token from the rep-auth service.

request body, use JSON format:

```js
{
    "request": [{
       "resource": "{file-hash}"
    }]
}
```

| **Parameter Name** | **Type** | **Is Optional** | **Description**                          |
| ------------------ | -------- | --------------- | ---------------------------------------- |
| resource           | String   | No              | SHA256 / MD5 / SHA1 of the file to query |

### **File classifications**

| **Classification** | **Description**                                                                                                                                                                                                                                                                                                      | **Severity** |
| ------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------ |
| Unclassified       | The service couldn't classify the domain. there is no enough data about this resource.                                                                                                                                                                                                                               | N/A          |
| Adware             | Installation file of Adware on your machine. Adware is a form of software that downloads or displays unwanted ads when a user is online, collects marketing data and other information without the user's knowledge or redirects search requests to certain advertising websites                                      | Low          |
| Riskware           | Riskware is the name given to legitimate programs that can cause damage if they are exploited by malicious users – in order to delete, block, modify, or copy data, and disrupt the performance of computers or networks. | Medium       |
| Malware            | Malicious file                                                                                                                                                                                                                                                                                                       | High         |
| Benign             | Legit file, which aren't serve any malicious purpose.                                                                                                                                                                                                                                                                    | Medium       |
| Unknown            | File that was never seen before by the service's vendors.                                                                                                                                                                                                                                                            | N/A          |
| Spam               | The file is used for spam.                                                                                                                                                                                                                                                                                           | High         |
| Cryptominer        | The file is used for cryptomining.                                                                                                                                                                                                                                                                                   | High         |

### **IP Reputation Service**

#### Request

Send an HTTPS POST request: https://rep.checkpoint.com/ip-rep/service/v2.0/query?resource={ip}

request headers: 

  - "Client-Key":  You authorization id.
  - "token": the token from the **rep-auth** service.

request body, use JSON format:

```js
{
    "request": [{
        "resource": "{ip}"
    }]
}
```

| **Parameter Name** | **Type** | **Is Optional** | **Description** |
| ------------------ | -------- | --------------- | --------------- |
| resource           | String   | No              | The IP to query |

### **IP classifications**

| **Classification** | **Description**                                                                                                                                                                                                                                                                                                                                                                                                                                               | **Severity** |
| ------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------ |
| Unclassified       | The service couldn't classify the IP. there is not enough data about this resource.                                                                                                                                                                                                                                                                                                                                                                           | N/A          |
| Adware             | The IP's domains operating in the gray areas of the law collecting private data on the users and display unwanted content, or website which contains sub-application to download.                                                                                                                                                                                                                                                                             | Low          |
| Volatile           | The IP's domains contain malicious software, for example hacking websites.                                                                                                                                                                                                                                                                                                                                                                                    | Medium       |
| Benign             | Legit IP, which doesn't serve any malicious purpose.                                                                                                                                                                                                                                                                                                                                                                                                          | N/A          |
| CnC Server         | Command and control of malware.                                                                                                                                                                                                                                                                                                                                                                                                                               | Critical     |
| Compromised Server | Legit IP that was hacked and now serve a malicious purpose.                                                                                                                                                                                                                                                                                                                                                                                                   | High         |
| Phishing           | The IP's domains attempt to obtain sensitive information such as usernames, passwords, and credit card details (and sometimes, indirectly, money), often for malicious reasons, by masquerading as a trustworthy entity in an electronic communication | High         |
| Infection Source   | The IP's domains may infect its visitors with malware.                                                                                                                                                                                                                                                                                                                                                                                                        | High         |
| Web Hosting        | The IP's domains allow to rent out space for websites to have your business in.                                                                                                                                                                                                                                                                                                                                                                               | Medium       |
| File Hosting       | The IP's domains allow to rent out space for storage to have your business in.                                                                                                                                                                                                                                                                                                                                                                                | Medium       |
| Parked             | The IP's domains permanently do not have content. it may contain advertising content on pages that have been registered but do not yet have original content                                                                                                                                                                                                                                                                                                  | Medium       |
| Scanner            | The IP is a known internet scanner.                                                                                                                                                                                                                                                                                                                                                                                                                           | Medium       |
| Anonymiser         | The IP is a known TOR anonymity internet.                                                                                                                                                                                                                                                                                                                                                                                                                     |              |
| Cryptominer        | The IP's domains are used for cryptomining.                                                                                                                                                                                                                                                                                                                                                                                                                   | High         |
| Spam               | The IP's domains are used for spam.                                                                                                                                                                                                                                                                                                                                                                                                                           | High         |
| Compromised Host   | Victim IP.                                                                                                                                                                                                                                                                                                                                                                                                                                                    | Medium       |
  
  
## **Response**

<table>
<thead>
<tr class="header">
<th><strong>Attribute Name</strong></th>
<th><strong>Type</strong></th>
<th><strong>Is Optional</strong></th>
<th><strong>Description</strong></th>
<th><strong>Inner Attribute</strong></th>
<th><strong>Inner Attribute Description</strong></th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>status</td>
<td>Object</td>
<td>No</td>
<td>Reflect the application status</td>
<td><ul>
<li><p>code</p></li>
<li><p>label</p></li>
<li><p>message</p></li>
</ul></td>
<td><p>code: 2001<br />
label: SUCCESS<br />
message: Succeeded to generate reputation</p>
<p>code: 2002<br />
label: PARTIAL_SUCCESS<br />
message: Some vendors are unavailable</p>
<p>code: 2003<br />
label: FAILED<br />
message: No available vendors</p>
<tr class="even">
<td>resource</td>
<td>String</td>
<td>No</td>
<td>The URL from the request</td>
<td></td>
<td></td>
</tr>
<tr class="odd">
<td>reputation</td>
<td>Object</td>
<td>No</td>
<td>Reputation meta-data</td>
<td>classification</td>
<td></td>
</tr>
<tr class="even">
<td></td>
<td></td>
<td></td>
<td></td>
<td>severity</td>
<td>The severity of the classification.<br/>
Possible values:
<ul>
<li>N/A</li>
<li>Low</li>
<li>Medium</li>
<li>High</li>
<li>Critical</li>
</ul></td>
</tr>
<tr class="odd">
<td></td>
<td></td>
<td></td>
<td></td>
<td>confidence</td>
<td><p>How much the service is confident with the reputation response.<br/>
Possible values:</p>
<ul>
<li>N/A</li>
<li>Low</li>
<li>Medium</li>
<li>High</li>
</ul></td>
</tr>
</tbody>
</table>

### **Response Status Codes**

| **HTTP Response Code** | **Description**                                                                                                        |
| ---------------------- | ---------------------------------------------------------------------------------------------------------------------- |
| 200                    | OK                                                                                                                     |
| 400                    | Bad request - either the resource is not valid or the request parameter doesn't match the resource in the request body |
| 401                    | Bad or missing "Client-Key" header                                                                                     |
| 403                    | Bad or missing "token" header                                                                                          |

## **Risk Threshold Guide**

| **Risk Range** | **Description**                                                                                                                            | **Confidence**  | **Severity**  |
| -------------- | ------------------------------------------------------------------------------------------------------------------------------------------ | --------------- | ------------- |
| Risk=0         | Indications of a legit website                                                                                                             | High            | N/A           |
| 0\<Risk\<10    | Internet long tail                                                                                                                         | Low/Medium      | Low           |
| 10\<=Risk\<50  | Adware servers, rouge popups URLs                                                                                                          | Low/Medium/High | Low/Medium    |
| Risk=50        | Anonymizers, hosting and parked websites, Unknown files                                                                                    | Medium/High     | Medium        |
| 50\<Risk\< 80  | No proven legit was activity witnessed by the resource                                                                                     | Low             | High/Critical |
| 80\<=Risk\<100 | No proven legit was activity witnessed by the resource and there are circumstantial evidences that ties the resource to malicious activity | Medium          | High/Critical |
| Risk=100       | Known malicious resource by at least one trusted vendors                                                                                   | High            | High/Critical |
