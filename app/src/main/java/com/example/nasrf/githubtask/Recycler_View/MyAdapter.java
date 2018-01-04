package com.example.nasrf.githubtask.Recycler_View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.nasrf.githubtask.R;
import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myHolder> {

    private Context context;
    private ArrayList<Details> data;
    private Response.Listener clickListner;
    public View view;


    public MyAdapter(Context context, ArrayList<Details> data) {
        this.context = context;
        this.data = data;
    }

    public void setClickListner(Response.Listener<String> clickListner){
        this.clickListner=clickListner;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data,null);
        myHolder holder = new myHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(myHolder holder, int position) {

        holder.name.setText("User name: " + data.get(position).getUserName());
        holder.repositoryName.setText("Repository name: " + data.get(position).getRepoName());
        holder.description.setText("DEscription: " + data.get(position).getDescription());
        holder.forkColor = data.get(position).getFork();

        if(holder.forkColor == "false" || holder.forkColor == null){
            LinearLayout element = (LinearLayout) view.findViewById(R.id.listItem);
            //int color = R.color.lightGreen;
            element.setBackgroundColor(view.getResources().getColor(R.color.lightGreen));
        }

        holder.Url = data.get(position).getUrl();
        final String dest = holder.Url;
        holder.setClickListener(new ClickListener() {
            @Override
            public void itemClicked(View view, int position, boolean isLongClicked) {
                if(isLongClicked){
                    Intent out = new Intent(Intent.ACTION_VIEW);
                    out.setData(Uri.parse(dest));
                    dialoge(out);
                    /*
                    String url = "http://www.example.com";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                    */
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class myHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

        TextView name, description, repositoryName;
        String forkColor,Url;

        ClickListener clickListener;

        public myHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.userName);
            description = (TextView) view.findViewById(R.id.desc);
            repositoryName = (TextView) view.findViewById(R.id.repoName);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

        }

        public void setClickListener(ClickListener clickListener){
            this.clickListener=clickListener;
        }

        @Override
        public void onClick(View v) {
            clickListener.itemClicked(v,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.itemClicked(v,getAdapterPosition(),true);
            return true;
        }
    }

    public void dialoge(final Intent out){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        context.startActivity(out);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();


    }

}
