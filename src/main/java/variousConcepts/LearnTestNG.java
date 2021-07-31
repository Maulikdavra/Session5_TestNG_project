package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LearnTestNG {

	
	WebDriver driver;
	
	String browser;
	
	@BeforeClass
	public void readConfig()
	{
		//BufferedReader //InputStream //FileReader //Scanner
		
		Properties prop = new Properties();
		
		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used:"+ browser);
			}
		
		catch(IOException e){
			e.printStackTrace();
		}
	}


	@BeforeMethod
	public void init() {

		if(browser.equalsIgnoreCase("Edge")) {
			System.setProperty("webdriver.edge.driver", "C:\\Users\\MD\\selenium\\session3\\driver\\msedgedriver.exe");
			driver = new EdgeDriver();
			
		}else if(browser.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "driver\\driver\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
		
		
		
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.techfios.com/billing/?ng=admin/");

	}

	@Test
	public void test() {
		
		Assert.assertEquals(driver.getTitle(), "Login - iBilling", "Wrong page!!!!!");
		
		WebElement USERNAME_ELEMENT = driver.findElement(By.xpath("//input[@id='username']"));
		WebElement PASSWORD_ELEMENT = driver.findElement(By.xpath("//input[@id='password']"));
		WebElement SIGNIN_ELEMENT = driver.findElement(By.xpath("/html/body/div/div/div/form/div[3]/button"));
		
		//Login data
		String LoginID = "demo@techfios.com";
		String password = "abc123";
		
		USERNAME_ELEMENT.sendKeys(LoginID);
		PASSWORD_ELEMENT.sendKeys(password);
		SIGNIN_ELEMENT.click();
		
		WebElement DASHBOARD_TITLE_ELEMENT = driver.findElement(By.xpath("/html/body/section/div/div[1]/div[2]/div/h2"));
		Assert.assertEquals(DASHBOARD_TITLE_ELEMENT.getText(), "Dashboard", "Wrong page!!!");

	
}
	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}
}
