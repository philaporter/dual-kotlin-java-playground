package com.philaporter;

import com.philaporter.domain.Transaction;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            KapowKt.run(new Transaction(Integer.toString(i), 12.24 + i));
        }
        Thread.sleep(10000);
    }
}
