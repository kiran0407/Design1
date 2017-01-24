package com.example.harikrishna.design1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText e1,e2;
    TextView t1;
    Button b1,b2;
    String url="http://kiran0407.tk/insert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        e1=(EditText)findViewById(R.id.et1);
        e2=(EditText)findViewById(R.id.et2);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button1);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override


            public void onClick(View v) {
                String s1=e1.getText().toString();
                String s2=e2.getText().toString();
                Toast.makeText(MainActivity.this,s2,Toast.LENGTH_SHORT).show();

                if(e1.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"please enter Name",Toast.LENGTH_SHORT).show();
                }
                else if(e2.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this,"please enter Phone Number",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "succesfull Added to database", Toast.LENGTH_SHORT).show();
                    insertme(s1,s2);


                }
            }
        });
b2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i1=new Intent(MainActivity.this,SecondActivity.class);
        startActivity(i1);
    }
});

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);

    }
    public void insertme(final String s1, final String s2){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, Allurl.url, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("na",s1);
                params.put("na1",s2);
                return params;
            }
        };
        java2.getInstance().addToRequestQueue(stringRequest);
    }

}
