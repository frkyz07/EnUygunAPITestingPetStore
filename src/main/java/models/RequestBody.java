package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class RequestBody {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class request{
        int id;
        String name;
        double price;
        int stock;


    }
}

