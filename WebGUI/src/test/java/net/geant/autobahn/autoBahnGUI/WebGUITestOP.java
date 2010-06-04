package net.geant.autobahn.autoBahnGUI;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * Run tests using Opera
 * @author <a href="mailto:stamos@cti.gr">Kostas Stamos</a>
 *
 */
public class WebGUITestOP {
    
    private Actions actions;
    private Selenium selenium;
    
    @Before
    public void setUp() throws Exception {
        selenium = new DefaultSelenium(
                Actions.SeleniumRCServer,
                Actions.SeleniumRCServerPort,
                "*opera",
                Actions.WebGUIServer);
        selenium.start();
        actions = new Actions(selenium);
    }
    
    @After
    public void tearDown() throws Exception {
        selenium.stop();
    }

    @Ignore
    @Test
    public void testLogin() throws Exception {
        actions.login();
    }
    
    @Ignore
    @Test
    public void testBrowseSite() throws Exception {
        actions.browseSite();
    }
    
    @Ignore
    @Test
    public void testReservation() throws Exception {
        actions.testReservation();
    }
    
}
