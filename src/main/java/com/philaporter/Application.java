package com.philaporter;

import com.philaporter.domain.Account;
import com.philaporter.domain.Transaction;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Application {

    public static void main(String[] args) throws InterruptedException, TimeoutException, ExecutionException {

        // Create our data store
        ConcurrentHashMap<String, Account> chm = new ConcurrentHashMap<>();

        // Setup test accounts for us to lock, unlock, and transact upon
        String[] accounts = new String[5];
        for (int i = 0; i < 5; i++) {
            accounts[i] = "10" + i;
            chm.put(accounts[i], new Account(accounts[i], 0, new AtomicBoolean(false)));
        }

        // Simulate incoming transactions
        for (int i = 0, ai = 0; i < 10000; i++, ai++) {

            // Make each account id predictable w/ round robin assignment
            if (ai > 4) ai = 0;

            // Make each spend predictable
            double spend = i + 1.25;

            // Test transaction
            Transaction t = new Transaction(Integer.toString(i), accounts[ai], spend);

            // Imagine this function is exposed via a library
            CompletableFuture<Boolean> future = Runner.runAsync(t, chm);

            // This would be handled by the implementing application
            future.whenCompleteAsync((check, threw) -> {
                if (threw != null) {
                    System.out.println("Transaction " + t.getId() + " ::: " + threw.getMessage());
                } else {
                    System.out.println("Transaction: " + t.getId() + " was successfully processed");
                }
            });
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("Graceful shutdown started; letting in flight transactions process");
                Thread.sleep(10000);
                System.out.println("===============================================");
                System.out.println("Check the totals:");
                chm.forEachValue(4, System.out::println);
                System.out.println("===============================================");
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }));
    }
}