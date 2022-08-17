import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.lessThan;


public class RestAssuredTests {

    Response response;

    public void base_url() {
        baseURI = "https://petstore.swagger.io/v2/pet";
    }

    @Test(priority = 1)
    public void validGetRequest() {

        response =
                given()
                        .accept(ContentType.JSON)
                        .header("Contect-Type", "application/json")
                        .param("status","pending")
                        .when()
                        .get("/findByStatus")
                        .then()
                        .statusCode(200)
                        .time(lessThan(500L))
                        .extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();

        // Asserting for the first response
        /*Assert.assertNotNull(response.getBody());
        Assert.assertEquals(1, allResponds.get(0).getId());
        Assert.assertEquals("apple", allResponds.get(0).getName());
        Assert.assertEquals(3, allResponds.get(0).getPrice());
        Assert.assertEquals(100, allResponds.get(0).getStock());

        // Asserting for the second response
        Assert.assertEquals(2, allResponds.get(1).getId());
        Assert.assertEquals("grapes", allResponds.get(1).getName());
        Assert.assertEquals(5, allResponds.get(1).getPrice());
        Assert.assertEquals(50, allResponds.get(1).getStock());*/
    }
    /*
    @Test(priority = 2, enabled = false)
    public void validPostRequest() {

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("id", createRequest().getId());
        requestData.put("name", createRequest().getName());
        requestData.put("price", createRequest().getPrice());
        requestData.put("stock", createRequest().getStock());

        response = given()
                .header("Content-Type", "application/json")
                .body(requestData)
                .when()
                .post("/add")
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath bodyJson = response.jsonPath();
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(3, (Integer) bodyJson.get("id"));
        Assert.assertEquals("banana", bodyJson.get("name"));
        Assert.assertEquals(3.5, (Double) bodyJson.get("price"));
        Assert.assertEquals(250, (Integer) bodyJson.get("stock"));

    }
    @Test(priority = 3, enabled = false)
    public void validGetNameRequest() {

        this.response =
                given()
                        .accept(ContentType.JSON)
                        .header("Contect-Type", "application/json")
                        .when()
                        .get(createRequest().getName())
                        .then()
                        .statusCode(200)
                        .time(lessThan(500L))
                        .extract().response();

        JsonPath bodyJson = response.jsonPath();
        // Asserting for the first response
        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(3, (Integer) bodyJson.get("id"));
        Assert.assertEquals("apple", bodyJson.get("name"));
        Assert.assertEquals(3.5, (Double) bodyJson.get("price"));
        Assert.assertEquals(250, (Integer) bodyJson.get("stock"));
    }

    @Test(priority = 4, enabled = false)
    public void invalidPostRequestFourHundred() {

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("name", createRequest().getName()); // invalid section for 400
        requestData.put("name", createRequest().getName());
        requestData.put("price", createRequest().getPrice());
        requestData.put("stock", createRequest().getStock());

        this.response =
                given()
                        .accept(ContentType.JSON)
                        .header("Contect-Type", "application/json")
                        .body(requestData)
                        .when()
                        .get(createRequest().getName())
                        .then()
                        .statusCode(400)
                        .time(lessThan(500L))
                        .extract().response();

        JsonPath bodyJson = response.jsonPath();
        // Asserting for the first response
        Assert.assertNull(bodyJson);
    }
    @Test(priority = 5, enabled = false)
    public void invalidPostRequestFourHundredFour() {

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("id", 123123123); // invalid section for 404
        requestData.put("name", createRequest().getName());
        requestData.put("price", createRequest().getPrice());
        requestData.put("stock", createRequest().getStock());

        this.response =
                given()
                        .accept(ContentType.JSON)
                        .header("Contect-Type", "application/json")
                        .when()
                        .get(createRequest().getName())
                        .then()
                        .statusCode(404)
                        .time(lessThan(500L))
                        .extract().response();

        JsonPath bodyJson = response.jsonPath();
        // Asserting for the first response
        Assert.assertNull(bodyJson);
    }*/

}
