package com.github.ograndebe.hdp;

import com.github.ograndebe.hdp.observer.PageObserver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@Component
public final class SeleniumWatcher {
    private String pageId;
    private List<PageObserver> observers = new LinkedList<>();

    @Autowired
    private WebDriver driver;

    @Value("${server.selenium}")
    private String seleniumServer;

    public void init(String pageId, Consumer<WebDriver> initializer) {
        this.pageId = pageId;
        initializer.accept(driver);
    }

    public void nextTick() {
        if (driver == null) throw new RuntimeException("Watcher not initialized");
        final String pageSource = driver.getPageSource();
        notifyObservers(pageSource);
    }

    public void addObserver(PageObserver pageObserver){
        observers.add(pageObserver);
    }

    protected void notifyObservers(String pageSource) {
        observers.forEach(pageObserver -> pageObserver.pageChanged(pageId, pageSource));
    }

}
