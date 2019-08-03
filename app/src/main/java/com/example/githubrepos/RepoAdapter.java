package com.example.githubrepos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RepoAdapter extends ArrayAdapter<Repos> {

    private final Context context;
    private final ArrayList<Repos> repos;

    public RepoAdapter(Context context, ArrayList<Repos> repos){
        super(context, android.R.layout.simple_list_item_1, repos);
        this.context = context;
        this.repos = repos;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        TextView nome = rowView.findViewById(android.R.id.text1);

        nome.setText(repos.get(position).getName());

        //return super.getView(position, convertView, parent);
        return rowView;
    }
}
