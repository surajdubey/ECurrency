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
            url = Utility.BASE_URL+"register.php";
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
            url = Utility.BASE_URL+"login.php";
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

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
            url = Utility.BASE_URL+"checkPhoneNum.php";
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

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

    public String processTrans(String receiver_id, String amount, String phone, String code)
    {
        try {
            url = Utility.BASE_URL+"ProcessTransaction.php";
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("phone" , phone));
            nameValuePairs.add(new BasicNameValuePair("receiver_id" , receiver_id));
            nameValuePairs.add(new BasicNameValuePair("amount" , amount));
            nameValuePairs.add(new BasicNameValuePair("code" , code));
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

    public JSONArray getAmountList(String phone)
    {
        JSONArray resultJson = new JSONArray();
        try {
            url = Utility.BASE_URL+"ReceiveMoney.php";
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("phone" , phone));

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

    public String acceptAmount(String t_id, String code)
    {
        try {
            url = Utility.BASE_URL+"AcceptAmount.php";
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("t_id" , t_id));
            nameValuePairs.add(new BasicNameValuePair("code" , code));

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpResponse = httpClient.execute(httpPost);
            responseText = EntityUtils.toString(httpResponse.getEntity());
            Log.i("response" , responseText);

            JSONObject jobject = new JSONObject(responseText);
            responseText = jobject.getString("message");
            return responseText;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return responseText;

    }

    public JSONObject getUserDetails(String phone)
    {
        JSONObject resultJson = new JSONObject();
        try {
            url = Utility.BASE_URL+"GetUserDetails.php";
            httpClient = new DefaultHttpClient();
            httpPost = new HttpPost(url);
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("phone" , phone));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpResponse = httpClient.execute(httpPost);
            responseText = EntityUtils.toString(httpResponse.getEntity());

            Log.i("response" , responseText);

            resultJson = new JSONObject(responseText);
            return resultJson;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return  resultJson;

    }
}