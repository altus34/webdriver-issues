/**
 * Copyright © 2016 Ovea (d.avenante@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ovea.testatoo.sample

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.handler.DefaultHandler
import org.eclipse.jetty.server.handler.HandlerList
import org.eclipse.jetty.server.handler.ResourceHandler
import org.junit.rules.ExternalResource
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.safari.SafariDriver
import org.testatoo.evaluator.webdriver.WebDriverEvaluator

import static org.testatoo.core.Testatoo.getConfig

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
class WebDriverConfig extends ExternalResource {
    private static Server server

    @Override
    protected void before() throws Throwable {
        // Defined by JVM maven arguments
        final String browser = System.getProperty('browser') ?: 'Chrome' // defined in the maven profile
        final String drivers = System.getProperty('drivers') ?: '/usr/bin/'

        startJetty()

        switch (browser) {
            case 'Firefox':
                println '================== Firefox Profile ==================='
                System.setProperty('webdriver.gecko.driver', drivers + 'geckodriver')
                DesiredCapabilities capabilities = DesiredCapabilities.firefox()
                capabilities.setCapability('marionette', true)
                config.evaluator = new WebDriverEvaluator(new FirefoxDriver(capabilities))
                break
            case 'Chrome':
                println '=================== Chrome Profile ==================='
                System.setProperty('webdriver.chrome.driver', drivers + 'chromedriver')
                config.evaluator = new WebDriverEvaluator(new ChromeDriver())
                break
            case 'Safari ':
                println '=================== Safari Profile ==================='
                System.setProperty('webdriver.safari.driver', '/usr/bin/safaridriver')
                config.evaluator = new WebDriverEvaluator(new SafariDriver())
                break
            case 'Edge':
                println '==================== Edge Profile ===================='
                System.setProperty('webdriver.edge.driver', 'C:\\Program Files (x86)\\Microsoft Web Driver\\MicrosoftWebDriver.exe')
                config.evaluator = new WebDriverEvaluator(new EdgeDriver())
                break
        }
    }

    @Override
    protected void after() {
        config.evaluator.close()
        server.stop()
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