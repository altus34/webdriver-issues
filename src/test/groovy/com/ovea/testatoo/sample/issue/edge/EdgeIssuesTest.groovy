package com.ovea.testatoo.sample.issue.edge

import com.ovea.testatoo.sample.issue.WebDriverConfig
import org.junit.ClassRule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.By
import org.openqa.selenium.WebElement

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
@RunWith(JUnit4)
class EdgeIssuesTest {
    @ClassRule
    public static WebDriverConfig config = new WebDriverConfig()

    @Test
    void checkbox_should_have_expected_behaviours() {
        config.webDriver.get('http://localhost:8080/index.html')

        WebElement _checkBox = config.webDriver.findElement(By.id("checkbox"))
        assert !_checkBox.selected

        _checkBox.click()
        assert _checkBox.selected

        _checkBox.click()
        assert !_checkBox.selected
    }
}
