package com.nautanki.loginregapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class IssuePage extends AppCompatActivity implements View.OnClickListener {
    EditText stdid,bkid1;
    Button btn_stdid,btn_bkid1,btn_bkid2;
    AlertDialog.Builder builder;
    String reg_url = "https://untruthful-oscillat.000webhostapp.com/issue.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_issue_page);
        stdid= findViewById(R.id.txt_issue_std_id);
        bkid1=findViewById(R.id.txt_issue_bkid1);

        initViews();

        User user=new User(IssuePage.this);
        stdid.setText(user.getStudentId().toString());
        bkid1.setText(user.getBookId1().toString());


    }

    private void initViews() {
        btn_stdid=findViewById(R.id.btn_issue_std_id);
        btn_bkid1=findViewById(R.id.btn_issue_bkid1);
        btn_bkid2=findViewById(R.id.btn_issue_bkid2);

        btn_stdid.setOnClickListener(this);
        btn_bkid1.setOnClickListener( this);
        btn_bkid2.setOnClickListener( this);

    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_issue_bkid1:
                Intent intent1=new Intent(this,ScannedBarcodeActivty.class);
                intent1.putExtra("btnvalue","issue_book1");
                startActivity(intent1);
                finish();
                break;
            case R.id.btn_issue_bkid2:
                Intent intent2=new Intent(this,ScannedBarcodeActivty.class);
                intent2.putExtra("btnvalue","issue_book2");
                startActivity(intent2);
                finish();
                break;

            case R.id.btn_issue_std_id:
                Intent i=new Intent(this,ScannedBarcodeActivty.class);
                i.putExtra("btnvalue","issue_student");
                startActivity(i);
                finish();
                break;

        }

    }

    public void reset(View view) {
        User user=new User(IssuePage.this);
        user.setStudentId("");
        user.setBookId1("");
        user.setBookId2("");
        user.setBookId3("");
        user.setBookId4("");
        startActivity(new Intent(this,IssuePage.class));
        finish();
    }

    public void issue_btn(View view) {

        final String bookid = bkid1.getText().toString();
        final String studid = stdid.getText().toString();
        builder = new AlertDialog.Builder(IssuePage.this);

        if (bookid.equals("")||studid.equals("") ){
            builder.setTitle("Error");
            builder.setMessage("Please fill up all the fields.");
            displayAlert("input_error");
        } else{

                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        reg_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Handle response.
                        try {
                            Toast.makeText(IssuePage.this, "onresponse", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(IssuePage.this, "on_error_response", Toast.LENGTH_SHORT).show();
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
                MySingleton.getInstance(IssuePage.this).addToRequestque(stringRequest);
            //}
        }

    }




    public void displayAlert(final String code){
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (code.equals("succ_issue")){
                    finish(); //Finish activity
                }
                else if(code.equals("stdid_wrong"))
                {
                    stdid.setText("");

                }
                else if(code.equals("bkid_wrong") || code.equals("returned_succ"))
                {
                    bkid1.setText("");
                }



            }
        });
        //Display the alert dialog.
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
