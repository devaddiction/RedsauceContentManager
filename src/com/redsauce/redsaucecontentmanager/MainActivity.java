package com.redsauce.redsaucecontentmanager;

import com.redsauce.redsaucecontentmanager.restclient.RestClient;
import com.redsauce.redsaucecontentmanager.restclient.Response;
import com.redsauce.redsaucecontentmanager.restclient.Request;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

    public static final String LOGIN_URL = "http://apps.redsauce.net/AppController/commands_RSM/utilities/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        findViewById(R.id.login_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        Button b = (Button) findViewById(R.id.login_button);
        b.setClickable(false);
        new LongRunningGetIO().execute();
    }


    private class LongRunningGetIO extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String text = null;

            RestClient client = new RestClient(LOGIN_URL);
            Response response = null;
            try {
                Request r = new Request("RSvalidateUser.php");
                r.addParam("Password", "d41d8cd98f00b204e9800998ecf8427e");
                r.addParam("Login", "sparra@redsauce.net");
                r.addParam("RSappName", "Redsauce Manager");
                r.addParam("RSbuild", "5.1.2.3.123");
                r.addParam("RSlanguage", "en");
                r.addParam("RSuserMD5Password", "d41d8cd98f00b204e9800998ecf8427e");
                response = client.post(r);
            } catch (Exception e) {
                return e.getMessage();
            }

            text = response.getResponse();
            System.out.println(text);
            return text;
        }


        protected void onPostExecute(String results) {
            if (results != null) {
                EditText et = (EditText) findViewById(R.id.username);
                et.setText(results);
            }
            Button b = (Button) findViewById(R.id.login_button);
            b.setClickable(true);
        }
    }
}