package com.github.kinesit;

public interface KinesisConfigProvider {
    String getKinesisEndpoint();

    String getDynamoDbEndpoint();

    String getAppName();

    String getStreamName();
}
