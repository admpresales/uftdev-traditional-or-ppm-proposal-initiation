package com.mf;

import com.hp.lft.report.ReportException;
import com.hp.lft.report.Reporter;
import com.hp.lft.report.Status;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.hp.lft.sdk.*;
import com.hp.lft.verifications.*;
import com.hp.lft.sdk.web.*;

import unittesting.*;

import java.util.Date;

public class UFTDeveloperTest extends UnitTestClassBase {
    Browser browser;

    public UFTDeveloperTest() {
        //Change this constructor to private if you supply your own public constructor
    }

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        instance = new UFTDeveloperTest();
        globalSetup(UFTDeveloperTest.class);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        globalTearDown();
    }

    @Before
    public void setUp() throws Exception {
        browser = BrowserFactory.launch(BrowserType.CHROME);
        browser.clearCache();
        browser.deleteCookies();
    }

    @After
    public void tearDown() throws Exception {
        browser.closeAllTabs();
    }

    @Test
    public void test() throws GeneralLeanFtException {
        java.util.Date date=new Date();
        String ProposalName = "Proposal Name " + date.getTime();
        String ExecutiveOverview = "This is the description for " + ProposalName;

        browser.navigate("http://nimbusserver.aos.com:8088");
        browser.sync();

        Image StrategicPortfolioImage = browser.describe(Image.class, new ImageDescription.Builder()
                .src("http://nimbusserver.aos.com:8088/images/btn_PFM_up.png")
                .type(com.hp.lft.sdk.web.ImageType.LINK).build());
        StrategicPortfolioImage.click();
        browser.sync();
        Area TinaFryLoginLink = browser.describe(Area.class, new AreaDescription.Builder()
                .href(new RegExpProperty(".*USERNAME=tfry.*")).build());
        TinaFryLoginLink.click();
        browser.sync();
        Link CreateLink = browser.describe(Link.class, new LinkDescription.Builder()
                .tagName("A")
                .innerText("CREATE").build());
        CreateLink.click();
        browser.sync();
        Link ProposalLink = browser.describe(Link.class, new LinkDescription.Builder()
                .tagName("A")
                .innerText("Proposal").build());
        ProposalLink.click();
        browser.sync();
        ListBox BusinessUnitListBox = browser.describe(ListBox.class, new ListBoxDescription.Builder()
                .title("Business Unit: is a Required Field.").build());
        BusinessUnitListBox.select("Corporate");
        browser.sync();
        EditField ProposalNameField = browser.describe(EditField.class, new EditFieldDescription.Builder()
                .title(new RegExpProperty("Proposal Name:.*")).build());
        ProposalNameField.setValue(ProposalName);
        EditField ExecutiveOverviewField = browser.describe(EditField.class, new EditFieldDescription.Builder()
                .title(new RegExpProperty("Executive Overview:.*")).build());
        ExecutiveOverviewField.setValue(ExecutiveOverview);
        EditField BusinessObjectiveField = browser.describe(EditField.class, new EditFieldDescription.Builder()
                .title(new RegExpProperty("Business Objective:.*")).build());
        BusinessObjectiveField.setValue("9 Month Release Cycle");
        Link SubmitLink = browser.describe(Link.class, new LinkDescription.Builder()
                .innerText("Submit").build());
        SubmitLink.click();
        browser.sync();
        WebElement YourRequestIsCreatedWebElement = browser.describe(WebElement.class, new WebElementDescription.Builder()
                .tagName("SPAN")
                .innerText("Your Request is Created").build());
        if (YourRequestIsCreatedWebElement.exists()){
            try {
                Reporter.reportEvent("Creation of Proposal","The proposal was created successfully", Status.Passed);
            } catch (ReportException e) {
                e.printStackTrace();
            }

        }
        else {
            try {
                Reporter.reportEvent("Creation of Proposal","The Proposal created status text didn't display within the default timeout", Status.Failed);
            } catch (ReportException e) {
                e.printStackTrace();
            }

        }
        WebElement MenuUserIconWebElement = browser.describe(WebElement.class, new WebElementDescription.Builder()
                .id("menuUserIcon").build());
        MenuUserIconWebElement.click();
        Link SignOutLink = browser.describe(Link.class, new LinkDescription.Builder()
                .innerText(new RegExpProperty("Sign Out.*")).build());
        SignOutLink.click();
        browser.sync();
    }

}