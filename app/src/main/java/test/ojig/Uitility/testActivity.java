//package test.blahblah;
//
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.content.ClipData;
//import android.content.ClipboardManager;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.graphics.Typeface;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.StrictMode;
//import android.provider.MediaStore;
//import android.support.design.widget.NavigationView;
//import android.support.design.widget.TabLayout;
//import android.support.v4.view.ViewPager;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.DataOutputStream;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.UnsupportedEncodingException;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLDecoder;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.regex.Pattern;
//
//import jp.wasabeef.glide.transformations.BlurTransformation;
//import jp.wasabeef.glide.transformations.ColorFilterTransformation;
//import jp.wasabeef.glide.transformations.CropCircleTransformation;
//import test.ojig.R;
//
//public class testActivity extends AppCompatActivity {
//    DrawerLayout drawer;
//    ImageView Navigation_Drawer;
//    ImageView navi_login;
//    RecyclerView list_MyActivity;RelativeLayout Layout_MyActivity;
//
//    SharedPreferences preferences; //캐쉬 데이터 생성
//    SharedPreferences.Editor editor; //캐쉬 데이터 에디터 생성
//
//    String[][] parseredData;
//
//    String User_Pk = "";String User_Code = "";
//
//    private TabLayout mTab;
//
//    public static ViewPager mViewPager;
//
//    public static Activity activity_main;
//    String Profile = "";ImageView Navi_Profile;ImageView Navi_Profile_Blur;
//
//    ArrayList<Navi_MyActiviy_Model> navi_myActiviy_model;
//    Navi_MyActivity_Adapter adapter;
//
//    View navi_view;
//
//    protected boolean shouldAskPermissions() {
//        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
//    }
//
//    @TargetApi(23)
//    protected void askPermissions() {
//        String[] permissions = {
//                "android.permission.READ_EXTERNAL_STORAGE",
//                "android.permission.WRITE_EXTERNAL_STORAGE"
//        };
//        int requestCode = 200;
//        requestPermissions(permissions, requestCode);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        if (shouldAskPermissions()) {
//            askPermissions();
//        }
//
//        activity_main = this;
//
//        preferences = getSharedPreferences("blahblah", MODE_PRIVATE);
//        User_Pk = preferences.getString("Pk", ".");
//
//
//
//
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        //프로필 업데이트
//        setProfile(navi_view);
//        //나의 활동 셋팅
//        setMyActivity(navi_view);
//        //나의 활동 카운트 셋팅
//        setMyActivity_Count(navi_view);
//    }
//
//
//    //내 활동 값 카운트 셋팅
//    public void setMyActivity_Count(View drawerview){
//        HttpClient http = new HttpClient();
//        String result = http.HttpClient("Web_Blah", "MyActivity_Count.jsp", User_Pk);
//        String[][] parseredData = jsonParserList_MyActivity_Count(result);
//
//        String waiting = parseredData[0][1];
//        String delivery = parseredData[0][2];
//        String finish = parseredData[0][3];
//        int all = Integer.parseInt(waiting) + Integer.parseInt(delivery) + Integer.parseInt(finish);
//        TextView Txt_Myactivity_All = (TextView)drawerview.findViewById(R.id.txt_myactivity_all);
//        TextView Txt_Myactivity_Waiting = (TextView)drawerview.findViewById(R.id.txt_myactivity_waiting);
//        TextView Txt_Myactivity_Delivery = (TextView)drawerview.findViewById(R.id.txt_myactivity_delivery);
//        TextView Txt_Myactivity_Finish = (TextView)drawerview.findViewById(R.id.txt_myactivity_finish);
//
//        Txt_Myactivity_All.setText(all+"");
//        Txt_Myactivity_Waiting.setText(waiting);
//        Txt_Myactivity_Delivery.setText(delivery);
//        Txt_Myactivity_Finish.setText(finish);
//
//    }
//    public void setBottommenu(View drawer){
//        LinearLayout Layout_Bottommenu1 = (LinearLayout)drawer.findViewById(R.id.layout_bottommenu1);
//        LinearLayout Layout_Bottommenu2 = (LinearLayout)drawer.findViewById(R.id.layout_bottommenu2);
//        LinearLayout Layout_Bottommenu3 = (LinearLayout)drawer.findViewById(R.id.layout_bottommenu3);
//        LinearLayout Layout_Bottommenu4 = (LinearLayout)drawer.findViewById(R.id.layout_bottommenu4);
//        LinearLayout Layout_Bottommenu5 = (LinearLayout)drawer.findViewById(R.id.layout_bottommenu5);
//        LinearLayout Layout_Bottommenu6 = (LinearLayout)drawer.findViewById(R.id.layout_bottommenu6);
//        Layout_Bottommenu1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, Beta.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
//            }
//        });
//        Layout_Bottommenu1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, Beta.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
//            }
//        });
//        Layout_Bottommenu2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, BottomMenu2.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
//            }
//        });
//        Layout_Bottommenu3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, BottomMenu3.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
//            }
//        });
//        Layout_Bottommenu4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, Beta.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
//            }
//        });
//        Layout_Bottommenu5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url ="http://addplay.kr/";
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
//            }
//        });
//        Layout_Bottommenu6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, Beta.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
//            }
//        });
//    }
//    public void setPrivacy(View drawer){
//        TextView Txt_Privacy =  (TextView) drawer.findViewById(R.id.txt_privacy);
//        TextView Txt_Use =  (TextView) drawer.findViewById(R.id.txt_use);
//        TextView Txt_Attacking =  (TextView) drawer.findViewById(R.id.txt_attacking);
//
//        Txt_Privacy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url ="http://13.124.32.32:8080/Blah_Doc/privacy.php";
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
//            }
//        });
//        Txt_Use.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url ="http://13.124.32.32:8080/Blah_Doc/privacy2.php";
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
//            }
//        });
//        Txt_Attacking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url ="http://13.124.32.32:8080/Blah_Doc/privacy3.php";
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
//            }
//        });
//    }
//    public void setProfile(View drawer) {
//        HttpClient httpuser = new HttpClient();
//        String result_user = httpuser.HttpClient("Web_Blah", "User.jsp", User_Pk);
//        parseredData = jsonParserList_Preview(result_user);
//
//        Profile = parseredData[0][7];
//        String Name = parseredData[0][2];
//        String Phone = parseredData[0][3];
//        String Email = parseredData[0][4];
//        String de_phone = URLDecoder.decode(Phone);
//        String de_email = URLDecoder.decode(Email);
//        User_Code = parseredData[0][6];
//
//        String profile_url = Profile;
//        profile_url = profile_url.replaceAll("\"","");
//
//        Navi_Profile = (ImageView) drawer.findViewById(R.id.navi_imgprofile);
//        Navi_Profile_Blur = (ImageView)drawer.findViewById(R.id.img_profile_blur);
//        TextView Navi_Phone = (TextView)drawer.findViewById(R.id.txt_phone);
//        TextView Navi_Email = (TextView)drawer.findViewById(R.id.txt_email);
//
//        if(Profile.equals("bnVsbA==")){
//            Glide.with(this).load(R.drawable.profile_basic).diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true).bitmapTransform(new CropCircleTransformation(Glide.get(this).getBitmapPool()))
//                    .into(Navi_Profile);
//            Navi_Profile_Blur.setBackgroundColor(getResources().getColor(R.color.main_back1));
//        }
//        else{
//            try {
//                Glide.with(this).load(profile_url).diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .skipMemoryCache(true).bitmapTransform(new CropCircleTransformation(Glide.get(this).getBitmapPool()))
//                        .into(Navi_Profile);
//                Glide.with(this).load(profile_url).diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .skipMemoryCache(true).bitmapTransform(new BlurTransformation(getBaseContext(),25), new ColorFilterTransformation(getBaseContext(), Color.argb(100, 0, 0, 0)))
//                        .into(Navi_Profile_Blur);
//            } catch (Exception e) {
//
//            }
//        }
//        if(de_phone.equals("null")){
//
//        }
//        else{
//            Navi_Phone.setText(setChange_phonetype(de_phone));
//            Navi_Phone.setTextSize(18);
//            Navi_Phone.setTextColor(getResources().getColor(R.color.Black));
//            Navi_Phone.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/chemrea.ttf"));
//        }
//        if(de_email.equals("null")){
//
//        }
//        else{
//            Navi_Email.setText(de_email);
//            Navi_Email.setTextSize(18);
//            Navi_Email.setTextColor(getResources().getColor(R.color.Black));
//            Navi_Email.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/notosanscjkkr_demilight.otf"));
//        }
//        //프로필 이미지 변경 이벤트
//        Navi_Profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                View layout = inflater.inflate(R.layout.dialog_album, (ViewGroup) view.findViewById(R.id.Dialog_Album_Root));
//                final TextView Dialog_Basicimage = (TextView) layout.findViewById(R.id.Dialog_Basicimage);
//                final TextView Dialog_Album = (TextView) layout.findViewById(R.id.Dialog_Album);
//                final AlertDialog.Builder aDialog = new AlertDialog.Builder(view.getContext());
//                aDialog.setView(layout);
//                final AlertDialog ad = aDialog.create();
//                ad.show();
//                Dialog_Basicimage.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Glide.with(MainActivity.this).load(R.drawable.profile_basic).diskCacheStrategy(DiskCacheStrategy.NONE).bitmapTransform(new CropCircleTransformation(Glide.get(MainActivity.this).getBitmapPool()))
//                                .skipMemoryCache(true)
//                                .into(Navi_Profile);
//                        Glide.with(MainActivity.this).load(R.color.main_back1).diskCacheStrategy(DiskCacheStrategy.NONE).bitmapTransform(new CropCircleTransformation(Glide.get(MainActivity.this).getBitmapPool()))
//                                .skipMemoryCache(true)
//                                .into(Navi_Profile_Blur);
//                        //Navi_Profile_Blur.setBackgroundColor(getResources().getColor(R.color.main_back1));
//                        HttpClient user = new HttpClient();
//                        user.HttpClient("Web_Blah", "Change_Profile.jsp", User_Pk, "bnVsbA==");
////                            Main_Navigation_ImageView_Profile.setImageResource(R.drawable.profile_basic_image);
//                        ad.dismiss();
//                        Profile = "bnVsbA==";
//                    }
//                });
//                Dialog_Album.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        //사진 읽어오기위한 uri 작성하기.
//                        Uri uri = Uri.parse("content://media/external/images/media");
//                        //무언가 보여달라는 암시적 인텐트 객체 생성하기.
//                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                        //인텐트에 요청을 덛붙인다.
//                        intent.setAction(Intent.ACTION_PICK);
//                        //모든 이미지
//                        intent.setType("image/*");
//                        //결과값을 받아오는 액티비티를 실행한다.
//                        startActivityForResult(intent, 0);
//                        //결과 URL DB 저장
//                        try {
//                            String En_Profile = URLEncoder.encode(User_Pk, "utf-8");
//                            Profile = "http://13.124.32.32:8080/Blah_img/Profile/" + En_Profile + ".jpg";
//                            HttpClient user = new HttpClient();
//                            user.HttpClient("Web_Blah", "Change_Profile.jsp", User_Pk, Profile);
//                        }catch (UnsupportedEncodingException e){
//
//                        }
//                        ad.dismiss();
//                    }
//                });
//            }
//        });
//
//    }
//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//        try {
//            //인텐트에 데이터가 담겨 왔다면
//            if (!intent.getData().equals(null)) {
//                //해당경로의 이미지를 intent에 담긴 이미지 uri를 이용해서 Bitmap형태로 읽어온다.
//                Bitmap selPhoto = MediaStore.Images.Media.getBitmap(getContentResolver(), intent.getData());
//                //이미지의 크기 조절하기.
//                selPhoto = Bitmap.createScaledBitmap(selPhoto, 100, 100, true);
//                //image_bt.setImageBitmap(selPhoto);//썸네일
//                //화면에 출력해본다.
//                //Profile_ImageVIew_Profile.setImageBitmap(selPhoto);
//                Log.e("선택 된 이미지 ", "selPhoto : " + selPhoto);
//
//                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                StrictMode.setThreadPolicy(policy);
//                //선택한 이미지의 uri를 읽어온다.
//                Uri selPhotoUri = intent.getData();
//                Log.e("전송", "시~~작 ~~~~~!");
//                //업로드할 서버의 url 주소
//                String urlString = "";
//                urlString = "http://13.124.32.32:8080/Web_Blah/Change_Profile_Upload.jsp";
//                //절대경로를 획득한다!!! 중요~
//                Cursor c = getContentResolver().query(Uri.parse(selPhotoUri.toString()), null, null, null, null);
//                c.moveToNext();
//                //업로드할 파일의 절대경로 얻어오기("_data") 로 해도 된다.
//                String absolutePath = c.getString(c.getColumnIndex(MediaStore.MediaColumns.DATA));
//                Log.e("###파일의 절대 경로###", absolutePath);
//                //파일 업로드 시작!
//                HttpFileUpload(urlString, "", absolutePath);
//
//                Glide.with(MainActivity.this).load(Profile).diskCacheStrategy(DiskCacheStrategy.NONE).bitmapTransform(new CropCircleTransformation(Glide.get(MainActivity.this).getBitmapPool()))
//                        .skipMemoryCache(true)
//                        .into(Navi_Profile);
//                Glide.with(this).load(Profile).diskCacheStrategy(DiskCacheStrategy.NONE)
//                        .skipMemoryCache(true).bitmapTransform(new BlurTransformation(getBaseContext(),25), new ColorFilterTransformation(getBaseContext(), Color.argb(100, 0, 0, 0)))
//                        .into(Navi_Profile_Blur);
//            }
//        } catch (FileNotFoundException e) {
//            Log.e("tt",e.toString());
//            e.printStackTrace();
//        } catch (IOException e) {
//            Log.e("tt",e.toString());
//            e.printStackTrace();
//        } catch (NullPointerException e) {
//            Log.e("tt",e.toString());
//        }
//
//    }
//
//    String lineEnd = "\r\n";
//    String twoHyphens = "--";
//    String boundary = "*****";
//
//    public void HttpFileUpload(String urlString, String params, String fileName) {
//        // fileName=TeamName;
//        try {
//            //선택한 파일의 절대 경로를 이용해서 파일 입력 스트림 객체를 얻어온다.
//            FileInputStream mFileInputStream = new FileInputStream(fileName);
//            //파일을 업로드할 서버의 url 주소를이용해서 URL 객체 생성하기.
//            URL connectUrl = new URL(urlString);
//            //Connection 객체 얻어오기.
//            HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
//            conn.setDoInput(true);//입력할수 있도록
//            conn.setDoOutput(true); //출력할수 있도록
//            conn.setUseCaches(false);  //캐쉬 사용하지 않음
//
//            //post 전송
//            conn.setRequestMethod("POST");
//            //파일 업로드 할수 있도록 설정하기.
//            conn.setRequestProperty("Connection", "Keep-Alive");
//            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//
//            //DataOutputStream 객체 생성하기.
//            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
//            //전송할 데이터의 시작임을 알린다.
//            //String En_TeamName = URLEncoder.encode(TeamName, "utf-8");
//            dos.writeBytes(twoHyphens + boundary + lineEnd);
//            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + URLEncoder.encode(User_Pk, "utf-8") + ".jpg" + "\"" + lineEnd);
//            dos.writeBytes(lineEnd);
//            //한번에 읽어들일수있는 스트림의 크기를 얻어온다.
//            int bytesAvailable = mFileInputStream.available();
//            //byte단위로 읽어오기 위하여 byte 배열 객체를 준비한다.
//            byte[] buffer = new byte[bytesAvailable];
//            int bytesRead = 0;
//            // read image
//            while (bytesRead != -1) {
//                //파일에서 바이트단위로 읽어온다.
//                bytesRead = mFileInputStream.read(buffer);
//                if (bytesRead == -1) break; //더이상 읽을 데이터가 없다면 빠저나온다.
//                Log.d("Test", "image byte is " + bytesRead);
//                //읽은만큼 출력한다.
//                dos.write(buffer, 0, bytesRead);
//                //출력한 데이터 밀어내기
//                dos.flush();
//            }
//            //전송할 데이터의 끝임을 알린다.
//            dos.writeBytes(lineEnd);
//            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//            //flush() 타이밍??
//            //dos.flush();
//            dos.close();//스트림 닫아주기
//            mFileInputStream.close();//스트림 닫아주기.
//            // get response
//            int ch;
//            //입력 스트림 객체를 얻어온다.
//            InputStream is = conn.getInputStream();
//            StringBuffer b = new StringBuffer();
//            while ((ch = is.read()) != -1) {
//                b.append((char) ch);
//            }
//            String s = b.toString();
//            Log.e("Test", "result = " + s);
//
//        } catch (Exception e) {
//            Log.d("Test", "exception " + e.getMessage());
//        }
//    }
//    public String[][] jsonParserList_Preview(String pRecvServerPage) {
//        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
//        try {
//            JSONObject json = new JSONObject(pRecvServerPage);
//            JSONArray jArr = json.getJSONArray("List");
//            String[] jsonName = {"msg1", "msg2", "msg3", "msg4", "msg5", "msg6", "msg7", "msg8", "msg9", "msg10"};
//            String[][] parseredData = new String[jArr.length()][jsonName.length];
//            for (int i = 0; i < jArr.length(); i++) {
//                json = jArr.getJSONObject(i);
//                for (int j = 0; j < jsonName.length; j++) {
//                    parseredData[i][j] = json.getString(jsonName[j]);
//                }
//            }
//            return parseredData;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//
//    public String[][] jsonParserList_MyActivity_Count(String pRecvServerPage) {
//        Log.i("서버에서 받은 전체 내용", pRecvServerPage);
//        try {
//            JSONObject json = new JSONObject(pRecvServerPage);
//            JSONArray jArr = json.getJSONArray("List");
//            String[] jsonName = {"msg1", "msg2", "msg3", "msg4"};
//            String[][] parseredData = new String[jArr.length()][jsonName.length];
//            for (int i = 0; i < jArr.length(); i++) {
//                json = jArr.getJSONObject(i);
//                for (int j = 0; j < jsonName.length; j++) {
//                    parseredData[i][j] = json.getString(jsonName[j]);
//                }
//            }
//            return parseredData;
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//}