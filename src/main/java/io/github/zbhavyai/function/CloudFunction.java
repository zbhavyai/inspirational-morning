package io.github.zbhavyai.function;

import com.google.cloud.functions.CloudEventsFunction;

import io.cloudevents.CloudEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named("inspirational-morning")
@ApplicationScoped
public class CloudFunction implements CloudEventsFunction {

    @Override
    public void accept(CloudEvent cloudEvent) throws Exception {
        System.out.println("Receive event Id: " + cloudEvent.getId());
        System.out.println("Receive event Subject: " + cloudEvent.getSubject());
        System.out.println("Receive event Type: " + cloudEvent.getType());
        System.out.println("Receive event Data: " + new String(cloudEvent.getData().toBytes()));
    }
}
