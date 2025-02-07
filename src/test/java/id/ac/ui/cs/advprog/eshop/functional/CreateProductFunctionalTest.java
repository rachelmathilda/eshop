package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;
    private String baseUrl;
    private String listUrl;
    private String createUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
        listUrl = String.format("%s/product/list", baseUrl);
        createUrl = String.format("%s/product/create", baseUrl);
    }

    @Test
    void listPageTitle_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(listUrl);
        String pageTitle = driver.getTitle();
        assertEquals("Product List", pageTitle);
    }

    @Test
    void listMessage_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(listUrl);
        String listMessage = driver.findElement(By.tagName("h2"))
                .getText();
        assertEquals("Product' List", listMessage);
    }

    @Test
    void createProductButton_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(listUrl);
        WebElement createButton=driver.findElement(By.cssSelector("a.btn.btn-primary.btn-sm"));
        createButton.click();
    }

    @Test
    void createPageTitle_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(createUrl);
        String pageTitle = driver.getTitle();
        assertEquals("Create New Product", pageTitle);
    }

    @Test
    void createMessage_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(createUrl);
        String createMessage = driver.findElement(By.tagName("h3"))
                .getText();
        assertEquals("Create New Product", createMessage);
    }

    @Test
    void nameInput_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(createUrl);
        WebElement nameInput=driver.findElement(By.id("nameInput"));
        nameInput.clear();

        String name="Sampo Cap Bambang";
        nameInput.sendKeys(name);
    }

    @Test
    void quantityInput_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(createUrl);
        WebElement quantityInput=driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        String quantity="100";
        quantityInput.sendKeys(quantity);
    }

    @Test
    void submitButton_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(createUrl);
        WebElement submitButton=driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();
    }
}