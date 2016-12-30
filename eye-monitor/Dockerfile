FROM registry.agilepromoter.com/alpine-java:8
VOLUME /tmp
ADD target/eye-monitor.jar app.jar
ENTRYPOINT  /usr/bin/java  -Xmx128m -jar /app.jar
