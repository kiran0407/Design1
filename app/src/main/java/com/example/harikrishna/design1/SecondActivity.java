package com.example.harikrishna.design1;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {
    private List<ListItem> listItems;
    ListView cl;

    private ProgressDialog dialog;
    Context context=this;
    String name,url;
    EditText e2;
    TextView t1;
    ImageButton ib1;
    Map<String, Integer> mapIndex;
    LinearLayout indexLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        e2=(EditText)findViewById(R.id.editText2);
        ib1=(ImageButton)findViewById(R.id.imageButton2);
        indexLayout = (LinearLayout) findViewById(R.id.side_index);

        cl=(ListView)findViewById(R.id.listv);
        mapIndex = new LinkedHashMap<String, Integer>();
        url="http://kiran0407.tk/display.php";
        //  Toast.makeText(SecondActivity.this,"data is"+url,Toast.LENGTH_LONG).show();
        new JSONTask().execute(url);
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String t3=e2.getText().toString();
                String url="http://kiran0407.tk/search.php?search="+t3;
                new JSONTask().execute(url);

            }
        });
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        for (String index : indexList) {
            t1 = (TextView) getLayoutInflater().inflate(
                    R.layout.side_index_item, null);
            t1.setText(index);
            t1.setOnClickListener((View.OnClickListener) this);
            indexLayout.addView(t1);
        }

       listItems=new ArrayList<>();
        //getIndexList(limits);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
            TextView selectedIndex = (TextView) view;
            cl.setSelection(mapIndex.get(selectedIndex.getText()));

    }


    public class JSONTask extends AsyncTask<String,String, List<ListItem> > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<ListItem> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder buffer = new StringBuilder();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("success");
                List movieModelList = new ArrayList<>();
                Gson gson = new Gson();

                for(int i=0; i<parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                   String nam= finalObject.getString("name");
                    String index = nam.substring(0, 1);
                    if (movieModelList.get(index) == null)
                        movieModelList.put(index, i);
                        ListItem listItems = gson.fromJson(finalObject.toString(), ListItem.class);

                        movieModelList.add(listItems);

                }


               /* for (int i = 0; i < limits.length; i++) {
                    String name = limits.get(i);
                    String index = name.substring(0, 1);

                    if (mapIndex.get(index) == null)
                        mapIndex.put(index, i);
                }*/
               // String t=listItems.getName();
               // limits.add(t);

                return movieModelList;

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return  null;

        }

        @Override
        protected void onPostExecute(final List<ListItem> movieModelList) {
            super.onPostExecute(movieModelList);
            dialog.dismiss();
            if(movieModelList != null) {
                MovieAdapter adapter = new MovieAdapter(getApplicationContext(), R.layout.list_item, movieModelList);
                cl.setAdapter(adapter);
                adapter.notifyDataSetChanged();





                cl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ListItem category=movieModelList.get(position);
                        //Toast.makeText(getApplicationContext(),t,Toast.LENGTH_LONG).show();
                    }
                });
            }
            else {
                Toast.makeText(SecondActivity.this,"error please try again...",Toast.LENGTH_SHORT).show();
            }

        }

    }
    public class MovieAdapter extends ArrayAdapter {

        private List<ListItem> movieModelList;
        private int resource;
        Context context;
        private LayoutInflater inflater;
        MovieAdapter(Context context, int resource, List<ListItem> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.context =context;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        }
        @Override
        public int getViewTypeCount() {

            return 1;
        }

        @Override
        public int getItemViewType(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ViewHolder holder  ;

            if(convertView == null){
                convertView = inflater.inflate(resource,null);
                holder = new ViewHolder();
                holder.menuname=(TextView) convertView.findViewById(R.id.tv);
                holder.idt=(TextView) convertView.findViewById(R.id.tv1);
                convertView.setTag(holder);

            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }
            ListItem listItem= movieModelList.get(position);
            //Picasso.with(context).load(listItem.getImage()).fit().error(R.drawable.backnom).fit().into(holder.menuimage);


            holder.menuname.setText(listItem.getName());
            holder.idt.setText(listItem.getNumber());
            return convertView;




        }

        class ViewHolder{

            private TextView menuname,idt;


        }



    }

}
