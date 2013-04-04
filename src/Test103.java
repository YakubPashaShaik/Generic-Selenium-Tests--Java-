import org.junit.Test;
import org.openqa.selenium.By;

public class Test103 extends Common {

	@Test
	public void energy_event_on_model() throws Exception {
		login();
		gotoProperty("standard");
		
		driver.findElement(By.name("implementationStart")).sendKeys("10/01/2008");
		driver.findElement(By.name("implementedOn")).sendKeys("10/15/2008");
		driver.findElement(By.id("done")).click();
	
		waitForPresence(By.xpath("(//table[@id='uaOwner']//a['Edit'])[1]"));
		driver.findElement(By.xpath("(//table[@id='uaOwner']//a['Edit'])[1]")).click();

		Thread.sleep(2000);

		exists(By.xpath("//circle[@fill='red']"));
	}

}
