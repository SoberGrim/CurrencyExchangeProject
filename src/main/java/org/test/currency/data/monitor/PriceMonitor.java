package org.test.currency.data.monitor;

import org.test.currency.data.source.Currency;
import org.test.currency.data.listener.PriceProcessor;

public interface PriceMonitor {
    void getActualPrice(Currency currency);
    void notifySubscribers();
    void subscribe(PriceProcessor subscriber);
    void unsubscribe(PriceProcessor subscriber);
}
