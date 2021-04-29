import org.junit.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.io.*;

class RepositoryPage extends PageBase {

    private By codeDropDownBy = By.xpath("//get-repo/details/summary");
    private By downloadBy = By.xpath("//get-repo/details/div/div/div/ul/li[last()]/a");
    private String downloadFolder;

    public RepositoryPage(WebDriver driver) {
        super(driver);
    }

    public ExpectedCondition<Boolean> filepresent() {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                File f = new File(downloadFolder + "Download_Test_Repo-main.zip"); 
                return f.exists();
            }
            @Override
            public String toString() {
            return String.format("file to be present within the time specified");
            }
        };
    }

    public void downloadRepo() {
        this.waitAndReturnElement(codeDropDownBy).click();
        this.waitAndReturnElement(downloadBy).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(this.filepresent());
    }

    public void setDownloadFolder(String path) {
        this.downloadFolder = path;
    }
}