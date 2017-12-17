package com.github.kinesit;

public class KinesisConfigProviderImpl implements KinesisConfigProvider {

    private final String kinesisEndpoint;
    private final String dynamoDbEndpoint;
    private final String appName;
    private final String streamName;

    public KinesisConfigProviderImpl(String kinesisEndpoint,
                                     String dynamoDbEndpoint,
                                     String appName,
                                     String streamName) {
        this.kinesisEndpoint = kinesisEndpoint;
        this.dynamoDbEndpoint = dynamoDbEndpoint;
        this.appName = appName;
        this.streamName = streamName;
    }

    @Override
    public String getKinesisEndpoint() {
        return kinesisEndpoint;
    }

    @Override
    public String getDynamoDbEndpoint() {
        return dynamoDbEndpoint;
    }

    @Override
    public String getAppName() {
        return appName;
    }

    @Override
    public String getStreamName() {
        return streamName;
    }

    public Builder builder() {
        return new Builder();
    }

    public final static class Builder {

        private String kinesisEndpoint;
        private String dynamoDbEndpoint;
        private String appName;
        private String streamName;

        private Builder() {
        }

        public Builder withKinesisEndpoint(String kinesisEndpoint) {
            this.kinesisEndpoint = kinesisEndpoint;
            return this;
        }

        public Builder withDynamoDbEndpoint(String dynamoDbEndpoint) {
            this.dynamoDbEndpoint = dynamoDbEndpoint;
            return this;
        }

        public Builder withAppName(String appName) {
            this.appName = appName;
            return this;
        }

        public Builder withStreamName(String streamName) {
            this.streamName = streamName;
            return this;
        }

        public KinesisConfigProvider build() {
            return new KinesisConfigProviderImpl(kinesisEndpoint,
                                                 dynamoDbEndpoint,
                                                 appName,
                                                 streamName);
        }
    }
}
