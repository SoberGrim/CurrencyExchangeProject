package org.test.currency.data.transport;

import org.test.currency.data.listener.PriceProcessor;

public class DataPackage {
    private final String data;
    private final PriceProcessor receiver;

    public DataPackage(String data, PriceProcessor receiver) {
        this.data = data;
        this.receiver = receiver;
    }

    public String getData() {
        return data;
    }

    public PriceProcessor getReceiver() {
        return receiver;
    }
}
