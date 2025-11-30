package Pages;

import Endpoints.User;
import Endpoints.Pet;
import Endpoints.Store;
import io.restassured.response.Response;
import models.UserPayload;
import models.PetPayload;
import models.OrderPayload;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
public class IntegrationFlowTests extends BaseTest
        {

    User userEndpoint = new User();
    Pet petEndpoint = new Pet();
    Store storeEndpoint = new Store();

    // بيانات اليوزر اللي هنمشي بيها الفلو كله
    String username = "fatma_integration";
    String password = "123456";

    // هانخزن فيهم القيم اللي جاية من الـ APIs
    long petId;
    long orderId;

    // ===== Helpers لبناء الـ Payloads =====

    private UserPayload buildUserPayload(long id, String username, String firstName,
                                         String lastName, String email,
                                         String password, String phone, long userStatus) {

        UserPayload user = new UserPayload();
        user.id = id;
        user.username = username;
        user.firstName = firstName;
        user.lastName = lastName;
        user.email = email;
        user.password = password;
        user.phone = phone;
        user.userStatus = userStatus;
        return user;
    }

    private PetPayload buildPetPayload(long id, String name, String status) {
        PetPayload pet = new PetPayload();
        pet.id = id;          // ممكن تبقي 0 وتسيبي الـ API يولّد
        pet.name = name;
        pet.status = status;  // مثلا: "available"
        return pet;
    }

    private OrderPayload buildOrderPayload(long id, long petId, int quantity,
                                           String shipDate, String status, boolean complete) {
        OrderPayload order = new OrderPayload();
        order.id = id;       // ممكن 0 برضه
        order.petId = petId;
        order.quantity = quantity;
        order.shipDate = shipDate;
        order.status = status;
        order.complete = complete;
        return order;
    }

    // ====== 1) Register a new User (POST /user) ======
    @Test(priority = 1)
    public void registerUser() {
        UserPayload payload = buildUserPayload(
                0,
                username,
                "Fatma",
                "Tester",
                "fatma@example.com",
                password,
                "0100000000",
                1
        );

        Response response = userEndpoint.addNewUser(payload);

        response.then()
                .statusCode(200)
                .body("code", equalTo(200))
                .body("type", equalTo("unknown"));
        // مش هنعتمد على message لأنه random في PetStore
    }

    // ====== 2) Login with this User (GET /user/login) ======
    @Test(priority = 2)
    public void loginUser() {
        Response response = userEndpoint.loginUser(username, password);

        response.then()
                .statusCode(200)
                .body("code", equalTo(200))
                .body("message", containsString("logged in user session"));
    }

    // ====== 3) Add a new Pet (POST /pet) and store petId ======
    @Test(priority = 3)
    public void addPet() {
        PetPayload petPayload = buildPetPayload(
                0,
                "fatma-pet",
                "available"
        );

        Response response = petEndpoint.addNewPet(petPayload);

        petId = response.then()
                .statusCode(200)
                .body("name", equalTo("fatma-pet"))
                .body("status", equalTo("available"))
                .extract()
                .path("id");

        Assert.assertTrue(petId > 0, "petId should be > 0");
    }

    // ====== 4) Place an Order for this Pet (POST /store/order) ======
    @Test(priority = 4)
    public void placeOrderForPet() {

        // shipDate أي سترينج ISO تقريبا بيعدّي
        OrderPayload orderPayload = buildOrderPayload(
                0,
                petId,
                1,
                "2025-11-26T19:48:55.421Z",
                "placed",
                true
        );

        Response response = storeEndpoint.placeOrder(orderPayload);

        orderId = response.then()
                .statusCode(200)
                .body("quantity", equalTo(1))
                .body("status", equalTo("placed"))
                .body("complete", equalTo(true))
                .body("id", notNullValue())
                .extract()
                .path("id");

        Assert.assertTrue(orderId > 0, "orderId should be > 0");
    }

    // ====== 5) Verify Order Details (GET /store/order/{orderId}) ======
    @Test(priority = 5)
    public void verifyOrderDetails() {
        Response response = storeEndpoint.getOrderById(orderId);

        response.then()
                .statusCode(200)
                .body("id", equalTo((orderId) ))   // الـ API بترجع الـ id كـ int
                .body("quantity", equalTo(1))
                .body("status", equalTo("placed"))
                .body("complete", equalTo(true));
        // مش هنشيك على petId عشان الـ API ساعات بتلعب فيه
    }



    // ====== 6) Delete the Order (DELETE /store/order/{orderId}) ======
    @Test(priority = 6)
    public void deleteOrder() {

        // 1) امسحي الأوردر
        storeEndpoint.deleteOrder(orderId)
                .then()
                .statusCode(200);

        // 2) تأكدي إنه اتمسح فعلاً عن طريق GET بعد الحذف
        storeEndpoint.getOrderById(orderId)
                .then()
                .statusCode(404)
                // الرسالة في Swagger ساعات بتبقى "Order not found" وساعات "Order Not Found"
                .body("message", anyOf(equalTo("Order not found"), equalTo("Order Not Found")));
    }



    // ====== 7) Logout the User (GET /user/logout) ======
    @Test(priority = 7)
    public void logoutUser() {
        Response response = userEndpoint.logoutUser();

        response.then()
                .statusCode(200)
                .body("code", equalTo(200))
                .body("message", equalTo("ok"));
    }
}
