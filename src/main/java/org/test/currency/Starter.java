package org.test.currency;

import org.test.currency.data.source.Currency;
import org.test.currency.data.listener.PriceConsumer;
import org.test.currency.data.monitor.PriceProcessor;
import org.test.currency.data.monitor.PriceThrottler;

public class Starter {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";


    public static void main(String[] args) {

        Currency usd = new Currency("USD", 78, '$', 11000, ANSI_GREEN, false);
        Currency eur = new Currency("EUR", 98, '€', 3000, ANSI_PURPLE, false);
        PriceProcessor stockExchange = new PriceThrottler(eur,usd);

      //  new PriceConsumer("Times",31).subscribe(stockExchange);
        new PriceConsumer("CNN",7).subscribe(stockExchange);

    }
}
