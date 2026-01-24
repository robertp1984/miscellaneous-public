FROM amazoncorretto:25.0.0-alpine

WORKDIR /app

COPY target/springboottours*.jar springboottours.jar
COPY src/main/resources/keystore.p12 keystore.p12
COPY src/main/resources/truststore.p12 truststore.p12
COPY src/main/resources/ca.crt ca.crt

EXPOSE 8080

CMD ["java", "-Djavax.net.ssl.keyStore=/app/keystore.p12", "-Djavax.net.ssl.keyStorePassword=password", "-Djavax.net.ssl.trustStore=/app/truststore.p12", "-Djavax.net.ssl.trustStorePassword=password", "-jar", "springboottours.jar"]
