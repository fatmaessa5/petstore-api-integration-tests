package Pages;

import Endpoints.Pet;
import models.Category;
import models.PetPayload;
import models.PetTag;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
public class PetTests extends BaseTest {

    Pet petEndpoint = new Pet();

    private PetPayload buildPetPayload(long id, String petName,
                                       long categoryId, String categoryName,
                                       String status)
    {

        PetPayload pet = new PetPayload();

        pet.id = id;

        pet.category = new Category();
        pet.category.id = categoryId;
        pet.category.name = categoryName;

        pet.name = petName;

        pet.photoUrls = Arrays.asList("string"); // أو أي URLs تحبيها

        pet.tags = Arrays.asList(
                new PetTag(1, "tag1"),
                new PetTag(2, "tag2")
        );

        pet.status = status;

        return pet;
    }

    @Test(priority = 1)
    public void addNewPet() {
        PetPayload payload = buildPetPayload(
                4, "DoggieDo",
                5, "My_Petso",
                "available"
        );

        petEndpoint.addNewPet(payload)
                .then()
                .statusCode(200);
    }

    @Test(priority = 2)
    public void getSinglePet() {
        petEndpoint.getPet("4")
                .then()
                .statusCode(200)
                .body("name", equalTo("DoggieDo"))
                .body("category.name", equalTo("My_Petso"))
                .body("tags[0].name", equalTo("tag1"))
                .body("tags[1].name", equalTo("tag2"));
    }

    @Test(priority = 3)
    public void updateSinglePet() {
        PetPayload updated = buildPetPayload(
                4, "DobyDo",
                5, "My_Petso1",
                "available"
        );

        petEndpoint.updatePet(updated)
                .then()
                .statusCode(200)
                .body("name", equalTo("DobyDo"))
                .body("category.name", equalTo("My_Petso1"))
                .body("tags[0].name", equalTo("tag1"))
                .body("tags[1].name", equalTo("tag2"));
    }

    @Test(priority = 4)
    public void getUpdatedSinglePet() {
        petEndpoint.getPet("4")
                .then()
                .statusCode(200)
                .body("name", equalTo("DobyDo"))
                .body("category.name", equalTo("My_Petso1"))
                .body("tags[0].name", equalTo("tag1"))
                .body("tags[1].name", equalTo("tag2"));
    }
}
