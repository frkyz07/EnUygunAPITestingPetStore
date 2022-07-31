import com.github.javafaker.Faker;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import java.awt.*;

// i wasnt able to finish this part of the project so sorry for that
public class HttpEntityTests {

    HttpHeaders headers;
    RestTemplate restTemplate;
    Faker faker = new Faker();
    String id = faker.address().buildingNumber();
    String invalidId = ("-"+id);
    String petId = faker.address().buildingNumber();
    String body = "{\n  \"id\":"+id+",\n  \"petId\": "+petId+",\n  \"quantity\": 1,\n  \"shipDate\": \"2022-07-29T15:15:50.460+0000\",\n  \"status\": \"placed\",\n  \"complete\": true\n}";
    String post_endpoint = "/store/order/";
    String inventory_endpoint = "/store/inventory";
    String url = "https://petstore.swagger.io/v2";


    String invalid_body = "{\n  \"id\":"+invalidId+",\n  \"petId\": "+petId+",\n  \"quantity\": 1,\n  \"shipDate\": \"2022-07-29T15:15:50.460+0000\",\n  \"status\": \"placed\",\n  \"complete\": true\n}";
    public void ApiTest(){

        headers.setContentType(PageAttributes.MediaType);
    }

    @Test
    public String valid_post_request(){


            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("headers1", "headers1");

            HttpEntity entity = new HttpEntity(body, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, (org.springframework.http.HttpEntity<?>) entity, String.class);

            return responseEntity.getBody();
    }
}
