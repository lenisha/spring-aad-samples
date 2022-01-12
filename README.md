# Connecting Spring with Azure AD

This repo contains two examples using Spring Boot REST API secured with Azure AD OAuth2

- [Microsft Azure AD SpringBoot Starter](./ms-aad-restapi)
- [Spring Securoty OAuth 2.0](./spring-security-restapi)

Both examples provide libraries and integration to secure REST API (resource server in OAuth 2.0 jargon) with JWT Tokens.
In both examples we will use same AzureAD app registrations and tests defined in `tests.http" are using client credentials grant to obtain the token and invoke API.


## Setup Azure API and Client App Registrations

For this example we will simulate two components API Client and API REST Application.

- API REST Application Registration is used by REST API, resource server for the api will validat that JWT tokens have this app registration as `audience`. [Optional setup] This App registration  defines Role exposed by the applications - `Admin` for advanced administrative methods.

- API Client Application is used by REST Client and optionally granted role `Admin` to be able to invoke advanced methods, will get 403 if `/admin` call ig foles not granted.

Follow the docs to 
- [Register the App in AAD](https://docs.microsoft.com/en-us/azure/active-directory/develop/quickstart-register-app)
- [[Optionally] Add Roles to your applications](https://docs.microsoft.com/en-us/azure/active-directory/develop/howto-add-app-roles-in-azure-ad-apps#assign-app-roles-to-applications)

In our example here is the setup:

- Create REST API App Registration `MSAADDemoAPI` and define a role `Admin` exposed by API
![docs](docs/appid.jpg)
![docs](docs/apiclientrole.jpg)


- Create Client App Registration `MSAADDemoAPIClient` , create secret and grant it role `Admin`

![docs](docs/clientapirole.jpg)


## Microsoft Spring Starter for Azure AD
This example is using Microsoft Azure AD Spring Starter that implements all OIDC flows with AzureAD and hides any AAD setup complexity.
[Microsft Azure AD SpringBoot Starter](./ms-aad-restapi)

## Spring Security with Azure AD
This example provides code that is vendor neutral and is using Apringbott Security Module and configuration pointing to OIDC well known config for AAD.

[Spring Security OAuth 2.0](./spring-security-restapi)

### Testing with REST Client 

In both examples we will use same AzureAD app registrations and tests defined in `tests.http" are using client credentials grant to obtain the token and invoke API. You will need to install  [Rest Client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client) extension in VSCode to run it.

- To obtain Azure AD Token via client_credential flow [Client Credential Flow and default scope](https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-permissions-and-consent#client-credentials-grant-flow-and-default) we will use following HTTP request in `tests.http` (replace valueds for tenant and app registrations to your setup)


``` 
POST https://login.microsoftonline.com/{{tenant}}/oauth2/v2.0/token
Host: login.microsoftonline.com
Content-Type: application/x-www-form-urlencoded

client_id={{appclient_id}}
&client_secret={{appclient_secret}}
&grant_type=client_credentials
&scope={{apiapp_id}}/.default
&request_type=token
```

Scope is always <resource app id>/.default for client_credentials , it will also be used by AAD to set audience in the token
![docs](docs/accesstoken.jpg)

You could verify content of the token at https://jwt.ms, our decoded token - you will see `roles` if granted to client application

{
  "typ": "JWT",
  "alg": "RS256",
  "x5t": "Mr5-AUibfBii7Nd1jBebaxboXW0",
  "kid": "Mr5-AUibfBii7Nd1jBebaxboXW0"
}.{
  "aud": "<apiclient_id>,
  "iss": "https://sts.windows.net/<tenantid>/",
  "iat": 1641967979,
  "nbf": 1641967979,
  "exp": 1641971879,
  "aio": "E2ZgYBCJvVemV+f5aMUG3y6TuQ8YAA==",
  "appid": "<appclient_id>",
  "appidacr": "1",
  "idp": "https://sts.windows.net/<tenantid>/",
  "oid": "xxxx",
  "rh": "0.AVEA-1rmm50Wt0W8wph-dDYql0aDHVId4mBMs_NAAvwUmBdRAAA.",
  "roles": [
    "AdminRole"
  ],
  "sub": "xxxx",
  "tid": "<tenantid>",
  "uti": "vrxcAQO3UUiejUZa1Z6-AQ",
  "ver": "1.0"
}.[Signature]

- Run Spring application `mvn spring-boot:run` and test endpoints ( you will receiev 403 in /admin if role not granted )
```
GET http://localhost:8080/echo
Authorization: Bearer {{getToken.response.body.access_token}}

GET http://localhost:8080/admin
Authorization: Bearer {{getToken.response.body.access_token}}
```



[Aquire Token for AD](https://docs.microsoft.com/en-us/azure/active-directory/develop/scenario-daemon-acquire-token?tabs=dotnet#azure-ad-v10-resources)
[AAD and default scope](https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-permissions-and-consent#the-default-scope)
