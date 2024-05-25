package com.pgmanagement.application.scheduler;


import com.pgmanagement.application.service.RentCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class RentCollectionScheduler {

    @Autowired
    RentCollectionService rentCollectionService;

    @Scheduled(cron = "0 */2 * * * *")
    public void performTask() {
        System.out.println("Invoked Scheduler");
        rentCollectionService.addRentReminder();
    }
}
