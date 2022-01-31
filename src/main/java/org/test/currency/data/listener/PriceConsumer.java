package org.test.currency.data.listener;

import org.test.currency.data.monitor.PriceProcessor;

import java.util.concurrent.TimeUnit;

import static org.test.currency.utils.CLog.log;

public class PriceConsumer implements PriceListener {
    private final String name;
    private final int incomingDataDelaySec;

    public PriceConsumer(String name, int incomingDataDelaySec) {
        this.name = name;
        this.incomingDataDelaySec = incomingDataDelaySec;
    }

    @Override
    public void subscribe(PriceProcessor subscription) {
        subscription.subscribe(this);
    }

    @Override
    public void onPrice(String newPrice) {
        try {
            TimeUnit.SECONDS.sleep(incomingDataDelaySec);
            log(String.format("\u001B[43m%s\u001B[0m: %s", name, newPrice));
        } catch (InterruptedException ignore) {
            log(String.format("Subscriber '%s' onPrice processing data interrupted", name));
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
