# Inspirational Morning

Send a good morning message to your Google Chat webhook. The message would include the day of the week, a quote, and the author of the quote. Quote and its author is fetched using [ZenQuotes API](https://zenquotes.io/).

> [!NOTE]
> Google Chat webhook only supports Google Workspace accounts.

## Local dev

1. Get the desired GChat webhook URL. You can register a webhook by following the [Google Chat webhooks documentation](https://developers.google.com/workspace/chat/quickstart/webhooks#register-webhook).

2. Save the webhook URL, and optionally a [time zone](https://en.wikipedia.org/wiki/List_of_tz_database_time_zones), in the [.env](./.env) file in the root of the project. For example:

   ```env
   GSPACE_WEBHOOK=https://chat.googleapis.com/v1/spaces/SPACE_ID/messages?key=KEY&token=TOKEN
   TIMEZONE=Pacific/Midway
   ```

3. You may run the application in dev mode,

   ```shell
   make dev
   ```

   Or in a container using

   ```shell
   make container-run
   ```

4. Once the container is running, hit the exposed REST endpoint to send the greeting.

   ```shell
   curl --silent --request POST --location http://localhost:3005/api/greet | jq
   ```

5. To clean up, stop and remove the container and the image.

   ```shell
   make container-destroy
   ```

## Deploy to Google Cloud Functions

1. Build the application. This will create a deployable JAR file in the `target/deployment` directory.

   ```shell
   make build
   ```

2. Create up a Pub/Sub topic.

   ```shell
   gcloud pubsub topics create topic-inspirational-morning
   ```

3. Create a cron job schedule for every weekday at 08:00.

   ```shell
   gcloud scheduler jobs create pubsub schedule-job-inspirational-morning \
      --schedule="0 8 * * 1-5" \
      --topic=topic-inspirational-morning \
      --message-body="job is triggered" \
      --time-zone="Pacific/Auckland" \
      --location="us-central1"
   ```

4. Deploy the application as a Cloud Function

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
      --set-env-vars=TIMEZONE="Pacific/Auckland",GSPACE_WEBHOOK="https://chat.googleapis.com/v1/spaces/SPACE_ID/messages?key=KEY&token=TOKEN"
   ```

5. [OPTIONAL] Trigger the job manually

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

-  [tz database Time Zones](https://en.wikipedia.org/wiki/List_of_tz_database_time_zones)
-  [Creating Google Chat webhook](https://developers.google.com/workspace/chat/quickstart/webhooks#register-webhook)
