package models;

import lombok.Data;

public class User {

    @Data
    public class request{
        private int id;
        private int invalidId;
        private int petID;
        private int quantity;
        private String shipDate;
        private String status;
        private boolean complete;

    }
}
