package test.ojig.Machine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import test.ojig.Uitility.HttpClient;
import test.ojig.Uitility.HttpFileUpload;

public class Machine_FileUpload extends AsyncTask<String, Void, String> {
    private ProgressDialog asyncDialog = null;
    private ArrayList<String> img_path;
    private String Machine_Pk;
    private Context context;

    public Machine_FileUpload(Context context, ArrayList<String> img_path, String Machine_Pk) {
        this.context = context;
        asyncDialog = new ProgressDialog(context);
        this.img_path = img_path;
        this.Machine_Pk = Machine_Pk;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        asyncDialog.setMessage("게시 중입니다..");
        // show dialog
        asyncDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {

            String urlString = "http://13.209.35.228:8080/Web_Ojig2/machine_image_upload.jsp";
            HttpClient http = new HttpClient();
            //파일 업로드 시작!
            if (!img_path.isEmpty()) {
                for (int i = 0; i < img_path.size(); i++) {
                    new HttpFileUpload(urlString, Machine_Pk + "_" + i, img_path.get(i));
                    http.HttpClient("Web_Ojig2", "machine_image_write.jsp", Machine_Pk, String.valueOf(i));

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
        ((Activity)context).finish();
        super.onPostExecute(result);
    }



}
