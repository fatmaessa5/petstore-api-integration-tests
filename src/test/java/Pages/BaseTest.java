package Pages;

import io.restassured.RestAssured;
import io.qameta.allure.restassured.AllureRestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    @BeforeClass
    public void setupAllure() {
        RestAssured.filters(new AllureRestAssured());
    }
}
