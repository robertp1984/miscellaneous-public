# Tear down
docker compose down

# Build everything
mvn install
docker compose build

# Start Docker containers
docker compose up -d postgresql mongo rabbitmq

# Start Spring Boot applications
#java   "-Djavax.net.ssl.keyStore=$PWD\springboottours\src\main\resources\keystore.p12" "-Djavax.net.ssl.keyStorePassword=password" "-Djavax.net.ssl.trustStore=$PWD\springboottours\src\main\resources\truststore.p12" "-Djavax.net.ssl.trustStorePassword=password"  -jar springboottours/target/springboottours-1.0.0-SNAPSHOT.jar
 java  "-Djavax.net.ssl.keyStore=$PWD\springbootimages\src\main\resources\keystore.p12" "-Djavax.net.ssl.keyStorePassword=password" "-Djavax.net.ssl.trustStore=$PWD\springbootimages\src\main\resources\truststore.p12" "-Djavax.net.ssl.trustStorePassword=password"  -jar springbootimages/target/springbootimages-1.0.0-SNAPSHOT.jar

