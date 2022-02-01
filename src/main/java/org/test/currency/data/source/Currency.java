package org.test.currency.data.source;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Currency {
    private double priceInRub;
    private final String name;
    private final Character sign;
    private final String color;
    private long lastPriceUpdateTime = 0;
    private long lastPricePublishTime = 0;
    private final boolean verbose;


    public Currency(String name, int startPriceInRub, char sign, int updateRateMs, String color, boolean verbose) {
        priceInRub = startPriceInRub;
        this.name = name;
        this.sign = sign;
        this.color = color;
        this.verbose = verbose;
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(this::updatePrice, 0, updateRateMs, TimeUnit.MILLISECONDS);
    }


    private synchronized void updatePrice() {
        priceInRub += Math.random() >= 0.5 ? Math.random() / 2 : -Math.random() / 2;
        priceInRub = (double) Math.round(priceInRub * 100) / 100;
        lastPriceUpdateTime = System.currentTimeMillis();
    }


    public synchronized double getPrice() {
        return priceInRub;
    }


    @Override
    public synchronized String toString() {
        if (verbose) {
            long timePassedSincePriceUpdate = System.currentTimeMillis() - lastPriceUpdateTime;
            long timePassedSincePricePublished = System.currentTimeMillis() - lastPricePublishTime;
            lastPricePublishTime = System.currentTimeMillis();
            return String.format("%s%s\\RUB %.2f%c changed: %dms ago (last published: %dms ago)\u001B[0m", color, name, priceInRub, sign, timePassedSincePriceUpdate, timePassedSincePricePublished);
        }
        return String.format("%s%s\\RUB %.2f%c\u001B[0m", color, name, priceInRub, sign);
    }
}