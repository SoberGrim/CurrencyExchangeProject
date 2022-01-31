package org.test.currency.data.listener;

import org.test.currency.data.monitor.PriceProcessor;

public interface PriceListener {
    void subscribe(PriceProcessor subscription);
    void onPrice(String text);
    String getName();
}
