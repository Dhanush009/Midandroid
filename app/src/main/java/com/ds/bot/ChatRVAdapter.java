package com.ds.bot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatRVAdapter extends RecyclerView.Adapter {

    private ArrayList<String> botMsgArray;
    private Context context;

    public ChatRVAdapter(ArrayList<String> botMsgArray, Context context) {
        this.botMsgArray = botMsgArray;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bot_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String msg = botMsgArray.get(position);
        ((ViewHolder)holder).botMsg.setText(msg);

    }

    @Override
    public int getItemCount() {
        return botMsgArray.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView botMsg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            botMsg = itemView.findViewById(R.id.idTVBot);
        }
    }
}
