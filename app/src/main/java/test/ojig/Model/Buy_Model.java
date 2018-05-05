package test.ojig.Model;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 박효근 on 2018-02-07.
 */

public class Buy_Model {
    private Activity Activity;
    private String Buy_Pk;
    private String Category;
    private String User_Pk;
    private String Name;
    private String Title;
    private String Address;
    private String Amount;
    private String Memo;
    private String status;

    public Buy_Model(Activity activity, String buy_Pk, String category, String user_Pk, String name, String title, String address, String amount, String memo, String status) {
        this.Activity = activity;
        this.Buy_Pk = buy_Pk;
        this.Category = category;
        this.User_Pk = user_Pk;
        this.Name = name;
        this.Title = title;
        this.Address = address;
        this.Amount = amount;
        this.Memo = memo;
        this.status = status;
    }

    public android.app.Activity getActivity() {
        return Activity;
    }

    public String getBuy_Pk() {
        return Buy_Pk;
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
        return status;
    }
}
