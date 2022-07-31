import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;


public class RestAssuredTests  {

    Response response;
    public void base_url(){
        baseURI = "https://petstore.swagger.io/v2";
    }


    Faker faker = new Faker();
    String id = faker.address().buildingNumber();
    String invalidId = ("-"+id);
    String petId = faker.address().buildingNumber();
    String body = "{\n  \"id\":"+id+",\n  \"petId\": "+petId+",\n  \"quantity\": 1,\n  \"shipDate\": \"2022-07-29T15:15:50.460+0000\",\n  \"status\": \"placed\",\n  \"complete\": true\n}";
    String post_endpoint = "/store/order/";
    String inventory_endpoint = "/store/inventory";
    String invalid_body = "{\n  \"id\":"+invalidId+",\n  \"petId\": "+petId+",\n  \"quantity\": 1,\n  \"shipDate\": \"2022-07-29T15:15:50.460+0000\",\n  \"status\": \"placed\",\n  \"complete\": true\n}";

   // same test scenarios with uniresttests
    @Test(priority = 1)
    public void valid_post_request(){

        response = given()
                .header("Contect-Type","application/json")
                .body(body)// giving the body here
                .when()
                .post(post_endpoint)// our end point that we wanna test
                .then()
                .statusCode(200)// checking the status code
                .extract().response();

        System.out.println(response.getBody());

    }
    @Test(priority = 2)
    public void invalid_post_request(){

        response = given()
                .header("Content-Type","application/json")
                .body(invalid_body)// giving the invalid body here
                .when()
                .post(post_endpoint)// the end point that we want to test
                .then()
                .statusCode(400)// checking status code
                .extract().response();

        JsonPath bodyJson = response.jsonPath();
        Assert.assertEquals(bodyJson.get("id"),invalidId); // the id's dont match

    }
    @Test(priority = 3)
    public void valid_get_order_request(){

        response = given()
                .header("Content-Type","application/json")
                .when()
                .get(post_endpoint+id)// giving the body and id to test it
                .then()
                .statusCode(200)// checking status code
                .extract().response();

        System.out.println(response.getBody());

    }
    @Test(priority = 4)
    public void invalid_get_order_request(){

        response = given()
                .header("Content-Type","application/json")
                .body(invalid_body)// giving the invalid body
                .when()
                .get(post_endpoint+invalidId)// valid post endpoint and invalid id
                .then()
                .statusCode(400)// checking the status code
                .extract().response();

        System.out.println(response.getBody());

    }
    @Test(priority = 5)
    public void valid_get_inventory_request(){

        response = given()
                .header("Content-Type","application/json")
                .when()
                .get(inventory_endpoint)// giving the endpoint for the test
                .then()
                .statusCode(200)// checking the status code
                .extract().response();

        System.out.println(response.getBody());

    }
    @Test(priority = 6)
    public void valid_delete_request(){

        response = given()
                .header("Content-Type","application/json")
                .when()
                .delete(post_endpoint+id)// giving the valid endpoint
                .then()
                .statusCode(200)// status code
                .extract().response();

        System.out.println(response.getBody());

    }
    @Test(priority = 7)
    public void invalid_delete_request(){

        response = given()
                .header("Content-Type","application/json")
                .when()
                .delete(post_endpoint+invalidId)// giving the invalid endpoint
                .then()
                .statusCode(400)// checking status code
                .extract().response();

        System.out.println(response.getBody());

    }


}
