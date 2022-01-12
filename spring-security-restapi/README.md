
# Spring Security with  Azure AD with REST API

This example is based on the docs [Spring Boot OAuth 2.0 Resource Server JWT](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html)

## Spring boot Application 

- Create Spring web app with Spring Initializer 
- Add Jose and OAuth resource server dependencies
```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-oauth2-jose</artifactId>
			<version>5.6.1</version>
		</dependency>
 ```       

 - Add `tenant-id`  in issuer-uri `application.yml` - copy values from AzureAD->App Registrations-> rest app(<MSAADDemoAPI>) ->Overview. Last closing slash is important it is used in Audience field

 ![docs](../docs/appid.jpg)

 ```yaml
 spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri:  https://sts.windows.net/<tenant id>/
 ```

 
 - [Optionally] Set required Role on the advances controller methods with Role name prefixed with `APPROLE_`
 
 ```java
    @GetMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasAuthority('APPROLE_AdminRole')")
    public String admin() {
        return "SP has admin success.";
    }

 ```

 - Run the app `mvn spring-boot:run` and test endpoints `/echo` and `/admin`