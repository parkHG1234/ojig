package test.ojig.Uitility;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;

import test.ojig.R;

public class FileUpload extends AsyncTask<String, Void, String> {
    ProgressDialog asyncDialog = null;
    String[][] parsedData;
    public FileUpload(Context context) {
        asyncDialog = new ProgressDialog(context);
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
//            HttpClient user = new HttpClient();
//            String result1 = user.HttpClient("Trophy_part1", "OutCourt_Focus_Write.jsp", User_Pk, Court_Pk, strCurToday, content, strCurYear);
//            parseredData_write = jsonParserList_Write(result1);
//            if(parseredData_write[0][0].equals("succed")){
//                filename = parseredData_write[0][1];
//                if(flag){
//                    //파일 업로드 시작!
//                    String urlString = "http://210.122.7.193:8080/Trophy_part1/Content_Image_Upload.jsp";
//                    HttpFileUpload(urlString, "", ImageURL);
//                    HttpClient image = new HttpClient();
//                    image.HttpClient("Trophy_part1", "OutCourt_Focus_Write_Image.jsp", filename,Integer.toString(Image_width),Integer.toString(Image_Height));
//                }else{
//
//                }
//                finish();
//                overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom);
//                flag = false;
//            }
//            else{
//                Snackbar.make(getCurrentFocus(),"잠시 후 다시 시도해주시기 바랍니다.", Snackbar.LENGTH_SHORT).show();
//            }
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
