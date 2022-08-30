package com.gaoice.distributed.scheduling;

import com.gaoice.distributed.scheduling.annotation.EnableDistributedScheduling;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@EnableDistributedScheduling
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Scheduled(cron = "0/1 * * * * ?")
    public void test() {
        System.out.println("Scheduled run");
    }
}
