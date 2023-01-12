package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static java.time.Duration.*;


public class PageToTest extends BasePage {

    public static final String BASE_URL = "https://www.w3schools.com/sql/trysql.asp?filename=trysql_select_all";
    public static final String CITY_SQL = "SELECT * FROM Customers WHERE city=\\'London\\';";
    public static final String INSERT_SQL = "INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country) " +
            "VALUES (\\'TEST\\', \\'Test Test\\', \\'test 11\\', \\'Test\\', \\'0000\\', \\'Mars\\');";
    public static final String CHECK_INSERT_SQL = "SELECT * FROM Customers WHERE CustomerName =\\'TEST\\';";
    public static final String UPDATE_SQL = "UPDATE Customers SET CustomerName = \\'UPDATED_Alfreds Futterkiste\\', " +
            "ContactName= \\'UPDATED_Maria Anders\\', Address = \\'UPDATED_Obere Str. 57\\', City = \\'UPDATED_Berlin\\', " +
            "PostalCode = \\'UPDATED_12209\\', Country = \\'UPDATED_Germany\\' WHERE CustomerID = 1;";
    public static final String CHECK_UPDATE_SQL = "SELECT * FROM Customers WHERE CustomerID = 1;";

    @FindBy(xpath = "//button[@class='ws-btn']")
    public WebElement runButton;

    @FindBy(xpath = "//table[@class='ws-table-all notranslate']")
    public WebElement resultTable;

    public By successChangeMessage = new By.ById("divResultSQL");

    @FindBy(xpath = "//button[@class='w3-btn ws-black w3-round ws-hover-black']")
    public WebElement restoreDbButton;

    public PageToTest(WebDriver webDriver) {
        this.driver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void open() {
        driver.get(BASE_URL);
    }

    public void inputQuery(String query) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("window.editor.doc.setValue('" + query + "')");
    }

    public void runQuery() {
        runButton.click();
    }

    public List<WebElement> getTableRows() {
        waitUntilElementIsPresent(By.xpath(".//tr"));
        List<WebElement> results = resultTable.findElements(By.xpath(".//tr"));
        results.remove(0);
        return results;
    }

    public List<String> getInfoByFieldName(List<WebElement> elements, Fields field) {
        int counter = getCounter(field);

        List<String> result = new ArrayList<>();
        for (WebElement element : elements) {
            WebElement qwe = element.findElement(By.xpath(".//td[" + counter + "]"));
            result.add(qwe.getText());
        }
        return result;
    }

    private int getCounter(Fields field) {
        int counter;
        switch (field) {
            case CUSTOMER_ID:
                counter = 1;
                break;
            case CUSTOMER_NAME:
                counter = 2;
                break;
            case CONTACT_NAME:
                counter = 3;
                break;
            case ADDRESS:
                counter = 4;
                break;
            case CITY:
                counter = 5;
                break;
            case POSTAL_CODE:
                counter = 6;
                break;
            case COUNTRY:
                counter = 7;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + field);
        }
        return counter;
    }

    public String getResultByFieldName(Fields field) {
        waitUntilElementIsPresent(By.xpath("//table[@class='ws-table-all notranslate']//td[" + getCounter(field) + "]"));
        return driver.findElement(By.xpath("//table[@class='ws-table-all notranslate']//td[" + getCounter(field) + "]")).getText();
    }


    public List<WebElement> getResultsByContactName(String text) {
        List<WebElement> table = getTableRows();
        List<WebElement> result = new ArrayList<>();
        for (WebElement element : table) {
            WebElement qwe = element.findElement(By.xpath(".//td[3]"));
            if (qwe.getText().equals(text)) result.add(element);
        }
        return result;
    }


    public String getChangeMessage() {
        new WebDriverWait(driver, ofSeconds(5)).until(ExpectedConditions.textToBe(successChangeMessage,
                "You have made changes to the database. Rows affected: 1"));
        return driver.findElement(successChangeMessage).getText();
    }

    public String getRestoreMessage() {
        new WebDriverWait(driver, ofSeconds(5)).until(ExpectedConditions.textToBe(successChangeMessage,
                "The database is fully restored."));
        return driver.findElement(successChangeMessage).getText();
    }

    public String getNoResultMessage() {
        new WebDriverWait(driver, ofSeconds(5)).until(ExpectedConditions.textToBe(successChangeMessage,
                "No result."));
        return driver.findElement(successChangeMessage).getText();
    }

    public void acceptAlert() {
        new WebDriverWait(driver, ofSeconds(5)).until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public void restoreDb() {
        restoreDbButton.click();
    }

    public void waitUntilElementIsPresent(By by) {
        new WebDriverWait(driver, ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(by));
    }
}
