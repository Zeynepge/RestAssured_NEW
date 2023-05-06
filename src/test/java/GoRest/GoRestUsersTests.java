package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GoRestUsersTests
{
    Faker randomUretici=new Faker();
    int userID;

    RequestSpecification reqSpec;

    @BeforeClass
    public void setup(){

        baseURI="https://gorest.co.in/public/v2/users"; // regSpec den önce tanimlanmali

        reqSpec=new RequestSpecBuilder()
                .addHeader("Authorization","Bearer 048bc5c47406287d5b562c7f0e5eb592a1b1726e994dac7870f586f9c8db86b8")
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test (enabled = false)
    public void createUserJson(){

        String rndFullname=randomUretici.name().fullName();
        String rndEmail=randomUretici.internet().emailAddress();

    userID=
        given()
                .spec(reqSpec)
                .body("{\"name\":\""+rndFullname+"\", \n" +
                        "\"gender\":\"male\", \n" +
                        "\"email\":\""+rndEmail+"\",\n" +
                        "\"status\":\"active\"\n" +
                        "}")
                .log().uri()
                .log().body()

                .when()
                .post("")

                .then()
                .log().body()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .extract().path("id")
        ;

    }
    @Test
    public void createUserMap(){

        String rndFullname=randomUretici.name().fullName();
        String rndEmail=randomUretici.internet().emailAddress();

        Map<String,String> newUser=new HashMap<>();
        newUser.put("name",rndFullname);
        newUser.put("gender","male");
        newUser.put("email",rndEmail);
        newUser.put("status","active");


        userID=
                given()
                        .spec(reqSpec)
                        .body(newUser)

                        .log().uri()
                        .log().body()

                        .when()
                        .post("")

                        .then()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id");

    }

    @Test(enabled = false)
    public void createUserClass(){

        String rndFullname=randomUretici.name().fullName();
        String rndEmail=randomUretici.internet().emailAddress();

        User newUser= new User();
        newUser.name=rndFullname;
        newUser.gender="male";
        newUser.email=rndEmail;
        newUser.status="active";

        userID=
                given()
                        .spec(reqSpec)
                        .body(newUser)

                        .log().uri()
                        .log().body()

                        .when()
                        .post("")

                        .then()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")
        ;

    }

    @Test(dependsOnMethods = "createUserMap")
    public void getUserByID(){

        given()
                .spec(reqSpec)

                .when()
                .get(""+userID)

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id",equalTo(userID))
                ;
    }

    @Test(dependsOnMethods = "getUserByID")
    public void updateUserByID(){

        Map<String,String> newUser=new HashMap<>();
        newUser.put("name","zeynep gedik");


        given()
                .spec(reqSpec)

                .body(newUser)

                .when()
                .put(""+userID)

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id",equalTo(userID))
                .body("name",equalTo("zeynep gedik"))
        ;
    }

    @Test(dependsOnMethods = "updateUserByID")
    public void deleteUserByID(){

        given()
                .spec(reqSpec)

                .when()

                .delete(""+userID)

                .then()
                .log().body()
                .statusCode(204)

        ;
    }

    @Test(dependsOnMethods = "deleteUserByID")
    public void deleteUserNegative()
    {
        given()
            .spec(reqSpec)

            .when()

            .delete(""+userID)

            .then()
            .log().body()
            .statusCode(404)

    ;
    }
    // TODO :HaftaSonu TODO:  GoRest de daha önce yaptığınız posts ve comments resourcelarını
    // TODO  API Automation yapınız(Create,get,update,delete,deletenegatife

}