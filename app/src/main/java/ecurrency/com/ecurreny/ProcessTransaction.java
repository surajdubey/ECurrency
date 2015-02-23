package ecurrency.com.ecurreny;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONObject;


public class ProcessTransaction extends ActionBarActivity {

    String receiver_id;
    String amount;
    String phone;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_transaction);

        Bundle bundle = getIntent().getExtras();
        receiver_id = bundle.getString("receiver_id");
        amount = bundle.getString("amount");
        phone = bundle.getString("phone");

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
            return params[0].processTrans(receiver_id,amount,phone);

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
