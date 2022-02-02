package org.test.currency;

import org.test.currency.data.source.Currency;
import org.test.currency.data.listener.PriceConsumer;
import org.test.currency.data.monitor.PriceMonitor;
import org.test.currency.data.monitor.PriceThrottler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Starter {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";


    public static void main(String[] args) {


        //Валюта живет своей жизнью и ей никто не требуется. В режиме verbose = true кроме курса сообщает доп. даные
        Currency usd = new Currency("USD", 78, '$', 11000, ANSI_GREEN, false);
        Currency eur = new Currency("EUR", 98, '€', 3000, ANSI_PURPLE, false);

        //Экзекьюторы которые показывают текущий курс валюты
        // ScheduledExecutorService service = Executors.newScheduledThreadPool(2);
        // service.scheduleAtFixedRate(() -> System.out.println(usd), 0, 1000, TimeUnit.MILLISECONDS);
        // service.scheduleAtFixedRate(() -> System.out.println(eur), 0, 1000, TimeUnit.MILLISECONDS);

        //"Биржа". Мониторит курс валют и оповещает о его изменнеиях подписчиков
        //В режиме verbose = true дополнительно сообщает об изменениях курсов в консоль
        PriceMonitor stockExchange = new PriceThrottler("Moscow Exchange",false, eur, usd);

        //клиенты биржи. Получают от биржи курс, при получении "думают" incomingDelay секунд, после печатают курс в консоль
        // new PriceConsumer("Times",31).subscribe(stockExchange);
        new PriceConsumer("CNN",7).subscribe(stockExchange);

    }
}
