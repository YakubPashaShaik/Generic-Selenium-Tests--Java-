import org.junit.*;
import org.openqa.selenium.*;

public class Test2 extends Common {
	@Test
	public void test_portfolio_tablinks() throws Exception {
		login();
		
		driver.findElement(By.linkText("List")).click();
		waitForPresence("Property List");
		assertOopsFalse();
		
		driver.findElement(By.linkText("Reports")).click();
		waitForPresence("Portfolio Reports");
		assertOopsFalse();
		
		driver.findElement(By.linkText("Alerts")).click();
		waitForPresence("Alert List");
		assertOopsFalse();
		
	}
}
