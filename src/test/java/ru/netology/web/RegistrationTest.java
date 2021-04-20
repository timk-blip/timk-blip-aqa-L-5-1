package ru.netology.web;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static java.util.concurrent.TimeUnit.DAYS;

public class RegistrationTest {

    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }

    @Test
    void shouldComplexElements() {
        open("http://localhost:9999");

        Faker faker =  new Faker(new Locale("ru"));
        $$("[placeholder=\"Город\"]").last().setValue(faker.address().city());
        Date fak = faker.date().future(25, DAYS);
        LocalDateTime planirovanieDate = LocalDateTime.ofInstant(fak.toInstant(), ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedString = planirovanieDate.format(formatter);
        SelenideElement a = $("[data-test-id=date] input");
        a.sendKeys(Keys.CONTROL + "A");
        a.sendKeys(Keys.BACK_SPACE);
        a.setValue(formattedString);
        $("[data-test-id=name] input").setValue(faker.name().fullName());
        $("[data-test-id=phone] input").setValue(faker.phoneNumber().cellPhone());
        $("[class='checkbox__box']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(withText("Встреча успешно запланирована на"));
    }

    @Test
    void shouldRescheduleTheDateOfTheMeeting(){
        open("http://localhost:9999");

        Faker faker =  new Faker(new Locale("ru"));
        $$("[placeholder=\"Город\"]").last().setValue(faker.address().city());
        Date fak = faker.date().future(25, DAYS);
        LocalDateTime planirovanieDate = LocalDateTime.ofInstant(fak.toInstant(), ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedString = planirovanieDate.format(formatter);
        SelenideElement a = $("[data-test-id=date] input");
        a.sendKeys(Keys.CONTROL + "A");
        a.sendKeys(Keys.BACK_SPACE);
        a.setValue(formattedString);
        $("[data-test-id=name] input").setValue(faker.name().fullName());
        $("[data-test-id=phone] input").setValue(faker.phoneNumber().cellPhone());
        $("[class='checkbox__box']").click();
        $$("button").find(exactText("Запланировать")).click();
        $(withText("Встреча успешно запланирована на"));
        sleep(1000);
        $$("button").find(exactText("Запланировать")).click();
        sleep(1000);
        $(withText("УронитьТест"));
        sleep(1000);
    }


}
