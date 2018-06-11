package test.ojig.Model;

/**
 * Created by 박효근 on 2018-06-11.
 */

public class Others_Menu_Model {
    private android.app.Activity Activity;
    private String Pk;
    private String Name;
    private String Price;

    public Others_Menu_Model(android.app.Activity activity, String pk, String name, String price) {
        Activity = activity;
        Pk = pk;
        Price = price;
        Name = name;
    }

    public android.app.Activity getActivity() {
        return Activity;
    }
    public String getPk() {
        return Pk;
    }
    public String getName() {
        return Name;
    }
    public String getPrice() {
        return Price;
    }
}
