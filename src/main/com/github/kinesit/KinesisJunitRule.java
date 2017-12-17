package com.github.kinesit;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.amazonaws.services.kinesis.metrics.impl.NullMetricsFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.rules.ExternalResource;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class KinesisJunitRule<T> extends ExternalResource {

    static {
        System.setProperty("AWS_CBOR_DISABLE", "true");
    }

    private final static Logger logger = LogManager.getLogger(KinesisJunitRule.class);

    private static final String WORKER_ID = UUID.randomUUID().toString();
    private static final NullMetricsFactory NULL_METRICS_FACTORY = new NullMetricsFactory();
    private final AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();

    private final KinesisConfigProvider configProvider;
    private final RecordProcessor<T> recordProcessor;
    private Worker worker;

    public KinesisJunitRule(KinesisConfigProvider configProvider,
                            RecordProcessor<T> recordProcessor) {
        this.configProvider = configProvider;
        this.recordProcessor = recordProcessor;
    }

    @Override
    protected void before() throws Throwable {

        KinesisClientLibConfiguration config = new KinesisClientLibConfiguration(configProvider.getAppName(),
                                                                                 configProvider.getStreamName(),
                                                                                 credentialsProvider,
                                                                                 WORKER_ID)
                .withInitialPositionInStream(InitialPositionInStream.TRIM_HORIZON)
                .withKinesisEndpoint(configProvider.getKinesisEndpoint())
                .withDynamoDBEndpoint(configProvider.getDynamoDbEndpoint());

        worker = new Worker.Builder()
                .recordProcessorFactory(() -> recordProcessor).config(config)
                .metricsFactory(NULL_METRICS_FACTORY).build();

        worker.run();
    }

    public T getRecord() {
        return recordProcessor.getResultsHolder().poll();
    }

    public T waitForRecord() {
        try {
            return recordProcessor.getResultsHolder().take();
        } catch (InterruptedException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    public T waitForRecord(long timeMilliSeconds) {
        try {
            return recordProcessor.getResultsHolder().poll(timeMilliSeconds, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void after() {
        worker.shutdown();
        super.after();
    }
}
