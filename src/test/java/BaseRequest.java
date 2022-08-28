import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import models.RequestBody;
import org.json.JSONObject;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class BaseRequest {

    Response response;

    public RequestBody createRequest() {
        int petId = 690;
        String name = "Ahmet";
        String status = "pending";
        String type ="unknown";
        return new RequestBody(petId, name, status,type);
    }
    public JSONObject postRequestCreater(){
        JSONObject properties = new JSONObject();
        properties.put("name",createRequest().getName());
        properties.put("status", createRequest().getStatus());
        return properties;
    }
    public Response getRequestWithParams(String param) throws JsonProcessingException {

        response = given()
                .log().all()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json")
                .when()
                .params("status", param)
                .get("/pet/findByStatus")
                .then()
                .extract().response();
            return response;
    }
    public Response getRequestWithId(int id) throws JsonProcessingException {

        response = given()
                .log().all()
                .accept(ContentType.JSON)
                .header("Content-Type", "application/json")
                .when()
                .get("/pet/"+id)
                .then()
                .extract().response();
        return response;
    }
    public Response postRequest(int id){
        response =
                given()
                        .log().all()
                        .accept("application/json")
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .body(postRequestCreater().toString())
                        .when()
                        .post("/pet/"+id)
                        .then()
                        .extract().response();
        return response;
    }
    public Response deleteRequest(int id){
        response =
                given()
                        .log().all()
                        .accept(ContentType.JSON)
                        .header("Content-Type", "application/json")
                        .when()
                        .delete("/pet/"+id)
                        .then()
                        .extract().response();
        return response;
    }
    public Response invalidDeleteRequest(){
        response =
                given()
                        .log().all()
                        .accept(ContentType.JSON)
                        .header("Content-Type", "application/json")
                        .when()
                        .delete("/pet/")
                        .then()
                        .extract().response();
        return response;
    }
    public ArrayList statusList(String param, String value){
        ArrayList status = response.path(param,value);
        return status;
    }
    public String idGetter(int id){
        ResponseBody body = response.getBody();
        // getting the id variables and body
        ArrayList firstResponseIds = body.jsonPath().get(String.valueOf("id"));
        Object newRequestId = firstResponseIds.get(id);
        return String.valueOf(newRequestId);
    }

}
