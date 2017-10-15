package com.github.kinesit;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.types.InitializationInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ProcessRecordsInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownInput;
import com.amazonaws.services.kinesis.model.Record;
import com.github.kinesit.processor.StringRecordProcessor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class RecordProcessor<T> implements IRecordProcessor {

    private final static Logger logger = LogManager.getLogger(StringRecordProcessor.class);

    private final BlockingQueue<T> resultRecords = new LinkedBlockingQueue<>();

    @Override
    public void initialize(InitializationInput initializationInput) {
        logger.info("Initializing " + this.getClass().getSimpleName());
    }

    @Override
    public void shutdown(ShutdownInput shutdownInput) {
        logger.info("Shutting down " + this.getClass().getSimpleName());
    }

    @Override
    public void processRecords(ProcessRecordsInput processRecordsInput) {
        processRecordsInput.getRecords()
                .stream()
                .map(this::processRecord)
                .forEach(this::insertRecords);
    }

    private void insertRecords(T record) {
        try {
            resultRecords.put(record);
        } catch (InterruptedException ex) {
            logger.error(ex);
            throw new RuntimeException(ex);
        }
    }

    protected abstract T processRecord(Record record);

    BlockingQueue<T> getResultsHolder() {
        return resultRecords;
    }
}
