package test.ojig.Model;

import android.app.Activity;

public class Sell_Model {
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
    private String Status;

    public Sell_Model(android.app.Activity activity, String sell_Pk, String category, String user_Pk, String name, String title, String price, String address, String amount, String memo, String status) {
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
        Status = status;
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


    public String getStatus() {
        return Status;
    }

}
