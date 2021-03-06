// Generated by Selenium IDE
import es.uma.informatica.sii.anotaciones.Requisitos;
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
@Requisitos({"RF7"})
public class RF7TestIT {
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
  public void rF7() {
    driver.get("http://localhost:8080/turingFintech-war/");
    driver.manage().window().setSize(new Dimension(1440, 900));
    driver.findElement(By.linkText("página de acceso")).click();
    driver.findElement(By.id("AccesoAdministrativos:user")).click();
    driver.findElement(By.id("AccesoAdministrativos:user")).sendKeys("PONCIANO");
    driver.findElement(By.id("AccesoAdministrativos:pass")).click();
    driver.findElement(By.id("AccesoAdministrativos:pass")).sendKeys("PONCIANO");
    driver.findElement(By.id("AccesoAdministrativos:pass")).sendKeys(Keys.ENTER);
    driver.findElement(By.linkText("Autorizados")).click();
    driver.findElement(By.id("Autorizados:j_idt6:0:Modificar")).click();
    driver.findElement(By.id("Autorizados:nombre")).click();
    driver.findElement(By.id("Autorizados:nombre")).click();
    {
      WebElement element = driver.findElement(By.id("Autorizados:nombre"));
      Actions builder = new Actions(driver);
      builder.doubleClick(element).perform();
    }
    driver.findElement(By.id("Autorizados:nombre")).sendKeys("ale");
    driver.findElement(By.id("Autorizados:ape")).click();
    driver.findElement(By.id("Autorizados:ape")).click();
    {
      WebElement element = driver.findElement(By.id("Autorizados:ape"));
      Actions builder = new Actions(driver);
      builder.doubleClick(element).perform();
    }
    driver.findElement(By.id("Autorizados:ape")).sendKeys("reque");
    driver.findElement(By.name("Autorizados:j_idt15")).click();
    driver.findElement(By.id("Autorizados:j_idt6:0:nombre")).click();
    driver.findElement(By.id("Autorizados:j_idt6:0:nombre")).click();
    {
      WebElement element = driver.findElement(By.id("Autorizados:j_idt6:0:nombre"));
      Actions builder = new Actions(driver);
      builder.doubleClick(element).perform();
    }
    assertThat(driver.findElement(By.id("Autorizados:j_idt6:0:nombre")).getText(), is("ale"));
  }
}
