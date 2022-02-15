package com.one.framework;

import org.littleshoot.proxy.HttpFiltersSource;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.io.IOException;
import java.net.*;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class WebDriverConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverConfig.class);

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public WebDriverFactory webDriverFactory(
            @Value("${webdriver.remote:false}") boolean remoteDriver,
            @Value("${webdriver.remote.url:http://computer}") URL remoteUrl) {
        return new WebDriverFactory(remoteDriver, remoteUrl);
    }

    @Bean
    public WebDriverCleaner webDriverCleaner() {
        return new WebDriverCleaner();
    }

    @Bean
    public DesiredCapabilities desiredCapabilities(@Value("${webdriver.capabilities.browserName:chrome}") String browserName,
                                                   @Value("${webdriver.capabilities.version:}") String version,
                                                   @Value("${webdriver.capabilities.build:}") String buildName,
                                                   @Value("${browserStack.enabled:false}") boolean browserStackEnabled) throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
        capabilities.setCapability("build", buildName);
        capabilities.setCapability("project", "UITestingFramework");
        capabilities.setCapability("name", System.getProperties().getProperty("test.suite.name"));

        capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

        populateCapabilities(capabilities);

        LOGGER.info("capabilities = {}", capabilities);

        return capabilities;
    }

    private void populateCapabilities(DesiredCapabilities capabilities) {
        String prefix = "webdriver.capabilities.";

        Map<String, String> map = System.getProperties().entrySet().stream()
                .map(e -> new AbstractMap.SimpleImmutableEntry<>(
                        String.valueOf(e.getKey()), String.valueOf(e.getValue())))
                .filter(e -> e.getKey().startsWith(prefix))
                .map(e -> new AbstractMap.SimpleImmutableEntry<>(
                        e.getKey().substring(prefix.length()), e.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        new MapFactory().create(map).forEach(capabilities::setCapability);
    }


    @Bean
    @Primary
    @Scope("prototype")
    public WebDriver webDriver(WebDriverCleaner webDriverCleaner,
                               @Qualifier("dirtyWebDriver") WebDriver driver) {
        return webDriverCleaner.cleanWebDriver(driver);
    }

    @Bean(destroyMethod = "quit")
    @Lazy
    public WebDriver dirtyWebDriver(WebDriverFactory webDriverFactory,
                                    DesiredCapabilities desiredCapabilities,
                                    URI baseUrl) throws IOException {
        return webDriverFactory.webDriver(desiredCapabilities, baseUrl);
    }

    @Primary
    @Bean
    public URI baseUrl(@Value("${webdriver.baseUrl:http://auto}") URI value)
            throws UnknownHostException {
        if (value.equals(URI.create("http://auto"))) {
            return URI.create("http://" + InetAddress.getLocalHost().getHostAddress() + ":8080");
        }
        return value;
    }


}
