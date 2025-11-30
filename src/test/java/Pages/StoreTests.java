package Pages;

import Endpoints.Store;
import models.OrderPayload;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;


import static org.hamcrest.Matchers.*;
import io.restassured.response.Response;

public class StoreTests extends BaseTest {

    Store storeEndpoint = new Store();

    // هنحفظ الـ orderId اللي السيرفر يولّده عشان نستخدمه في GET و DELETE
    private long orderId;

    private OrderPayload buildOrderPayload(long id, long petId, int quantity,
                                           String shipDate, String status, boolean complete) {

        OrderPayload order = new OrderPayload();
        order.id = id;
        order.petId = petId;
        order.quantity = quantity;
        order.shipDate = shipDate;
        order.status = status;
        order.complete = complete;
        return order;
    }

    // 9 - Place an Order (POST /store/order) and verify order ID is generated.
    @Test(priority = 1)
    public void placeOrder() {

        OrderPayload payload = buildOrderPayload(
                0,
                0,
                1,
                "2025-11-26T19:48:55.421Z",
                "placed",
                true
        );
        // نحتفظ بالـ response عشان نطلع منه الـ id
        Response response = storeEndpoint.placeOrder(payload);

        orderId = response.then()
                .statusCode(200)
                .body("petId", equalTo(0))
                .body("quantity", equalTo(1))
                .body("status", equalTo("placed"))
                .body("complete", equalTo(true))
                .body("id", notNullValue())
                .extract()
                .path("id");

        Assert.assertTrue(orderId > 0);
    }

    // 10 - Get Order by ID (GET /store/order/{orderId}) and validate order details.
    @Test(priority = 2)
    public void getOrderById() {

        Response response = storeEndpoint.getOrderById(orderId);

        long fetchedId = response.then()
                .statusCode(200)
                .body("petId", equalTo(0))
                .body("quantity", equalTo(1))
                .body("status", equalTo("placed"))
                .body("complete", equalTo(true))
                .extract()
                .path("id");

        Assert.assertEquals(fetchedId, orderId);
    }


    // 11 - Delete Order (DELETE /store/order/{orderId}) and verify it is removed.
    @Test(priority = 3)
    public void deleteOrder() {

        storeEndpoint.deleteOrder(orderId)
                .then()
                .statusCode(200);

        storeEndpoint.getOrderById(orderId)
                .then()
                .statusCode(404)
                .body("message", equalTo("Order not found"));
    }


    // 12 - Check Inventory (GET /store/inventory) and validate response structure.
    @Test(priority = 4)
    public void checkInventory() {

        Response response = storeEndpoint.getInventory();

        response.then()
                .statusCode(200)
                .body("$", notNullValue())
                .body("$", aMapWithSize(greaterThan(0)))
                .body("available", greaterThanOrEqualTo(0))
                .body("pending", greaterThanOrEqualTo(0))
                .body("sold", greaterThanOrEqualTo(0));
    }

}
