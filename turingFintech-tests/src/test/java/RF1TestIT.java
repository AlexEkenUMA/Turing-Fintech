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
public class RF1TestIT {
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
  public void rF1() {
    driver.get("http://localhost:8080/turingFintech-war/");
    driver.manage().window().setSize(new Dimension(1440, 900));
    driver.findElement(By.id("Acceso:user")).click();
    driver.findElement(By.id("Acceso:user")).sendKeys("ana");
    driver.findElement(By.id("Acceso:pass")).click();
    driver.findElement(By.id("Acceso:pass")).sendKeys("ana");
    driver.findElement(By.id("login")).click();
    driver.findElement(By.id("Acceso:botonLogin")).click();
    driver.findElement(By.cssSelector(".titulo")).click();
    assertThat(driver.findElement(By.cssSelector(".titulo")).getText(), is("Bienvenido a Ebury"));
    driver.findElement(By.cssSelector(".au:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".au:nth-child(2)")).click();
    driver.findElement(By.cssSelector(".au:nth-child(2)")).click();
    assertThat(driver.findElement(By.cssSelector(".au:nth-child(2)")).getText(), is("Está autenticado como Ana."));
    driver.findElement(By.linkText("salir")).click();
    driver.findElement(By.linkText("página de acceso")).click();
    driver.findElement(By.id("AccesoAdministrativos:user")).click();
    driver.findElement(By.id("AccesoAdministrativos:user")).sendKeys("ponciano");
    driver.findElement(By.id("AccesoAdministrativos:pass")).sendKeys("ponciano");
    driver.findElement(By.id("AccesoAdministrativos:pass")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".titulo")).click();
    assertThat(driver.findElement(By.cssSelector(".titulo")).getText(), is("PANEL ADMINISTRATIVO"));
    driver.findElement(By.cssSelector(".au:nth-child(3)")).click();
    assertThat(driver.findElement(By.cssSelector(".au:nth-child(3)")).getText(), is("Está autenticado como ponciano."));
  }
}
