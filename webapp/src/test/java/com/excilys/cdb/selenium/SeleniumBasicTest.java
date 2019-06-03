package com.excilys.cdb.selenium;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumBasicTest {
	private WebDriver driver;
    private final String PROJECT_URL = "http://localhost:8080/computer-database";
    private final String PAGINATION_PATTERN = "//ul[@class='pagination']";
    
    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setupTest() {
        driver = new ChromeDriver();
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void test() {
    	driver.get(PROJECT_URL);
    	WebElement pagination = driver.findElement(By.xpath(PAGINATION_PATTERN));
    	
    	List<String> realValues = pagination.findElements(By.xpath("li")).stream().map(WebElement::getText).collect(Collectors.toList());
    	List<String> expectedValues = Arrays.asList("«","1","2","3","4","5","»");
    	
    	assertEquals(expectedValues,realValues);    	
    }

}
