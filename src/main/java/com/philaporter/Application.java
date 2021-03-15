package com.philaporter;

import com.philaporter.domain.Transaction;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Application {

    public static void main(String[] args) throws InterruptedException, TimeoutException, ExecutionException {

        for (int i = 0; i < 10; i++) {
            Transaction t = new Transaction(Integer.toString(i), 12.24 + i);
            CompletableFuture<Boolean> future = Runner.runAsync(new Transaction(Integer.toString(i), 12.24 + i));

            future.whenCompleteAsync((check, threw) -> {

                if (threw != null) {
                    System.out.println("Transaction " + t.getId() + " ::: " + threw.getMessage());
                } else {
                    System.out.println("Transaction: " + t.getId() + " was successfully processed");
                }
            });
        }

        System.out.println("Sleeping for 5 seconds and then exiting");
        Thread.sleep(5000);
    }
}
