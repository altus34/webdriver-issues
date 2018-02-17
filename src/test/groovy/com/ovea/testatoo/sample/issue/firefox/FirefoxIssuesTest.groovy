package com.ovea.testatoo.sample.issue.firefox

import com.ovea.testatoo.sample.issue.WebDriverConfig
import org.junit.ClassRule
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
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

        new Actions(config.webDriver)
                .keyDown(input, Keys.SHIFT)
                .sendKeys(input, 'value')
                .keyUp(input, Keys.SHIFT)
                .perform()

        assert input.getAttribute('value') == 'VALUE'
    }

    @Test
    void single_select_should_have_expected_feature_on_click_with_action() {
        // https://github.com/mozilla/geckodriver/issues/1176
        config.webDriver.get('http://localhost:8080/index.html')

        WebElement ubuntu = config.webDriver.findElement(By.id('ubuntu'))
        assert !ubuntu.isSelected()

        new Actions(config.webDriver)
                .click(ubuntu)
                .perform()

        assert ubuntu.isSelected()
    }

    @Test
    void double_click_should_have_expected_feature_on_click_with_action() {
        config.webDriver.get('http://localhost:8080/index.html')

        WebElement button = config.webDriver.findElement(By.id('button_1'))

        assert button.getAttribute('value') == 'Button'

        new Actions(config.webDriver)
                .doubleClick(button)
                .perform()

        assert button.getAttribute('value') == 'Button Double Clicked!'

    }
}
