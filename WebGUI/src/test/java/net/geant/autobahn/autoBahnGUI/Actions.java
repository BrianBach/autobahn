package net.geant.autobahn.autoBahnGUI;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleneseTestCase;

public class Actions {
    
    public static String SeleniumRCServer = "150.140.8.24";
    public static int SeleniumRCServerPort = 4444;
    public static String WebGUIServer = "http://150.140.8.15:8080/";

    private Selenium selenium;
    
    SeleneseTestCase stc = new SeleneseTestCase();

    public Actions(Selenium s){
        selenium = s;
    }
    
    public void login() throws Exception {
        selenium.open("/autobahn-gui/portal/home.htm");
        selenium.click("link=Request service");
        selenium.waitForPageToLoad("30000");
        selenium.type("j_username", "dmarchal");
        selenium.type("j_password", "auto123");
        selenium.click("Login");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");
    }
    
    public void browseSite() throws Exception {
        selenium.open("/autobahn-gui/portal/login.htm");
        selenium.type("j_username", "dmarchal");
        selenium.type("j_password", "auto123");
        selenium.click("Login");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=About AutoBAHN");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=Request service");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=Submited services");
        selenium.waitForPageToLoad("30000");
        selenium.select("currentIdm", "label=http://150.140.8.17:8080/autobahn/interdomain");
        selenium.click("_eventId_change");
        selenium.waitForPageToLoad("30000");
        selenium.select("currentIdm", "label=http://150.140.8.18:8080/autobahn/interdomain");
        selenium.click("_eventId_change");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=Autobahn map");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=Domains settings");
        selenium.waitForPageToLoad("30000");
        selenium.select("currentIdm", "label=http://150.140.8.17:8080/autobahn/interdomain");
        selenium.click("_eventId_change");
        selenium.waitForPageToLoad("30000");
        selenium.select("currentIdm", "label=http://150.140.8.18:8080/autobahn/interdomain");
        selenium.click("_eventId_change");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=Domains logs");
        selenium.waitForPageToLoad("30000");
        selenium.select("currentIdm", "label=http://150.140.8.17:8080/autobahn/interdomain");
        selenium.click("_eventId_change");
        selenium.waitForPageToLoad("30000");
        selenium.select("currentIdm", "label=http://150.140.8.18:8080/autobahn/interdomain");
        selenium.click("_eventId_change");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");
    }
    
    public void testReservation() throws Exception {
        selenium.open("/autobahn-gui/portal/login.htm");
        selenium.type("j_username", "dmarchal");
        selenium.type("j_password", "auto123");
        selenium.click("Login");
        selenium.waitForPageToLoad("30000");
        stc.verifyTrue(selenium.isTextPresent("About AutoBAHN"));
        selenium.click("link=Request service");
        selenium.waitForPageToLoad("30000");
        stc.verifyTrue(selenium.isTextPresent("Request Reservation Service"));
        selenium.type("justification", "test justification");
        selenium.click("_eventId_add");
        selenium.waitForPageToLoad("30000");
        //selenium.click("//div[@onclick='xc_dr(0,1)']");
        //selenium.click("//div[@onclick='xc_dr(0,1)']");
        //selenium.click("//div[@onclick='xc_dr(0,1)']");
        //selenium.click("//div[@onclick='xc_ed(0,\"20130601\");']");
        //SeleneseTestCase.assertEquals("End Date was earlier than Start Date, it's now set to Start Date.", selenium.getAlert());
        //selenium.click("//div[@onclick='xc_ed(0,\"20130602\");']");
        selenium.type("request.description", "test description");
        selenium.click("_eventId_test");
        selenium.waitForPageToLoad("30000");
        stc.verifyTrue(selenium.isTextPresent("Reservation test succeeded"));
        selenium.click("_eventId_submit");
        selenium.waitForPageToLoad("30000");
        stc.verifyTrue(selenium.isTextPresent("NORMAL"));
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");
    }
    
    public void checkLabels() throws Exception {
        selenium.open("/autobahn-gui/portal/login.htm");
        selenium.type("j_username", "dmarchal");
        selenium.type("j_password", "auto123");
        selenium.click("Login");
        selenium.waitForPageToLoad("30000");
        stc.verifyTrue(selenium.isTextPresent("About AutoBAHN"));
        selenium.click("link=Request service");
        selenium.waitForPageToLoad("30000");
        stc.verifyTrue(selenium.isTextPresent("Service Reservations"));
        stc.verifyTrue(selenium.isTextPresent("Action"));
        stc.verifyTrue(selenium.isTextPresent("Start time"));
        stc.verifyTrue(selenium.isTextPresent("End Time"));
        stc.verifyTrue(selenium.isTextPresent("Priority"));
        stc.verifyTrue(selenium.isTextPresent("Start port"));
        stc.verifyTrue(selenium.isTextPresent("End port"));
        stc.verifyTrue(selenium.isTextPresent("Delay [ms]"));
        stc.verifyTrue(selenium.isTextPresent("Capacity [bits/s]"));
        stc.verifyTrue(selenium.isTextPresent("VLAN identifier"));
        stc.verifyTrue(selenium.isTextPresent("Resilience"));
        selenium.click("link=Submited services");
        selenium.waitForPageToLoad("30000");
        selenium.click("link=Domains settings");
        selenium.waitForPageToLoad("30000");
        stc.verifyTrue(selenium.isTextPresent("Domains Settings"));
        selenium.click("link=Submited services");
        selenium.waitForPageToLoad("30000");
        stc.verifyTrue(selenium.isTextPresent("Submitted Reservation Services"));
        selenium.click("link=Autobahn map");
        selenium.waitForPageToLoad("30000");
        stc.verifyTrue(selenium.isTextPresent("AutoBAHN Reservation and Reachability Map"));
        selenium.click("link=Domains logs");
        selenium.waitForPageToLoad("30000");
        stc.verifyTrue(selenium.isTextPresent("Domains Logs"));
        stc.verifyTrue(selenium.isTextPresent("Welcome, dmarchal | Logout"));
        selenium.click("link=Logout");
        selenium.waitForPageToLoad("30000");
    }
}
