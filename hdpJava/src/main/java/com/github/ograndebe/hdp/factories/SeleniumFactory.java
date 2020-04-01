package com.github.ograndebe.hdp.factories;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumFactory {

    @Value("${server.selenium}")
    private String seleniumServer;

    @Bean
    public WebDriver webDriver() {
        if (seleniumServer == null || StringUtils.isEmpty(seleniumServer)) {
            final FirefoxOptions options = new FirefoxOptions()
                    .setProfile(new FirefoxProfile());
            final WebDriver driver = new FirefoxDriver(options);
            return driver;
        }
        return RemoteWebDriver.builder()
                .url(seleniumServer)
                .oneOf(new ChromeOptions())
                .build();
    }
}
