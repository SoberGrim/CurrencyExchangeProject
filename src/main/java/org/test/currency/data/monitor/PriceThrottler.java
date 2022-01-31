package org.test.currency.data.monitor;

import org.test.currency.data.source.Currency;
import org.test.currency.data.transport.DataPackage;
import org.test.currency.data.listener.PriceListener;
import org.test.currency.data.transport.PackageSenderAgent;
import org.test.currency.data.transport.PackageSenderService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class PriceThrottler implements PriceProcessor {
    private final Currency[] currencies;
    private final ConcurrentHashMap<Currency, Double> savedCurrencyRates = new ConcurrentHashMap<>();
    private final HashMap<PriceListener, PackageSenderService> subscriberService = new HashMap<>();

    public PriceThrottler(Currency... currency) {
        currencies = currency;
        ScheduledExecutorService currencyRateUpdateService = Executors.newScheduledThreadPool(currencies.length);
        for (Currency curr : currencies) {
            savedCurrencyRates.put(curr, curr.getPrice());
            currencyRateUpdateService.scheduleAtFixedRate(() -> getActualPrice(curr), 0, 1, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public synchronized void subscribe(PriceListener subscriber) {
        subscriberService.put(subscriber, new PackageSenderAgent("Courier for [" + subscriber.getName() + "]"));
    }

    @Override
    public synchronized void unsubscribe(PriceListener subscriber) {
        subscriberService.remove(subscriber);
    }

    @Override
    public synchronized void notifySubscribers() {
        for (Map.Entry<PriceListener, PackageSenderService> node : subscriberService.entrySet()) {
            node.getValue().send(new DataPackage(Arrays.toString(currencies), node.getKey()));
        }
    }

    @Override
    public synchronized void getActualPrice(Currency currency) {
        double actualPrice = currency.getPrice();
        if (savedCurrencyRates.get(currency) != actualPrice) {
            savedCurrencyRates.put(currency, actualPrice);
            notifySubscribers();
        }
    }
}
