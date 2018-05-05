package test.ojig.Model;

import android.app.Activity;

public class Video_Model {
    private String Pk;
    private String Title;
    private String CompanyName;
    private String Content;
    private String Date;
    private String GameName;
    public Video_Model( String pk,  String title, String CompanyName, String Content, String Date, String gamename){

        this.Pk = pk;
        this.Title = title;
        this.CompanyName = CompanyName;
        this.Content = Content;
        this.Date = Date;
        this.GameName = gamename;
    }

    public String getPk() {
        return Pk;
    }

    public String getTitle() {
        return Title;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public String getContent() {
        return Content;
    }

    public String getDate() {
        return Date;
    }

    public String getGameName() {
        return GameName;
    }
}
