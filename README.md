# Connecting Spring with Azure AD

This repo contains two examples using Spring Boot REST API secured with Azure AD OAuth2

- [Microsft Azure AD SpringBoot Starter](./ms-aad-restapi)
- [Spring Securoty OAuth 2.0](./spring-security-restapi)

Both examples provide libraries and integration to secure REST API (resource server in OAuth 2.0 jargon) with JWT Tokens
In both examples we will use same AzureAD app registrations and tests defined in `tests.http" are uing client credentials grant to obtain the token and invoke API. You will need to install  [Rest Client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client) extension in VSCode to run it.

## Setup Azure API and Client App Registrations

For this example we will simulate two components API Client and API REST Application.

- API REST Application Registration is used by REST API, resource server for the api will validat that JWT tokens have this app registration as `audience`. [Optional setup] This App registration  defines Role exposed by the applications - `Admin` for advanced administrative methods.

- API Client Application is used by REST Client and optionally granted role `Admin` to be able to invoke advanced methods, will get 403 if `/admin` call ig foles not granted.

Follow the docs to 
- [Register the App in AAD](https://docs.microsoft.com/en-us/azure/active-directory/develop/quickstart-register-app)
- [Add Roles to your applications](https://docs.microsoft.com/en-us/azure/active-directory/develop/howto-add-app-roles-in-azure-ad-apps#assign-app-roles-to-applications)

In our example here is the setup:

- Create REST API App Registration `MSAADDemoAPI` and define a role `Admin` exposed by API
![docs](/docs/apiclient.png)

- Create Client App Registration `MSAADDemoAPIClient` and grant it role `Admin`

![docs](/docs/apiclient.png)


## Microsoft Spring Starter for Azure AD

[Spring Boot Starter for Azure Active Directory developer's guide](https://docs.microsoft.com/en-us/azure/developer/java/spring-framework/spring-boot-starter-for-azure-active-directory-developer-guide#protect-a-resource-serverapi)

## Spring Security with Azure AD

### Scope is always resource/.default for client_creds https://docs.microsoft.com/en-us/azure/active-directory/develop/scenario-daemon-acquire-token?tabs=dotnet#azure-ad-v10-resources
### https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-permissions-and-consent#the-default-scope
### https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-permissions-and-consent#client-credentials-grant-flow-and-default
### Send client credentials call to get token https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-oauth-ropc
