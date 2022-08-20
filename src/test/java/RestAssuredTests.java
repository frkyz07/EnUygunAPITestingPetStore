import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.http.ContentType;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


import io.restassured.response.ResponseBody;
import models.RequestBody;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.*;


public class RestAssuredTests {

    Response response;

    public RestAssuredTests() {
        baseURI = "https://petstore.swagger.io/v2";
    }
    public RequestBody createRequest() {
        int petId = 570;
        String name = "Aslan";
        String status = "sold";
        String type ="unknown";
        return new RequestBody(petId, name, status,type);
    }


    @Test(priority = 1)
    public void validGetRequestWithAvailable() {

        response =
                given()
                        .log().all()
                        .accept(ContentType.JSON)
                        .header("Content-Type", "application/json")
                        .when()
                        .params("status", "available")
                        .get("/pet/findByStatus")
                        .then()
                        .statusCode(200)
                        .extract().response();


        response.getHeader("param");
        ArrayList status = response.path("status");

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(status.get(0),"available");
        Assert.assertEquals(response.getStatusCode(),200 );

    }
    @Test(priority = 2)
    public void validGetRequestWithPending() {

        response =
                given()
                        .log().all()
                        .accept(ContentType.JSON)
                        .header("Content-Type", "application/json")
                        .when()
                        .params("status","pending")
                        .get("/pet/findByStatus")
                        .then()
                        .statusCode(200)
                        .extract().response();

        response.getHeader("param");
        ArrayList status = response.path("status");

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(status.get(0),"pending");
        Assert.assertEquals(response.getStatusCode(),200 );


    }

    @Test(priority = 3)
    public void validGetRequestWithSold() {

        response =
                given()
                        .log().all()
                        .accept(ContentType.JSON)
                        .header("Content-Type", "application/json")
                        .when()
                        .params("status","sold")
                        .get("/pet/findByStatus")
                        .then()
                        .statusCode(200)
                        .extract().response();

        response.getHeader("param");
        ArrayList status = response.path("status");

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(status.get(0),"sold");
        Assert.assertEquals(response.getStatusCode(),200 );
    }
    @Test(priority = 4)
    public void validGetRequestWithAvailableAndGetId() {

        response =
                given()
                        .log().all()
                        .accept(ContentType.JSON)
                        .header("Content-Type", "application/json")
                        .when()
                        .param("status","available")
                        .get("/pet/findByStatus")
                        .then()
                        .statusCode(200)
                        .extract().response();

        Assert.assertNotNull(response.getBody());
        ResponseBody body = response.getBody();

        ArrayList firstResponseIds = body.jsonPath().get("id");
        ArrayList firstResponse = body.jsonPath().get();

        Object newRequestId = firstResponseIds.get(3);
        Object firstResponseThirdObject = firstResponse.get(3);

        response =
                given()
                        .log().all()
                        .accept(ContentType.JSON)
                        .header("Content-Type", "application/json")
                        .when()
                        .get("/pet/"+newRequestId)
                        .then()
                        .statusCode(200)
                        .extract().response();

        ResponseBody body2 = response.getBody();
        JsonPath secondResponse = body2.jsonPath();
        Object secondRespondObject = secondResponse.get();

        System.out.println(body2);
        System.out.println(secondRespondObject);

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(firstResponseThirdObject,secondRespondObject);

    }

