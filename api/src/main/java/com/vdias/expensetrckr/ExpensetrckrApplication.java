package com.vdias.expensetrckr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application entry point.
 */
@SpringBootApplication
public class ExpensetrckrApplication {
    /**
     * Default constructor.
     */
    protected ExpensetrckrApplication() {
    }

    /**
     * Main method.
     * @param args program arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(ExpensetrckrApplication.class, args);
    }

}
