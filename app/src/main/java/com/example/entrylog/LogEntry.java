package com.example.entrylog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
    AppCompatButton b1,b2;
    String apiUrl="http://10.0.4.16:3000/api/students";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_entry);
        ed1=(EditText) findViewById(R.id.name);
        ed2=(EditText) findViewById(R.id.addmno);
        ed3=(EditText) findViewById(R.id.sysno);
        ed4=(EditText) findViewById(R.id.dept);
        b1=(AppCompatButton) findViewById(R.id.add);
        b2=(AppCompatButton) findViewById(R.id.logout);

        b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        String getName = ed1.getText().toString();
                        String getAddno = ed2.getText().toString();
                        String getSysno = ed3.getText().toString();
                        String getDept = ed4.getText().toString();

                        //creating JSON object
                        JSONObject student = new JSONObject();
                    try {
                        student.put("name", getName);
                        student.put("admission_number", getAddno);
                        student.put("system_number", getSysno);
                        student.put("department", getDept);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                    //JSON object request creation
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            apiUrl,
                                                student,
                                                new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                                                    }
                                                },
                                                new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

                                                    }
                                                }

                    );

                    //Request Queue
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(jsonObjectRequest);
                }
            });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref=getSharedPreferences("logged",MODE_PRIVATE);
                SharedPreferences.Editor editor= pref.edit();
                editor.clear();
                editor.apply();
                Intent i=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}