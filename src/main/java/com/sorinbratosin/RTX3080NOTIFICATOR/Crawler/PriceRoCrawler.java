package com.sorinbratosin.RTX3080NOTIFICATOR.Crawler;

import com.sorinbratosin.RTX3080NOTIFICATOR.Database.GPU;
import com.sorinbratosin.RTX3080NOTIFICATOR.Helper.FormatPrice;
import com.sorinbratosin.RTX3080NOTIFICATOR.Service.GPUService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class PriceRoCrawler {

    @Autowired
    GPUService gpuService;



    @Scheduled(fixedRate = 6000000)
    public void run() throws InterruptedException {
        extract("https://www.price.ro/index.php?action=q&f=1&text=rtx%203080&pcategory=Placi%20grafice&orderBy=pret&asc=1");
    }

    private void extract(String url) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\IntelliJ projects\\RTX3080-NOTIFICATOR\\RTX3080-NOTIFICATOR\\src\\main\\resources\\res\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        options.addArguments("start-maximized");
        WebDriver driver = new ChromeDriver(options);
        driver.get(url);

        WebElement firstGpuTitle = driver.findElement(By.cssSelector(".produs-lista .prod-content .titlu-text h2 a"));
        firstGpuTitle.click();
        try {
            WebElement sortAscendingButton = driver.findElement(By.cssSelector(".t-cr .bg-fundal .wrapper .content div .page-tabs a .f-right div:nth-of-type(1)"));
            sortAscendingButton.click();
        } catch (NoSuchElementException ignored) {

        }

        String page = driver.getPageSource();

        Document doc = Jsoup.parse(page);
        Elements elements = doc.select(".produs-lista");

        String name = elements.first().select(".pret-box-container .pret-box .titlu .titlu-text .desc-box").text();
        String price = elements.first().select(".pret-box-container .pret-box .de-la a .price").text();

        WebElement toStoreButton = driver.findElement(By.cssSelector(".spre-magazin-box a .spre-magazin"));
        toStoreButton.click();
        ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
        driver.close();
        driver.switchTo().window(tabs2.get(1));
        Thread.sleep(3000);
        String storeUrl = driver.getCurrentUrl();

        driver.quit();

        Instant instant = Instant.now();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String timestamp = localDateTime.format(formatter);

        GPU gpu = new GPU();
        gpu.setName(name);
        gpu.setUrl(storeUrl);
        gpu.setPrice(FormatPrice.format(price));
        gpu.setTimestamp(timestamp);

        gpuService.save(gpu);
    }
}
