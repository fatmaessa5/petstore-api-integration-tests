package Endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.PetPayload;
import models.UserPayload;
import io.qameta.allure.restassured.AllureRestAssured;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class User {

    // Base request
    private RequestSpecification baseRequest = given()
            .baseUri("https://petstore.swagger.io/v2")
            .basePath("/user")
            .filter(new AllureRestAssured())
            .accept(ContentType.JSON);



    public Response addNewUser(UserPayload userPayload) {
        return baseRequest
                .contentType(ContentType.JSON)
                .body(userPayload)
                .when()
                .post();
    }
    public Response getUsername(String username) {
        return baseRequest
                .contentType(ContentType.JSON).when()
                .get("/"+username);


    }

    // PUT /user/{username}
    public Response updateUser(String username, UserPayload userPayload) {
        return baseRequest
                .body(userPayload)
                .when()
                .put("/{username}", username);
    }

    // DELETE /user/{username}
    public Response deleteUser(String username) {
        return baseRequest
                .when()
                .delete("/{username}", username);
    }

    // GET /user/login
    public Response loginUser(String username, String password) {
        return baseRequest
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get("/login");
    }

    // GET /user/logout
    public Response logoutUser() {
        return baseRequest
                .when()
                .get("/logout");
    }


}
