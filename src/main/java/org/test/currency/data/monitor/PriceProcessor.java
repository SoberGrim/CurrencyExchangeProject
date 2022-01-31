package org.test.currency.data.monitor;

import org.test.currency.data.source.Currency;
import org.test.currency.data.listener.PriceListener;

public interface PriceProcessor {
    void getActualPrice(Currency currency);
    void notifySubscribers();
    void subscribe(PriceListener subscriber);
    void unsubscribe(PriceListener subscriber);
}
