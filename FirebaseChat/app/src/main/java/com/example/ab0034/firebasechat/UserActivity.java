package com.example.ab0034.firebasechat;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class UserActivity extends AppCompatActivity {
    ListView usersList;
    ImageView noUsersText, Img;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ImageView ImgMore;
    SQLiteDatabase mydatabase;
    String DB_NAME = "User";


    //    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        usersList = (ListView) findViewById(R.id.usersList);
        noUsersText = (ImageView) findViewById(R.id.noUsersText);
        ImgMore = (ImageView) findViewById(R.id.ImgMore);
        Img = (ImageView) findViewById(R.id.Img);

        try {
            ImgMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(UserActivity.this, ImgMore);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater()
                            .inflate(R.menu.menu, popup.getMenu());
                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            mydatabase = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
                            mydatabase.execSQL("DROP TABLE IF EXISTS UserDetails");
                            startActivity(new Intent(UserActivity.this, MainActivity.class));

                            return true;
                        }
                    });
                    popup.show();
                }
            });
        } catch (Exception e) {
        }
//        TxtCount = (TextView) findViewById(R.id.TxtCount);
        String url = "https://chat-610be.firebaseio.com//User.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(UserActivity.this);
        rQueue.add(request);
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = al.get(position);
                String val = UserDetails.chatWith = al.get(position);
                startActivity(new Intent(UserActivity.this, Chat.class).putExtra("UserName", val));
            }
        });
//        String token = FirebaseInstanceId.getInstance().getToken();
    }


    public void doOnSuccess(String s) {
        Context context = null;
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while (i.hasNext()) {
                key = i.next().toString();

                if (!key.equals(UserDetails.username)) {
                    al.add(key);
                }
                totalUsers++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (totalUsers <= 1) {
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        } else {
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));

        }

//        pd.dismiss();
    }
}