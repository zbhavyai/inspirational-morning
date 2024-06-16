# Inspirational Morning

Send a good morning message to your Google Chat webhook. The message would include the day of the week, a quote, and the author of the quote. Quote and its author is fetched using [ZenQuotes API](https://zenquotes.io/).

## Running dev mode

You can run your application in dev mode that enables live coding using below. Dev UI should be accessible at [http://localhost:3005/q/dev-ui/](http://localhost:3005/q/dev-ui/).

```shell
./mvnw compile quarkus:dev
```

## Packaging and running

Create the JAR

```shell
./mvnw clean package -DskipTests
```

Run the JAR with specific GChat webhook URL and a time zone [optional]. By default, the the `America/Edmonton` time zone is used.

```shell
java -Dzoneid="Pacific/Auckland" -Dgspace.webhook="<GCHAT-WEBHOOK-URL> -jar target/inspirational-morning-*.jar"
```

Once the JAR is running, hit the exposed ReST endpoint to send the greeting

```shell
curl --silent --request POST --location http://localhost:3005/api/greet | jq
```

## Reference guides

- [tz database Time Zones](https://en.wikipedia.org/wiki/List_of_tz_database_time_zones)

- [Quarkus extension for GCloud Functions](https://quarkus.io/guides/gcp-functions)

- [Scheduling function](https://cloud.google.com/scheduler/docs/tut-gcf-pub-sub)
