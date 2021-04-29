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


class LoginPage extends PageBase {

    private By nameInputBoxBy = By.name("login");
    private By passwordInputBoxBy = By.name("password");

    public LoginPage(WebDriver driver) {
        super(driver);
    }    
    
    public DashboardPage login(String username, String password){
        this.waitAndReturnElement(nameInputBoxBy).sendKeys(username);
        this.waitAndReturnElement(passwordInputBoxBy).sendKeys(password+"\n");
        return new DashboardPage(this.driver);
    }
    
}
