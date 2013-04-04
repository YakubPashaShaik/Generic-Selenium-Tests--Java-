import java.io.File;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.openqa.selenium.By;

public class Test107 extends Common {
	
	File fileToChecksum = new File("downloads\\Test.xls");
	
	@Override
	public void setUp() {
		setUpFirefox();
		if (fileToChecksum.isFile() && fileToChecksum.length() > 0)
			fileToChecksum.delete();
	}
	
	@Test
	public void export_property() throws Exception {
		loginFirefox();
		gotoProperty("standard");
		driver.findElement(By.linkText("Dashboard")).click();

		waitForPresence(fileToChecksum);
		byte[] checksum = getChecksum(fileToChecksum);
		fileToChecksum.delete();
		
		//expected md5sum: d41d8cd98f00b204e9800998ecf8427e
		textEquals(new String(Hex.encodeHex(checksum)), "d41d8cd98f00b204e9800998ecf8427e");
	}
}
