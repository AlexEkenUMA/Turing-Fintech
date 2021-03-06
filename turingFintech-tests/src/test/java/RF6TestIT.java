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
@Requisitos({"RF6"})
public class RF6TestIT {
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
  public void rF6() {
    driver.get("http://localhost:8080/turingFintech-war/");
    driver.manage().window().setSize(new Dimension(1440, 900));
    driver.findElement(By.linkText("página de acceso")).click();
    driver.findElement(By.id("AccesoAdministrativos:pass")).click();
    driver.findElement(By.id("AccesoAdministrativos:user")).click();
    driver.findElement(By.id("AccesoAdministrativos:user")).sendKeys("ponciano");
    driver.findElement(By.id("AccesoAdministrativos:pass")).sendKeys("ponciano");
    driver.findElement(By.id("AccesoAdministrativos:pass")).sendKeys(Keys.ENTER);
    driver.findElement(By.linkText("Empresas")).click();
    driver.findElement(By.id("Autorizados:j_idt6:0:anadirAutor")).click();
    driver.findElement(By.id("Autorizados:nuevo")).click();
    driver.findElement(By.id("Autorizados:Iden")).click();
    driver.findElement(By.id("Autorizados:Iden")).sendKeys("rf6");
    driver.findElement(By.id("Autorizados:nombre")).sendKeys("rf6");
    driver.findElement(By.id("Autorizados:Ape")).sendKeys("rf6");
    driver.findElement(By.id("Autorizados:Dir")).sendKeys("rf6");
    driver.findElement(By.id("Autorizados:basic_input")).sendKeys("20/02/2001");
    driver.findElement(By.id("Autorizados:basic_input")).sendKeys(Keys.ENTER);
    driver.findElement(By.id("Autorizados:usu")).click();
    driver.findElement(By.id("Autorizados:usu")).sendKeys("rf6");
    driver.findElement(By.id("Autorizados:pass")).sendKeys("rf6");
    driver.findElement(By.id("Autorizados:repass")).sendKeys("rf6");
    driver.findElement(By.id("Autorizados:repass")).sendKeys(Keys.ENTER);
    driver.findElement(By.cssSelector(".titulo")).click();
    assertThat(driver.findElement(By.cssSelector(".titulo")).getText(), is("PANEL ADMINISTRATIVO"));
  }
}
