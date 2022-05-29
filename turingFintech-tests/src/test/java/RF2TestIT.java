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
public class RF2TestIT {
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
  public void rF2() {
    driver.get("http://localhost:8080/turingFintech-war/");
    driver.manage().window().setSize(new Dimension(1200, 831));
    driver.findElement(By.linkText("página de acceso")).click();
    driver.findElement(By.id("AccesoAdministrativos:user")).click();
    driver.findElement(By.id("AccesoAdministrativos:user")).sendKeys("ponciano");
    driver.findElement(By.id("AccesoAdministrativos:pass")).sendKeys("ponciano");
    driver.findElement(By.id("AccesoAdministrativos:pass")).sendKeys(Keys.ENTER);
    driver.findElement(By.linkText("Clientes")).click();
    driver.findElement(By.id("clientes:botonJuridico")).click();
    driver.findElement(By.id("clientes:DNI")).click();
    driver.findElement(By.id("clientes:DNI")).sendKeys("rf2");
    driver.findElement(By.id("clientes:direccion")).click();
    driver.findElement(By.id("clientes:direccion")).sendKeys("rf2");
    driver.findElement(By.id("clientes:codPost")).click();
    driver.findElement(By.id("clientes:codPost")).sendKeys("2");
    driver.findElement(By.id("clientes:pais")).click();
    driver.findElement(By.id("clientes:pais")).sendKeys("rf2");
    driver.findElement(By.id("clientes:ciudad")).click();
    driver.findElement(By.id("clientes:ciudad")).sendKeys("rf2");
    driver.findElement(By.id("clientes:Razon")).click();
    driver.findElement(By.id("clientes:Razon")).sendKeys("rf2");
    driver.findElement(By.name("clientes:j_idt19")).click();
    driver.findElement(By.id("clientes:j_idt6:2:textoDNI")).click();
    driver.findElement(By.id("clientes:j_idt6:2:textoDNI")).click();
    {
      WebElement element = driver.findElement(By.id("clientes:j_idt6:2:textoDNI"));
      Actions builder = new Actions(driver);
      builder.doubleClick(element).perform();
    }
    assertThat(driver.findElement(By.id("clientes:j_idt6:2:textoDNI")).getText(), is("rf2"));
    driver.findElement(By.id("clientes:j_idt6:2:textoTipo")).click();
    driver.findElement(By.id("clientes:j_idt6:2:textoTipo")).click();
    {
      WebElement element = driver.findElement(By.id("clientes:j_idt6:2:textoTipo"));
      Actions builder = new Actions(driver);
      builder.doubleClick(element).perform();
    }
    assertThat(driver.findElement(By.id("clientes:j_idt6:2:textoTipo")).getText(), is("Juridico"));
  }
}
