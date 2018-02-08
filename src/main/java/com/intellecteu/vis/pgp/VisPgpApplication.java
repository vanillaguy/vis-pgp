package com.intellecteu.vis.pgp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Application
 * 
 * @author sergiipozharov
 */
@SpringBootApplication
public class VisPgpApplication {

    /**
     * A main method to start this application.
     */
    public static void main(String[] args) {
        // TODO: Implement something strong
        System.setProperty("jasypt.encryptor.password", "Abcd1234Abcd1234");
        SpringApplication.run(VisPgpApplication.class, args);
    }

}
