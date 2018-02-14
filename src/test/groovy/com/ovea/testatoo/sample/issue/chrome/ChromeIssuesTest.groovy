package com.ovea.testatoo.sample.issue.chrome

import com.ovea.testatoo.sample.issue.WebDriverConfig
import org.junit.ClassRule
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

class ChromeIssuesTest {
    @ClassRule
    public static WebDriverConfig config = new WebDriverConfig()

    @Test
    void multiselect_should_have_expected_feature_on_click() {
        config.webDriver.get('http://localhost:8080/index.html')

        // Montreal is already selected in the html
        WebElement montreal = config.webDriver.findElement(By.id('montreal'))
        assert montreal.isSelected()

        WebElement montpellier = config.webDriver.findElement(By.id('montpellier'))
        montpellier.click()

        assert montpellier.isSelected()
        assert !montreal.isSelected()
    }
}
