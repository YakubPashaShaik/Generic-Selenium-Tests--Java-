import org.junit.Test;
import org.openqa.selenium.By;

public class Test108 extends Common {

	@Test
	public void delete_properties() throws Exception {
		login();
		deleteProperty("standard");
		deleteProperty("scraper");
		deleteProperty("new");
		deleteProperty("multispace");
		deleteProperty("multiproperty");
	}
	
	public void deleteProperty(String toDelete) throws Exception {
		gotoProperty(toDelete);
		driver.findElement(By.xpath("//input[@value='Delete Property']")).click();
		acceptAlert();
		Thread.sleep(1000);
		gotoProperty(toDelete);
		waitForPresence("*** DELETED ***");
	}
}
