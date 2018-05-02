package test.ojig.Uitility;

import android.os.StrictMode;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by 박효근 on 2018-02-28.
 */

public class HttpClient_Get {
    public String HttpClient_Get(String Web){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String result = "";
        try {
            org.apache.http.client.HttpClient client = new DefaultHttpClient();
            String postURL = "Web";
            HttpGet post = new HttpGet(postURL);

            HttpResponse response = client.execute(post);
            BufferedReader bufreader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "utf-8"));
            String line = null;
            while ((line = bufreader.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
    }
}
