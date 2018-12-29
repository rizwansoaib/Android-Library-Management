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

public class LoginSuccess extends AppCompatActivity implements View.OnClickListener {
    /*TextView username,password;*/
    TextView t1;
    Button btnScanStdid,btnScanBkid,return_butn;
    EditText txtstdid,txtbkid;
    AlertDialog.Builder builder;
    String ret_url = "https://untruthful-oscillat.000webhostapp.com/return.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login_success);

        t1=findViewById(R.id.textView6);
        t1.setText("Books Return Area");

        Intent intent=getIntent();

        User user=new User(LoginSuccess.this);



        txtstdid=findViewById(R.id.txt_return_stdid);
        txtbkid=findViewById(R.id.txt_return_bkid);


        initViews();

        // Toast.makeText(this, sharedPrefForIDs.getBookId()+" stdid "+sharedPrefForIDs.getStudentId(), Toast.LENGTH_LONG).show();
        if( user.getStudentId()!="")
        {

            //Toast.makeText(this, sharedPrefForIDs.getBookId().toString(), Toast.LENGTH_SHORT).show();

            txtstdid.setText(user.getStudentId().toString());
            txtbkid.setText(user.getBookId2().toString());
        }


    }



    private void initViews() {
        btnScanStdid = findViewById(R.id.btn_return_stdid);
        btnScanBkid = findViewById(R.id.btn_return_bkid);
        return_butn = findViewById(R.id.return_butn);



        // btnScanBarcode = findViewById(R.id.btnScanBarcode);

        btnScanStdid.setOnClickListener( this);
        btnScanBkid.setOnClickListener( this);
        return_butn.setOnClickListener( this);
        //  btnScanBarcode.setOnClickListener(this);
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_return_stdid:
                Intent i=new Intent(this,ScannedBarcodeActivty.class);
                i.putExtra("btnvalue","return_student");
                startActivity(i);
                finish();
                break;
            case R.id.btn_return_bkid:
                Intent i1=new Intent(this,ScannedBarcodeActivty.class);
                i1.putExtra("btnvalue","return_student");
                startActivity(i1);
                finish();
                break;
            case R.id.return_butn:
                return_book();
                break;

        }

    }

    private void return_book() {
       // Toast.makeText(this, "from Return area", Toast.LENGTH_SHORT).show();
        final String bookid = txtbkid.getText().toString();
        final String studid = txtstdid.getText().toString();
        builder = new AlertDialog.Builder(LoginSuccess.this);

        if (bookid.equals("")||studid.equals("") ){
            builder.setTitle("Error");
            builder.setMessage("Please fill up all the fields.");
            displayAlert("input_error");
        } else{

            StringRequest stringRequest = new StringRequest(Request.Method.POST,
                    ret_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Handle response.
                    try {
                        Toast.makeText(LoginSuccess.this, "onresponse bookid"+bookid+"stdid  "+studid, Toast.LENGTH_SHORT).show();
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0); //0=Index
                        //Fetch data from server
                        String code = jsonObject.getString("code");
                        String message = jsonObject.getString("message");
                        builder.setTitle("Server response");
                        builder.setMessage(message);
                        displayAlert(code); //Method we defined.
                    } catch (JSONException e) {
                        // tw.setText("From Exception Error!");
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginSuccess.this, "on_error_response", Toast.LENGTH_SHORT).show();
                }
            }){
                //Override a method called get params to pass data.

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    //The keys must match the keys on $_POST on SSS.

                    params.put("sid",studid);
                    params.put("bid",bookid);

                    return params; //Return the MAP.
                }
            };
            //Add this string request to request queue.
            MySingleton.getInstance(LoginSuccess.this).addToRequestque(stringRequest);
            //}
        }

    }




    public void displayAlert(final String code){
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (code.equals("return_succ")){
                    txtbkid.setText("");
                    finish(); //Finish activity
                }
                else if(code.equals("stdid_wrong"))
                {
                    txtstdid.setText("");

                }
                else if(code.equals("bkid_wrong"))
                {
                    txtbkid.setText("");
                }

            }
        });
        //Display the alert dialog.
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }




}