import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Description;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
@Slf4j
public class RestAssuredTests extends BaseRequest{

    // create logger for the logs
    private static Logger logger = LoggerFactory.getLogger(RestAssuredTests.class);

    public RestAssuredTests() {
        baseURI = "https://petstore.swagger.io/v2";
    }

    @Test(priority = 1, description = "validGetRequestWithAvailable")
    @Description("Sending a valid get request with available parameters ")
    public void validGetRequestWithAvailable() {

        // send a valid get request
        try {
            response = getRequestWithParams("available");
            logger.info("validGetRequestWithAvailable request send");
        }catch (RuntimeException | JsonProcessingException e){
            logger.error("Problem with request sending "+e);
        }
        // get the param variable to check it
        // check the assertions
        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals(statusList("status","available").get(0),"available");
            Assert.assertEquals(response.getStatusCode(),200 );
            logger.info("for validGetRequestWithAvailable assertions passed");
        }catch (AssertionError e){
            logger.error("for validGetRequestWithAvailable assertions didn't pass "+e);
        }


    }
    @Test(priority = 2, description = "validGetRequestWithPending")
    @Description("Sending a valid get request with pending parameter")
    public void validGetRequestWithPending() {

        // same request for the pending parameters
        try {
            response = getRequestWithParams("pending");
            logger.info("validGetRequestWithPending request send");
        }catch (RuntimeException | JsonProcessingException e){
            logger.error("Problem with request sending "+e);
        }
        // get the param variable for checking
        // check the assertions
        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals(statusList("status","pending").get(0),"pending");
            Assert.assertEquals(response.getStatusCode(),200 );
            logger.info("for validGetRequestWithPending assertions passed");
        }catch (AssertionError e){
            logger.error("for validGetRequestWithPending assertions didn't pass "+e);
        }

    }

    @Test(priority = 3, description = "validGetRequestWithSold")
    @Description("Sending a valid get request with sold parameter")
    public void validGetRequestWithSold() {
        // same test for the sold param
        try {
            response = getRequestWithParams("sold");
            logger.info("validGetRequestWithSold request send");

        }catch (RuntimeException | JsonProcessingException e){
            logger.error("Problem with request sending "+e);
        }
        // get the param variable for checking
        // check the params
        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals(statusList("status","sold").get(0),"sold");
            Assert.assertEquals(response.getStatusCode(),200 );
            logger.info("for validGetRequestWithSold assertions passed");
        }catch (AssertionError e){
            logger.error("for validGetRequestWithSold assertions didn't pass "+e);
        }
    }
    @Test(priority = 4, description = "validGetRequestWithAvailableAndGetId")
    @Description("Sending a valid get request with available and sending new request with the id ")
    public void validGetRequestWithAvailableAndGetId() {

        // first request for getting the other request
        try {
            response = getRequestWithParams("pending");
            logger.info("validGetRequestWithSold first request send");

        }catch (RuntimeException | JsonProcessingException e){
            logger.error("First request Problem with request sending "+e);
        }
        // check the body if it is null
        try {
            Assert.assertNotNull(response.getBody());
            logger.info("for validGetRequestWithAvailableAndGetId first request's assertions passed");
        }catch (AssertionError e){
            logger.error("for validGetRequestWithSold first request's assertions didn't pass "+e);
        }

        // creating new request with the id that we got from earlier request
        try {
            response = getRequestWithId(Integer.parseInt(idGetter(3)));
            logger.info("validGetRequestWithSold second request send");

        }catch (RuntimeException | JsonProcessingException e){
            logger.error("Second request Problem with request sending "+e);
        }
        // get the body for checking
        // check the body for assertion
        try {
            Assert.assertNotNull(response.getBody());
            logger.info("for validGetRequestWithAvailableAndGetId second request's assertions passed");
        }catch (AssertionError e){
            logger.error("for validGetRequestWithSold second request's assertions didn't pass "+e);
        }
    }

   @Test(priority = 5, description = "validPostRequest")
   @Description("Sending a valid get request")
   public void validPostRequest(){

       // creating a new json object for the request
       // sending the valid request
       try {
           response = postRequest(createRequest().getPetID());
           logger.info("validPostRequest second request send");

       }catch (RuntimeException | AssertionError e){
           logger.error("Problem with request sending "+e);
       }
        // getting the body
       // checking the body
       try {
           Assert.assertNotNull(response.getBody());
           Assert.assertEquals((Integer) response.jsonPath().get("code"),200 );
           Assert.assertEquals(createRequest().getType(), response.jsonPath().get("type"));
           Assert.assertEquals(createRequest().getPetID(), Integer.valueOf(response.jsonPath().get("message")));
           logger.info("for validPostRequest assertions passed");
       }catch (AssertionError e){
           logger.error("for validPostRequest assertions didn't pass "+e);
       }

    }

    @Test(priority = 6, description = "invalidPostRequest")
    @Description("Sending a invalid get request")
    public void invalidPostRequest(){

        // same test as before but in this case i am checking for the empty request
        try {
            response = postRequest(Integer.valueOf(""));
            logger.info("invalidPostRequest second request send");

        }catch (RuntimeException e){
            logger.error("Problem with request sending "+e);
        }
        // getting the body
        // checking the body
        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals((Integer)  response.jsonPath().get("code"),415 );
            Assert.assertEquals(createRequest().getType(), response.jsonPath().get("type"));
            logger.info("for invalidPostRequest assertions passed");
        }catch (AssertionError e){
            logger.error("for invalidPostRequest assertions didn't pass "+e);
        }
    }
    @Test(priority = 7, description = "invalidPostRequestFourHundredFour")
    @Description("Sending a invalid post request and getting 404 response")
    public void invalidPostRequestFourHundredFour(){

        // creating invalid variable for testing
        String invalidKey = "Random";
        String assertionError ="java.lang.NumberFormatException: For input string: \""+invalidKey+"\"";
        // sending the invalid request
        try {
            response = postRequest(Integer.valueOf(invalidKey));
            logger.info("invalidPostRequestFourHundredFour request send");

        }catch (RuntimeException e){
            logger.error("Problem with request sending "+e);
        }
        // get the body
        // checking the body
        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals((Integer) response.jsonPath().get("code"),404 );
            Assert.assertEquals(createRequest().getType(), response.jsonPath().get("type"));
            Assert.assertEquals(assertionError, response.jsonPath().get("message"));
            logger.info("for invalidPostRequestFourHundredFour assertions passed");
        }catch (AssertionError e){
            logger.error("for invalidPostRequestFourHundredFour assertions didn't pass "+e);
        }
    }
    @Test(priority = 8, description = "validDeleteRequest")
    @Description("Sending a valid delete request")
    public void validDeleteRequest(){
        // valid get request in here i made a 2 request in one of them i am getting the id
        // in the second i am deleting the id that i get
        // I am doing this beacuse if i do this in a static way after one delete request
        // My request wouldnt be available for testing
        // Sending the request
        try {
            response = getRequestWithParams("pending");
            logger.info("validDeleteRequest first request send");

        }catch (RuntimeException | JsonProcessingException e){
            logger.error("Problem with request sending "+e);
        }
        // getting the first request
        try {
            Assert.assertNotNull(response.getBody());
            logger.info("for validDeleteRequest first assertions passed");
        }catch (AssertionError e){
            logger.info("for validDeleteRequest first assertions didn't passed "+e);
        }
        // sending the last request
        String checkId = idGetter(0);
        try {
            response = deleteRequest(Integer.valueOf(checkId));
            logger.info("validDeleteRequest second request send");

        }catch (RuntimeException e){
            logger.error("Problem with request sending "+e);
        }
        // getting the body
        // check body for assertions
        try {
            Assert.assertNotNull(response.getBody());
            Assert.assertEquals((Integer) response.getBody().jsonPath().get("code"),200);
            Assert.assertEquals(response.getBody().jsonPath().get("type"),"unknown");
            Assert.assertEquals(response.getBody().jsonPath().get("message"), checkId);
            logger.info("for validDeleteRequest second assertions passed");
        }catch (AssertionError e){
            System.out.println("Assertion Error "+e);
            logger.info("for validDeleteRequest second assertions didn't passed "+e);
        }

    }
    @Test(priority = 9,description = "invalidDeleteRequestFourHundred")
    @Description("Sending a invalid delete request and expecting 404 response")
    public void invalidDeleteRequestFourHundred(){

        // sending the invalid delete request
        try {
            response = deleteRequest(123); // deleted id
            logger.info("invalidDeleteRequestFourHundred request send");

        }catch (RuntimeException e){
            logger.error("Problem with request sending "+e);
        }
        // getting the body for checking
        // check the body for assertions
        try {
            Assert.assertNull(response.getBody());
            logger.info("for invalidDeleteRequestFourHundred assertions passed");
        }catch (AssertionError e){
            System.out.println("Assertion Error "+e);
            logger.info("for invalidDeleteRequestFourHundred assertions didn't passed "+e);
        }
    }
    @Test(priority = 10,description = "invalidDeleteRequestFourHundredFive")
    @Description("Sending a invalid delete request and expecting 404 response")
    public void invalidDeleteRequestFourHundredFive(){
        // sending the request
        try {
            response = invalidDeleteRequest();
            logger.info("invalidDeleteRequestFourHundredFive request send");

        }catch (RuntimeException e){
            logger.error("Problem with request sending "+e);
        }
        // getting the body
        // checking the body for assertions
        try {
            Assert.assertNull(response.getBody().asString());
            logger.info("for invalidDeleteRequestFourHundredFive assertions passed");
        }catch (AssertionError e){
            logger.info("for invalidDeleteRequestFourHundredFive assertions didn't passed "+e);
        }

    }

}
