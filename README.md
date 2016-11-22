# eYe: Monitoramento e Métricas de Funcionalidade/Software

eYe é um servidor e uma biblioteca para possibilitar a execusão e monitoramento de funcionalidades em realtime.

## Communication

- [GitHub](https://github.com/marcelosv/eye)
- [Linkedin](https://www.linkedin.com/in/marcelo-souza-vieira-112174a9)
- [Twitter](https://twitter.com/uaicelo)


## What does it do?

#### 1) Monitoramento

Monitorar um sistema e ter métricas é muito importante para analisar individualmente ou em conjunto as informações e poder ter decisões estratêgicas.

#### 2) Realtime

Com o eYe pode ser visto em tempo real as métricas de quantidade de acessos, exceptions, médias de execusão, etc...

#### 3) Integrando ao seu sistema

A integração é bem simples, e pode ser recolhido métricas de quaisquer método usando a anotação que o eYe define.

#### 1) Binaries

Adicione esta dependência em seu sistema.

```xml
<dependency>
    <groupId>br.com.eye</groupId>
    <artifactId>eye</artifactId>
    <version>2016.11.1.1-SNAPSHOT</version>
</dependency>
```
#### 2) Configuração
No application.properties adicine as chaves abaixo. A principal é "eye.url" que deve ser o local onde o servidor do eYe vai estar.

```
spring.application.name=nome-sistema
spring.application.version=0.0.0.1
eye.url=http://localhost:8181
```

#### 3) Anotações

Code to be isolated is wrapped inside the run() method of a HystrixCommand similar to the following:

```java
    @Sensor(description="Name", tags="test", type=TypesData.API_ENDPOINT)
    @RequestMapping("/exemple")
    public void testar() {
        System.out.println("Hello...");
    }
```
A anotação @Sensor é a principal e deve ser usada em todos os método que deseja ser monitorado. 
Pode ser monitorado endpoints, service, repositorys, etc...

## Testar

#### 1) Redis.
Inicie o Redis. O servidor utiliza o redis para gravar e organizar as métricas.

#### 2) Primeiro suba o servidor do eYe.
```
java -jar eye-monitor.jar
```

Acesse o link para abrir o dashboard.
```
http://localhost:8181/
```

#### 3) Seu sistema

Inicie seu sistema e execute ele normalmente. 
O sistema vai começar a registrar as métricas no servidor.


<img src="eye.png">