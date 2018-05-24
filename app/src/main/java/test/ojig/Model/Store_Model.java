package test.ojig.Model;

import android.app.Activity;

public class Store_Model {
    private Activity Activity;
    private String Store_Pk;
    private String Category;
    private String Type;
    private String User_Pk;
    private String Title;
    private String Space;
    private String Layer;
    private String Price;
    private String Deposit;
    private String Rental;
    private String Address;
    private String Memo;
    private String status;

    public Store_Model(android.app.Activity activity, String store_Pk, String category, String type, String user_Pk, String title, String space, String layer, String price, String deposit, String rental, String address, String memo, String status) {
        Activity = activity;
        Store_Pk = store_Pk;
        Category = category;
        Type = type;
        User_Pk = user_Pk;
        Title = title;
        Space = space;
        Layer = layer;
        Price = price;
        Deposit = deposit;
        Rental = rental;
        Address = address;
        Memo = memo;
        this.status = status;
    }

    public android.app.Activity getActivity() {
        return Activity;
    }

    public String getStore_Pk() {
        return Store_Pk;
    }

    public String getCategory() {
        return Category;
    }

    public String getType() {
        return Type;
    }

    public String getUser_Pk() {
        return User_Pk;
    }

    public String getTitle() {
        return Title;
    }

    public String getSpace() {
        return Space;
    }

    public String getLayer() {
        return Layer;
    }

    public String getPrice() {
        return Price;
    }

    public String getDeposit() {
        return Deposit;
    }

    public String getRental() {
        return Rental;
    }

    public String getAddress() {
        return Address;
    }

    public String getMemo() {
        return Memo;
    }

    public String getStatus() {
        return status;
    }
}
