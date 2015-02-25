package ecurrency.com.ecurreny;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Random;


public class ProcessTransaction extends ActionBarActivity {

    String receiver_id;
    String amount;
    String phone;
    Context context = this;
    String code;
    int maxInt = 999999;
    int minInt = 100000;
    TextView tvCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_transaction);

        tvCode = (TextView) findViewById(R.id.tvCode);

        Bundle bundle = getIntent().getExtras();
        receiver_id = bundle.getString("receiver_id");
        amount = bundle.getString("amount");
        //phone = bundle.getString("phone");

        phone = getSharedPreferences("ECurrency", Context.MODE_PRIVATE).getString("phone", "0");
        Random rand = new Random();
        int codeInt = rand.nextInt((maxInt - minInt)+1)+minInt;
        code = String.valueOf(codeInt);

        new ProcessTransAsync().execute(new ApiConnector());

    }


    private class ProcessTransAsync extends AsyncTask<ApiConnector, Void, String>
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
        protected String doInBackground(ApiConnector... params) {

            return params[0].processTrans(receiver_id,amount,phone, code);

        }

        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();
            checkResult(result);
        }
    }

    private void checkResult(String result)
    {
        if(result.equals("Success"))
        {
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
            tvCode.setText("Code for transaction is "+code);
        }
        else
        {
            Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_process_transaction, menu);
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
