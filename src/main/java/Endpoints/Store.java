package Endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.OrderPayload;   // هشرحه تحت لو مش عندك

import static io.restassured.RestAssured.given;
import io.qameta.allure.restassured.AllureRestAssured;

public class Store
{
    private RequestSpecification baseRequest = given()
            .baseUri("https://petstore.swagger.io/v2")
            .basePath("/store")
            .filter(new AllureRestAssured())
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON);

    // 9) POST /store/order
    public Response placeOrder(OrderPayload orderPayload) {
        return baseRequest
                .body(orderPayload)
                .when()
                .post("/order");
    }

    // 10) GET /store/order/{orderId}
    public Response getOrderById(long orderId) {
        return baseRequest
                .when()
                .get("/order/{orderId}", orderId);
    }

    // 11) DELETE /store/order/{orderId}
    public Response deleteOrder(long orderId) {
        return baseRequest
                .when()
                .delete("/order/" + orderId);
    }


    // 12) GET /store/inventory
    public Response getInventory() {
        return baseRequest
                .when()
                .get("/inventory");
    }
}
