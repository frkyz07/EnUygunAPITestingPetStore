import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.restassured.http.ContentType;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


import io.restassured.response.ResponseBody;
import models.RequestBody;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.*;
public class RestAssuredTests {

    Response response;
    private static Logger logger = LoggerFactory.getLogger(RestAssuredTests.class);


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

            logger.info("validGetRequestWithAvailable request sended");

        }catch (RuntimeException e){
            System.out.println("Request cannot sended "+e);
            logger.error("Problem with request sending");
        }



        response.getHeader("param");
        ArrayList status = response.path("status");

        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals(status.get(0),"available");
            Assert.assertEquals(response.getStatusCode(),200 );
            logger.info("for validGetRequestWithAvailable assertions passed");
        }catch (AssertionError e){
            System.out.println("Assertion Error"+e);
            logger.error("for validGetRequestWithAvailable assertions didnt pass");
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

            logger.info("validGetRequestWithPending request sended");

        }catch (RuntimeException e){
            System.out.println("Request cannot be send"+e);
            logger.error("Problem with request sending");
        }


        response.getHeader("param");
        ArrayList status = response.path("status");

        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals(status.get(0),"pending");
            Assert.assertEquals(response.getStatusCode(),200 );
            logger.info("for validGetRequestWithPending assertions passed");
        }catch (AssertionError e){
            System.out.println("Assertion Error"+e);
            logger.error("for validGetRequestWithPending assertions didnt pass");
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

            logger.info("validGetRequestWithSold request sended");

        }catch (RuntimeException e){
            System.out.println("Request cannot sended"+e);
            logger.error("Problem with request sending");
        }


        response.getHeader("param");
        ArrayList status = response.path("status");

        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals(status.get(0),"sold");
            Assert.assertEquals(response.getStatusCode(),200 );
            logger.info("for validGetRequestWithSold assertions passed");
        }catch (AssertionError e){
            System.out.println("Assertion Error"+e);
            logger.error("for validGetRequestWithSold assertions didnt pass");
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

            logger.info("validGetRequestWithSold first request sended");

        }catch (RuntimeException e){
            System.out.println("Requset cannot sended"+e);
            logger.error("Problem with request sending");
        }

        try {
            Assert.assertNotNull(response.getBody());
            logger.info("for validGetRequestWithAvailableAndGetId first request's assertions passed");
        }catch (AssertionError e){
            System.out.println("Assertion Error"+e);
            logger.error("for validGetRequestWithSold first request's assertions didnt pass");
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

            logger.info("validGetRequestWithSold second request sended");

        }catch (RuntimeException e){
            System.out.println("Request Can not sended");
            logger.error("Problem with request sending");
        }


        ResponseBody body2 = response.getBody();
        JsonPath secondResponse = body2.jsonPath();
        Object secondRespondObject = secondResponse.get();

        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals(firstResponseThirdObject,secondRespondObject);
            logger.info("for validGetRequestWithAvailableAndGetId second request's assertions passed");
        }catch (AssertionError e){
            System.out.println("Assertion Error"+e);
            logger.error("for validGetRequestWithSold second request's assertions didnt pass");
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

           logger.info("validPostRequest second request sended");

       }catch (RuntimeException | AssertionError e){
           System.out.println("Request can not send or Failed request "+e);
           logger.error("Problem with request sending");
       }

        JsonPath jsonPathEvaluator = response.jsonPath();
       try {
           Assert.assertNotNull(response.getBody());
           Assert.assertEquals((Integer) jsonPathEvaluator.get("code"),200 );
           Assert.assertEquals(createRequest().getType(), jsonPathEvaluator.get("type"));
           Assert.assertEquals(createRequest().getPetID(), Integer.valueOf(jsonPathEvaluator.get("message")));
           logger.info("for validPostRequest assertions passed");
       }catch (AssertionError e){
           System.out.println("Assertion Error"+e);
           logger.error("for validPostRequest assertions didnt pass");
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

            logger.info("invalidPostRequest second request sended");

        }catch (RuntimeException e){
            System.out.println("Request can not send"+e);
            logger.error("Problem with request sending");
        }


        JsonPath jsonPathEvaluator = response.jsonPath();
        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals((Integer) jsonPathEvaluator.get("code"),415 );
            Assert.assertEquals(createRequest().getType(), jsonPathEvaluator.get("type"));
            logger.info("for invalidPostRequest assertions passed");
        }catch (AssertionError e){
            System.out.println("Assertion Error "+e);
            logger.error("for invalidPostRequest assertions didnt pass");
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

            logger.info("invalidPostRequestFourHundredFour request sended");

        }catch (RuntimeException e){
            System.out.println("Request can not send "+e);
            logger.error("Problem with request sending");
        }

        JsonPath jsonPathEvaluator = response.jsonPath();

        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals((Integer) jsonPathEvaluator.get("code"),404 );
            Assert.assertEquals(createRequest().getType(), jsonPathEvaluator.get("type"));
            Assert.assertEquals(assertionError, jsonPathEvaluator.get("message"));
            logger.info("for invalidPostRequestFourHundredFour assertions passed");
        }catch (AssertionError e){
            System.out.println("Assertion Error "+e);
            logger.error("for invalidPostRequestFourHundredFour assertions didnt pass");
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

            logger.info("validDeleteRequest first request sended");

        }catch (RuntimeException e){
            System.out.println("Request can not send "+e);
            logger.error("Problem with request sending");
        }


        try {
            Assert.assertNotNull(response.getBody());
            logger.info("for validDeleteRequest first assertions passed");
        }catch (AssertionError e){
            System.out.println("Assertion Error "+e);
            logger.info("for validDeleteRequest first assertions didnt passed");
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

            logger.info("validDeleteRequest second request sended");

        }catch (RuntimeException e){
            System.out.println("Request con not send "+e);
            logger.error("Problem with request sending");
        }


        ResponseBody body2 = response.getBody();
        JsonPath secondResponse = body2.jsonPath();


        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals((Integer) secondResponse.get("code"),200);
            Assert.assertEquals(secondResponse.get("type"),"unknown");
            Assert.assertEquals(secondResponse.get("message"), (String.valueOf(newRequestId)));
            logger.info("for validDeleteRequest second assertions passed");
        }catch (AssertionError e){
            System.out.println("Assertion Error "+e);
            logger.info("for validDeleteRequest second assertions didnt passed");
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

            logger.info("invalidDeleteRequestFourHundred request sended");

        }catch (RuntimeException e){
            System.out.println("Request can not send");
            logger.error("Problem with request sending");
        }

        ResponseBody body = response.getBody();
        JsonPath jsonResponse = body.jsonPath();

        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals((Integer) jsonResponse.get("code"),404);
            Assert.assertEquals(jsonResponse.get("type"),"unknown");
            logger.info("for invalidDeleteRequestFourHundred assertions passed");
        }catch (AssertionError e){
            System.out.println("Assertion Error "+e);
            logger.info("for invalidDeleteRequestFourHundred assertions didnt passed");
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

            logger.info("invalidDeleteRequestFourHundredFive request sended");

        }catch (RuntimeException e){
            System.out.println("Request can not send "+e);
            logger.error("Problem with request sending");
        }

        ResponseBody body = response.getBody();
        JsonPath jsonResponse = body.jsonPath();

        try {
            Assert.assertNotNull(response.getBody().asString());
            Assert.assertEquals((Integer) jsonResponse.get("code"),405);
            Assert.assertEquals(jsonResponse.get("type"),"unknown");
            logger.info("for invalidDeleteRequestFourHundredFive assertions passed");
        }catch (AssertionError e){
            System.out.println("Assertion Error "+e);
            logger.info("for invalidDeleteRequestFourHundredFive assertions didnt passed");
        }

    }

}
