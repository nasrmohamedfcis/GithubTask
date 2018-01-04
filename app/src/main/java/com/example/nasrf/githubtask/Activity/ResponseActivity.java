package com.example.nasrf.githubtask.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nasrf.githubtask.Helper.MyHelper;
import com.example.nasrf.githubtask.R;
import com.example.nasrf.githubtask.Recycler_View.ClickListener;
import com.example.nasrf.githubtask.Recycler_View.Details;
import com.example.nasrf.githubtask.Recycler_View.MyAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;



public class ResponseActivity extends AppCompatActivity{

    private LinearLayoutManager layoutManager;
    private ArrayList<Details> details;
    private List<String> urlList;
    private MyAdapter myAdapter;
    private RecyclerView recyclerView;
    private SQLiteDatabase db;
    private MyHelper myHelper;
    private SwipeRefreshLayout mySwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        initializer();

        RequestData();

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mySwipeRefreshLayout.setRefreshing(false);
                        RequestData();
                    }
                }
        );


    }


    private void initializer() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        details = new ArrayList<>();
        urlList = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        myHelper = new MyHelper(this);
        db = myHelper.getWritableDatabase();

        mySwipeRefreshLayout = new SwipeRefreshLayout(this);
        mySwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipToRefreshLayout);
    } //end of initializer


    private void RequestData(){


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.github.com/users/square/repos";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //testing the connection
                        //Toast.makeText(ResponseActivity.this, "Response is: "+ response.substring(0,500), Toast.LENGTH_SHORT).show();

                        try {
                            JSONArray all = new JSONArray(response);
                            String name, repo, desc, sourceHTML,fork;
                            long id;
                            int count=0;
                            boolean filled = false;

                            for(int i = 0;i<all.length();i++){
                                //testing
                                //Toast.makeText(ResponseActivity.this, "TEST", Toast.LENGTH_SHORT).show();
                                //getting data
                                JSONObject member = all.getJSONObject(i);
                                name = member.getString("name");
                                repo = member.getString("full_name");
                                desc = member.getString("description");
                                fork = member.getString("fork");
                                JSONObject owner = member.getJSONObject("owner");
                                sourceHTML = owner.getString("url");
                                urlList.add(sourceHTML);
                                //Toast.makeText(ResponseActivity.this, name+" "+repo+" "+desc, Toast.LENGTH_SHORT).show();
                                details.add(new Details(name,desc,repo,fork,sourceHTML));

                                //storing data locally in DB
                                //name text,repo text,desc text,fork text,source text
                                if(filled = false){
                                    ContentValues row = new ContentValues();
                                    row.put("name",name);
                                    row.put("repo",repo);
                                    row.put("desc",desc);
                                    row.put("fork",fork);
                                    row.put("source",sourceHTML);
                                    id = db.insert("data",null,row);

                                    if(count == all.length())
                                        filled=true;
                                }
                            }

                            myAdapter = new MyAdapter(ResponseActivity.this,details);
                            //myAdapter.setClickListner(this);
                            recyclerView.setAdapter(myAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ResponseActivity.this, "Connection Error, Showing previously saved Data", Toast.LENGTH_SHORT).show();
                details.clear();
                db = myHelper.getReadableDatabase();
                //name text,repo text,desc text,fork text,source text
                Cursor pointer = db.query("data",null,null,null,null,null,"id");
                String Name="",Repo="",Desc="",Fork="",Source="";

                while(pointer.moveToNext()){
                    Name=pointer.getString(1);
                    Repo=pointer.getString(2);
                    Desc=pointer.getString(3);
                    Fork=pointer.getString(4);
                    Source=pointer.getString(5);
                    details.add(new Details(Name,Desc,Repo,Fork,Source));
                }
                myAdapter = new MyAdapter(ResponseActivity.this,details);
                recyclerView.setAdapter(myAdapter);
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);



    }//end of RequestDAta


}


