package ecurrency.com.ecurreny;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    EditText etPhone;
    EditText etPassword;
    Button btnRegister;
    Button btnLogin;
    Context context = this;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    String phone;
    String password;

    public static Activity login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPhone = (EditText) findViewById(R.id.etPhone);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        login= this;

        sharedPreferences = getSharedPreferences("ECurrency", Context.MODE_PRIVATE);
        //startActivity(new Intent(context, ReportActivity.class));

        if (sharedPreferences.getString("isLoggedIn", "false").equals("true")) {
            startActivity(new Intent(context, WelcomeActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = etPhone.getText().toString();
                password = etPassword.getText().toString();

                new LoginAsync().execute(new ApiConnector());
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void checkResult(String result)
    {
        if(result.equals("Success"))
        {
            editor= sharedPreferences.edit();
            editor = sharedPreferences.edit();
            editor.putString("isLoggedIn" , "true");
            editor.putString("phone" , phone);
            editor.commit();
            startActivity(new Intent(context , WelcomeActivity.class));
            finish();
        }
        else
        {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }

    }

    private class LoginAsync extends AsyncTask<ApiConnector, Void, String>
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
        protected String doInBackground(ApiConnector... params) {
            return params[0].checkLogin(phone, password);
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            checkResult(s);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
