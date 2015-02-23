package ecurrency.com.ecurreny;


import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suraj on 15-01-2015.
 */
public class ApiConnector {
    String result = "";
    HttpClient httpClient;
    HttpPost httpPost;
    String url = "";
    HttpResponse httpResponse;
    String responseText;

    public String registerUser(String name, String phone, String address, String aadhar, String email, String password)
    {
        try {
            url = "http://192.168.1.100/ecurrency/register.php";
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("name" , name));
            nameValuePairs.add(new BasicNameValuePair("phone" , phone));
            nameValuePairs.add(new BasicNameValuePair("address" , address));
            nameValuePairs.add(new BasicNameValuePair("dob" , "1 1 1"));
            nameValuePairs.add(new BasicNameValuePair("aadhar" , aadhar));
            nameValuePairs.add(new BasicNameValuePair("email" , email));
            nameValuePairs.add(new BasicNameValuePair("password" , password));


            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpResponse = httpClient.execute(httpPost);
            responseText = EntityUtils.toString(httpResponse.getEntity());

            Log.d("ECurrency", responseText);
            //responseText = responseText.substring(0,20);
            JSONObject jobject = new JSONObject(responseText);
            responseText = jobject.getString("message");
            return responseText;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return e.getMessage();
        }


    }

    public String checkLogin(String phone, String password)
    {
        try {
            url = "http://192.168.1.100/ecurrency/login.php";
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

            nameValuePairs.add(new BasicNameValuePair("phone" , phone));
            nameValuePairs.add(new BasicNameValuePair("password" , password));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpResponse = httpClient.execute(httpPost);
            responseText = EntityUtils.toString(httpResponse.getEntity());
            Log.e("ECurrency", responseText);
            //responseText = responseText.substring(0,20);
            JSONObject jobject = new JSONObject(responseText);
            responseText = jobject.getString("message");
            return responseText;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    public JSONObject checkPhone(String phone)
    {
        JSONObject resultJson = new JSONObject();
        try {
            url = "http://192.168.1.100/ecurrency/checkPhoneNum.php";
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);

            nameValuePairs.add(new BasicNameValuePair("phone" , phone));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpResponse = httpClient.execute(httpPost);
            responseText = EntityUtils.toString(httpResponse.getEntity());
            resultJson = new JSONObject(responseText);
            return resultJson;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return  resultJson;

    }

    public String processTrans(String phone, String receiver_id, String amount)
    {
        try {
            url = "http://192.168.1.100/ecurrency/ProcessTransaction.php";
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("phone" , phone));
            nameValuePairs.add(new BasicNameValuePair("receiver_id" , receiver_id));
            nameValuePairs.add(new BasicNameValuePair("amount" , amount));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpResponse = httpClient.execute(httpPost);
            responseText = EntityUtils.toString(httpResponse.getEntity());
            Log.e("ECurrency", responseText);
            //responseText = responseText.substring(0,20);
            JSONObject jobject = new JSONObject(responseText);
            responseText = jobject.getString("message");
            return responseText;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    public JSONArray getTestId(String email)
    {
        JSONArray resultJson = new JSONArray();
        try {
            url = "http://adaptivetest.tk/aesmob/report.php";
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(0);

            nameValuePairs.add(new BasicNameValuePair("Email_ID" , email));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpResponse = httpClient.execute(httpPost);
            responseText = EntityUtils.toString(httpResponse.getEntity());
            Log.i("response" , responseText);
            resultJson = new JSONArray(responseText);

            return resultJson;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return  resultJson;

    }

    public JSONArray getReport(String email, String test_ID)
    {
        JSONArray resultJson = new JSONArray();
        try {
            url = "http://adaptivetest.tk/aesmob/report1.php";
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(0);

            nameValuePairs.add(new BasicNameValuePair("Email_ID" , email));
            nameValuePairs.add(new BasicNameValuePair("Test_ID" , test_ID));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpResponse = httpClient.execute(httpPost);
            responseText = EntityUtils.toString(httpResponse.getEntity());
            Log.i("response" , responseText);
            resultJson = new JSONArray(responseText);

            return resultJson;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return  resultJson;

    }
}