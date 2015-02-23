package ecurrency.com.ecurreny;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class ReceiveActivity extends ActionBarActivity {

    TextView tvInfo;
    Context context = this;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        tvInfo = (TextView) findViewById(R.id.tvInfo);
        phone = getSharedPreferences("ECurrency", Context.MODE_PRIVATE).getString("phone", "0");
        new GetAmountAsync().execute(new ApiConnector());
    }


    private void checkResult(JSONArray result)
    {
        //tvInfo.setText(result.toString());

        try {
            if(result.length()>0)
            {
                tvInfo.setText(result.toString());
            } else {
                Toast.makeText(context, "No money available to be received.", Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    private class GetAmountAsync extends AsyncTask<ApiConnector, Void, JSONArray>
    {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd= new ProgressDialog(context);
            pd.setMessage("Please Wait");
            pd.setCancelable(false);
            pd.setIndeterminate(false);
            pd.show();
        }

        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            return params[0].getAmountList(phone);

        }

        @Override
        protected void onPostExecute(JSONArray result) {
            pd.dismiss();
            checkResult(result);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_receive, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
