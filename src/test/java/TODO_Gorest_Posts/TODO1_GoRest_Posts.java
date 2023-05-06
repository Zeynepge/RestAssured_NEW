package TODO_Gorest_Posts;


import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class TODO1_GoRest_Posts {
    // TODO :HaftaSonu TODO:  GoRest de daha önce yaptığınız posts ve comments
    //  resourcelarını


    Faker faker=new Faker();
    RequestSpecification reqSpec;


    int postID;


    @BeforeClass
    public void Setup(){

        baseURI="https://gorest.co.in/public/v2/posts";

        reqSpec=new RequestSpecBuilder()
                .addHeader("Authorization","Bearer 3f4c352c7990cf988a5366a7baab6260362ca54a8bedc0a416b02e48926443b8")
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void createPostJson(){
       /* Post newPost=new Post();

        newPost.user_id=faker.number().numberBetween(100,20000);
        newPost.title=faker.name().title();
        newPost.body=faker.book().author();*/

        int _id=faker.number().numberBetween(100,20000);
        int us_id=faker.number().numberBetween(100,20000);
        String title=faker.name().title();
        String body =faker.book().author();


        postID=
                given()
                        .spec(reqSpec)
                        .body("{\"user_id\":"+us_id+", \n" +
                                "\"title\": \""+title+"\",\n" +
                                "\"body\": \""+body+"\"\n" +
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
        System.out.println("postID : "+postID);

    }



}
