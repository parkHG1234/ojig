package test.ojig.Model;

import android.app.Activity;

public class User_Model {

    private Activity Activity;
    private String User_Pk;
    private String Phone;
    private String Pass;
    private String Company_Name;
    private String Company_Num;
    private String Company_Txt;
    private String Company_Focus;

    public User_Model(Activity activity,String user_Pk, String phone, String pass, String company_Name, String company_Num, String company_Txt, String company_Focus) {
        Activity = activity;
        User_Pk = user_Pk;
        Phone = phone;
        Pass = pass;
        Company_Name = company_Name;
        Company_Num = company_Num;
        Company_Txt = company_Txt;
        Company_Focus = company_Focus;
    }

    public String getUser_Pk() {
        return User_Pk;
    }

    public String getPhone() {
        return Phone;
    }

    public String getPass() {
        return Pass;
    }

    public String getCompany_Name() {
        return Company_Name;
    }

    public String getCompany_Num() {
        return Company_Num;
    }

    public String getCompany_Txt() {
        return Company_Txt;
    }

    public String getCompany_Focus() {
        return Company_Focus;
    }
}
