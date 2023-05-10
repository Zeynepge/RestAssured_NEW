package TODO_Gorest_Comment;

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


/*Eğer bir data yo body de göndereceksek ve içerisinde hem int ve hemde value değerler var ise Map in tanımlama şekli aşağıdaki gibi olmalı
Map<Stringi,Object>  @Student
Map<String,Object> newComment = new HashMap<>();
        newComment.put("post_id",16394);
        newComment.put("name",fullname);
        newComment.put("email",email);
        newComment.put("body",comment);      gibi @Student
Faker her zaman initialize edilmeli    Faker randomGenerator=new Faker();   gibi

 */

public class TODO1_GoRest_Comments {

    Faker faker2=new Faker();
    RequestSpecification reqSpec;
    int commentID;

    @BeforeClass
    public void Setup(){

        baseURI="https://gorest.co.in/public/v2/comments";

        reqSpec=new RequestSpecBuilder()
                .addHeader("Authorization","Bearer 6fdc14a1ab97bf9fecdaa233e63cfee45f2be7ab59d879668664b51639ba2de5")
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test(enabled = true)
    public void createCommentClass(){

        Comment newComment= new Comment();

        newComment.post_id=19471;
        newComment.name=faker2.name().fullName();
        newComment.email=faker2.internet().emailAddress();
        newComment.body=faker2.book().author();

        commentID=
                given()
                        .spec(reqSpec)
                        .body(newComment)

                        .log().uri()

                        .when()
                        .post("")

                        .then()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .log().body()
                        .extract().path("id")
        ;

    }

    @Test(enabled = false)
    public void createCommentMap() {

        Map<String,Object> newComment = new HashMap<>();

        newComment.put("post_id",19471);
        newComment.put("name",faker2.name().fullName());
        newComment.put("email",faker2.internet().emailAddress());
        newComment.put("body",faker2.book().author());


        commentID=
                given()
                        .spec(reqSpec)
                        .body(newComment)

                        .log().uri()

                        .when()
                        .post("")

                        .then()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")
        ;
    }

    @Test(dependsOnMethods = "createCommentClass")
    public void getCommentByID(){

        given()
                .spec(reqSpec)

                .when()
                .get(""+commentID)

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id",equalTo(commentID))
        ;

    }

    @Test(dependsOnMethods = "getCommentByID")
    public void updateCommentByID(){

        Comment newComment= new Comment();
        String name=faker2.name().fullName();
        String email=faker2.internet().emailAddress();
        String body_=faker2.book().author();

        newComment.post_id=19471;
        newComment.name=name;
        newComment.email=email;
        newComment.body=body_;

        given()
                .spec(reqSpec)

                .body(newComment)

                .when()
                .put(""+commentID)

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id",equalTo(commentID))
                .body("name",equalTo(name))
                .body("email",equalTo(email))
                .body("body",equalTo(body_))
        ;

    }

    @Test(dependsOnMethods = "updateCommentByID")
    public void deleteCommentByID()
    {
        given()
                .spec(reqSpec)

                .when()
                .delete(""+commentID)

                .then()
                .log().body()
                .statusCode(204)

        ;
    }

    @Test(dependsOnMethods = "deleteCommentByID")
    public void deleteCommentByIDNegative(){
        given()
                .spec(reqSpec)

                .when()
                .delete(""+commentID)

                .then()
                .statusCode(404)
        ;

    }






}
