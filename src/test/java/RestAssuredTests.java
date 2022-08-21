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

        try {
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
        }catch (RuntimeException e){
            System.out.println("Request cannot sended"+e);
        }



        response.getHeader("param");
        ArrayList status = response.path("status");

        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals(status.get(0),"available");
            Assert.assertEquals(response.getStatusCode(),200 );
        }catch (AssertionError e){
            System.out.println("Assertion Error"+e);
        }


    }
    @Test(priority = 2)
    public void validGetRequestWithPending() {

        try {
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
        }catch (RuntimeException e){
            System.out.println("Request cannot be send"+e);
        }


        response.getHeader("param");
        ArrayList status = response.path("status");

        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals(status.get(0),"pending");
            Assert.assertEquals(response.getStatusCode(),200 );
        }catch (AssertionError e){
            System.out.println("Assertion Error"+e);
        }

    }

    @Test(priority = 3)
    public void validGetRequestWithSold() {

        try {
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
        }catch (RuntimeException e){
            System.out.println("Request cannot sended"+e);
        }


        response.getHeader("param");
        ArrayList status = response.path("status");

        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals(status.get(0),"sold");
            Assert.assertEquals(response.getStatusCode(),200 );
        }catch (AssertionError e){
            System.out.println("Assertion Error"+e);
        }

    }
    @Test(priority = 4)
    public void validGetRequestWithAvailableAndGetId() {

        try {
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
        }catch (RuntimeException e){
            System.out.println("Requset cannot sended"+e);
        }

        try {
            Assert.assertNotNull(response.getBody());
        }catch (AssertionError e){
            System.out.println("Assertion Error"+e);
        }

        ResponseBody body = response.getBody();

        ArrayList firstResponseIds = body.jsonPath().get("id");
        ArrayList firstResponse = body.jsonPath().get();

        Object newRequestId = firstResponseIds.get(3);
        Object firstResponseThirdObject = firstResponse.get(3);

        try {
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
        }catch (RuntimeException e){
            System.out.println("Request Can not sended");
        }


        ResponseBody body2 = response.getBody();
        JsonPath secondResponse = body2.jsonPath();
        Object secondRespondObject = secondResponse.get();

        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals(firstResponseThirdObject,secondRespondObject);
        }catch (AssertionError e){
            System.out.println("Assertion Error"+e);
        }


    }

   @Test(priority = 5)
   public void validPostRequest(){

       ObjectMapper mapper = new ObjectMapper();
       mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

       JSONObject properties = new JSONObject();
       properties.put("name",createRequest().getName());
       properties.put("status", createRequest().getStatus());

       try {
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
       }catch (RuntimeException e){
           System.out.println("Request can not send"+e);
       }


        JsonPath jsonPathEvaluator = response.jsonPath();
       try {
           Assert.assertNotNull(response.getBody());
           Assert.assertEquals((Integer) jsonPathEvaluator.get("code"),200 );
           Assert.assertEquals(createRequest().getType(), jsonPathEvaluator.get("type"));
           Assert.assertEquals(createRequest().getPetID(), Integer.valueOf(jsonPathEvaluator.get("message")));
       }catch (AssertionError e){
           System.out.println("Assertion Error"+e);
       }

    }
    @Test(priority = 6)
    public void invalidPostRequest(){

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        JSONObject properties = new JSONObject();
        properties.put("name",createRequest().getName());
        properties.put("status", createRequest().getStatus());

        try {
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
        }catch (RuntimeException e){
            System.out.println("Request can not send"+e);
        }


        JsonPath jsonPathEvaluator = response.jsonPath();
        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals((Integer) jsonPathEvaluator.get("code"),415 );
            Assert.assertEquals(createRequest().getType(), jsonPathEvaluator.get("type"));
        }catch (AssertionError e){
            System.out.println("Assertion Error "+e);
        }


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

        try {
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
        }catch (RuntimeException e){
            System.out.println("Request can not send "+e);
        }

        JsonPath jsonPathEvaluator = response.jsonPath();

        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals((Integer) jsonPathEvaluator.get("code"),404 );
            Assert.assertEquals(createRequest().getType(), jsonPathEvaluator.get("type"));
            Assert.assertEquals(assertionError, jsonPathEvaluator.get("message"));
        }catch (AssertionError e){
            System.out.println("Assertion Error "+e);
        }
    }
    @Test(priority = 8)
    public void validDeleteRequest(){

        try {
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
        }catch (RuntimeException e){
            System.out.println("Request can not send "+e);
        }


        try {
            Assert.assertNotNull(response.getBody());
        }catch (AssertionError e){
            System.out.println("Assertion Error "+e);
        }


        ResponseBody body = response.getBody();
        ArrayList firstResponseIds = body.jsonPath().get("id");
        Object newRequestId = firstResponseIds.get(0);

        try {
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
        }catch (RuntimeException e){
            System.out.println("Request con not send "+e);
        }


        ResponseBody body2 = response.getBody();
        JsonPath secondResponse = body2.jsonPath();
        Object secondRespondObject = secondResponse.get();

        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals((Integer) secondResponse.get("code"),200);
            Assert.assertEquals(secondResponse.get("type"),"unknown");
            Assert.assertEquals(secondResponse.get("message"), (String.valueOf(newRequestId)));
        }catch (AssertionError e){
            System.out.println("Assertion Error "+e);
        }



    }
    @Test(priority = 9)
    public void invalidDeleteRequestFourHundred(){

        try {
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
        }catch (RuntimeException e){
            System.out.println("Request can not send");
        }

        ResponseBody body = response.getBody();
        JsonPath jsonResponse = body.jsonPath();

        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals((Integer) jsonResponse.get("code"),404);
            Assert.assertEquals(jsonResponse.get("type"),"unknown");
        }catch (AssertionError e){
            System.out.println("Assertion Error "+e);
        }


    }
    @Test(priority = 10)
    public void invalidDeleteRequestFourHundredFive(){

        try {
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
        }catch (RuntimeException e){
            System.out.println("Request can not send "+e);
        }

        ResponseBody body = response.getBody();
        JsonPath jsonResponse = body.jsonPath();

        try {
            Assert.assertNotNull(response.getBody().asString());
            Assert.assertEquals((Integer) jsonResponse.get("code"),405);
            Assert.assertEquals(jsonResponse.get("type"),"unknown");
        }catch (AssertionError e){
            System.out.println("Assertion Error "+e);
        }

    }

}
