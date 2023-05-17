package Campus;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CountryTest {

    Faker faker=new Faker();
    RequestSpecification reqSpec;

    String countryName;
    String countryCode;
    String countryID;

    @BeforeClass
    public void Setup() throws IOException {

        baseURI="https://test.mersys.io";

        String path="src/test/java/Excel_Data/Campus_Data.xlsx";

        FileInputStream inputStream=new FileInputStream(path);
        Workbook workbook= WorkbookFactory.create(inputStream);
        Sheet sheet=workbook.getSheetAt(0);

        String userName=String.valueOf(sheet.getRow(1).getCell(0));
        String password=String.valueOf(sheet.getRow(1).getCell(1));

        Map<String,String > userCredential=new HashMap<>();
        userCredential.put("username",userName);
        userCredential.put("password",password);
        userCredential.put("rememberMe","true");

        Cookies cookies=
                given()
                        .contentType(ContentType.JSON)
                        .body(userCredential)

                        .when()
                        .post("/auth/login")

                        .then()
                        //.log().all()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()

                ;

        reqSpec=new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addCookies(cookies)
                .build();
    }

    @Test
    public void createCountry(){

        Map<String,String> country=new HashMap<>();
        countryName=faker.address().country()+faker.number().digits(5);
        country.put("name",countryName);
        country.put("code",faker.address().countryCode()+faker.number().digits(5));

        countryID=
                given()
                        .spec(reqSpec)
                        .body(country)
                        .log().body()

                        .when()
                        .post("/school-service/api/countries")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id");

    }

    @Test(dependsOnMethods ="createCountry")
    public void createCountryNegative(){

        given()
                .spec(reqSpec)
                .body(countryName)
                .log().body()

                .when()
                .post("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(400);

    }

    @Test(dependsOnMethods = "createCountry")
    public void updateCountry(){
        countryName=faker.address().country()+faker.number().digits(5);
        countryCode=faker.address().countryCode()+faker.number().digits(5);

        Map<String,String> country=new HashMap<>();
        country.put("id",countryID);
        country.put("name",countryName);
        country.put("code",countryCode);

        given()
                .spec(reqSpec)

                .body(country)

                .when()
                .put("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id",equalTo(countryID))
                .body("name",equalTo(countryName))
                .body("code",equalTo(countryCode))
        ;

    }

    @Test(dependsOnMethods = "updateCountry")
    public void deleteCountry(){

        given()
                .spec(reqSpec)

                .when()

                .delete("/school-service/api/countries/"+countryID)

                .then()
                .log().body()
                .statusCode(200);

    }

    @Test(dependsOnMethods = "deleteCountry")
    public void deleteCountryNegative(){

        given()
                .spec(reqSpec)

                .when()

                .delete("/school-service/api/countries/"+countryID)

                .then()
                .log().body()
                .statusCode(400);

    }






}