import java.util.regex.Matcher;

import org.junit.Test;
import org.openqa.selenium.By;

public class Test13 extends Common {
    @Test
    public void upload_file() throws Exception {
    	login();
    	driver.findElement(By.linkText("Add Property")).click();
    	waitForPresence(By.name("file"));
    	driver.findElement(By.name("file")).sendKeys(testFilesLocation + "/Test.xls");
    	waitForPresence(By.name("submit"));
    	driver.findElement(By.name("submit")).click();
    	waitForPresence(By.xpath("//input[@value='Continue Upload']"));
        driver.findElement(By.xpath("//input[@value='Continue Upload']")).click();
    }
}