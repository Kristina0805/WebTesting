package ru.netology;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.util.List;


public class RequestTest {

    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }


    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void correctFillingTest() {
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Трапезникова Кристина-Игоревна");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79227280798");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        Assertions.assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());

    }

    @Test
    void wrongNumberTest(){
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Трапезникова Кристина");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("79227280798");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        Assertions.assertEquals ("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());

    }

    @Test
    void invalidNameTest(){
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Trapeznikova Kristina");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79227280798");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        Assertions.assertEquals ("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());

    }
    @Test
    void emptyName(){
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79227280798");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        Assertions.assertEquals ("Поле обязательно для заполнения", text.trim());
    }
    @Test
    void emptyPhone(){
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Трапезникова Кристина");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        Assertions.assertEquals ("Поле обязательно для заполнения", text.trim());

    }
    @Test
    void emptyCheckbox(){
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Трапезникова Кристина");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79227280798");
        driver.findElement(By.className("button_view_extra")).click();
               String text = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text")).getText();
        Assertions.assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных" +
                " и разрешаю сделать запрос в бюро кредитных историй", text.trim());

    }


}
