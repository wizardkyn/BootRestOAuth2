# BootRestOAuth2
Rest with Spring Security OAuth2 Authentication using Mysql<br>
TokenStore with In Memory<br>
The Branch "useJdbc" : TokenStore with JDBC
The Branch "useJwt" : TokenStore with JSON Web Tokens(JWT)

# Environment
Spring Boot 1.4.0 , Spring Security 4.0.3 , spring-security-oauth2 2.0.9<br>
Maven<br>
mysql , mybatis 3.3.1 , mybatis-spring 1.2.4<br>
using @RestController No jerysy<br>
Logback<br>
External Tomcat 8 with https<br>

access token
```
curl -k -u rest-client:rest-secret https://localhost:8443/BootRestOAuth2/oauth/token -d "grant_type=password&username=restUser&password=1234"
```
response
```
{"access_token":"122a9f84-880d-423a-ad00-283433ef71e2","token_type":"bearer","expires_in":43199,"scope":"read write trust"}
```
 
site access
```
curl -k -H "authorization: bearer 122a9f84-880d-423a-ad00-283433ef71e2" https://localhost:8443/BootRestOAuth2/api/users
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
. In Memory

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
