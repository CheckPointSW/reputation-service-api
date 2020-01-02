# Reputation Service

  - [Overview](#ReputationService-Overview)

  - [Rep-Auth Service](#ReputationService-Rep-AuthService)
    
      - [How to generate a token?](#ReputationService-Howtogenerateatoken?)

  - [URL Reputation Service](#ReputationService-URLReputationService)
    
      - [Request](#ReputationService-Request)
    
      - [URL Response](#ReputationService-URLResponse)
    
      - [URL classifications](#ReputationService-URLclassifications)

  - [File Reputation Service](#ReputationService-FileReputationService)
    
      - [Request](#ReputationService-Request.1)
    
      - [File Response](#ReputationService-FileResponse)
    
      - [File classifications](#ReputationService-Fileclassifications)

  - [IP Reputation Service](#ReputationService-IPReputationService)
    
      - [Request](#ReputationService-Request.2)
    
      - [IP Response](#ReputationService-IPResponse)
    
      - [IP classifications](#ReputationService-IPclassifications)

  - [Request Context](#ReputationService-RequestContext)

  - [Response](#ReputationService-Response)
    
      - [Response Status Codes](#ReputationService-ResponseStatusCodes)
    
      - [Response Context](#ReputationService-ResponseContext)

  - [Risk Threshold Guide](#ReputationService-RiskThresholdGuide)

  - [Postman Collection](#ReputationService-PostmanCollection)

# **Overview**

the reputation service currently supports request for URLs, IP and
files.

for using the reputation service first get a token that is valid for a
week from the rep-auth service, and then send a request to the
reputation service using that token in the request.

# **Rep-Auth Service**

authentication to the reputation service  acquires using a token
generated from the rep-auth service.

The token will expire after a week, to renew the authentication - send a
new token request.

the token should look like this: exp=\<date
in long format\>\~acl=/\*\~hmac=\<some hash\>

## **How to generate a token**?

Send an HTTPS GET
request: [https://rep.checkpoint.com/rep-auth/service/v1.0/request](https://rep.checkpoint.com/auth-rep/service/v1.0/request)

Use the "Client-Key" header. If you don't have a Client-Key, ask for one
from the reputation service team. (otherwise you will get HTTP status
401)

**how do I know that the token expired?**

service HTTP response code 403 Forbidden

# **URL Reputation Service**

## Request

Send an HTTPS POST
request: [https://rep.checkpoint.com/url-rep/service/v2.0/query](https://rep.checkpoint.com/domain-rep/service/v2.0/query)?resource=[google.com/aaa](http://google.com/aaa)

request headers: 

  - "Client-Key":  If you don't have a Client-Key, ask for one from the
    reputation service team.

  - "token": the token from the rep-auth service  **(not relevant
    for queries from inside the same data-center)**

request body, use JSON format:

 

{

"request": \[

{

"resource": "google.com",

"context": {}

}

\]

}

| **parameter name** | **type**                                                                                         | **is optional** | **default value** | **description**                |
| ------------------ | ------------------------------------------------------------------------------------------------ | --------------- | ----------------- | ------------------------------ |
| resource           | String                                                                                           | no              |                   | the URL to query about         |
| context            | Json(See "[Request Context](#ReputationService-RequestContext) " table for all available fields) | yes             |                   | Container for resource context |

## **URL Response**

See  Response section

## **URL classifications**

| **Classification**  | **Description**                                                                                                                                                                                                                                                                                                                                                                                                                                            | **Severity** |
| ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------ |
| Unclassified        | the service couldn't classify the domain. there is no enough data about this resource.                                                                                                                                                                                                                                                                                                                                                                     | N/A          |
| Adware              | website operating in the gray areas of the law collecting private data on the users and display unwanted content, or website which contains sub-application to download.                                                                                                                                                                                                                                                                                   | Low          |
| Volatile Website    | website that contains malicious software, for example: hacking websites.                                                                                                                                                                                                                                                                                                                                                                                   | Medium       |
| Benign              | legit website, which aren't serve any malicious purpose.                                                                                                                                                                                                                                                                                                                                                                                                   | N/A          |
| CnC Server          | command and controller of malware.                                                                                                                                                                                                                                                                                                                                                                                                                         | Critical     |
| Compromised Website | legit website that was hacked and now serve a malicious purpose.                                                                                                                                                                                                                                                                                                                                                                                           | High         |
| Phishing            | websites that attempt to obtain [sensitive information](https://en.wikipedia.org/wiki/Information_sensitivity) such as usernames, passwords, and [credit card](https://en.wikipedia.org/wiki/Credit_card) details (and sometimes, indirectly, [money](https://en.wikipedia.org/wiki/Money)), often for malicious reasons, by masquerading as a trustworthy entity in an [electronic communication](https://en.wikipedia.org/wiki/Electronic_communication) | High         |
| Infecting Website   | website that may infect it’s visitors with malware.                                                                                                                                                                                                                                                                                                                                                                                                        | High         |
| Infecting URL       | URL that may infect it’s visitors with malware.                                                                                                                                                                                                                                                                                                                                                                                                            |              |
| Web Hosting         | websites that allows to rent out space for websites to have your business in.                                                                                                                                                                                                                                                                                                                                                                              | Medium       |
| File Hosting        | websites that allows to rent out space for storage to have your business in.                                                                                                                                                                                                                                                                                                                                                                               | Medium       |
| Parked              | website which permanently does not have a content. it may contains advertising content on pages that have been registered but do not yet have original content                                                                                                                                                                                                                                                                                             | Medium       |
| Spam                | The url is used for spam.                                                                                                                                                                                                                                                                                                                                                                                                                                  | High         |
| Cryptominer         | The url is used for cryptomining.                                                                                                                                                                                                                                                                                                                                                                                                                          | High         |

# **File Reputation Service**

## Request

Send an HTTPS POST
request: [https://rep.checkpoint.com/file-rep/service/v2.0/query](https://rep.checkpoint.com/domain-rep/service/v2.0/query)?resource=71b6bb5acbf16ec9bdaf949fc347ba18

request headers: 

  - "Client-Key":  If you don't have a Client-Key, ask for one from the
    reputation service team.

  - "token": the token from the rep-auth service. ** (not relevant
    for queries from inside the same data-center)**

request body, use JSON format:

{

"request": \[

{

"resource": "9a6e9e182caddfd382f40f980ebc4353",

"context": {}

}

\]

}

| **parameter name** | **type**                                                                                         | **is optional** | **default value** | **description**                          |
| ------------------ | ------------------------------------------------------------------------------------------------ | --------------- | ----------------- | ---------------------------------------- |
| resource           | String                                                                                           | no              |                   | SHA256or MD5 of the file to query about. |
| context            | Json(See " [Request Context](#ReputationService-RequestContext)" table for all available fields) | yes             |                   | Container for resource context           |

## **File Response**

See  Response section

## **File classifications**

| **Classification** | **Description**                                                                                                                                                                                                                                                                                                      | **Severity** |
| ------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------ |
| Unclassified       | the service couldn't classify the domain. there is no enough data about this resource.                                                                                                                                                                                                                               | N/A          |
| Adware             | Installation file of Adware on your machine.Adware is a form of software that downloads or displays unwanted ads when a user is online, collects marketing data and other information without the user's knowledge or redirects search requests to certain advertising websites                                      | Low          |
| Riskware           | Riskware is the name given to legitimate programs that can cause damage if they are [exploited](https://usa.kaspersky.com/internet-security-center/threats/malware-system-vulnerability) by malicious users – in order to delete, block, modify, or copy data, and disrupt the performance of computers or networks. | Medium       |
| Malware            | Malicious file                                                                                                                                                                                                                                                                                                       | High         |
| Benign             | legitfile, whicharen'tserveany malicious purpose.                                                                                                                                                                                                                                                                    | Medium       |
| Unknown            | file that was never seen before by the service's vendors.                                                                                                                                                                                                                                                            | N/A          |
| Spam               | The file is used for spam.                                                                                                                                                                                                                                                                                           | High         |
| Cryptominer        | The file is used for cryptomining.                                                                                                                                                                                                                                                                                   | High         |

# **IP Reputation Service**

## Request

Send an HTTPS POST
request: <https://rep.checkpoint.com/ip-rep/service/v2.0/query?resource=>[8](http://google.com/aaa).8.8.8

request headers: 

  - "Client-Key":  If you don't have a Client-Key, ask for one from the
    reputation service team.

  - "token": the token from the **rep-auth** service  **(not relevant
    for queries from inside the same data-center)**

request body, use JSON format:

{

"request": \[

{

"resource": "8.8.8.8",

"context": {}

}

\]

}

| **parameter name** | **type**                                                                                         | **is optional** | **default value** | **description**                |
| ------------------ | ------------------------------------------------------------------------------------------------ | --------------- | ----------------- | ------------------------------ |
| resource           | String                                                                                           | no              |                   | the URL to query about         |
| context            | Json(See " [Request Context](#ReputationService-RequestContext)" table for all available fields) | yes             |                   | Container for resource context |

## **IP Response**

See  Response section

## **IP classifications**

| **Classification** | **Description**                                                                                                                                                                                                                                                                                                                                                                                                                                               | **Severity** |
| ------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------ |
| Unclassified       | The service couldn't classify the IP. there is not enough data about this resource.                                                                                                                                                                                                                                                                                                                                                                           | N/A          |
| Adware             | The IP's domains operating in the gray areas of the law collecting private data on the users and display unwanted content, or website which contains sub-application to download.                                                                                                                                                                                                                                                                             | Low          |
| Volatile           | The IP's domains contain malicious software, for example hacking websites.                                                                                                                                                                                                                                                                                                                                                                                    | Medium       |
| Benign             | Legit IP, which doesn't serve any malicious purpose.                                                                                                                                                                                                                                                                                                                                                                                                          | N/A          |
| CnC Server         | Command and control of malware.                                                                                                                                                                                                                                                                                                                                                                                                                               | Critical     |
| Compromised Server | Legit IP that was hacked and now serve a malicious purpose.                                                                                                                                                                                                                                                                                                                                                                                                   | High         |
| Phishing           | The IP's domains attempt to obtain [sensitive information](https://en.wikipedia.org/wiki/Information_sensitivity) such as usernames, passwords, and [credit card](https://en.wikipedia.org/wiki/Credit_card) details (and sometimes, indirectly, [money](https://en.wikipedia.org/wiki/Money)), often for malicious reasons, by masquerading as a trustworthy entity in an [electronic communication](https://en.wikipedia.org/wiki/Electronic_communication) | High         |
| Infection Source   | The IP's domains may infect its visitors with malware.                                                                                                                                                                                                                                                                                                                                                                                                        | High         |
| Web Hosting        | The IP's domains allow to rent out space for websites to have your business in.                                                                                                                                                                                                                                                                                                                                                                               | Medium       |
| File Hosting       | The IP's domains allow to rent out space for storage to have your business in.                                                                                                                                                                                                                                                                                                                                                                                | Medium       |
| Parked             | The IP's domains permanently do not have content. it may contain advertising content on pages that have been registered but do not yet have original content                                                                                                                                                                                                                                                                                                  | Medium       |
| Scanner            | The IP is a known internet scanner.                                                                                                                                                                                                                                                                                                                                                                                                                           | Medium       |
| Anonymiser         | The IP is a known TOR anonymity internet.                                                                                                                                                                                                                                                                                                                                                                                                                     |              |
| Cryptominer        | The IP's domains are used for cryptomining.                                                                                                                                                                                                                                                                                                                                                                                                                   | High         |
| Spam               | The IP's domains are used for spam.                                                                                                                                                                                                                                                                                                                                                                                                                           | High         |
| Compromised Host   | Victim IP.                                                                                                                                                                                                                                                                                                                                                                                                                                                    | Medium       |

# **Request Context**

| **Field**               | **Enforced values/Closed set of values** | **Description**                                          |
| ----------------------- | ---------------------------------------- | -------------------------------------------------------- |
| md5                     | 32 chars hex string                      |                                                          |
| sha1                    | 40 chars hex string                      |                                                          |
| sha256                  | 64 chars hex string                      |                                                          |
| relation\_to\_file      | vm/embbeded/download\_url                | how this URL relate to a file                            |
| referer                 | URL                                      | referer HTTP header                                      |
| destination\_port       | 1-65535                                  |                                                          |
| destination\_ip         | IP Address                               | IP address mapped to this domain                         |
| http\_method            | GET/POST/DELETE/PUT/HEAD                 | HTTP Method                                              |
| malware\_family         |                                          | Malware family assisated with this URL                   |
| dropped\_file\_count    |                                          |                                                          |
| initial\_url            |                                          | first URL lead to sequence of events                     |
| indicator\_origin       |                                          |                                                          |
| report\_url             | URL                                      | related OSINT URL                                        |
| protection\_name        |                                          | protection name seen this resource                       |
| victim\_country         | ISO 3166-1 alpha-2 country code          | country of the specific client                           |
| product                 |                                          |                                                          |
| related\_domains        | list                                     | list of related domains                                  |
| subject                 |                                          | subject of email message                                 |
| attachment\_file\_names | list                                     |                                                          |
| phishing\_subject       |                                          | targeted brand of phishing attack                        |
| infection\_state        | pre/post                                 | infection state of subject in case of malicious activity |
| location                | URL                                      | location HTTP header                                     |
| ttl                     | integer                                  |                                                          |
| ck                      |                                          | specific client identifier                               |
| ssdeep\_hash            | ssdeep string                            | ssdeep fuzzy hash                                        |
| ssdeep\_block\_size     | integer                                  |                                                          |
| languages               | list                                     |                                                          |
| machine\_id             |                                          |                                                          |
| org\_id                 |                                          |                                                          |

# **Response**

<table>
<thead>
<tr class="header">
<th><strong>attribute name</strong></th>
<th><strong>type</strong></th>
<th><strong>is optional</strong></th>
<th><strong>description</strong></th>
<th><strong>inner attribute</strong></th>
<th><strong>inner attribute description</strong></th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>status</td>
<td>Object</td>
<td>no</td>
<td>reflect the application status</td>
<td><ul>
<li><p>code</p></li>
<li><p>label</p></li>
<li><p>message</p></li>
</ul></td>
<td>code: 2001<br />
label: SUCCESS<br />
message: Succeeded to generate reputation</td>
</tr>
<tr class="even">
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td>code: 2002<br />
label: PARTIAL_SUCCESS<br />
message: Some vendors are unavailable</td>
</tr>
<tr class="odd">
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td>code: 2003<br />
label: FAILED<br />
message: No available vendors</td>
</tr>
<tr class="even">
<td>resource</td>
<td>String</td>
<td>no</td>
<td>the URL from the request</td>
<td></td>
<td></td>
</tr>
<tr class="odd">
<td>reputation</td>
<td>Object</td>
<td>no</td>
<td>reputation meta-data</td>
<td>classification</td>
<td>see below URL classification table.</td>
</tr>
<tr class="even">
<td></td>
<td></td>
<td></td>
<td></td>
<td>severity</td>
<td><p>the severity of the classification.possible values:</p>
<ul>
<li><p>N/A</p></li>
<li><p>Low</p></li>
<li><p>Medium</p></li>
<li><p>High</p></li>
<li><p>Critical</p></li>
</ul></td>
</tr>
<tr class="odd">
<td></td>
<td></td>
<td></td>
<td></td>
<td>confidence</td>
<td><p>how much the service is confidence with the reputation response.possible values:</p>
<ul>
<li><p>N/A</p></li>
<li><p>Low</p></li>
<li><p>Medium</p></li>
<li><p>High</p></li>
</ul></td>
</tr>
<tr class="even">
<td>context</td>
<td>Object</td>
<td>yes</td>
<td>see <a href="#ReputationService-ResponseContext">Response Context</a></td>
<td></td>
<td></td>
</tr>
</tbody>
</table>

## **Response Status Codes**

| **HTTP Response Code** | **Explanation**                                                                                                       |
| ---------------------- | --------------------------------------------------------------------------------------------------------------------- |
| 200                    | OK                                                                                                                    |
| 400                    | bad request: either the resource is not valid or the request parameter doesn't match the resource in the request body |
| 401                    | bad or missing "Client-Key" header                                                                                    |
| 403                    | bad or missing "token" header                                                                                         |

## **Response Context**

<table>
<thead>
<tr class="header">
<th><strong>attribute name</strong></th>
<th><strong>type</strong></th>
<th><strong>service</strong></th>
<th><strong>description</strong></th>
<th><strong>inner values</strong></th>
<th><strong>inner values type</strong></th>
<th><strong>inner values description</strong></th>
<th></th>
<th></th>
<th></th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>malware_family</td>
<td>String</td>
<td>URL/FILE/IP</td>
<td>the malware family associated with the resource</td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="even">
<td>categories</td>
<td>List of Strings</td>
<td>URL</td>
<td>URLF categories</td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="odd">
<td>google_safe_browsing_categories</td>
<td>List of Strings</td>
<td>URL</td>
<td>Google safe browsing categories</td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="even">
<td>protection_name</td>
<td>String</td>
<td>URL/File</td>
<td>The protection name returned from Malware Service / AntiVirus</td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="odd">
<td>redirections</td>
<td>List of Strings</td>
<td>URL</td>
<td>The redirections of the resource</td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="even">
<td>malware_types</td>
<td>List of Strings</td>
<td>File</td>
<td>The malware types based on VT scans</td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="odd">
<td>asn</td>
<td>int</td>
<td>IP</td>
<td>ASN of the IP</td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="even">
<td>as_owner</td>
<td>String</td>
<td>IP</td>
<td>ASN owner of the IP</td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="odd">
<td>safe</td>
<td>Boolean</td>
<td>URL</td>
<td>exists and true if certified safe</td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="even">
<td>location</td>
<td>Object</td>
<td>IP</td>
<td>geo location information</td>
<td></td>
<td>countryCode</td>
<td>String</td>
<td>see <a href="https://dev.maxmind.com/geoip/geoip2/geoip2-city-country-csv-databases/">geoip2-city-country-csv-databases</a> for more information</td>
<td></td>
<td></td>
</tr>
<tr class="odd">
<td></td>
<td></td>
<td></td>
<td></td>
<td></td>
<td>countryName</td>
<td>String</td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="even">
<td></td>
<td></td>
<td></td>
<td></td>
<td>region</td>
<td>String</td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="odd">
<td></td>
<td></td>
<td></td>
<td></td>
<td>city</td>
<td>String</td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="even">
<td></td>
<td></td>
<td></td>
<td></td>
<td>postalCode</td>
<td>String</td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="odd">
<td></td>
<td></td>
<td></td>
<td></td>
<td>latitude</td>
<td>float</td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="even">
<td></td>
<td></td>
<td></td>
<td></td>
<td>longitude</td>
<td>float</td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="odd">
<td></td>
<td></td>
<td></td>
<td></td>
<td>dma_code</td>
<td>int</td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="even">
<td></td>
<td></td>
<td></td>
<td></td>
<td>area_code</td>
<td>int</td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="odd">
<td></td>
<td></td>
<td></td>
<td></td>
<td>metro_code</td>
<td>int</td>
<td></td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="even">
<td>related_resources</td>
<td>Object</td>
<td>File</td>
<td>Similar files based on ssdeep similarity</td>
<td>similar_files_found</td>
<td>long</td>
<td>How many similar files found</td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="odd">
<td></td>
<td></td>
<td></td>
<td></td>
<td>similar_files_details</td>
<td><p>Object</p>
<ul>
<li><p>md5</p></li>
<li><p>sha256</p></li>
<li><p>ssdeep</p></li>
<li><p>ssdeep_similarity</p></li>
<li><p>file_type</p></li>
<li><p>classification</p></li>
<li><p>malware_family</p></li>
<li><p>confidence</p></li>
</ul></td>
<td>Information about the similar file</td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="even">
<td>phishing</td>
<td>Object</td>
<td>URL</td>
<td>Phishing information.</td>
<td>brand</td>
<td>String</td>
<td>brand of the phishing resource</td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="odd">
<td></td>
<td></td>
<td></td>
<td></td>
<td>type</td>
<td>Sting</td>
<td>type of brand</td>
<td></td>
<td></td>
<td></td>
</tr>
<tr class="even">
<td></td>
<td></td>
<td></td>
<td></td>
<td>domain</td>
<td>String</td>
<td>the primary domain</td>
<td></td>
<td></td>
<td></td>
</tr>
</tbody>
</table>

# **Risk Threshold Guide**

| **Risk Range**     | **Description**                                                                                                                            | **Confidence**  | **Severity**  |
| ------------------ | ------------------------------------------------------------------------------------------------------------------------------------------ | --------------- | ------------- |
| Risk = 0           | Indications of a legit website                                                                                                             | High            | N/A           |
| 0 \< Risk \< 10    | Internet long tail                                                                                                                         | Low/Medium      | Low           |
| 10 \<= Risk \< 50  | Adware servers, rouge popups URLs                                                                                                          | Low/Medium/High | Low/Medium    |
| Risk = 50          | Anonymizers, hosting and parked websites, Unknown files                                                                                    | Medium/High     | Medium        |
| 50 \< Risk \< 80   | No proven legit was activity witnessed by the resource                                                                                     | Low             | High/Critical |
| 80 \<= Risk \< 100 | No proven legit was activity witnessed by the resource and there are circumstantial evidences that ties the resource to malicious activity | Medium          |               |
| Risk = 100         | known malicious resource by at least one trusted vendors                                                                                   | High            |               |

#
