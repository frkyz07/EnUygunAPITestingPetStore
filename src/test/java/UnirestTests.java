import com.github.javafaker.Faker;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.testng.Assert;
import org.testng.annotations.Test;


public class UnirestTests {

    Faker faker = new Faker();
    String id = faker.address().buildingNumber();
    String invalidId = ("-"+id);
    String petId = faker.address().buildingNumber();
    @Test(priority = 1)
    public void valid_post_request() throws UnirestException {


        String body = "{\n  \"id\":"+id+",\n  \"petId\": "+petId+",\n  \"quantity\": 1,\n  \"shipDate\": \"2022-07-29T15:15:50.460+0000\",\n  \"status\": \"placed\",\n  \"complete\": true\n}";

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post("https://petstore.swagger.io/v2/store/order/")
                .header("Content-Type", "application/json")
                .body(body)
                .asString();


        Assert.assertEquals(response.getStatus(),200);
        System.out.println(response.getBody());


    }
    @Test(priority = 2)
    public void invalid_post_request() throws UnirestException, NoSuchFieldException, IllegalAccessException {

        String body = "{\n  \"id\":" +invalidId + ",\n  \"petId\": " + petId + ",\n  \"quantity\": 1,\n  \"shipDate\": \"2022-07-29T15:15:50.460+0000\",\n  \"status\": \"placed\",\n  \"complete\": true\n}";

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest.post("https://petstore.swagger.io/v2/store/order/")
                .header("Content-Type", "application/json")
                .body(body)
                .asJson();

        int reponse_id = (int) response.getBody().getObject().get("id");
        System.out.println(body);
        System.out.println(response.getBody());
        Assert.assertEquals(response.getStatus(), 400);
        Assert.assertEquals(reponse_id, invalidId);




    }
    @Test(priority = 3)
    public void valid_get_order_request() throws UnirestException {

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("https://petstore.swagger.io/v2/store/order/1313")
                .header("id", id)
                .asString();


        System.out.println(response.getBody());
        Assert.assertEquals(response.getStatus(), 200);

    }
    @Test(priority = 4)
    public void valid_get_inventory_request() throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("https://petstore.swagger.io/v2/store/inventory")
                .asString();

        System.out.println(response.getBody());
        Assert.assertEquals(response.getStatus(), 200);
    }

    @Test(priority = 5)
   public void invalid_get_order_request() throws UnirestException {

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get("https://petstore.swagger.io/v2/store/order/-1313")
                .header("invalidid", invalidId)
                .asString();



        System.out.println(response.getBody());
        Assert.assertEquals(response.getStatus(), 404);

    }
    @Test(priority = 6)
    public void valid_delete_request() throws UnirestException {

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.delete("https://petstore.swagger.io/v2/store/order/1313")
                .header("id", id)
                .body("")
                .asString();


        System.out.println(response.getBody());
        Assert.assertEquals(response.getStatus(), 200);

    }
    @Test(priority = 7)
    public void invalid_delete_request() throws UnirestException {

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.delete("https://petstore.swagger.io/v2/store/order/-1313")
                .header("invalidId", invalidId)
                .body("")
                .asString();

        System.out.println(response.getBody());
        Assert.assertEquals(response.getStatus(), 404);
    }




}


