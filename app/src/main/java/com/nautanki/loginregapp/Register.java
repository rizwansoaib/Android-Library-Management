package com.nautanki.loginregapp;

import android.content.DialogInterface;
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
//2
public class Register extends AppCompatActivity {
    Button reg_bn;
    TextView tw;
    EditText Name, UserName, Password, ConPassword;
    String name, username, password, conpass;
    AlertDialog.Builder builder;
    String reg_url = "https://untruthful-oscillat.000webhostapp.com/register.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_register);
        tw=(TextView)findViewById(R.id.textView);
        reg_bn = (Button)findViewById(R.id.bn_reg);
        Name = (EditText)findViewById(R.id.reg_name);
        UserName = (EditText)findViewById(R.id.reg_user_name);
        Password = (EditText)findViewById(R.id.reg_password);
        ConPassword = (EditText)findViewById(R.id.reg_con_password);
        builder = new AlertDialog.Builder(Register.this);
        reg_bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Fetch the values.
                name = Name.getText().toString();
                username = UserName.getText().toString();
                password = Password.getText().toString();
                conpass = ConPassword.getText().toString();
                if (name.equals("")||username.equals("")||password.equals("")||conpass.equals("")){
                    builder.setTitle("Error");
                    builder.setMessage("Please fill up all the fields.");
                    displayAlert("input_error");
                } else{
                    //Check if passwords match.
                    if (!(password.equals(conpass))){
                        builder.setTitle("Error");
                        builder.setMessage("Passwords do not match.");
                        displayAlert("input_error");
                    }else {
                        //Register user
                        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                                reg_url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //Handle response.
                                try {
                                    Toast.makeText(Register.this, "onresponse", Toast.LENGTH_SHORT).show();
                                    JSONArray jsonArray = new JSONArray(response);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0); //0=Index
                                    //Fetch data from server
                                    String code = jsonObject.getString("code");
                                    String message = jsonObject.getString("message");
                                    builder.setTitle("Server response");
                                    builder.setMessage(message);
                                    displayAlert(code); //Method we defined.
                                } catch (JSONException e) {
                                    tw.setText("From Exception Error!");
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Register.this, "onerrorresponse", Toast.LENGTH_SHORT).show();
                            }
                        }){
                            //Override a method called get params to pass data.

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String, String>();
                                //The keys must match the keys on $_POST on SSS.
                                params.put("name",name);
                                params.put("email",username);
                                params.put("pass",password);
                                return params; //Return the MAP.
                            }
                        };
                        //Add this string request to request queue.
                        MySingleton.getInstance(Register.this).addToRequestque(stringRequest);
                    }
                }
            }
        });
    } // End on onCreate.

    public void displayAlert(final String code){
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (code == "input_error"){
                    Password.setText("");
                    ConPassword.setText("");
                }
                else if (code.equals("reg_success")){
                    finish(); //Finish activity
                }
                else if (code.equals("reg_failed")){
                    //Reset all input.
                    Name.setText("");
                    UserName.setText("");
                    Password.setText("");
                    ConPassword.setText("");
                }
            }
        });
        //Display the alert dialog.
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}