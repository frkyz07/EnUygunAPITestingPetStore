import com.github.javafaker.Faker;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

// i wasnt able to finish this part of the project so sorry for that
public class HttpEntityTests {

    HttpHeaders headers;
    RestTemplate restTemplate;
    ObjectMapper objectMapper;
    JSONObject jsonObject;
    Faker faker = new Faker();
    private Faker fakeValuesService;
    int id = Integer.parseInt(faker.address().buildingNumber());
    int petId = this.faker.number().randomDigitNotZero();
    String shipDate = this.fakeValuesService.bothify("####-##-29T13:##:##.####");

    //String body = "{\n  \"id\":"+id+",\n  \"petId\": "+petId+",\n  \"quantity\": 1,\n  \"shipDate\": \"2022-07-29T15:15:50.460+0000\",\n  \"status\": \"placed\",\n  \"complete\": true\n}";
    String post_endpoint = "/store/order/";
    String inventory_endpoint = "/store/inventory";
    String url = "https://petstore.swagger.io/v2";
    String invalidId = ("-"+id);



    //String invalid_body = "{\n  \"id\":"+invalidId+",\n  \"petId\": "+petId+",\n  \"quantity\": 1,\n  \"shipDate\": \"2022-07-29T15:15:50.460+0000\",\n  \"status\": \"placed\",\n  \"complete\": true\n}";
    public HttpEntityTests(Faker fakeValuesService){
        this.fakeValuesService = fakeValuesService;
        this.headers = new HttpHeaders();
        this.objectMapper = new ObjectMapper();
        this.jsonObject = new JSONObject();
        this.restTemplate = new RestTemplate();

    }

    @Test
    public String valid_post_request(){



            HttpEntity entity = new HttpEntity(body, headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, (org.springframework.http.HttpEntity<?>) entity, String.class);

            return responseEntity.getBody();
    }
}
