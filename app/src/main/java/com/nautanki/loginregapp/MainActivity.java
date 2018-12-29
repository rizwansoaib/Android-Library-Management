package com.nautanki.loginregapp;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
//kkkkk
public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button login_button;
    Button b10;
    Button b11;
    EditText UserName, Password;
    String username, password;
    String login_url = "https://untruthful-oscillat.000webhostapp.com/  ";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        User user=new User(MainActivity.this);
        if(user.getName()!="")
        {
            Intent intent=new Intent(this,Choose.class);

            startActivity(intent);
            finish();
        }


        /*textView = (TextView)findViewById(R.id.reg_txt); //Reg new user.
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start the newly created activity.
                startActivity(new Intent(MainActivity.this,Register.class));
            }
        });*/

        builder = new AlertDialog.Builder(MainActivity.this); //Init builder.
        login_button = (Button) findViewById(R.id.bn_login);
        b10=(Button)findViewById(R.id.button10);
        b11=(Button)findViewById(R.id.button11);
        UserName = (EditText) findViewById(R.id.login_name);
        Password = (EditText) findViewById(R.id.login_password);
        //Click listner for login button
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check if they entered un and pwd
                username = UserName.getText().toString();
                password = Password.getText().toString();
                Toast.makeText(MainActivity.this, username+password, Toast.LENGTH_SHORT).show();
                if (username.equals("")||password.equals("")){
                    builder.setTitle("Error");
                    //Call display Alert. Code it now.
                    displayAlert("Enter a valid username or password.");
                } else{
                    //Auth user using script.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST,
                            login_url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Handle response, from server its a JSON array
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");

                                if (code.equals("login_failed")){
                                    builder.setTitle("Login failed");
                                    displayAlert(jsonObject.getString("message"));
                                } else{
                                    String name=jsonObject.getString("name");
                                    User user=new User(MainActivity.this);
                                    user.setName(name);
                                    builder.setTitle("Login Success!");
                                    displayAlert1(jsonObject.getString("message")+""+jsonObject.getString("name"),jsonObject.getString("name"));


                                }
                            } catch (JSONException e) {

                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this,"Error from ErroResponse",Toast.LENGTH_LONG).show();
                            error.printStackTrace();
                        }
                    }){
                        //We need to pass username and password, thus override getparams

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            //As return type is a map create a map
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email", username);
                            params.put("pass", password);
                            return params;
                        }
                    };
                    //Add string request to request queue
                    MySingleton.getInstance(MainActivity.this).addToRequestque(stringRequest);

                }
            }
        });
    }

    public void displayAlert(String message){
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UserName.setText(""); Password.setText("");
            }
        });
        AlertDialog alertDialog = builder.create(); //create
        alertDialog.show(); //Show it.
    }

    public void displayAlert1(String message, final String name){
        builder.setMessage(message);
        builder.setPositiveButton("Go Ahead", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                UserName.setText(""); Password.setText("");

                //Start another activity. Create an intent.
                Intent intent = new Intent(MainActivity.this, Choose.class);


                startActivity(intent);
                finish();
            }
        });
        AlertDialog alertDialog = builder.create(); //create
        alertDialog.show(); //Show it.
    }

    public void logOut(View view) {
        new User(this).removeUser();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void userlogin(View view)
    {
        Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show();
        Intent intent=new Intent(this,UserLoginPage.class);
        startActivity(intent);

    }

    public void register(View view) {
        Intent intent=new Intent(this,Register.class);
        startActivity(intent);
    }


}