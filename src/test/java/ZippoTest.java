import Model.Location;
import Model.Place;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ZippoTest {

    @Test
    public void test() {
        given().when().then();
    }

    @Test
    public void statusCodeTest() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()    // dönen body json datat sı,   log.all()
                .statusCode(200) // dönüş kodu 200 mü
        ;
    }

    @Test
    public void contentTypeTest() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()    // dönen body json datat sı,   log.all()
                .statusCode(200) // dönüş kodu 200 mü
                .contentType(ContentType.JSON) // dönen sonuç JSON mı
        ;
    }

    @Test
    public void checkCountrInResponseBody() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .body("country", equalTo("United States"))

        ;
    }
    // PM                            RestAssured
    //body.country                  body("country")
    //body.'post code'              body("post code")
    //body.places[0].'place name'   body("places[0].'place name'")
    //body.places.'place name'      body("places.'place name'")
    //bütün place nameleri bir arraylist olarak verir

    @Test
    public void checkstateInResponseBody() {
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .body("places[0].state", equalTo("California"))

        ;
    }

    @Test
    public void checkHasItemy() {
        given()
                .when()
                .get("http://api.zippopotam.us/tr/46000")

                .then()
                //.log().body()
                .statusCode(200)
                .body("places.'place name'", hasItem("Göllü Köyü"))
        // bütün place name lerin herhangi birinde Dörtağaç Köyü varmı
        ;
    }

    @Test
    public void bodyArrayHasSizeTest() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .body("places", hasSize(1))

        ;

    }

    @Test
    public void combiningTest() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .body("places", hasSize(1))// size i 1 mi
                .body("places.state", hasItem("California"))
                .body("places[0].'place name'", equalTo("Beverly Hills"))
        ;
    }

    //http://api.zippopotam.us/us/90210    path PARAM

    //https://sonuc.osym.gov.tr/Sorgu.aspx?SonucID=9617  Query PARAM


    @Test
    public void pathParamTest() {
        given()
                .pathParam("ulke", "us")
                .pathParam("postaKod", 90210)
                .log().uri()


                .when()
                .get("http://api.zippopotam.us/{ulke}/{postaKod}")

                .then()
                .statusCode(200)
        // .log().body()

        ;
    }

    @Test
    public void queryParamTest() {
        given()
                .param("page", 1)
                .log().uri() //request Link

                .when()
                .get("https://gorest.co.in/public/v1/users")//?page=1

                .then()
                .statusCode(200)
                .log().body()


        ;
    }

    @Test
    public void queryParamTest2() {
        // https://gorest.co.in/public/v1/users?page=3
        // bu linkteki 1 den 10 kadar sayfaları çağırdığınızda response daki donen page degerlerinin
        // çağrılan page nosu ile aynı olup olmadığını kontrol ediniz.

        for (int i = 1; i < 11; i++) {


            given()
                    .param("page", i)
                    .log().uri() //request Link

                    .when()
                    .get("https://gorest.co.in/public/v1/users")//?page=1

                    .then()
                    .statusCode(200)
                    //.log().body()
                    .body("meta.pagination.page", equalTo(i))

            ;
        }
    }

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;


    @BeforeClass
    public void Setup() {
        baseURI = "https://gorest.co.in/public/v1";

        requestSpec = new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setContentType(ContentType.JSON)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .log(LogDetail.BODY)
                .build();
    }


    @Test
    public void requestResponseSpecification() {
        given()
                .param("page", 1)
                .spec(requestSpec)

                .when()
                .get("/users")//?page=1

                .then()
                .spec(responseSpec)

        ;
    }


    @Test
    public void extractingJsonPath() {
        String countryName = given()
                .when()
                .get("https://api.zippopotam.us/us/90210")

                .then()
                .log().body()

                .extract().path("country");
        System.out.println("countryName : " + countryName);
        Assert.assertEquals(countryName, "United States");

        String placeName = given()
                .when()
                .get("https://api.zippopotam.us/us/90210")

                .then()
                .extract().path("places[0].'place name'");
        System.out.println("placeName : " + placeName);
        Assert.assertEquals(placeName, "Beverly Hills");
    }


    @Test
    public void extractingJsonPath3() {
        // https://gorest.co.in/public/v1/users  dönen değerdeki limit bilgisini yazdırınız.

        int limit=  given()
                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
                .log().body()
                .statusCode(200)
                .extract().path("meta.pagination.limit")

                ;
        System.out.println("limit : " + limit);


    }


    @Test
    public void extractingJsonPath4(){

        // https://gorest.co.in/public/v1/users  dönen değerdeki bütün idleri yazdiriniz.

        ArrayList<Integer> idAl;
        //List<Integer> ids;

        idAl=    given()
            .when()
            .get("https://gorest.co.in/public/v1/users")

            .then()
                //.log().body()
            .statusCode(200)
            .extract().path("data.id")

            ;

        System.out.println("ID : "+idAl);

    }

    @Test
    public void extractingJsonPath5(){
        List<String> names;
        names=given()

                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
                .statusCode(200)
                .extract().path("data.name")

                ;
        System.out.println("isimler : "+names);

    }

    @Test
    public void extractingJsonPathResponsAll(){

        Response donenData =
                given()

                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
                .statusCode(200)
                .extract().response()

        ;
        //System.out.println("isimler : "+names);
List<Integer> idler= donenData.path("data.id");
List<String> names=donenData.path("data.name");
List<String> emails=donenData.path("data.email");
List<String> genders=donenData.path("data.gender");
List<String> status=donenData.path("data.status");
int limit=donenData.path("meta.pagination.limit");
int pages=donenData.path("meta.pagination.pages");


        System.out.println(genders);
        System.out.println(names);

    }

    @Test
    public void extractJsonAll_POJO(){
        // POJO JSON nesnesi :locationNesnesi
        Location locationNesnesi=
        given()

                .when()
                .get("https://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .extract().body().as(Location.class)
                 // location sablonuna

        ;
        System.out.println(locationNesnesi.getCountry());

        for (Place p:locationNesnesi.getPlaces()) {
            System.out.println(p);
        }

        System.out.println(locationNesnesi.getPlaces().get(0).getPlacename());


    }

    @Test
    public void extractPOJO_Soru() {
        // asagidaki endpointte Göllü Köyüne ait diger bilgileri yazdiriniz

        Location locationNesnesi=       given()
                .when()
                .get("http://api.zippopotam.us/tr/46000")

                .then()
                .statusCode(200)
                //.body("places.'place name'", hasItem("Kemalli Köyü"))
                .extract().body().as(Location.class)
        ;
        for (Place p:locationNesnesi.getPlaces()) {
            if (p.getPlacename().equalsIgnoreCase("Kemalli Köyü"))
            System.out.println(p);
        }

    }




}