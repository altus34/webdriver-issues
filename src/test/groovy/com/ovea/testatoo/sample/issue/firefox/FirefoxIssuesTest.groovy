package com.ovea.testatoo.sample.issue.firefox

import com.ovea.testatoo.sample.issue.WebDriverConfig
import org.junit.ClassRule
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Action
import org.openqa.selenium.interactions.Actions

class FirefoxIssuesTest {
    @ClassRule
    public static WebDriverConfig config = new WebDriverConfig()

    @Test
    void textfield_should_have_expected_behaviours_on_key_modifier() {
        // https://github.com/mozilla/geckodriver/issues/646
        config.webDriver.get('http://localhost:8080/index.html')

        WebElement input =  config.webDriver.findElement(By.id('textfield'))
        input.click()

        Actions actions = new Actions(config.webDriver)
        Action action = actions
                .keyDown(input, Keys.SHIFT)
                .sendKeys(input, 'value')
                .keyUp(input, Keys.SHIFT)
                .build()

        action.perform()

        assert input.getAttribute('value') == 'VALUE'

    }

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
