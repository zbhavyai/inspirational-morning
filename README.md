# Inspirational Morning

Send a good morning message to your Google Chat webhook. The message would include the day of the week, a quote, and the author of the quote. Quote and its author is fetched using [ZenQuotes API](https://zenquotes.io/).

## Running dev mode

You can run your application in dev mode that enables live coding using below. Dev UI should be accessible at [http://localhost:3005/q/dev-ui/](http://localhost:3005/q/dev-ui/).

```shell
./mvnw compile quarkus:dev
```

## Packaging and running

1. Create the JAR

   ```shell
   ./mvnw clean package -DskipTests
   ```

2. Run the JAR with specific GChat webhook URL and a time zone [optional]. By default, the the `America/Edmonton` time zone is used.

   ```shell
   java -Dzoneid="Pacific/Auckland" -Dgspace.webhook="<GCHAT-WEBHOOK-URL>" -jar target/inspirational-morning-*.jar
   ```

3. Once the JAR is running, hit the exposed ReST endpoint to send the greeting

   ```shell
   curl --silent --request POST --location http://localhost:3005/api/greet | jq
   ```

## Deploy to Google Cloud Functions

1. Create up a Pub/Sub topic.

   ```shell
   gcloud pubsub topics create topic-inspirational-morning
   ```

2. Create a cron job schedule for every weekday at 08:00.

   ```shell
   gcloud scheduler jobs create pubsub schedule-job-inspirational-morning \
      --schedule="0 8 * * 0-5" \
      --topic=topic-inspirational-morning \
      --message-body="job is triggered" \
      --time-zone="Pacific/Auckland" \
      --location="us-central1"
   ```

3. Deploy the application as a Cloud Function

   ```shell
   gcloud functions deploy inspirational-morning \
      --gen2 \
      --allow-unauthenticated \
      --trigger-topic=topic-inspirational-morning \
      --region=us-central1 \
      --timeout=540s \
      --entry-point=io.quarkus.gcp.functions.QuarkusCloudEventsFunction \
      --runtime=java17 \
      --memory=256MiB \
      --cpu=0.167 \
      --source=target/deployment \
      --set-env-vars=ZONEID="Pacific/Auckland",GSPACE_WEBHOOK="https://chat.googleapis.com/v1/spaces/SPACE_ID/messages?key=KEY&token=TOKEN"
   ```

4. [OPTIONAL] Trigger the job manually

   ```shell
   gcloud scheduler jobs run schedule-job-inspirational-morning --location="us-central1"
   ```

## Google Cloud Clean up

1. Delete the function

   ```shell
   gcloud functions delete inspirational-morning --region=us-central1
   ```

2. Delete the job schedule

   ```shell
   gcloud scheduler jobs delete schedule-job-inspirational-morning --location=us-central1
   ```

3. Delete the topic

   ```shell
   gcloud pubsub topics delete topic-inspirational-morning
   ```

## Reference guides

- [tz database Time Zones](https://en.wikipedia.org/wiki/List_of_tz_database_time_zones)
