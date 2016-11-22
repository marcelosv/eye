# eYe: Monitoring and Software Metrics

eYe is a simple application that enables the realtime monitoring of Spring Boot software.

## How important is monitoring software?

The relevance of a software monitoring is to identify strategic points of most used functionalities, identify bottlenecks and anticipate possible errors, thus adding value to the product.

## Requirements
    the system that will be integrated must be development in Spring Boot

## Structure
    - eye-monitor: Server developed in Spring boot with the dashboard in Angularjs. This server receives and organizes all metrics;
    - eye: Integration library where it should be added as a dependency in the software where you want to collect the metrics;

## Communication

- [GitHub](https://github.com/marcelosv/eye)
- [Linkedin](https://www.linkedin.com/in/marcelo-souza-vieira-112174a9)
- [Twitter](https://twitter.com/uaicelo)

## Binaries

Add this dependency in your software.

```xml
<dependency>
    <groupId>br.com.eye</groupId>
    <artifactId>eye</artifactId>
    <version>2016.11.1.1-SNAPSHOT</version>
</dependency>
```

<img src="../eye.png">