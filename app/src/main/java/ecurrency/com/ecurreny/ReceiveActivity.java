package ecurrency.com.ecurreny;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;


public class ReceiveActivity extends ActionBarActivity {

    TextView tvInfo;
    Context context = this;
    String phone;
    ListView lvReceive;
    ArrayList<String> items;

    public static Activity receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);

        receive = this;

        lvReceive = (ListView) findViewById(R.id.lvReceive);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
        phone = getSharedPreferences("ECurrency", Context.MODE_PRIVATE).getString("phone", "0");
        new GetAmountAsync().execute(new ApiConnector());
    }


    private void checkResult(final JSONArray jsonArray)
    {
        //tvInfo.setText(result.toString());

        JSONObject jobject = new JSONObject();

        if(jsonArray.length()==0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setCancelable(false);
            alertDialog.setTitle("Nothing to receive");
            alertDialog.setMessage("No money available to receive");
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
                    items.add("Rs. "+jobject.getString("amount")+" from "+jobject.getString("sender_name")+"("+jobject.getString("sender_phone")+")");

                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
                lvReceive.setAdapter(arrayAdapter);

                lvReceive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        try {

                            Intent intent = new Intent(getApplicationContext(), AcceptMoneyActivity.class);
                            intent.putExtra("t_id", jsonArray.getJSONObject(position).getString("t_id"));
                            startActivity(intent);
                        }
                        catch(Exception e)
                        {

                        }
                    }
                });

            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
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
    protected void onRestart() {
        super.onRestart();

        finish();
        startActivity(getIntent());
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
