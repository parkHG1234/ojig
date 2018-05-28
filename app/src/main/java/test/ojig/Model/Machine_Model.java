package test.ojig.Model;

import android.app.Activity;

public class Machine_Model {

    private android.app.Activity Activity;
    private String Machine_Pk;
    private String User_Pk;
    private String Title;
    private String Machine;
    private String Address;
    private String Price;
    private String Count;
    private String Memo;
    private String Status;

    public Machine_Model(android.app.Activity activity, String machine_Pk, String user_Pk, String title, String machine, String address, String price, String count, String memo, String status) {
        Activity = activity;
        Machine_Pk = machine_Pk;
        User_Pk = user_Pk;
        Title = title;
        Machine = machine;
        Address = address;
        Price = price;
        Count = count;
        Memo = memo;
        Status = status;
    }

    public android.app.Activity getActivity() {
        return Activity;
    }

    public String getMachine_Pk() {
        return Machine_Pk;
    }

    public String getUser_Pk() {
        return User_Pk;
    }

    public String getTitle() {
        return Title;
    }

    public String getMachine() {
        return Machine;
    }

    public String getAddress() {
        return Address;
    }

    public String getPrice() {
        return Price;
    }

    public String getCount() {
        return Count;
    }

    public String getMemo() {
        return Memo;
    }

    public String getStatus() {
        return Status;
    }
}
