package org.test.currency.data.listener;

import org.test.currency.data.monitor.PriceMonitor;

public interface PriceProcessor {
    void subscribe(PriceMonitor subscription);
    void onPrice(String text);
    String getName();
}
