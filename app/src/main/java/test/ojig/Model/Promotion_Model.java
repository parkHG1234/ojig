package test.ojig.Model;

import android.app.Activity;

/**
 * Created by 박효근 on 2018-05-05.
 */

public class Promotion_Model {
    private android.app.Activity Activity;
    private String Pk;
    private String GameName;
    private String CompanyName;
    private String Title;
    private String Date;
    public Promotion_Model(Activity activity, String pk, String gameName, String companyName, String title,  String date){
        this.Activity = activity;
        this.Pk = pk;
        this.GameName = gameName;
        this.CompanyName = companyName;
        this.Title = title;
        this.Date = date;
    }
    public Activity getActivity() {
        return Activity;
    }
    public String getPk() {
        return Pk;
    }
    public String getGameName(){
        return GameName;
    }
    public String getCompanyName(){
        return CompanyName;
    }
    public String getTitle(){
        return Title;
    }
    public String getDate(){
        return Date;
    }
}
