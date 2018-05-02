package test.ojig.Model;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 박효근 on 2018-02-07.
 */

public class Buy_Model {
    private Activity Activity;
    private String Pk;
    private String User_Pk;
    private String Title;
    private String Memo;
    private String Address;
    private String Count;
    private String GameName;
    private String Status;
    public Buy_Model(Activity activity, String pk, String user_Pk, String title, String memo, String address, String count, String gamename, String status){
        this.Activity = activity;
        this.Pk = pk;
        this.User_Pk = user_Pk;
        this.Title = title;
        this.Memo = memo;
        this.Address = address;
        this.Count = count;
        this.GameName = gamename;
        this.Status = status;
    }
    public Activity getActivity() {
        return Activity;
    }
    public String getPk() {
        return Pk;
    }
    public String getUser_Pk() {
        return User_Pk;
    }
    public String getTitle(){return Title;}
    public String getMemo(){
        return Memo;
    }
    public String getAddress(){return Address;}
    public String getCount(){return Count;}
    public String getGameName(){return GameName;}
    public String getStatus(){return Status;}
}
