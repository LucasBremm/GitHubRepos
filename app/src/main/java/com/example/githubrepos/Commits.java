package com.example.githubrepos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Commits extends AppCompatActivity {

    ListView list_commits;
    TextView textCommits;

    ArrayList<String> commits;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits);

        list_commits = findViewById(R.id.list_commits);
        textCommits = findViewById(R.id.textCommits);

        commits = new ArrayList<>();

        queue = Volley.newRequestQueue(this);

        parseInfo();
    }

    private void parseInfo(){

        String url = getIntent().getExtras().getString("url");

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{

                            JSONObject object;

                            for(int i = 0; i < response.length(); i++){
                                object = response.getJSONObject(i);
                                object = object.getJSONObject("commit");
                                commits.add(object.getString("message"));
                            }

                            ArrayAdapter adapter = new ArrayAdapter<>(Commits.this, android.R.layout.simple_list_item_1, commits);
                            list_commits.setAdapter(adapter);

                        }catch(JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }
}
