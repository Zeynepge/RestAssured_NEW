import GoRest.User;
import Model.Location;
import Model.Place;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;


public class PathAndJsonPath {

    @Test
    public void extractingPath(){

        // "post code": "90210",

        String postCode=
        given()

                .when()

                .get("https://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .extract().path("'post code'")

                ;

        System.out.println(postCode);

 }
    @Test
    public void extractingJsonPath(){

        // "post code": "90210",

        int postCode=
                given()

                        .when()

                        .get("https://api.zippopotam.us/us/90210")

                        .then()
                        .log().body()
                        .extract().jsonPath().getInt("'post code'")
                // tip dönüsümü otomatik, uygun tip verilmeli

                ;

        System.out.println(postCode);

    }

    @Test
    public void getUser(){

        Response response=
        given()

                .when()
                .get("https://gorest.co.in/public/v2/users")

                .then()
                //.log().body()
                .extract().response()

                ;
    int id_2_path= response.path("[2].id");
    int id_2_JsonPath=response.jsonPath().getInt("[2].id");
        System.out.println(id_2_path);
        System.out.println(id_2_JsonPath);

        User[] usersPath=response.as(User[].class);

        List<User> usersJsonPath=response.jsonPath().getList("", User.class);

        System.out.println("---------------------------------------");
        System.out.println("usersPath = \n"+ Arrays.toString(usersPath));
        System.out.println("---------------------------------------");
        System.out.println("usersJsonPath = \n"+usersJsonPath);

    }

    @Test
    public void getUserV1(){

        Response body=
        given()

                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
               // .log().body()
                .extract().response()

        ;

        List<User> dataUsers=body.jsonPath().getList("data",User.class);
        // JSONPATH bir response içindeki bir parçayı nesneye ödnüştürebiliriz.
        System.out.println("dataUsers = "+dataUsers);

        // Daha önceki örneklerde (as) Class dönüşümleri için tüm yapıya karşılık gelen
        // gereken tüm classları yazarak dönüştürüp istediğimiz elemanlara ulaşıyorduk.

        // Burada ise(JsonPath) aradaki bir veriyi classa dönüştürerek bir list olarak almamıza
        // imkan veren JSONPATH i kullandık.Böylece tek class ile veri alınmış oldu
        // diğer class lara gerek kalmadan

        // path : class veya tip dönüşümüne imkan veremeyen direk veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.

    }

    @Test
    public void getZipCode(){
Response response=
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
               //.log().body()

                .extract().response()

                ;

        Location locPathAs=response.as(Location.class); // bütün classlari yazmak zorundasin
        System.out.println("locPAthAs = "+locPathAs.getPlaces());

        List<Place> places=response.jsonPath().getList("places",Place.class);// nokta atisi istedigimiz nesneyi aldik
        System.out.println(places);

    }



}
