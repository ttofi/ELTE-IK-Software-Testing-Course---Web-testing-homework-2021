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

class NewRepoPage extends PageBase {

    private By repositoryNameBy = By.id("repository_name");
    private By repositoryDescriptionBy = By.id("repository_description");
    private By radioButtonPrivatBy = By.id("repository_visibility_private");
    private By readmeCheckboxBy = By.id("repository_auto_init");
    private By gitIgnoreCheckboxBy = By.id("repository_gitignore_template_toggle");
    private By gitIgnoreDropdownBy = By.xpath("//i[text()='.gitignore template:']");
    private By gitIgnoreDropdownCppBy = By.xpath("//span[text()='C++']");
    private By createRepoButtonBy = By.xpath("//main/div/form/div/button[@class='btn btn-primary first-in-line']");

    public NewRepoPage(WebDriver driver) {
        super(driver);
    }

    public void fillRepoName(String name) {
        this.waitAndReturnElement(repositoryNameBy).sendKeys(name);
    }

    public void fillDescription(String desc) {
        this.waitAndReturnElement(repositoryDescriptionBy).sendKeys(desc);
    }

    public void checkRepoVisibility() {
        this.waitAndReturnElement(radioButtonPrivatBy).click();
    }

    public void checkRepoInitializationOptions() {
        this.waitAndReturnElement(readmeCheckboxBy).click();
        this.waitAndReturnElement(gitIgnoreCheckboxBy).click();
        this.waitAndReturnElement(gitIgnoreDropdownBy).click();
        this.waitAndReturnElement(gitIgnoreDropdownCppBy).click();
    }

    public RepositoryPage createRepository() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(createRepoButtonBy));
        this.waitAndReturnElement(createRepoButtonBy).click();
        return new RepositoryPage(driver);
    }

}