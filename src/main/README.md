Есть валюты (Currency), их цена (в эквиваленте Rub) самопроизвольно меняется во времени
с разной периодичностью. 
Текущую цену можно узнать через "currency.toString()" или "currency.getPrice()"

Есть биржа (PriceProcessor), она мониторит цены валют
и оповещает подписчиков об изменении цен через "notifySubscribers()"

Есть клиенты биржи (PriceListener), которые подписались на обновления курсов валют на бирже
через PriceProcessor.subscribe(). Клиенты получают оповещения от биржи через метод
"PriceListener.onPrice()". Метод .onPrice() имеет разное по длительности время выполнения.

Биржа шлет оповещинея клиентам в виде "писем" (DataPackage), у которых 
есть поле "данные" (курс валюты) и поле "получатель" (PriceListener)

Письма от биржи к клиентам доставляют курьеры (PackageSenderService) 
На каждого клиента - по одному курьеру. У курьера есть только один метод - "доставить"
(send()).

/**
 * You have to write a PriceThrottler class which will implement the following requirements:
 * 1) Implement PriceProcessor interface
 * 2) Distribute updates to its listeners which are added through subscribe() and
 * removed through unsubscribe()
 * 3) Some subscribers are very fast (i.e. onPrice() for them will only take a microsecond) and some are very slow
 * (onPrice() might take 30 minutes). Imagine that some subscribers might be showing a price on a screen and some
 * might be printing them on a paper
 * 4) Some ccyPairs change rates 100 times a second and some only once or two times a day
 * 5) ONLY LAST PRICE for each ccyPair matters for subscribers. I.e. if a slow subscriber is not coping
 * with updates for EURUSD - it is only important to deliver the latest rate
 * 6) It is important not to miss rarely changing prices. I.e. it is important to deliver EURRUB if it ticks once
 * per day but you may skip some EURUSD ticking every second
 * 7) You don't know in advance which ccyPair are frequent and which are rare. Some might become more frequent
 * at different time of a day
 * 8) You don't know in advance which of the subscribers are slow and which are fast.
 * 9) Slow subscribers should not impact fast subscribers
 *
 * In short words the purpose of PriceThrottler is to solve for slow consumers
 *
 */
