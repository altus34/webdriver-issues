package com.ovea.testatoo.sample.issue

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.DefaultHandler
import org.eclipse.jetty.server.handler.HandlerList
import org.eclipse.jetty.server.handler.ResourceHandler
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.edge.EdgeDriver

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
@RunWith(JUnit4)
class EdgeIssuesTest {
    static WebDriver webDriver

    @BeforeClass
    static void before() {
        startJetty()

        System.setProperty('webdriver.edge.driver', 'C:\\Program Files (x86)\\Microsoft Web Driver\\MicrosoftWebDriver.exe')
        webDriver = new EdgeDriver()

        webDriver.get('http://localhost:8080/index.html')
    }

    @AfterClass
    static void after() {
        webDriver.quit()
        server.stop()
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

    private static void startJetty() {
        server = new Server(8080)
        ResourceHandler resource_handler = new ResourceHandler()

        resource_handler.directoriesListed = true
        resource_handler.welcomeFiles = ['index.html']
        resource_handler.resourceBase = 'src/test/webapp'

        HandlerList handlers = new HandlerList()
        handlers.handlers = [resource_handler, new DefaultHandler()]
        server.handler = handlers

        server.start()
    }
}
