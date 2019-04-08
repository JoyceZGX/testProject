package com.example.testproject;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class CommentList extends ArrayAdapter<String> {

    private Activity context;
    private List<MyDataSetGet> commentList;

    public CommentList (Activity context, List<MyDataSetGet> commentList){
        super(context,R.layout.layout_row_view,commentList);
        this.commentList=commentList;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listView = inflater.inflate(R.layout.layout_row_view,null,true);

        TextView c = (TextView)listView.findViewById(R.id.textViewName);

        MyDataSetGet comm = commentList.get(position);

        c.setText(comm.getComment());
        return listView;


    }
}
