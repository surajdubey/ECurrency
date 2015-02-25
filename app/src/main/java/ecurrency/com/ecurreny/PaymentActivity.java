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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;


public class PaymentActivity extends ActionBarActivity {

    EditText etPhone;
    EditText etAmount;
    Button btnSend;
    String phone;
    String amount;
    String receiver_id;
    String senderPhone;
    String userBalance;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        etPhone = (EditText) findViewById(R.id.etPhone);
        etAmount = (EditText) findViewById(R.id.etAmount);
        btnSend = (Button) findViewById(R.id.btnSend);

        userBalance = getIntent().getExtras().getString("userBalance");

        senderPhone = getSharedPreferences("ECurrency", Context.MODE_PRIVATE).getString("phone","0");

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = etPhone.getText().toString();
                amount = etAmount.getText().toString();

                if(senderPhone.equals(phone))
                {
                    Toast.makeText(context, "Sender and receiver's number can't be same.", Toast.LENGTH_SHORT).show();
                }
                else
                if(Integer.parseInt(userBalance)< Integer.parseInt(amount))
                {
                    Toast.makeText(context, "Sorry, you don't have enough balance to perform this transaction.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    new CheckPhoneAsync().execute(new ApiConnector());
                }
            }
        });
    }

    private void checkResult(JSONObject result)
    {
        try {
            if (result.getString("message").equals("Success")) {
                receiver_id = result.getString("user_id");
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Name: "+result.getString("name")+"\n Amount : "+amount+"\n Phone : "+phone);
                builder1.setCancelable(true);
                builder1.setTitle("Please confirm..");
                builder1.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                    public void onClick (DialogInterface dialog,int id){
                        //dialog.cancel();
                        Intent intent = new Intent(getApplicationContext(), ProcessTransaction.class);
                        intent.putExtra("receiver_id", receiver_id);
                        intent.putExtra("amount", amount);
                        //intent.putExtra("phone", phone);

                        startActivity(intent);

                    }
                });
                builder1.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            } else {
                Toast.makeText(context, "Phone number not found..!!", Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    private class CheckPhoneAsync extends AsyncTask<ApiConnector, Void, JSONObject>
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
        protected JSONObject doInBackground(ApiConnector... params) {
            return params[0].checkPhone(phone);

        }

        @Override
        protected void onPostExecute(JSONObject result) {
            pd.dismiss();
            checkResult(result);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment, menu);
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
