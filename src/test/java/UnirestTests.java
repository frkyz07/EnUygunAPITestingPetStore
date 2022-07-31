import com.github.javafaker.Faker;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.testng.Assert;
import org.testng.annotations.Test;


public class UnirestTests {

    // we are creting new faker class to use in our method
    // in this way our method will be more dynamic
    Faker faker = new Faker();
    String id = faker.address().buildingNumber();
    String invalidId = ("-"+id);
    String petId = faker.address().buildingNumber();
    String post_endpoint = "/store/order/";
    String inventory_endpoint = "/store/inventory";
    String url = "https://petstore.swagger.io/v2";
    @Test(priority = 1)
    public void valid_post_request() throws UnirestException {

        // this is our valid post scenario
        // we are getting the body from postman
        String body = "{\n  \"id\":"+id+",\n  \"petId\": "+petId+",\n  \"quantity\": 1,\n  \"shipDate\": \"2022-07-29T15:15:50.460+0000\",\n  \"status\": \"placed\",\n  \"complete\": true\n}";

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(url+post_endpoint)
                .header("Content-Type", "application/json")
                .body(body)
                .asString();

        // in here we are checking our response status code
        Assert.assertEquals(response.getStatus(),200);
        System.out.println(response.getBody());


    }
    @Test(priority = 2)
    public void invalid_post_request() throws UnirestException, NoSuchFieldException, IllegalAccessException {

        // this is our invalid post request
        // i did this test for checking invalid scenario
        // instead of giving a normal id number i am giving a number below than zero
        String body = "{\n  \"id\":" +invalidId + ",\n  \"petId\": " + petId + ",\n  \"quantity\": 1,\n  \"shipDate\": \"2022-07-29T15:15:50.460+0000\",\n  \"status\": \"placed\",\n  \"complete\": true\n}";

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest.post("https://petstore.swagger.io/v2/store/order/")
                .header("Content-Type", "application/json")
                .body(body)
                .asJson();

        int reponse_id = (int) response.getBody().getObject().get("id");
        System.out.println(body);
        System.out.println(response.getBody());
        // the test should fail but it dont fail
        Assert.assertEquals(response.getStatus(), 400);
        // and the response id and post id dont match you can see the screenshot in the postman file
        Assert.assertEquals(reponse_id, invalidId);




    }
    @Test(priority = 3)
    public void valid_get_order_request() throws UnirestException {

        // this is valid get order request
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("https://petstore.swagger.io/v2/store/order/1313")
                .header("id", id)
                .asString();

        // checking the status code
        System.out.println(response.getBody());
        Assert.assertEquals(response.getStatus(), 200);

    }
    @Test(priority = 4)
    public void valid_get_inventory_request() throws UnirestException {

        // this is valid scenario for the getting the inventory request

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("https://petstore.swagger.io/v2/store/inventory")
                .asString();

        // checking the status code
        System.out.println(response.getBody());
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test(priority = 5)
   public void invalid_get_order_request() throws UnirestException {

        // this is invalid scenario for the get user i am giving invalid id for this scenario

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("https://petstore.swagger.io/v2/store/order/-1313")
                .header("invalidid", invalidId)
                .asString();


        // checking the status code
        System.out.println(response.getBody());
        Assert.assertEquals(response.getStatus(), 404);

    }
    @Test(priority = 6)
    public void valid_delete_request() throws UnirestException {

        // valid delete scenario

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.delete("https://petstore.swagger.io/v2/store/order/1313")
                .header("id", id)
                .body("")
                .asString();

        // checking the status code
        System.out.println(response.getBody());
        Assert.assertEquals(response.getStatus(), 200);

    }
    @Test(priority = 7)
    public void invalid_delete_request() throws UnirestException {

        // invalid delete scenario i am giving invalid id for deleting

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.delete("https://petstore.swagger.io/v2/store/order/-1313")
                .header("invalidId", invalidId)
                .body("")
                .asString();

        // checking status code

        System.out.println(response.getBody());
        Assert.assertEquals(response.getStatus(), 404);
    }




}


