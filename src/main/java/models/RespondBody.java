package models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RespondBody {
    public class Category{
        public int id;
        public String name;
    }

    public class Root{
        public int id;
        public Category category;
        public String name;
        public ArrayList<String> photoUrls;
        public ArrayList<Tag> tags;
        public String status;
    }

    public class Tag{
        public int id;
        public String name;
    }
}
