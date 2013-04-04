import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@SuiteClasses(value = { 
	Test107.class,  // Download Excel
        Test13.class,  // Upload file
	Test19.class,  // UI comparison test
	Test2.class,  // Wait and assert
	Test103.class,  // Click SVG
	Test108.class,  // Delete units
})
public class TestSuite3 {}