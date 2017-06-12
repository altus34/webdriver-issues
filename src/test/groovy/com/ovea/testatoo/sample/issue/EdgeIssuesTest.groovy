package com.ovea.testatoo.sample.issue

import com.ovea.testatoo.sample.WebDriverConfig
import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
@RunWith(JUnit4)
class EdgeIssuesTest {
    @ClassRule
    public static WebDriverConfig config = new WebDriverConfig()

    @BeforeClass
    static void before() {
        config.webDriver.get('http://localhost:8080/index.html')
    }

    @Test
    void checkbox_should_have_expected_behaviours() {
        WebDriver driver = config.webDriver

        WebElement _checkBox = driver.findElement(By.id("checkbox"))
        assert !_checkBox.selected

        _checkBox.click()
        assert _checkBox.selected

        _checkBox.click()
        assert !_checkBox.selected
    }
}
