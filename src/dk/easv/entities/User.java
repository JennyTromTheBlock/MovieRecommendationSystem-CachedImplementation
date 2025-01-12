package dk.easv.entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private List<Rating> ratings;

    private String password;


    private String imgPath;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        this.ratings = new ArrayList();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public int getRatingsSize(){
        return ratings.size();
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return id +
                ", '" + name + '\'' +
                ", ratings=" + ratings.size();
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
