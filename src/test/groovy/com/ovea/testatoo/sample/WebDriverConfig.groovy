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
import org.openqa.selenium.WebDriver
import org.openqa.selenium.edge.EdgeDriver

/**
 * @author David Avenante (d.avenante@gmail.com)
 */
class WebDriverConfig extends ExternalResource {
    private static Server server
    public static WebDriver webDriver

    @Override
    protected void before() throws Throwable {
        startJetty()

        System.setProperty('webdriver.edge.driver', 'C:\\Program Files (x86)\\Microsoft Web Driver\\MicrosoftWebDriver.exe')
        webDriver = new EdgeDriver()
    }

    @Override
    protected void after() {
        webDriver.quit()
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