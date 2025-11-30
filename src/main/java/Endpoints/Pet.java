package Endpoints;

import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.qameta.allure.restassured.AllureRestAssured;


import models.PetPayload;

public class Pet {

    // Base request
    private RequestSpecification baseRequest = given()
            .baseUri("https://petstore.swagger.io/v2")
            .basePath("/pet")
            .filter(new AllureRestAssured())
            .accept(ContentType.JSON);

    public Response addNewPet(PetPayload petPayload) {
        return baseRequest
                .contentType(ContentType.JSON)
                .body(petPayload)   // مفيش JSON manual هنا
                .when()
                .post();
    }

    public Response updatePet(PetPayload petPayload) {
        return baseRequest
                .contentType(ContentType.JSON)
                .body(petPayload)
                .when()
                .put();
    }

    public Response getPet(String id) {
        return baseRequest
                .when()
                .get("/" + id);
    }

    public Response deletePet(String id) {
        return baseRequest
                .when()
                .delete("/" + id);
    }
}
