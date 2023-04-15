package ordertest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class OrderACardTest {
    private WebDriver driver;
    @BeforeAll
    public static void globalSetUp() {
        WebDriverManager.chromedriver().setup();
    //  System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
    }
    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
     //   driver = new ChromeDriver();
        driver.get("http://localhost:9999");
    }
    @Test
    public void orderACardWithValidDataTest() {
        driver.findElement(By.cssSelector("input[name ='name']")).sendKeys("Геворгян Милена");
        driver.findElement(By.cssSelector("input[name ='phone']")).sendKeys("+79758564617");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector("button[role='button']")).click();
        String expected = ("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.");
        String actual = driver.findElement(By.tagName("p")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void orderACardWithHyphenValidDataTest() {
        driver.findElement(By.cssSelector("input[name ='name']")).sendKeys("Геворгян-Милена");
        driver.findElement(By.cssSelector("input[name ='phone']")).sendKeys("+79758564617");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector("button[role='button']")).click();
        String expected = ("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.");
        String actual = driver.findElement(By.tagName("p")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void orderACardWithValidDataWithoutSpaceTest() {
        driver.findElement(By.cssSelector("input[name ='name']")).sendKeys("ГеворгянМилена");
        driver.findElement(By.cssSelector("input[name ='phone']")).sendKeys("+79758564617");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector("button[role='button']")).click();
        String expected = ("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.");
        String actual = driver.findElement(By.tagName("p")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void orderACardWithIncorrectNameTest() {
        driver.findElement(By.cssSelector("input[name ='name']")).sendKeys("Gevorgyan Milena");
        driver.findElement(By.cssSelector("input[name ='phone']")).sendKeys("+79758564617");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector("button[role='button']")).click();
        String expected = ("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.");
        String actual = driver.findElement(By.className("input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void orderACardWithoutNameTest() {
        driver.findElement(By.cssSelector("input[name ='phone']")).sendKeys("+79758564617");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector("button[role='button']")).click();
        String expected = ("Поле обязательно для заполнения");
        String actual = driver.findElement(By.className("input__sub")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void orderACardWithIncorrectPhoneTest() {
        driver.findElement(By.cssSelector("input[name ='name']")).sendKeys("Геворгян Милена");
        driver.findElement(By.cssSelector("input[name ='phone']")).sendKeys("+797585646");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector("button[role='button']")).click();
        String expected = ("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.");
        String actual = driver.findElements(By.className("input__sub")).get(1).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void orderACardWithIncorrectPhoneWithoutPlusTest() {
        driver.findElement(By.cssSelector("input[name ='name']")).sendKeys("Геворгян Милена");
        driver.findElement(By.cssSelector("input[name ='phone']")).sendKeys("797585646");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector("button[role='button']")).click();
        String expected = ("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.");
        String actual = driver.findElements(By.className("input__sub")).get(1).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void orderACardWithoutPhoneTest() {
        driver.findElement(By.cssSelector("input[name ='name']")).sendKeys("Геворгян Милена");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.cssSelector("button[role='button']")).click();
        String expected = ("Поле обязательно для заполнения");
        String actual = driver.findElements(By.className("input__sub")).get(1).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void orderACardWithoutCheckboxTest() {
        driver.findElement(By.cssSelector("input[name ='name']")).sendKeys("Геворгян Милена");
        driver.findElement(By.cssSelector("input[name ='phone']")).sendKeys("+79758564617");
        driver.findElement(By.cssSelector("button[role='button']")).click();
        Assertions.assertTrue(driver.findElement(By.xpath
                        (".//*[contains(@class,' input_invalid')]"))
                .isDisplayed());
    }
    @AfterEach
    public void cleanUp() {
        driver.quit();
    }
}
