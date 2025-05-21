package dev.vmc.smartledger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main application class for Smart Ledger.
 */
@SpringBootApplication(scanBasePackages = "dev.vmc.smartledger")
@EntityScan(basePackages = "dev.vmc.smartledger.model")
@EnableJpaRepositories(basePackages = "dev.vmc.smartledger.repository")
public class SmartLedgerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartLedgerApplication.class, args);
    }
}