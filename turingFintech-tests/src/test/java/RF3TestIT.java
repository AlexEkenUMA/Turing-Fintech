// Generated by Selenium IDE
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;
import java.net.MalformedURLException;
import java.net.URL;
public class RF3TestIT {
  private WebDriver driver;
  private Map<String, Object> vars;
  JavascriptExecutor js;
  @Before
  public void setUp() {
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }
  @After
  public void tearDown() {
    driver.quit();
  }
  @Test
  public void rF3() {
    driver.get("http://localhost:8080/turingFintech-war/");
    driver.manage().window().setSize(new Dimension(1440, 900));
    driver.findElement(By.linkText("página de acceso")).click();
    driver.findElement(By.id("AccesoAdministrativos:user")).click();
    driver.findElement(By.id("AccesoAdministrativos:user")).sendKeys("ponciano");
    driver.findElement(By.id("AccesoAdministrativos:pass")).sendKeys("ponciano");
    driver.findElement(By.id("AccesoAdministrativos:pass")).sendKeys(Keys.ENTER);
    driver.findElement(By.linkText("Clientes")).click();
    driver.findElement(By.id("clientes:j_idt6:0:modJu")).click();
    driver.findElement(By.id("clientes:ciudad")).click();
    driver.findElement(By.id("clientes:ciudad")).click();
    {
      WebElement element = driver.findElement(By.id("clientes:ciudad"));
      Actions builder = new Actions(driver);
      builder.doubleClick(element).perform();
    }
    driver.findElement(By.id("clientes:ciudad")).sendKeys("malaga");
    driver.findElement(By.id("clientes:dic")).click();
    driver.findElement(By.id("clientes:dic")).click();
    {
      WebElement element = driver.findElement(By.id("clientes:dic"));
      Actions builder = new Actions(driver);
      builder.doubleClick(element).perform();
    }
    driver.findElement(By.id("clientes:dic")).sendKeys("rf3");
    driver.findElement(By.name("clientes:j_idt19")).click();
    driver.findElement(By.id("clientes:j_idt6:0:modJu")).click();
    driver.findElement(By.cssSelector("tr:nth-child(4)")).click();
  }
}
