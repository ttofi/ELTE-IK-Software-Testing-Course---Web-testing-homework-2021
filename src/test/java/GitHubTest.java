import org.junit.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.CapabilityType;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.Reader;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class GitHubTest {
    public WebDriver driver;
    public TestConfig config;
    
    @Before
    public void setup() {
        Gson g = new Gson();
        try {
            Reader reader = new FileReader("src/test/java/testConfig.json");
            this.config = g.fromJson(reader, TestConfig.class);
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        WebDriverManager.chromedriver().setup();

        String downloadFilepath = config.getDownloadPath();
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", downloadFilepath);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", chromePrefs);
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        cap.setCapability(ChromeOptions.CAPABILITY, options);

        this.driver = new ChromeDriver(cap);
        this.driver.manage().window().maximize();
    }
    
    @Test
    public void testLoginLogout() {
        MainPage mainPage = new MainPage(this.driver);
        LoginPage loginPage = mainPage.openLogin();
        DashboardPage dashboardPage = loginPage.login(this.config.getUsername(),this.config.getPassword());
        
        System.out.println(dashboardPage.getNewsCardTitle());
        Assert.assertTrue(dashboardPage.getNewsCardTitle().contains("Discover interesting projects and people to populate your personal news feed."));

        mainPage = dashboardPage.Logout();
    }

    @Test
    public void testStaticPages() {
        for (StaticPageTest sp : this.config.getStaticPages()) {
            StaticPage staticPage = new StaticPage(driver, sp.getUrl());
            Assert.assertEquals(sp.getTitle(), staticPage.getTitle());
        }
    }

    @Test
    public void testRepositoryCreation() {
        MainPage mainPage = new MainPage(this.driver);
        LoginPage loginPage = mainPage.openLogin();
        DashboardPage dashboardPage = loginPage.login(this.config.getUsername(),this.config.getPassword());
        NewRepoPage newRepoPage = dashboardPage.createNewRepo();

        int randomNum = ThreadLocalRandom.current().nextInt(10, 99999 + 1);
        newRepoPage.fillRepoName("SWTest_DummyRepo_" + Integer.toString(randomNum));
        newRepoPage.fillDescription("This is just a testing repo.");
        newRepoPage.checkRepoVisibility();
        newRepoPage.checkRepoInitializationOptions();

        RepositoryPage repoPage = newRepoPage.createRepository();
    }

    @Test
    public void historyTest() {
        MainPage mainPage = new MainPage(this.driver);
        LoginPage loginPage = mainPage.openLogin();
        DashboardPage dashboardPage = loginPage.login(this.config.getUsername(),this.config.getPassword());
        RepositoryPage repoPage = dashboardPage.goToTestRepo();
        repoPage.goBack();
        Assert.assertTrue(dashboardPage.getNewsCardTitle().contains("Discover interesting projects and people to populate your personal news feed."));
    }

    @Test
    public void testRepoDownload() {
        MainPage mainPage = new MainPage(this.driver);
        LoginPage loginPage = mainPage.openLogin();
        DashboardPage dashboardPage = loginPage.login(this.config.getUsername(),this.config.getPassword());
        RepositoryPage repoPage = dashboardPage.goToTestRepo();
        repoPage.setDownloadFolder(config.getDownloadPath());
        repoPage.downloadRepo();
    }
    
    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
