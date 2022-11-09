package test;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeleniumUtilities {
    private final static int DEFAULT_TIMEOUT_SECONDS = 30;
    private WebDriverWait webDriverWait;

    public SeleniumUtilities(RemoteWebDriver driver) {
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS));
    }

    public void waitUntilElementIsClickableAndPerformClick(By strategy) {
        boolean isSuccesses = false;
        for (int i = 0; i < 5 && !isSuccesses; i++) {
            try {
                webDriverWait.until(ExpectedConditions.elementToBeClickable(strategy)).click();
                isSuccesses = true;
            } catch (Exception e) {
                try {
                    Thread.sleep(500);
                } catch (Exception e2) {

                }
            }
        }
    }

    public void waitUntilElementIsVisible(By strategy) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(strategy));
    }

    public void waitUntilElementIsInvisible(By strategy) {
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(strategy));
    }
}
