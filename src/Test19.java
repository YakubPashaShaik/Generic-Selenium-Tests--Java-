import org.junit.*;
import org.openqa.selenium.*;

import java.util.regex.Matcher;

public class Test19 extends Common {

	private int round = 1;
	private String source = "";
	//cannot make header, it is specialized here
	//Test 19: Because the HTML code does not change except for which is active when a tab is selected,
	//It is sufficient to just compare HTML code
	
	public void compareSource(String compare) {
		contains(source, compare);
		System.out.println("Passed compare " + round++);
	}
	
	@Test
	public void test_scorecard_ui() throws Exception {
		login();
		gotoProperty("standard");

        Matcher m = ps.matcher(driver.getCurrentUrl());
        String sId = m.replaceAll("");

		Thread.sleep(5000);
		
		String[] matchList = {sId, testStandardId};
		source = formatPageSource(matchList);
		
		//remove dynamic tracker id
		source = source.replaceAll("isTracker=[0-9]+", "isTracker=@");
		
		//remove dynamic href
		source = source.replaceAll("a href=#tab[a-z0-9]+", "a href=#tab@");

		//If failure, print page source:
		//System.out.println(source + "\n\n\n\n\n");
		
		//And then NotePad++ -> Edit -> Line Operations -> Split Lines
		//Use cygwin to diff the files

		//Complete page source for domain on IE9. 
		//Let's see how well this test goes. If it fails frequently, 
		//We will have to manually clean/format the page source to remove dynamic values.
		compareSource("<html lang=en xml:lang=en xmlns=http://www.w3.org/1999/xhtml");
		//1
		compareSource("</script><link rel=stylesheet media=screen></div>");
		//2
		compareSource("<div style=width: 380px; float: left; id=esc_logo><div style=text-align: right; margin-top: 20px; margin-right: 5px; float: right; id=loginheader><h3 style=margin: 6px; font-size: 14px;>Welcome,<span class=userhighlight><a href=/profile/show/");
		//3
		compareSource("<div id=nav-wrapper><div id=main-nav><ul class=clearfix></html>");
	}
}
