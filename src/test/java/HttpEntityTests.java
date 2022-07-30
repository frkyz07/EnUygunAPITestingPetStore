import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.testng.annotations.Test;

import java.awt.*;

public class HttpEntityTests {

    HttpHeaders headers = new HttpHeaders();
    public void apitest(){
        headers.setContentType(PageAttributes.MediaType.APPLICATION_JSON);
    }

    @Test
    public void valid_post_request(){

        HttpEntity<String> request =
                new HttpEntity<String>(body,headers);
    }
}
