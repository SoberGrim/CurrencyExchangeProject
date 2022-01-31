package org.test.currency.data.transport;

import org.test.currency.data.listener.PriceListener;

public class DataPackage {
    private final String data;
    private final PriceListener receiver;

    public DataPackage(String data, PriceListener receiver) {
        this.data = data;
        this.receiver = receiver;
    }

    public String getData() {
        return data;
    }

    public PriceListener getReceiver() {
        return receiver;
    }
}
