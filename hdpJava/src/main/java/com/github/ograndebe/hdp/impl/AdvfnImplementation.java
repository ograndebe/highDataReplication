package com.github.ograndebe.hdp.impl;

import com.github.ograndebe.hdp.RedisNotificator;
import com.github.ograndebe.hdp.SeleniumWatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AdvfnImplementation {

    @Autowired
    private SeleniumWatcher seleniumWatcher;

    @Autowired
    private RedisNotificator redisNotificator;


    @PostConstruct
    public void init() {
        seleniumWatcher.init("advfn",webDriver -> {
            final String startPoint = "https://br.advfn.com/";
            webDriver.get(startPoint);
        });
        seleniumWatcher.addObserver(redisNotificator);
    }


    @Scheduled(cron = "${cron.advfn}")
    public void next() {
        seleniumWatcher.nextTick();
    }

}
