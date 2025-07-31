package com.assignment;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class TrackXpath {
    private static final String LOG_FILE = "xpath_results.txt";
    private static final String XPATH_FILE = "src/main/java/com/assignment/xpaths.txt";
    private static final String PAGE_URL = "https://support.mozilla.org/en-US/questions/firefox?show=all";
    private static final String HIGHLIGHT_CLASS = "highlighted-element";
    private static final String HIGHLIGHT_STYLE = "3px solid red";
    private static final int WAIT_TIME_MS = 4000;

    public static void main(String[] args) throws Exception {
        boolean headless = Arrays.asList(args).contains("--headless");
        WebDriver driver;
        if (headless) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            driver = new ChromeDriver(options);
        } else {
            driver = new ChromeDriver(); // regular browser window
            driver.manage().window().maximize();
        }


        driver.get(PAGE_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));


        List<String> xpaths = loadXpathsFromFile(XPATH_FILE);

        // Clear log file if it exists
        Files.write(Paths.get(LOG_FILE), new byte[0], StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        for (String xpath : xpaths) {
            if (xpath.trim().isEmpty()) continue;

            String status;
            try {
                clearHighlights(driver);
                System.out.println("Trying XPath: " + xpath);
                status = addHighlightToElement(driver, xpath);

                if ("NO_MATCH".equals(status)) {
                    System.out.println("No elements matched: " + xpath +"\n");
                    writeLog(xpath, "NO MATCH");
                } else {
                    System.out.println("Highlighted successfully!\n");
                    writeLog(xpath, "OK");
                }
            } catch (Exception e) {
                System.out.println("Failed: " + xpath);
                writeLog(xpath, "ERROR");
            }

            Thread.sleep(WAIT_TIME_MS); // Wait 4 seconds to see the highlight
        }

        driver.quit();
        System.out.println("\nLog saved to " + LOG_FILE);
    }

    public static List<String> loadXpathsFromFile(String path) throws IOException {
        return Files.readAllLines(Paths.get(path));
    }

    public static String addHighlightToElement(WebDriver driver, String xpath) {
        String script = String.format("""
        var xpath = arguments[0];
        var elements = document.evaluate(xpath, document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);

        if (elements.snapshotLength === 0) {
            return 'NO_MATCH';
        }

        for (var i = 0; i < elements.snapshotLength; i++) {
            var node = elements.snapshotItem(i);
            var el = (node.nodeType === Node.TEXT_NODE) ? node.parentElement : node;

            if (!el || el.nodeType !== Node.ELEMENT_NODE) continue;

            el.style.outline = '%s';
            el.classList.add('%s');
            el.scrollIntoView({behavior: 'smooth', block: 'center'});
        }

        return 'OK';
        """, HIGHLIGHT_STYLE, HIGHLIGHT_CLASS);

        return (String) ((JavascriptExecutor) driver).executeScript(script, xpath);
    }

    public static void clearHighlights(WebDriver driver) {
        String script = String.format("""
        var elements = document.querySelectorAll('.%s');
        elements.forEach(el => {
            el.style.outline = '';
            el.classList.remove('%s');
        });
        """, HIGHLIGHT_CLASS, HIGHLIGHT_CLASS);
        ((JavascriptExecutor) driver).executeScript(script);
    }

    public static void writeLog(String xpath, String result) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logLine = String.format("[%s] %s --> %s%n", timestamp, xpath, result);
        try {
            Files.write(Paths.get(LOG_FILE), logLine.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println("Failed to write log: " + e.getMessage());
        }
    }
}