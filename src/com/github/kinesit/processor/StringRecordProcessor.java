package com.github.kinesit.processor;

import com.amazonaws.services.kinesis.model.Record;
import com.github.kinesit.RecordProcessor;

public class StringRecordProcessor extends RecordProcessor<String> {

    protected String processRecord(Record record) {
        return new String(record.getData().array());
    }
}
