package net.geant.autobahn.autoBahnGUI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * Run tests using Konqueror
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 *
 */
public class WebGUITestKO {
    
    private Actions actions;
    private Selenium selenium;
    
    @Before
    public void setUp() throws Exception {
        selenium = new DefaultSelenium(
                Actions.SeleniumRCServer,
                Actions.SeleniumRCServerPort,
                "*custom konqueror",
                Actions.WebGUIServer);
        selenium.start();
        actions = new Actions(selenium);
    }
    
    @After
    public void tearDown() throws Exception {
        selenium.stop();
    }

    @Test
    public void testLogin() throws Exception {
        actions.login();
    }
    
    @Test
    public void testBrowseSite() throws Exception {
        actions.browseSite();
    }
    
    @Test
    public void testReservation() throws Exception {
        actions.testReservation();
    }
    
}
