package CtizenShips_TODO;

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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class CtizenShipsTest {

    // TODO : CtizenShip  API Automation nı yapınız (create, createNegative, update, delete, deleteNegative)
    Faker faker=new Faker();
    RequestSpecification reqSpec;

    String ctizenShipID;
    String ctizenShipName;
    String ctizenShipshortName;


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
    public void createCtizenShip(){

        ctizenShipName=faker.name().fullName()+faker.number().digits(2);

        Map<String,String> ctizenShip=new HashMap<>();

        ctizenShip.put("name",ctizenShipName);
        ctizenShip.put("shortName",faker.name().lastName()+faker.number().digits(2));

        ctizenShipID=
                given()
                        .spec(reqSpec)
                        .body(ctizenShip)
                        .log().body()

                        .when()
                        .post("/school-service/api/citizenships")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id");

    }

    @Test(dependsOnMethods ="createCtizenShip")
    public void createCtizenShipNegative(){

        given()
                .spec(reqSpec)
                .body(ctizenShipName)
                .log().body()

                .when()
                .post("/school-service/api/citizenships")

                .then()
                .log().body()
                .statusCode(400);

                ;
    }

    @Test(dependsOnMethods = "createCtizenShip")
    public void updateCtizenShip(){

        ctizenShipName=faker.name().fullName()+faker.number().digits(2);
        ctizenShipshortName=faker.name().lastName()+faker.number().digits(2);

        Map<String,String> ctizenShip=new HashMap<>();
        ctizenShip.put("id",ctizenShipID);
        ctizenShip.put("name",ctizenShipName);
        ctizenShip.put("shortName",ctizenShipshortName);

        given()
                .spec(reqSpec)

                .body(ctizenShip)

                .when()
                .put("/school-service/api/citizenships")

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id",equalTo(ctizenShipID))
                .body("name",equalTo(ctizenShipName))
                .body("shortName",equalTo(ctizenShipshortName));

    }

    @Test(dependsOnMethods = "updateCtizenShip")
    public void deleteCtizenShip(){

        given()
                .spec(reqSpec)

                .when()

                .delete("/school-service/api/citizenships/"+ctizenShipID)

                .then()
                .log().body()
                .statusCode(200);

    }

    @Test(dependsOnMethods = "deleteCtizenShip")
    public void deleteCtizenShipNegative(){

       given()
                .spec(reqSpec)

                .when()

                .delete("/school-service/api/citizenships/"+ctizenShipID)

                .then()
                .log().body()
                .statusCode(400);

    }


}
