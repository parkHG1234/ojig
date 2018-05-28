package test.ojig.Store;

import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

import test.ojig.Model.User_Model;
import test.ojig.R;
import test.ojig.Uitility.HttpClient;
import test.ojig.Uitility.HttpFileUpload_Video;

public class Store_Write extends AppCompatActivity implements View.OnClickListener {
    private static final int SELECT_VIDEO = 3;

    private String area = "";
    private Button btn_deal, btn_rent;
    private String Profile = "";
    private String User_Pk;
    private ArrayList<User_Model> user_models;
    private String[][] parseredData;
    private AlertDialog dialog;
    private Button btn_area;
    private EditText et_title, et_space, et_layer, et_price, et_deposit, et_rental, et_memo, et_company_name, et_phone, et_company_focus;
    private ImageView video_write;
    private ArrayList<String> img_path;
    private ArrayList<ImageView> img_obj;
    private LinearLayout layout_deal, layout_rent;
    private Boolean flag = true;
    private String type = "deal";
    private String selectedPath = "";
    Bitmap bitmap_video;

    String video = "false";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_write);

        init();

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {

        video_write = (ImageView) findViewById(R.id.video_write);

        User_Pk = getIntent().getStringExtra("User_Pk");

        btn_area = (Button) findViewById(R.id.btn_area);
        et_title = (EditText) findViewById(R.id.et_title);
        et_space = (EditText) findViewById(R.id.et_space);
        et_layer = (EditText) findViewById(R.id.et_layer);
        et_price = (EditText) findViewById(R.id.et_price);
        et_deposit = (EditText) findViewById(R.id.et_deposit);
        et_rental = (EditText) findViewById(R.id.et_rental);
        et_memo = (EditText) findViewById(R.id.et_memo);
        et_company_name = (EditText) findViewById(R.id.et_company_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_company_focus = (EditText) findViewById(R.id.et_company_focus);
        btn_deal = (Button) findViewById(R.id.btn_deal);
        btn_rent = (Button) findViewById(R.id.btn_rent);
        layout_deal = (LinearLayout) findViewById(R.id.layout_deal);
        layout_rent = (LinearLayout) findViewById(R.id.layout_rent);
        btn_rent.setSelected(true);

        setBtn();
//
        HttpClient http = new HttpClient();
        String result = http.HttpClient("Web_Ojig2", "user_select.jsp", User_Pk);
        parseredData = jsonParserList(result);


        user_models = new ArrayList<User_Model>();
        for (int i = 0; i < parseredData.length; i++) {
            user_models.add(new User_Model(Store_Write.this, parseredData[i][0], parseredData[i][1], parseredData[i][2], parseredData[i][3], parseredData[i][4], parseredData[i][5], parseredData[i][6]));
        }

        et_company_name.setText(user_models.get(0).getCompany_Name());
        et_phone.setText(user_models.get(0).getPhone());
        et_company_focus.setText(user_models.get(0).getCompany_Focus());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_write:
                btn_write();
                break;
            case R.id.video_write:
                Album();
                break;
            case R.id.btn_deal:
                flag = true;
                setBtn();
                break;
            case R.id.btn_rent:
                flag = false;
                setBtn();
                break;
            case R.id.btn_area:
                Toast.makeText(Store_Write.this, "ttt", Toast.LENGTH_LONG).show();
                setDialog_Area(v);
                break;
            case R.id.seoul:
                dialogset("서울");
                break;
            case R.id.gyeonggi:
                dialogset("경기");
                break;
            case R.id.incheon:
                dialogset("인천");
                break;
            case R.id.gangwon:
                dialogset("강원");
                break;
            case R.id.chungbuk:
                dialogset("충북");
                break;
            case R.id.chungnam:
                dialogset("충남");
                break;
            case R.id.jeonbuk:
                dialogset("전북");
                break;
            case R.id.jeonnam:
                dialogset("전남");
                break;
            case R.id.gyeongbuk:
                dialogset("경북");
                break;
            case R.id.gyeongnam:
                dialogset("경남");
                break;
            case R.id.jeju:
                dialogset("제주");
                break;
            case R.id.img_back:
                finish();
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
                break;
        }
    }
    public void setDialog_Area(View view) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_area, (ViewGroup) view.findViewById(R.id.Root));

        final AlertDialog.Builder aDialog = new AlertDialog.Builder(Store_Write.this);
        aDialog.setCancelable(false);
        aDialog.setView(layout);
        // Dialog 사이즈 조절 하기
        dialog = aDialog.create();
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    return true;
                }
                return false;
            }
        });
        dialog.show();
    }

    private void dialogset(String area) {
        this.area = area;
        btn_area.setText(area);
        dialog.dismiss();
    }

    public void Album() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select a Video "), SELECT_VIDEO);
    }

    public void setBtn() {
        if (flag) {
            btn_deal.setSelected(true);
            btn_deal.setBackgroundColor(getResources().getColor(R.color.point));
            btn_deal.setTextColor(getResources().getColor(R.color.white));
            btn_rent.setSelected(false);
            btn_rent.setBackgroundColor(getResources().getColor(R.color.white));
            btn_rent.setTextColor(getResources().getColor(R.color.black));
            layout_deal.setVisibility(View.VISIBLE);
            layout_rent.setVisibility(View.GONE);
            type = "deal";
        } else {
            btn_deal.setSelected(false);
            btn_deal.setBackgroundColor(getResources().getColor(R.color.white));
            btn_deal.setTextColor(getResources().getColor(R.color.black));
            btn_rent.setSelected(true);
            btn_rent.setBackgroundColor(getResources().getColor(R.color.point));
            btn_rent.setTextColor(getResources().getColor(R.color.white));
            layout_deal.setVisibility(View.GONE);
            layout_rent.setVisibility(View.VISIBLE);
            type = "rent";
        }
    }

    private void btn_write() {
        if (et_title.getText().length() != 0) {
            if (!area.equals("")) {
                if (et_space.getText().length() != 0) {
                    if (et_layer.getText().length() != 0) {
                        if (flag) {
                            if (et_price.getText().length() != 0) {
                                if (et_memo.getText().length() != 0) {
                                    HttpClient http = new HttpClient();
                                    String result = http.HttpClient("Web_Ojig", "Store_Write.jsp", type, User_Pk, et_title.getText().toString(), et_space.getText().toString(), et_layer.getText().toString(), et_price.getText().toString(), area, et_memo.getText().toString(), et_company_name.getText().toString(), et_phone.getText().toString(), et_company_focus.getText().toString(),video);

                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        if (jsonObject.getString("msg1").equals("succed")) {
                                            if(video.equals("true")){
                                                HttpFileUpload_Video upload_video = new HttpFileUpload_Video();
                                                upload_video.HttpFileUpload_Video(selectedPath, "http://13.209.35.228:8080/Web_Ojig/Upload_Video.jsp", jsonObject.getString("msg2"));
                                                HttpFileUpload("http://13.209.35.228:8080/Web_Ojig/Upload_Store_Thumbnail.jsp", "", saveBitmapToJpeg(Store_Write.this,bitmap_video,jsonObject.getString("msg2")));
                                            }
                                            else{

                                            }
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "인터넷연결을 확인해주세요", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "메모를 입력해주세요", Toast.LENGTH_LONG).show();

                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "매매가를 입력해주세요", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            if (et_deposit.getText().length() != 0) {
                                if (et_rental.getText().length() != 0) {
                                    if (et_memo.getText().length() != 0) {
                                        HttpClient http = new HttpClient();
                                        String result = http.HttpClient("Web_Ojig", "Store_Write.jsp", type, User_Pk, et_title.getText().toString(), et_space.getText().toString(), et_layer.getText().toString(), et_deposit.getText().toString(),
                                                et_rental.getText().toString(), area, et_memo.getText().toString(), et_company_name.getText().toString(), et_phone.getText().toString(), et_company_focus.getText().toString(), video);

                                    try {
                                        JSONObject jsonObject = new JSONObject(result);
                                        if (jsonObject.getString("msg1").equals("succed")) {
                                            if(video.equals("true")){
                                                HttpFileUpload_Video upload_video = new HttpFileUpload_Video();
                                                upload_video.HttpFileUpload_Video(selectedPath, "http://13.209.35.228:8080/Web_Ojig/Upload_Video.jsp", jsonObject.getString("msg2"));
                                                HttpFileUpload("http://13.209.35.228:8080/Web_Ojig/Upload_Store_Thumbnail.jsp", "", saveBitmapToJpeg(Store_Write.this,bitmap_video,jsonObject.getString("msg2")));
                                            }
                                            else{

                                            }
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "인터넷연결을 확인해주세요", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "메모가를 입력해주세요", Toast.LENGTH_LONG).show();

                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "임대료를 입력해주세요", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "보증금을 입력해주세요", Toast.LENGTH_LONG).show();

                            }
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "층수를 입력해주세요", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "평수를 입력해주세요", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "지역를 선택해주세요", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "제목를 입력해주세요", Toast.LENGTH_LONG).show();
        }
    }

    public String[][] jsonParserList(String pRecvServerPage) {
        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
        try {
            JSONObject json = new JSONObject(pRecvServerPage);
            JSONArray jArr = json.getJSONArray("List");
            String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg5", "msg6", "msg7"};
            String[][] parseredData = new String[jArr.length()][jsonName.length];
            for (int i = 0; i < jArr.length(); i++) {
                json = jArr.getJSONObject(i);
                for (int j = 0; j < jsonName.length; j++) {
                    parseredData[i][j] = json.getString(jsonName[j]);
                }
            }
            return parseredData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_VIDEO) {
                System.out.println("SELECT_VIDEO");
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(Store_Write.this,selectedImageUri);
                System.out.println("SELECT_VIDEO Path : " + selectedPath);
                bitmap_video = ThumbnailUtils.createVideoThumbnail(selectedPath, MediaStore.Images.Thumbnails.FULL_SCREEN_KIND);
                video_write.setImageBitmap(bitmap_video);

                video = "true";
            }
        }
    }
    public static String saveBitmapToJpeg(Context context,Bitmap bitmap, String name){

        File storage = context.getCacheDir(); // 이 부분이 임시파일 저장 경로

        String fileName = name + ".jpg";  // 파일이름은 마음대로!

        File tempFile = new File(storage,fileName);

        try{
            tempFile.createNewFile();  // 파일을 생성해주고

            FileOutputStream out = new FileOutputStream(tempFile);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 90 , out);  // 넘거 받은 bitmap을 jpeg(손실압축)으로 저장해줌

            out.close(); // 마무리로 닫아줍니다.

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempFile.getAbsolutePath();   // 임시파일 저장경로를 리턴해주면 끝!
    }
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";

    public void HttpFileUpload(String urlString, String params, String fileName) {
        // fileName=TeamName;
        try {
            //선택한 파일의 절대 경로를 이용해서 파일 입력 스트림 객체를 얻어온다.
            FileInputStream mFileInputStream = new FileInputStream(fileName);
            //파일을 업로드할 서버의 url 주소를이용해서 URL 객체 생성하기.
            URL connectUrl = new URL(urlString);
            //Connection 객체 얻어오기.
            HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
            conn.setDoInput(true);//입력할수 있도록
            conn.setDoOutput(true); //출력할수 있도록
            conn.setUseCaches(false);  //캐쉬 사용하지 않음

            //post 전송
            conn.setRequestMethod("POST");
            //파일 업로드 할수 있도록 설정하기.
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            //DataOutputStream 객체 생성하기.
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            //전송할 데이터의 시작임을 알린다.
            //String En_TeamName = URLEncoder.encode(TeamName, "utf-8");
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);
            //한번에 읽어들일수있는 스트림의 크기를 얻어온다.
            int bytesAvailable = mFileInputStream.available();
            //byte단위로 읽어오기 위하여 byte 배열 객체를 준비한다.
            byte[] buffer = new byte[bytesAvailable];
            int bytesRead = 0;
            // read image
            while (bytesRead != -1) {
                //파일에서 바이트단위로 읽어온다.
                bytesRead = mFileInputStream.read(buffer);
                if (bytesRead == -1) break; //더이상 읽을 데이터가 없다면 빠저나온다.
                Log.d("Test", "image byte is " + bytesRead);
                //읽은만큼 출력한다.
                dos.write(buffer, 0, bytesRead);
                //출력한 데이터 밀어내기
                dos.flush();
            }
            //전송할 데이터의 끝임을 알린다.
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            //flush() 타이밍??
            //dos.flush();
            dos.close();//스트림 닫아주기
            mFileInputStream.close();//스트림 닫아주기.
            // get response
            int ch;
            //입력 스트림 객체를 얻어온다.
            InputStream is = conn.getInputStream();
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            String s = b.toString();
            Log.e("Test", "result = " + s);

        } catch (Exception e) {
            Log.d("Test", "exception " + e.getMessage());
        }
    }
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
