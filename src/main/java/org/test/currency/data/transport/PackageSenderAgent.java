package org.test.currency.data.transport;

import java.util.concurrent.*;

import static org.test.currency.utils.CLog.log;
import static org.test.currency.utils.Color.*;

public class PackageSenderAgent implements PackageSenderService {
    private final String name;
    private DataPackage sending;
    private DataPackage waitingToSend;
    private CompletableFuture<Void> senderAgent;
    private CompletableFuture<Void> senderManager;


    public PackageSenderAgent(String name) {
        this.name = name;
        senderAgent = senderManager = CompletableFuture.supplyAsync(() -> null);
    }


    public synchronized void send(DataPackage data) {
        log(waitingToSend != null ?
                name + " current pending package " + textBlue("updated ") + waitingToSend.getData() + " -> " + data.getData() :
                name + textBlue(" received") + " new package to be sent: " + data.getData());
        waitingToSend = data;

        if (senderAgent.isDone()) {
            sending = waitingToSend;
            waitingToSend = null;
            log(name + textYellow(" started") + " sending package: " + sending.getData());
            senderAgent = CompletableFuture.runAsync(() -> sending.getReceiver().onPrice(sending.getData()));
        } else {
            if (senderManager.isDone()) {
                log(name + " is busy, " + textCyan("created manager") + " to control delivery of next package");
                senderManager = CompletableFuture.runAsync(() -> {
                    while (!senderAgent.isDone()) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(10);
                        } catch (InterruptedException ignore) {
                        }
                    }
                    sending = waitingToSend;
                    waitingToSend = null;
                    log(textCyan("Manager told") + name + " to deliver " + textYellow("next package ") + sending.getData());
                    senderAgent = CompletableFuture.runAsync(() -> sending.getReceiver().onPrice(sending.getData()));
                });
            } else {
                log(name + " is busy, manager is " + textCyan("waiting") + " him to deliver " + sending.getData());
            }
        }
    }


}
