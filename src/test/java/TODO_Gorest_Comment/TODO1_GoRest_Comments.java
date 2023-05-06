package TODO_Gorest_Comment;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;


/*Eğer bir data yo body de göndereceksek ve içerisinde hem int ve hemde value değerler var ise Map in tanımlama şekli aşağıdaki gibi olmalı Map<Stringi,Object>  @Student
Map<String,Object> newComment = new HashMap<>();
        newComment.put("post_id",16394);
        newComment.put("name",fullname);
        newComment.put("email",email);
        newComment.put("body",comment);      gibi @Student
Faker her zaman initialize edilmeli    Faker randomGenerator=new Faker();   gibi

 */


public class TODO1_GoRest_Comments {

    Faker randomfaker3=new Faker();
    RequestSpecification reqSpec;
    int commentID;

    @BeforeClass
    public void Setup(){

        baseURI="https://gorest.co.in/public/v2/comments";

        reqSpec=new RequestSpecBuilder()
                .addHeader("Authorization","Bearer 3f4c352c7990cf988a5366a7baab6260362ca54a8bedc0a416b02e48926443b8")
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test(enabled = true)
    public void createUserClass(){

        Comment newComment= new Comment();

        newComment.post_id=randomfaker3.number().numberBetween(100,20000);
        newComment.name=randomfaker3.name().fullName();
        newComment.email=randomfaker3.internet().emailAddress();
        newComment.body=randomfaker3.book().author();

        commentID=
                given()
                        .spec(reqSpec)
                        .body(newComment)

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

   /* @Test(enabled = false)
    public void createUserMap() {

       // String rndFullname = randomUretici.name().fullName();
        //String rndEmail = randomUretici.internet().emailAddress();

        Map<String,Object> newComment = new HashMap<>();
        newComment.put("post_id",randomfaker2.number().numberBetween(100,20000));
        newComment.put("name",randomfaker2.name().fullName());
        newComment.put("email",randomfaker2.internet().emailAddress());
        newComment.put("body",randomfaker2.book().author());


        commentID=
                given()
                        .spec(reqSpec)
                        .body(newComment)

                        .log().uri()
                        .log().body()

                        .when()
                        .post("")

                        .then()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")
        ;
    }*/




}
