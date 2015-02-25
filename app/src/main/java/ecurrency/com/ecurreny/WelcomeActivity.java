package ecurrency.com.ecurreny;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;


public class WelcomeActivity extends ActionBarActivity {

    Button btnPayment;
    Button btnReceive;
    Button btnTrans;

    TextView tvName;
    TextView tvBalance;

    Context context = this;

    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnPayment = (Button) findViewById(R.id.btnPayment);
        btnReceive = (Button) findViewById(R.id.btnReceive);
        btnTrans = (Button) findViewById(R.id.btnTrans);

        tvName = (TextView) findViewById(R.id.tvName);
        tvBalance = (TextView) findViewById(R.id.tvBalance);

        phone = getSharedPreferences("ECurrency", Context.MODE_PRIVATE).getString("phone" , "0");

        new WelcomeAsync().execute(new ApiConnector());

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                startActivity(intent);
            }
        });

        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReceiveActivity.class);
                startActivity(intent);

            }
        });

        btnTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TransactionListActivity.class);
                startActivity(intent);
            }
        });

    }


    private void checkResult(JSONObject result)
    {
        try {
            if (result.getString("message").equals("Success")) {
                tvName.setText("Hello "+result.getString("name"));
                tvBalance.setText("Your cuurent balance is "+result.getString("balance"));
            } else {
                Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }



    private class WelcomeAsync extends AsyncTask<ApiConnector, Void, JSONObject>
    {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd= new ProgressDialog(context);
            pd.setMessage("Please Wait");
            pd.setCancelable(false);
            pd.setIndeterminate(false);
            pd.show();
        }

        @Override
        protected JSONObject doInBackground(ApiConnector... params) {
            return params[0].getUserDetails(phone);
        }

        @Override
        protected void onPostExecute(JSONObject s) {
            pd.dismiss();
            checkResult(s);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        finish();
        startActivity(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
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
