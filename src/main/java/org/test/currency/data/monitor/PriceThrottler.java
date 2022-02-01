package org.test.currency.data.monitor;

import org.test.currency.data.source.Currency;
import org.test.currency.data.transport.DataPackage;
import org.test.currency.data.listener.PriceProcessor;
import org.test.currency.data.transport.PackageSenderAgent;
import org.test.currency.data.transport.PackageSenderService;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.*;

import static org.test.currency.utils.CLog.log;
import static org.test.currency.utils.Color.*;

public class PriceThrottler implements PriceMonitor {
    private final Currency[] currencies;
    private final ConcurrentHashMap<Currency, Double> savedCurrencyRates = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<PriceProcessor, PackageSenderService> subscriberService = new ConcurrentHashMap<>();
    private final boolean verbose;
    private final String name;

    public PriceThrottler(String name, boolean verbose, Currency... currency) {
        currencies = currency;
        this.name = name;
        this.verbose = verbose;
        ScheduledExecutorService currencyRateUpdateService = Executors.newScheduledThreadPool(currencies.length);
        for (Currency curr : currencies) {
            savedCurrencyRates.put(curr, curr.getPrice());
            currencyRateUpdateService.scheduleAtFixedRate(() -> getActualPrice(curr), 0, 1, TimeUnit.MILLISECONDS);
        }
    }


    @Override
    public synchronized void subscribe(PriceProcessor subscriber) {
        subscriberService.put(subscriber, new PackageSenderAgent("Courier for [" + subscriber.getName() + "]"));
    }

    @Override
    public synchronized void unsubscribe(PriceProcessor subscriber) {
        subscriberService.remove(subscriber);
    }

    @Override
    public synchronized void notifySubscribers() {
        for (Map.Entry<PriceProcessor, PackageSenderService> node : subscriberService.entrySet()) {
            node.getValue().send(new DataPackage(Arrays.toString(currencies), node.getKey()));
        }
    }

    @Override
    public synchronized void getActualPrice(Currency currency) {
        double actualPrice = currency.getPrice();
        if (savedCurrencyRates.get(currency) != actualPrice) {
            savedCurrencyRates.put(currency, actualPrice);
            notifySubscribers();
            if (verbose) {
                log(String.format("[%s] publishes update: %s", textYellow(name), currency));
            }
        }
    }
}
