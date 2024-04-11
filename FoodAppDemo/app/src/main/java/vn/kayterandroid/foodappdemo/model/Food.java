package vn.kayterandroid.foodappdemo.model;

import com.google.gson.annotations.SerializedName;

public class Food {
    @SerializedName("image")
    String image;

    @SerializedName("title")
    String title;

    @SerializedName("price")
    String price;

    public Food(String image, String title, String price) {
        this.image = image;
        this.title = title;
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
