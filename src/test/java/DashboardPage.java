import org.junit.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;


class DashboardPage extends PageBase {

    private By newsCardBy = By.xpath("//div[@class='news']/div[@class='Box p-5 mt-3']/h2");
    private By profileDropDownBy = By.xpath("//header/div/details/summary/img");
    private By singoutButtonBy = By.xpath("//details-menu/form[@class='logout-form']/button[@type='submit']");
    private By newRepoButtonBy = By.xpath("//h2/a[@href='/new']");
    private By testRepoLinkBy = By.xpath("//div[@id='repos-container']/ul/li[last()]/div/a");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }    
    
    public String getNewsCardTitle() {
        return this.waitAndReturnElement(newsCardBy).getText();
    }

    public NewRepoPage createNewRepo() {
        this.waitAndReturnElement(newRepoButtonBy).click();
        return new NewRepoPage(driver);
    }

    public RepositoryPage goToTestRepo() {
        this.waitAndReturnElement(testRepoLinkBy).click();
        return new RepositoryPage(driver);
    }

    public MainPage Logout() {
        this.waitAndReturnElement(profileDropDownBy).click();
        this.waitAndReturnElement(singoutButtonBy).click();
        return new MainPage(driver);
    }
}
