import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;


public class RestAssuredTests {

    Response response;

    public RestAssuredTests() {
        baseURI = "https://petstore.swagger.io/v2/pet";
    }
    @Test(priority = 1)
    public void validGetRequestWithAvailable() {

        response =
                given()
                        .log().all()
                        .accept(ContentType.JSON)
                        .header("Content-Type", "application/json")
                        .when()
                        .param("status", "available")
                        .get("/findByStatus")
                        .then()
                        .statusCode(200)
                        .extract().response();


        Assert.assertNotNull(response.getBody());
        ResponseBody body = response.getBody();
        System.out.println("Response Body is: " + body.asString());

    }

    @Test(priority = 2)
    public void validGetRequestWithPending() {

        response =
                given()
                        .log().all()
                        .accept(ContentType.JSON)
                        .header("Content-Type", "application/json")
                        .when()
                        .param("status","pending")
                        .get("/findByStatus")
                        .then()
                        .statusCode(200)
                        .extract().response();

        Assert.assertNotNull(response.getBody());
        ResponseBody body = response.getBody();

        ArrayList requestResponse = body.jsonPath().get("id");
        Object newRequestId = requestResponse.get(3);
        System.out.println("Response Body is: " + newRequestId);
        System.out.println("Response Body is: " + body.asString());

        response =
                given()
                        .log().all()
                        .accept(ContentType.JSON)
                        .header("Content-Type", "application/json")
                        .when()
                        .get("/"+newRequestId)
                        .then()
                        .statusCode(200)
                        .extract().response();

        Assert.assertNotNull(response.getBody());
        ResponseBody body2 = response.getBody();
        System.out.println(body2.asString());
        System.out.println((String) body2.jsonPath().get("name"));

    }

    @Test(priority = 3)
    public void validGetRequest() {

        response =
                given()
                        .log().all()
                        .accept(ContentType.JSON)
                        .header("Content-Type", "application/json")
                        .when()
                        .param("status","sold")
                        .get("/findByStatus")
                        .then()
                        .statusCode(200)
                        .extract().response();

        Assert.assertNotNull(response.getBody());
        ResponseBody body = response.getBody();

        System.out.println("Response Body is: " + body.asString());

    }


}
