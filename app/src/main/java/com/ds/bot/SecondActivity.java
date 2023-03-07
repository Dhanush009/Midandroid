package com.ds.bot;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.BroadcastReceiver;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    BroadReceiver receiver;

    private RecyclerView chatRV;
    private Button generate;
    private Button stop;
    private ArrayList<String> chatBotMsg;
    private ChatRVAdapter chatRVAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        String usr = intent.getStringExtra("user");

        IntentFilter filter = new IntentFilter("BroadCastMsg");

        receiver = new BroadReceiver();
        registerReceiver(receiver,filter);

        chatRV = findViewById(R.id.idRVChats);
        generate = findViewById(R.id.generate);
        stop = findViewById(R.id.stop);

        chatBotMsg = new ArrayList<>();

        chatRVAdapter = new ChatRVAdapter(chatBotMsg,SecondActivity.this);
        LinearLayoutManager manager = new LinearLayoutManager(SecondActivity.this);

        chatRV.setLayoutManager(manager);
        chatRV.setAdapter(chatRVAdapter);



        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this,ChatBotService.class);
                intent.putExtra("generate","yes");
                intent.putExtra("user",usr);
                startActivity(intent);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SecondActivity.this, ChatBotService.class);
                intent.putExtra("generate","no");
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public class BroadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String[] botMessages = intent.getStringArrayExtra("messages");
            if (botMessages != null) {

                chatBotMsg = new ArrayList<>();
                chatBotMsg.add(botMessages[0]);
                chatBotMsg.add(botMessages[1]);
                chatBotMsg.add(botMessages[2]);

                chatRVAdapter.notifyDataSetChanged();
            }
        }}

}
