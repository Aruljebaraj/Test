package com.example.ab0034.firebasechat;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity implements View.OnClickListener {
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton, ImgBack;
    EditText messageArea;
    TextView TxtUsername;
    ScrollView scrollView;
    Firebase reference1, reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout) findViewById(R.id.layout2);
        sendButton = (ImageView) findViewById(R.id.sendButton);
        ImgBack = (ImageView) findViewById(R.id.ImgBack);
        messageArea = (EditText) findViewById(R.id.messageArea);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        TxtUsername = (TextView) findViewById(R.id.TxtUsername);
        String User = getIntent().getStringExtra("UserName");
        TxtUsername.setText(User);
        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://chat-610be.firebaseio.com/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://chat-610be.firebaseio.com/" + UserDetails.chatWith + "_" + UserDetails.username);
        ImgBack.setOnClickListener(this);
        sendButton.setOnClickListener(this);
        reference1.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {
                    Map map = dataSnapshot.getValue(Map.class);
                    String message = map.get("message").toString();
                    String userName = map.get("user").toString();

                    if (userName.equals(UserDetails.username)) {
                        addMessageBox("You:-\n" + message, 1);
                    } else {
                        addMessageBox(UserDetails.chatWith + ":-\n" + message, 2);
                    }
                } catch (Exception e) {
                    Toast.makeText(Chat.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void addMessageBox(String message, int type) {
        TextView textView = new TextView(Chat.this);
        TextView txtstatus = new TextView(Chat.this);

        textView.setText(message);

        textView.setTextColor(getResources().getColor(R.color.white));

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if (type == 1) {
            lp2.gravity = Gravity.LEFT;
            textView.setGravity(Gravity.CENTER | Gravity.TOP);
            textView.setBackgroundResource(R.drawable.bubble_out);

        } else {
            lp2.gravity = Gravity.RIGHT;
            textView.setGravity(Gravity.CENTER | Gravity.TOP);
            textView.setBackgroundResource(R.drawable.bubble_in);

        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ImgBack:
                startActivity(new Intent(Chat.this, UserActivity.class));
                finish();
                break;
            case R.id.sendButton:
                String messageText = messageArea.getText().toString();

                if (!messageText.equals("")) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", UserDetails.username);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    messageArea.setText("");
                    break;

                }
        }
    }
}