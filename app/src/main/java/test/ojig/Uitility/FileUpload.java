package test.ojig.Uitility;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

public class FileUpload extends AsyncTask<String, Void, String> {
    private ProgressDialog asyncDialog = null;
    private ArrayList<String> img_path;
    private String User_Pk;

    public FileUpload(Context context, ArrayList<String> img_path, String User_Pk) {
        asyncDialog = new ProgressDialog(context);
        this.img_path = img_path;
        this.User_Pk = User_Pk;
    }

    @Override
    protected void onPreExecute() {
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("게시 중입니다..");
        // show dialog
        asyncDialog.show();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        try {

            String urlString = "http://13.209.35.228:8080/Web_Ojig2/ImageUpload.jsp";
            //파일 업로드 시작!
            for (int i = 0; i < img_path.size(); i++) {
                if (!img_path.isEmpty()) {
                    new HttpFileUpload(urlString, User_Pk+"_"+i, img_path.get(i));
                }
            }
            return "succed";
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
    }

    @Override
    protected void onPostExecute(String result) {

        asyncDialog.dismiss();
        super.onPostExecute(result);
    }
}
