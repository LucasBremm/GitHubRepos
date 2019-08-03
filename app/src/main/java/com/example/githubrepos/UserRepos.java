package com.example.githubrepos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserRepos extends AppCompatActivity {

    ListView list_repos;
    TextView user_name;
    TextView textRepos;
    TextView textClick;
    ImageView user_image;

    ArrayList<Repos> repos;

    RequestQueue queue;

    ProgressBar progressBar;

    String reposURL;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_repos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        list_repos = findViewById(R.id.list_repos);
        user_name = findViewById(R.id.user_name);
        textRepos = findViewById(R.id.textRepos);
        textClick = findViewById(R.id.textClick);
        user_image = findViewById(R.id.user_image);
        progressBar = findViewById(R.id.progressBar);

        user_image.setVisibility(View.INVISIBLE);
        user_name.setVisibility(View.INVISIBLE);
        textRepos.setVisibility(View.INVISIBLE);
        textClick.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        repos = new ArrayList<>();

        queue = Volley.newRequestQueue(this);

        parseUser();

        list_repos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(UserRepos.this, Commits.class);
                intent.putExtra("url", repos.get(position).getCommits());
                startActivity(intent);
            }
        });
    }

    private void parseUser(){

        String url = getIntent().getExtras().getString("url");

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            user = response.getString("login");
                            user_name.setText(user);

                            Picasso.get().load(response.getString("avatar_url"))
                                    .into(user_image);

                            reposURL = response.getString("repos_url");

                            parseRepos();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

       //Todo: Try to get header and continue to get repos
        queue.add(request);
    }

    private void parseRepos(){

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, reposURL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try{
                            JSONObject object;

                            for(int i = 0; i < response.length(); i++){
                                object = response.getJSONObject(i);
                                repos.add(new Repos(
                                        object.getString("name"),
                                        object.getString("commits_url").replace("{/sha}", "") + "?per_page=5"
                                        //Todo: Try to get commits from other branches if they are more recent
                                ));
                            }
                            ArrayAdapter adapter = new RepoAdapter(UserRepos.this, repos);
                            list_repos.setAdapter(adapter);

                            user_image.setVisibility(View.VISIBLE);
                            user_name.setVisibility(View.VISIBLE);
                            textRepos.setVisibility(View.VISIBLE);
                            textClick.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);

                        } catch (JSONException e){
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
