import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.*;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;

import org.junit.*;

public class Common {
	
    public WebDriver driver;
	public String testFilesLocation = "https://dl.dropbox.com/u/109";
	public String domainURL = "http://example.com";
        public Pattern p = Pattern.compile("(.*)(/url/)"); //strip href to get ids
	public Pattern ps = Pattern.compile("(.*)(/url/)"); //strip href to get ids

	//these are set by uploads
	public static String testStandardId = "30023";
	public static String testScraperId = "30015";
	public static String testNewId = "30018";
	public static String testMultispaceId = "29522";
	public static String testMultipropId = "29546";
	
    @Before
    public void setUp() {
    	try {
	    	File file = new File("lib/IEDriverServer.exe");
	    	System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
	    	driver = new InternetExplorerDriver();
    	}
    	catch (Exception e) {
    		System.out.println("Failed Internet Explorer Set up: " + e.getMessage());
    	}
    }
    
    public void setUpFirefox() {
		try {
	    	File directory = new File("downloads");
	    	
	    	FirefoxProfile firefoxProfile = new FirefoxProfile();
	
	        firefoxProfile.setPreference("browser.download.folderList",2);
	        firefoxProfile.setPreference("browser.download.dir",directory.getAbsolutePath());
	        firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
	        	"application/vnd.ms-excel, text/csv, application/pdf");
	     
	    	driver = new FirefoxDriver(firefoxProfile);
		}
		catch (Exception e) {
			System.out.println("Failed to setup Firefox: " + e.getMessage());
		}
    }

    @After
    public void tearDown() {
    	try {
    		driver.quit();
    	}
    	catch (Exception e) {
    		System.out.println("Failed tear down: " + e.getMessage());
    	}
    }
    
    /**
     * Login with QA credentials
     */
    public void login() {
    	try {
	        driver.get("http://example.com/");
	        driver.findElement(By.id("username")).clear();
	        driver.findElement(By.id("password")).clear();
	        driver.findElement(By.id("username")).sendKeys("qatester");
	        driver.findElement(By.id("password")).sendKeys("QAtest1ng");
	        driver.findElement(By.id("loginButton")).click();
    	}
    	catch (Exception e) {
    		System.out.println("Login failed: " + e.getMessage());
    	}
    }
    
    /**
     * Login with QA credentials
     */
    public void loginFirefox() {
    	try {
	        driver.get("http://example.com/");
	        driver.findElement(By.id("username")).clear();
	        driver.findElement(By.id("password")).clear();
	        driver.findElement(By.id("username")).sendKeys("aong");
	        driver.findElement(By.id("password")).sendKeys("QAtest1ng");
	        driver.findElement(By.id("loginButton")).click();
    	}
    	catch (Exception e) {
    		System.out.println("Login failed: " + e.getMessage());
    	}
    }

    /**
     * Go to a specific property
     * @param String which - the String name of the property to go to
     */
    public void gotoProperty(String which) {
    	switch(which) {
	    	case "standard":
	    		driver.get(domainURL + testStandardId); break;
	    	case "scraper":
	    		driver.get(domainURL + testScraperId); break;
	    	case "new":
	    		driver.get(domainURL + testNewId); break;
	    	case "multispace":
	    		driver.get(domainURL + testMultispaceId); break;
	    	case "multiproperty":
	    		driver.get(domainURL + testMultipropId); break;
	    	default:
	    		driver.findElement(By.linkText("Property")).click();
	    		break;
    	}
    }
    
    /**
     * Check that this element or text is present on the page
     * Fails and stops the current test if it is not.
     * @param Object thisThing - polymorphic object that is being checked for on the page
     * Supported Types:
     * 		- Typeof By
     * 		- Typeof String
     */
    public void waitForPresence(Object thisThing) {
    	int waitIncrement = 10000; // milliseconds

    	for (int second = 0;; second++) {
			if (second >= 200) fail("timeout");

			try {
				if (thisThing instanceof By) {
					driver.findElement((By)thisThing);
					break;
				}
				else if (thisThing instanceof String) {
					if (driver.getPageSource().contains((String)thisThing))
						break;
					//else continue to wait
				}
				else if (thisThing instanceof File) {
					if (((File)thisThing).isFile() && ((File)thisThing).length() > 0)
						break;
				}
				else {
					fail("\nWaiting for an object that is not defined in Common::waitForPresence(): " +
						thisThing.getClass());
				}
				Thread.sleep(waitIncrement);
            }
			catch (Exception e) {
				System.out.println(second + " Waiting...\n");
				//e.printStackTrace();
			}
		}
	}
    
    /**
     * Check that this element or text is not present on the page
     * Fails and stops the current test if it is.
     * @param Object thisThing - polymorphic object that is being checked for on the page
     * Supported Types:
     * 		- Typeof By
     * 		- Typeof String
     */
    public void notPresent(Object thisThing) {
    	try {
			if (thisThing instanceof By) {
				driver.findElement((By) thisThing);
				fail("Element exists when it should not: " + ((By)thisThing).toString());
			}
			else if (thisThing instanceof String) {
				if (driver.getPageSource().contains((String)thisThing))
					fail("Text exists when it should not: " + (String)thisThing);
				//else does not exist, which is good
			}
    	}
    	catch (Exception e){
    		// Good it doesn't exist
    	}
    }
    
    /**
     * Check that the page does not contain any oops.
     */
    public void assertOopsFalse() {
    	try {
			assertFalse(driver.getPageSource().contains("Oops!"));
		} catch (Error e) {
			fail("\nOops! was found.\n" + e.toString());
		}
    }
    
    /**
     * Click ok on alert/message window
     */
    public void acceptAlert() {
    	try {
			Thread.sleep(1500);
			driver.switchTo().alert().accept();
			Thread.sleep(1500);
    	}
    	catch (Exception e) {
    		System.out.println("Failed to Accept Alert: " + e.getMessage());
    	}
    }
    
    /**
     * Checks that text exists on page
     * Fails and stops current test if they exist.
     * @param String toMatch - String to match
     */
    public void textPresent(String toMatch) {
    	try {
			assertTrue(driver.getPageSource().contains(toMatch));
		} catch (Error e) {
			fail("\nText is not present:" + toMatch + "\n" + e.toString());
		}
    }
    
    /**
     * Checks that text does not exist anywhere on the page
     * Fails and stops current test if they are.
     * @param String toMatch - String to match
     */
    public void textNotPresent(String toMatch) {
    	try {
			assertFalse(driver.getPageSource().contains(toMatch));
		} catch (Error e) {
			fail("Text is present:" + toMatch + "\n" + e.toString());
		}
    }
    
    /**
     * Asserts two strings are equal. 
     * Fails and stops current test if they are not.
     * @param String from - String 1 for comparison
     * @param String to   - String 2 for comparison
     */
    public void textEquals(String from, String to) {
    	try {
			assertEquals(from, to);
		} catch (Error e) {
			fail("\nNot equal:" + from + " and " + to + "\n" + e.toString());
		}
    }
    
    /**
     * Asserts two strings are not equal. 
     * Fails and stops current test if they are.
     * @param String from - String 1 for comparison
     * @param String to   - String 2 for comparison
     */
    public void isNot(String from, String to) {
    	try {
			assertThat(from, is(not(to)));
		} catch (Error e) {
			fail("\nIsNot Failed (therefore are equal):" + from + " and " + to  + "\n" + e.toString());
		}
    }
    
    /**
     * Assert that element exists
     * @param By element - element to check
     */
    public void exists(By element) {
    	try {
    		assertTrue(driver.findElements(element).size() > 0);
    	}
    	catch (Error e) {
    		fail("\nElement " + element.toString() + " does not exist.\n" + e.toString());
    	}
    }
	
    /**
     * Asserts source string contains toMatch string
     * Fails and stops current test if does not contain toMatch
     * @param String source    - String 1 for comparison
     * @param String toMatch   - String 2 for comparison
     */
    public void contains(String source, String toMatch) {
		try {
			assertTrue(source.contains(toMatch));
		} catch (Error e) {
			fail("\nDoes not contain:" + toMatch + "\n" + e.toString());
		}
    }
    
    /**
     * Remove white space characters from page source for comparison
     * @return String - the formatted pageSource
     */
    public String formatPageSource(String[] options) {
    	//get a matchable page source
		String source = driver.getPageSource();
		
		//Remove any repetition and combination of spaces, tabs, newlines between tags
		source = source.replaceAll("(?<=>)[ |\t|\n]*", "");
		source = source.replaceAll("[ |\t|\n]*(?=<)", "");
		
		//Remove all other newlines, tabs and quotes
		source = source.replaceAll("[\n|\t|\r|\"|\\\\]+", "");
		if (options != null) {
			for (int i=0; i<options.length; i++) {
				source = source.replaceAll(options[i],  "@");
			}
		}
		
		//remove dynamic id
		source = source.replaceAll("id=[0-9]+", "id=@");
		
		//retain dates
		source = source.replaceAll("(?<=/2[01][0-9][0-9]) [ ]+", "@");
		
		//remove dynamic id
		source = source.replaceAll("/[0-9]+ ", "/@ ");
		
		//remove dynamic id
		source = source.replaceAll("/[0-9]+>", "/@>");
		
		//remove dynamic id
		source = source.replaceAll("/[0-9]+'", "/@'");
		
		//remove dynamic id
		source = source.replaceAll("/[0-9]+#", "/@#");
		
		//remove dynamic id
		source = source.replaceAll("/[0-9]+\\?", "/@?");
		
		return source;
    }
    
    /**
     * Remove white space characters from page source for comparison
     * @return String - the formatted pageSource
     */
    public byte[] getChecksum(File location) {

    	MessageDigest md;
    	InputStream is;
    	try {
    		md = MessageDigest.getInstance("MD5");
    	    is = new DigestInputStream(new FileInputStream(location.getAbsolutePath()), md);
    	    // read stream to EOF as normal...
    	    is.close();
        	
        	return md.digest();
    	}
    	catch(Exception e) {
    		fail("Exception found in Common::getChecksum::" + e.getMessage());
    	}
    	
    	fail("Failed to get checksum");
    	return null;
    }
}