   @Test(priority = 5)
   public void validPostRequest(){

       ObjectMapper mapper = new ObjectMapper();
       mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

       JSONObject properties = new JSONObject();
       properties.put("name",createRequest().getName());
       properties.put("status", createRequest().getStatus());

       response =
               given()
                        .log().all()
                        .accept("application/json")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .body(properties.toString())
                        .when()
                        .post("/pet/"+createRequest().getPetID())
                        .then()
                        .statusCode(200)
                        .extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals((Integer) jsonPathEvaluator.get("code"),200 );
        Assert.assertEquals(createRequest().getType(), jsonPathEvaluator.get("type"));
        Assert.assertEquals(createRequest().getPetID(), Integer.valueOf(jsonPathEvaluator.get("message")));

    }
    @Test(priority = 6)
    public void invalidPostRequest(){

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        JSONObject properties = new JSONObject();
        properties.put("name",createRequest().getName());
        properties.put("status", createRequest().getStatus());

        response =
                given()
                        .log().all()
                        .accept("application/json")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .body(properties.toString())
                        .when()
                        .post("/pet/")
                        .then()
                        .statusCode(415)
                        .extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals((Integer) jsonPathEvaluator.get("code"),415 );
        Assert.assertEquals(createRequest().getType(), jsonPathEvaluator.get("type"));

    }
    @Test(priority = 7)
    public void invalidPostRequestFourHundredFour(){

        String invalidKey = "Random";
        String assertionError ="java.lang.NumberFormatException: For input string: \""+invalidKey+"\"";

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        JSONObject properties = new JSONObject();
        properties.put("name",createRequest().getName());
        properties.put("status", createRequest().getStatus());

        response =
                given()
                        .log().all()
                        .accept("application/json")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .body(properties.toString())
                        .when()
                        .post("/pet/"+invalidKey)
                        .then()
                        .statusCode(404)
                        .extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals((Integer) jsonPathEvaluator.get("code"),404 );
        Assert.assertEquals(createRequest().getType(), jsonPathEvaluator.get("type"));
        Assert.assertEquals(assertionError, jsonPathEvaluator.get("message"));

    }
    @Test(priority = 8)
    public void validDeleteRequest(){

        response =
                given()
                        .log().all()
                        .accept(ContentType.JSON)
                        .header("Content-Type", "application/json")
                        .when()
                        .param("status","pending")
                        .get("/pet/findByStatus")
                        .then()
                        .statusCode(200)
                        .extract().response();

        Assert.assertNotNull(response.getBody());
        ResponseBody body = response.getBody();

        ArrayList firstResponseIds = body.jsonPath().get("id");
        ArrayList firstResponse = body.jsonPath().get();

        Object newRequestId = firstResponseIds.get(0);

        response =
                given()
                        .log().all()
                        .accept(ContentType.JSON)
                        .header("Content-Type", "application/json")
                        .when()
                        .delete("/pet/"+newRequestId)
                        .then()
                        .statusCode(200)
                        .extract().response();

        Assert.assertNotNull(response.getBody());
        ResponseBody body2 = response.getBody();

        JsonPath secondResponse = body2.jsonPath();
        Object secondRespondObject = secondResponse.get();

        Assert.assertEquals((Integer) secondResponse.get("code"),200);
        Assert.assertEquals(secondResponse.get("type"),"unknown");
        Assert.assertEquals(secondResponse.get("message"), (String.valueOf(newRequestId)));

        System.out.println(secondRespondObject);
    }
    @Test(priority = 9)
    public void invalidDeleteRequestFourHundred(){

        response =
                given()
                        .log().all()
                        .accept(ContentType.JSON)
                        .header("Content-Type", "application/json")
                        .when()
                        .delete("/pet/"+"somethingBad123123%+'^^")
                        .then()
                        .statusCode(404)
                        .extract().response();

        Assert.assertNotNull(response.getBody());
        System.out.println(response.getBody().asString());

        ResponseBody body = response.getBody();
        JsonPath jsonResponse = body.jsonPath();

        Assert.assertEquals((Integer) jsonResponse.get("code"),404);
        Assert.assertEquals(jsonResponse.get("type"),"unknown");

    }
    @Test(priority = 10)
    public void invalidDeleteRequestFourHundredFive(){

        response =
                given()
                        .log().all()
                        .accept(ContentType.JSON)
                        .header("Content-Type", "application/json")
                        .when()
                        .delete("/pet/")
                        .then()
                        .statusCode(405)
                        .extract().response();

        System.out.println(response.getBody().asString());
        Assert.assertNotNull(response.getBody().asString());

        ResponseBody body = response.getBody();
        JsonPath jsonResponse = body.jsonPath();

        Assert.assertEquals((Integer) jsonResponse.get("code"),405);
        Assert.assertEquals(jsonResponse.get("type"),"unknown");
    }

}
