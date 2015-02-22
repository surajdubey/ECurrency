package ecurrency.com.ecurreny;

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


public class RegisterActivity extends ActionBarActivity {

    EditText etName, etPhone, etAddress, etAadhar, etEmail, etPassword;
    Button btnRegister;

    String name;
    String phone;
    String address;
    String aadhar;
    String email;
    String password;
    Context context = this;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.etName);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etAadhar = (EditText) findViewById(R.id.etAadhar);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        sharedPreferences = getSharedPreferences("ECurrency", Context.MODE_PRIVATE);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etName.getText().toString();
                phone = etPhone.getText().toString();
                address = etAddress.getText().toString();
                aadhar = etAadhar.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();
                new RegisterAsync().execute(new ApiConnector());
            }
        });
    }

    private class RegisterAsync extends AsyncTask<ApiConnector, Void, String> {

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
            return params[0].registerUser(name, phone , address, aadhar, email, password);

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
            editor = sharedPreferences.edit();
            editor.putString("isLoggedIn" , "true");
            editor.putString("phome" , phone);
            editor.commit();
            startActivity(new Intent(context , WelcomeActivity.class));
            MainActivity.login.finish();
            finish();
        }
        else
        {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
