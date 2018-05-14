package test.ojig.Model;

import android.app.Activity;

public class Store_Model {
    private Activity Activity;
    private String Sell_Pk;
    private String Category;
    private String User_Pk;
    private String Name;
    private String Title;
    private String Price;
    private String Address;
    private String Amount;
    private String Memo;
    private String Img;
    private String Video;
    private String status;

    public Store_Model(android.app.Activity activity, String sell_Pk, String category, String user_Pk, String name, String title, String price, String address, String amount, String memo, String img, String video, String status) {
        Activity = activity;
        Sell_Pk = sell_Pk;
        Category = category;
        User_Pk = user_Pk;
        Name = name;
        Title = title;
        Price = price;
        Address = address;
        Amount = amount;
        Memo = memo;
        Img = img;
        Video = video;
        this.status = status;
    }

    public android.app.Activity getActivity() {
        return Activity;
    }

    public String getSell_Pk() {
        return Sell_Pk;
    }

    public String getCategory() {
        return Category;
    }

    public String getUser_Pk() {
        return User_Pk;
    }

    public String getName() {
        return Name;
    }

    public String getTitle() {
        return Title;
    }

    public String getPrice() {
        return Price;
    }

    public String getAddress() {
        return Address;
    }

    public String getAmount() {
        return Amount;
    }

    public String getMemo() {
        return Memo;
    }

    public String getImg() {
        return Img;
    }

    public String getVideo() {
        return Video;
    }

    public String getStatus() {
        return status;
    }
}