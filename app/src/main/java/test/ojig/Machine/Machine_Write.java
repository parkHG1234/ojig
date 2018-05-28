package test.ojig.Machine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import test.ojig.Model.User_Model;
import test.ojig.R;
import test.ojig.Uitility.HttpClient;
import test.ojig.Sell.Sell_FileUpload;

public class Machine_Write extends AppCompatActivity implements View.OnClickListener {

    private String area = "";
    private String Profile = "";
    private String User_Pk;
    private ArrayList<User_Model> user_models;
    private String[][] parseredData;
    private AlertDialog dialog;
    private Button btn_area;
    private EditText et_title, et_machine, et_price, et_count, et_memo, et_company_name, et_phone, et_company_focus;
    private ImageView img_write0, img_write1, img_write2, img_write3, img_write4, img_write5;
    private ArrayList<String> img_path;
    private ArrayList<ImageView> img_obj;
    int img_count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine_write);

        init();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init() {


        img_write0 = (ImageView) findViewById(R.id.img_write0);
        img_write1 = (ImageView) findViewById(R.id.img_write1);
        img_write2 = (ImageView) findViewById(R.id.img_write2);
        img_write3 = (ImageView) findViewById(R.id.img_write3);
        img_write4 = (ImageView) findViewById(R.id.img_write4);
        img_write5 = (ImageView) findViewById(R.id.img_write5);

        img_path = new ArrayList<String>();
        img_obj = new ArrayList<ImageView>(Arrays.asList(img_write0, img_write1, img_write2, img_write3, img_write4, img_write5));

        User_Pk = getIntent().getStringExtra("User_Pk");
        Log.i("User_Pk", User_Pk);

        btn_area = (Button) findViewById(R.id.btn_area);
        et_title = (EditText) findViewById(R.id.et_title);
        et_machine = (EditText) findViewById(R.id.et_machine);
        et_count = (EditText) findViewById(R.id.et_count);
        et_price = (EditText) findViewById(R.id.et_price);
        et_memo = (EditText) findViewById(R.id.et_memo);
        et_company_name = (EditText) findViewById(R.id.et_company_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_company_focus = (EditText) findViewById(R.id.et_company_focus);

//
        HttpClient http = new HttpClient();
        String result = http.HttpClient("Web_Ojig2", "user_select.jsp", User_Pk);
        parseredData = jsonParserList(result);


        user_models = new ArrayList<User_Model>();
        for (int i = 0; i < parseredData.length; i++) {
            user_models.add(new User_Model(Machine_Write.this, parseredData[i][0], parseredData[i][1], parseredData[i][2], parseredData[i][3], parseredData[i][4], parseredData[i][5], parseredData[i][6]));
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
            case R.id.img_write0:
                Album(0);
                break;
            case R.id.img_write1:
                Album(1);
                break;
            case R.id.img_write2:
                Album(2);
                break;
            case R.id.img_write3:
                Album(3);
                break;
            case R.id.img_write4:
                Album(4);
                break;
            case R.id.img_write5:
                Album(5);
                break;
            case R.id.btn_area:
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
        }
    }


    public void setDialog_Area(View view) {
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_area, (ViewGroup) view.findViewById(R.id.Root));

        final AlertDialog.Builder aDialog = new AlertDialog.Builder(Machine_Write.this);
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

    public void Album(int i) {
        Uri uri = Uri.parse("content://media/external/images/media");
        //무언가 보여달라는 암시적 인텐트 객체 생성하기.
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        //인텐트에 요청을 덛붙인다.
        intent.setAction(Intent.ACTION_PICK);
        //모든 이미지
        intent.setType("image/*");
        //결과값을 받아오는 액티비티를 실행한다.
        startActivityForResult(intent, i);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        try {
            //인텐트에 데이터가 담겨 왔다면
            if (!intent.getData().equals(null)) {
                //해당경로의 이미지를 intent에 담긴 이미지 uri를 이용해서 Bitmap형태로 읽어온다.
                Bitmap selPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData());

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                //선택한 이미지의 uri를 읽어온다.
                Uri selPhotoUri = intent.getData();
                //절대경로를 획득한다!!! 중요~
                Cursor c = getContentResolver().query(Uri.parse(selPhotoUri.toString()), null, null, null, null);
                c.moveToNext();
                //업로드할 파일의 절대경로 얻어오기("_data") 로 해도 된다.
                String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
                if (img_path.size() >= requestCode + 1) {
                    img_path.set(requestCode, absolutePath);
                } else {
                    img_path.add(requestCode, absolutePath);
                    if (requestCode <= 5) {
                        img_count++;
                        if (requestCode == 5) {
                            Snackbar.make(getCurrentFocus(), "6장이 최대입니다.", Snackbar.LENGTH_SHORT).show();
                        } else {
                            img_obj.get(requestCode + 1).setVisibility(View.VISIBLE);
                        }
                    } else {
                        Snackbar.make(getCurrentFocus(), "6장이 최대입니다.", Snackbar.LENGTH_SHORT).show();
                    }
                }

                ExifInterface exif = new ExifInterface(absolutePath);
                int exifOrientation = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                int exifDegree = exifOrientationToDegrees(exifOrientation);
                img_obj.get(requestCode).setImageBitmap(rotate(selPhoto, exifDegree));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void fileUpload(String Machine_Pk) {
        Machine_FileUpload machine_FileUpload = new Machine_FileUpload(Machine_Write.this, img_path, Machine_Pk);
        for(int i=0;i<img_path.size();i++){
            Log.i("aaaaaa",img_path.get(i).toString());
        }
        Log.i("aaaaaaa",Machine_Pk);
        machine_FileUpload.execute();
    }

    private void btn_write() {

        if (et_title.getText().length() != 0) {
            if (et_machine.getText().length() != 0) {
                if (et_price.getText().length() != 0) {
                    if (et_count.getText().length() != 0) {
                        if (!area.equals("")) {
                            if (et_memo.getText().length() != 0) {
                                HttpClient http = new HttpClient();
                                String result = http.HttpClient("Web_Ojig2", "machine_write.jsp",
                                        User_Pk,
                                        et_title.getText().toString(),
                                        et_machine.getText().toString(),
                                        et_price.getText().toString(),
                                        et_count.getText().toString(),
                                        area,
                                        et_memo.getText().toString(),
                                        et_company_name.getText().toString(),
                                        et_phone.getText().toString(),
                                        et_company_focus.getText().toString());

                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    if (jsonObject.getString("msg1").equals("succed")) {

                                        for (int i = 0; i < img_count; i++) {
                                            http.HttpClient("Web_Ojig", "machine_imageupload.jsp", jsonObject.getString("msg2"), Integer.toString(i + 1));
                                        }
                                        fileUpload(jsonObject.getString("msg2"));
                                   } else {
                                        Toast.makeText(getApplicationContext(), "인터넷연결을 확인해주세요", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "메모을 입력해주세요", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "지역을 선택해주세요", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "보유 갯수을 입력해주세요", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "가격을 입력해주세요", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "게임기명을 입력해주세요", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "제목을 입력해주세요", Toast.LENGTH_LONG).show();
        }

    }

    public int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    public Bitmap rotate(Bitmap bitmap, int degrees) {
        if (degrees != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2,
                    (float) bitmap.getHeight() / 2);

            try {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (bitmap != converted) {
                    bitmap.recycle();
                    bitmap = converted;
                }
            } catch (OutOfMemoryError ex) {
                // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
            }
        }
        return bitmap;
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
}
