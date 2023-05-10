package TODO_Gorest_Posts;


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

public class TODO1_GoRest_Posts {
    // TODO :HaftaSonu TODO:  GoRest de daha önce yaptığınız posts ve comments
    //  resourcelarını


    Faker faker3 = new Faker();
    RequestSpecification reqSpec;


    int postID;


    @BeforeClass
    public void Setup() {

        baseURI = "https://gorest.co.in/public/v2/posts";

        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer 6fdc14a1ab97bf9fecdaa233e63cfee45f2be7ab59d879668664b51639ba2de5")
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test(enabled = true)
    public void createPostClass() {
        Post newPost = new Post();

        newPost.user_id = 1526224;
        newPost.title = faker3.name().title();
        newPost.body = faker3.book().author();

        postID =
                given()
                        .spec(reqSpec)
                        .body(newPost)

                        .log().uri()

                        .when()
                        .post("")

                        .then()
                        .log().body()

                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")
        ;

    }

    @Test(enabled = false)
    public void createPostMap() {

        Map<String, Object> newPost = new HashMap<>();

        newPost.put("user_id", 1526224);
        newPost.put("title", faker3.book().title());
        newPost.put("body", faker3.book().author());

        postID =
                given()
                        .spec(reqSpec)

                        .body(newPost)

                        .when()
                        .post("")

                        .then()
                        .log().body()

                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")
        ;
    }

    @Test(dependsOnMethods = "createPostClass")
    public void getPostByID() {

        given()
                .spec(reqSpec)

                .when()
                .get("" + postID)

                .then()
                .log().body()

                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(postID))
        ;
    }

    @Test(dependsOnMethods = "getPostByID")
    public void updatePostByID() {
        Map<String, Object> newPost = new HashMap<>();

        String title = faker3.book().title();
        String body_ = faker3.book().author();

        newPost.put("user_id", 1526224);
        newPost.put("title", title);
        newPost.put("body", body_);

        given()
                .spec(reqSpec)

                .body(newPost)

                .when()
                .put("" + postID)

                .then()
                .log().body()

                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(postID))
                .body("title", equalTo(title))
                .body("body", equalTo(body_))
        ;

    }

    @Test(dependsOnMethods = "updatePostByID")
    public void deletePostByID() {

        given()
                .spec(reqSpec)

                .when()
                .delete("" + postID)

                .then()
                .log().body()

                .statusCode(204)
        ;
    }

    @Test(dependsOnMethods = "deletePostByID")
    public void deletePostByIDNegative(){

        given()
                .spec(reqSpec)

                .when()
                .delete(""+postID)

                .then()
                .statusCode(404);
    }


}