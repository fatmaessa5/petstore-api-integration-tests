package Pages;

import Endpoints.User;
import models.UserPayload;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserTests  extends BaseTest {
    User userEndpoint = new User();

    private UserPayload buildUserPayload(long id,String username ,String firstName , String lastName,
          String email  ,String password ,String phone ,long userStatus )
    {

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

    @Test(priority = 1)
    public void addNewUser() {
        UserPayload payload = buildUserPayload(
                4, "new",
                "user1", "user2",
                "available","10101010","01010101",1
        );

        userEndpoint.addNewUser(payload)
                .then()
                .statusCode(200)
                .body("code", equalTo(200))
                .body("type", equalTo("unknown"));

    }
    // 2) Get user + verify details
    @Test(priority = 2)
    public void getUsername() {
        userEndpoint.getUsername("new")
                .then()
                .statusCode(200)
                .body("firstName", equalTo("user1"))
                .body("lastName", equalTo("user2"))
                .body("email", equalTo("available"))
                .body("password", equalTo("10101010"))
                .body("phone", equalTo("01010101"))
                .body("userStatus", equalTo(1));
    }



    // 3) Update user + confirm changes
    @Test(priority = 3)
    public void updateUser() {
        UserPayload updated = buildUserPayload(
                4, "USERNAME",
                "updatedFirst", "updatedLast",
                "updated@mail.com","20202020","02020202",2
        );

        userEndpoint.updateUser("USERNAME", updated)
                .then()
                .statusCode(200);

        // تأكيد التغييرات
        userEndpoint.getUsername("USERNAME")
                .then()
                .statusCode(200)
                .body("firstName", equalTo("updatedFirst"))
                .body("lastName", equalTo("updatedLast"))
                .body("email", equalTo("updated@mail.com"))
                .body("password", equalTo("20202020"))
                .body("phone", equalTo("02020202"))
                .body("userStatus", equalTo(2));
    }



    // 4) Delete user + verify deletion
    @Test(priority = 4)
    public void deleteUser() {
        userEndpoint.deleteUser("USERNAME")
                .then()
                .statusCode(200);

        // verify deletion – المفروض يرجّع 404
        userEndpoint.getUsername("USERNAME")
                .then()
                .statusCode(404);
    }




}
