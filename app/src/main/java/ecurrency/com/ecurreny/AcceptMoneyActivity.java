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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;


public class AcceptMoneyActivity extends ActionBarActivity {

    TextView tvInfo;
    EditText etCode;
    Button btnSubmit;
    String code;
    String t_id;
    String receiver_phone;
    String amount;
    String name;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_money);

        tvInfo = (TextView) findViewById(R.id.tvInfo);
        etCode = (EditText) findViewById(R.id.etCode);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);


        t_id = getIntent().getExtras().getString("t_id");
        amount = getIntent().getExtras().getString("amount");
        receiver_phone = getIntent().getExtras().getString("receiver_phone");
        name = getIntent().getExtras().getString("name");

        tvInfo.setText(" Amount : "+amount+"\n Sender's Phone : "+receiver_phone+"\n Name : "+name);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                code = etCode.getText().toString();
                new AcceptAsync().execute(new ApiConnector());

            }
        });
    }

    private void checkResult(String result)
    {
        if(result.equals("Success"))
        {

            Toast.makeText(context, "Transaction Completed..!!", Toast.LENGTH_LONG).show();

            finish();
            //ReceiveActivity.receive.finish();
        }

        else
        {
            Toast.makeText(context, "Wrong Code.. Please retry..", Toast.LENGTH_SHORT).show();
        }
    }

    private class AcceptAsync extends AsyncTask<ApiConnector, Void, String>
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
            return params[0].acceptAmount(t_id, code);

        }

        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();
            checkResult(result);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accept_money, menu);
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
