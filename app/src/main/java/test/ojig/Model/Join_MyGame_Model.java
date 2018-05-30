package test.ojig.Model;

import android.app.Activity;

/**
 * Created by 박효근 on 2018-05-20.
 */

public class Join_MyGame_Model {
    private android.app.Activity Activity;
    private String Name;
    private String Count;

    public Join_MyGame_Model(Activity activity, String name, String count) {
        this.Activity = activity;
        this.Name = name;
        this.Count = count;
    }
    public Join_MyGame_Model() {
    }
    public android.app.Activity getActivity() {
        return Activity;
    }

    public String getName() {
        return Name;
    }

    public String getCount() {
        return Count;
    }

    public void setData(String name, String count){
        this.Name = name;
        this.Count = count;
    }

}

