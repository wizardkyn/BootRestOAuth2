# BootRestOAuth2
Rest with Spring Security OAuth2 Authentication using Mysql<br>
TokenStore with JSON Web Tokens(JWT) 

# Environment
Spring Boot 1.4.0 , Spring Security 4.0.3 , spring-security-oauth2 2.0.9, spring-security-jwt 1.0.3<br>
Maven<br>
mysql , mybatis 3.3.1 , mybatis-spring 1.2.4<br>
using @RestController No jerysy<br>
Logback<br>
External Tomcat 8 with https<br>

access token
```
curl -k  -u rest-client:rest-secret https://localhost:8443/BootRestOAuth2/oauth/token -d "grant_type=password&username=restUser&password=1234"
```
response 
```
{
    "access_token":"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiUkVTVF9TRVJWSUNFIl0sInVzZXJfbmFtZSI6InNpY29tcyIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il0sImV4cCI6MTQ1ODIxOTgxNiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJqdGkiOiJiYTYyZTg0OC1iYmM3LTRmZTUtOTAyNy02OGI0OWY2ZDFmNzgiLCJjbGllbnRfaWQiOiJyZXN0LWNsaWVudCJ9.2kClJ4MGs3MWribYO7SW9OWxsbq6cy6M-qltrkRb2sw","token_type":"bearer","expires_in":43199,"scope":"read write trust","jti":"ba62e848-bbc7-4fe5-9027-68b49f6d1f78"
} 
```
site access
```
curl -k -H "authorization: bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOlsiUkVTVF9TRVJWSUNFIl0sInVzZXJfbmFtZSI6InNpY29tcyIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSIsInRydXN0Il0sImV4cCI6MTQ1ODIxOTgxNiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiJdLCJqdGkiOiJiYTYyZTg0OC1iYmM3LTRmZTUtOTAyNy02OGI0OWY2ZDFmNzgiLCJjbGllbnRfaWQiOiJyZXN0LWNsaWVudCJ9.2kClJ4MGs3MWribYO7SW9OWxsbq6cy6M-qltrkRb2sw" https://localhost:8443/BootRestOAuth2/api/users
```
response
```
[{"userId":"restUser"},{"userId":"restUser2"},{"userId":"restUser3"}]
```
# Client Details
. authorized Grant Types : "password" only.(UserDetailsService using Mysql with Mybatis)<br>
. not use rest of them ("authorization_code","refresh_token", "implicit","client_credentials").<br>
. only one clientId, secret. using authorities("USER") and scopes("read", "write", "trust")<br>
. In Memory 

# Tokens
. JSON Web Tokens(JWT) 

# Enforcing SSL

# Tomcat Datasource JNDI
```
<GlobalNamingResources>
<Resource auth="Container" driverClassName="com.mysql.jdbc.Driver" 
loginTimeout="10" maxActive="200" maxIdle="8" maxWait="5000" 
name="jdbc/sim" username="dbuser" password="1234" 
type="javax.sql.DataSource"
url="jdbc:mysql://db.example.com:3306/exampledb?zeroDateTimeBehavior=convertToNull"/>      
</GlobalNamingResources>

<Context docBase="BootRestOAuth2" path="/BootRestOAuth2" reloadable="true">
<ResourceLink global="jdbc/sim" name="jdbc/sim" type="javax.sql.DataSource"/>
</Context>
```

# Tomcat SSL
```
    <Connector SSLEnabled="true" clientAuth="false" 
    keystoreFile="C:/job/sts/Servers/Tomcat v8.0 Server at localhost-config/.keystore" 
    keystorePass="1234" keystoreType="pkcs12" maxThreads="150" port="8443"
    protocol="org.apache.coyote.http11.Http11Protocol" scheme="https" secure="true" sslProtocol="TLS"/>
```
