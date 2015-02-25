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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class TransactionListActivity extends ActionBarActivity {

    ListView lvTransactionList;
    Context context = this;

    private ArrayList<String> items;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);

        phone = getSharedPreferences("ECurrency", Context.MODE_PRIVATE).getString("phone", "0");
        lvTransactionList = (ListView) findViewById(R.id.lvTransactionList);
        new GetTransactionListAsync().execute(new ApiConnector());
    }

    private void checkResult(final JSONArray jsonArray)
    {
        //tvInfo.setText(result.toString());

        JSONObject jobject = new JSONObject();

        if(jsonArray.length()==0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setCancelable(false);
            alertDialog.setTitle("No transaction performed");
            alertDialog.setMessage("Please perform any transation");
            alertDialog.setButton("OK" , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    finish();

                }
            });
            alertDialog.show();
        }

        else {
            try
            {
                items = new ArrayList<String>();
                for(int i=0;i<jsonArray.length();i++)
                {
                    jobject = jsonArray.getJSONObject(i);
                    String content  = jobject.getString("activity")+" - "+jobject.getString("name")+"("+jobject.getString("phone")+") \n";
                    if(jobject.getString("completed").equals("No"))
                    {
                        content = content + " Transaction Not Completed";

                        if(jobject.getString("activity").equals("sent"))
                        {
                            content = content + "\n Transaction Code : "+jobject.getString("code");
                        }

                        if(jobject.getString("activity").equals("received"))
                        {
                            content = content + "\n Complete from receive section";
                        }

                    }
                    else
                    {
                        content = content + "Transaction Completed";
                    }



                    items.add(content);

                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
                lvTransactionList.setAdapter(arrayAdapter);

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

    }



    private class GetTransactionListAsync extends AsyncTask<ApiConnector, Void, JSONArray>
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
            return params[0].getTransactionList(phone);

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
        getMenuInflater().inflate(R.menu.menu_transaction_list, menu);
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
