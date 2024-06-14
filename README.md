# Inspirational Morning

Send a good morning message to your Google Chat webhook. The message would include the day of the week, a quote, and the author of the quote. Quote and its author is fetched using [ZenQuotes API](https://zenquotes.io/).

## Running dev mode

You can run your application in dev mode that enables live coding using below. Dev UI should be accessible at [http://localhost:3005/q/dev-ui/](http://localhost:3005/q/dev-ui/).

```shell
./mvnw compile quarkus:dev
```

## Packaging and running

The application can be packaged and resulting JAR can be run using

```shell
./mvnw clean package -DskipTests
java -jar target/inspirational-morning-*.jar
```

## Reference guides

- [Quarkus extension for GCloud Functions](https://quarkus.io/guides/gcp-functions)

- [Scheduling function](https://cloud.google.com/scheduler/docs/tut-gcf-pub-sub)
