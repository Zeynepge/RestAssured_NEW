import Model.toDo;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Tasks {

    @Test
    public void task2(){
        /**

         Task 2
         create a request to https://httpstat.us/203
         expect status 203
         expect content type TEXT
         */

        given()

                .when()
                .get("https://httpstat.us/203")

                .then()
                .log().all()    // dönen body json datat sı,   log.all()
                .statusCode(203) // dönüş kodu 200 mü
                .contentType(ContentType.TEXT) // dönen sonuç JSON mı
        ;

    }

    @Test
    public void task1(){
        /** Task 1

         create a request to https://jsonplaceholder.typicode.com/todos/2
        expect status 200
        Converting Into POJO*/

        toDo nesnebilgi=
                given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().body().as(toDo.class)

                ;

        System.out.println(nesnebilgi);



    }

    @Test
    public void task3(){
/**

 Task 3
 create a request to https://jsonplaceholder.typicode.com/todos/2
 expect status 200
 expect content type JSON
 expect title in response body to be "quis ut nam facilis et officia qui"
 */
String titleVer=
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                //.log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().path("title")
                ;
        System.out.println(titleVer);
        Assert.assertEquals(titleVer,"quis ut nam facilis et officia qui")
        ;

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                //.log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title",equalTo("quis ut nam facilis et officia qui"))
            ;


    }

    @Test
    public void task4(){
/**

 Task 4
 create a request to https://jsonplaceholder.typicode.com/todos/2
 expect status 200
 expect content type JSON
 expect response completed status to be false
 extract completed field and testNG assertion
 */

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                //.log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completed",equalTo(false))

        ;
        Boolean c=
                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")

                        .then()
                        //.log().all()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract().path("completed")
                ;
        System.out.println(c);
        Assert.assertFalse(c);

    }


}
