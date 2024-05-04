package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment= RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest{
    @LocalServerPort
    private int serverPort;

    @Value("$(app.baseUrl:http://localhost)")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest(){
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) throws Exception {
        // Exercise
        driver.get(baseUrl);
        String pageTitle = driver.getTitle();

        // Verify
        assertEquals("ADV Shop", pageTitle);
    }

    @Test
    void createProduct(ChromeDriver driver) throws Exception{
        driver.get(baseUrl);
        driver.findElement(By.tagName("a")).click();

        // Mengisi form create
        driver.findElement(By.name("productName")).sendKeys("Sampo Pak Bambang");
        driver.findElement(By.name("productQuantity")).sendKeys("20");
        driver.findElement(By.tagName("button")).click();

        // Mendapatkan product list
        driver.get("http://localhost:8080/product/list");

        // Mencari nama product yang diinginkan lalu mengecek apakah sesuai
        WebElement productNameElement = driver.findElement(By.xpath("//td[text()='Sampo Pak Bambang']"));
        assertEquals("Sampo Pak Bambang", productNameElement.getText());
    }

    @Test
    void deleteProduct(ChromeDriver driver) throws Exception{
        driver.get(baseUrl);
        driver.findElement(By.tagName("a")).click();

        // Mengisi form create
        driver.findElement(By.name("productName")).sendKeys("Sampo Pak Bambang");
        driver.findElement(By.name("productQuantity")).sendKeys("20");
        driver.findElement(By.tagName("button")).click();

        // Menghapus product yang telah dibuat
        WebElement productRow = driver.findElement(By.xpath("//td[text()='Sampo Pak Bambang']/following-sibling::td[text()='20']/.."));
        WebElement deleteButton = productRow.findElement(By.xpath(".//a[text()='Delete']"));
        deleteButton.click();

        if (isElementPresent(driver, "//td[text()='Sampo Pak Bambang']/following-sibling::td[text()='20']/..")) {
            Assertions.fail("Product row still exists after deletion");
        }
    }

    private boolean isElementPresent(ChromeDriver driver, String xpath) {
        try {
            driver.findElement(By.xpath(xpath));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}