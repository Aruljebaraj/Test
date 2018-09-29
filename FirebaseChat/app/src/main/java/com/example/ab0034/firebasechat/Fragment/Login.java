package com.example.ab0034.firebasechat.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ab0034.firebasechat.R;
import com.example.ab0034.firebasechat.UserActivity;
import com.example.ab0034.firebasechat.UserDetails;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {

    EditText username, password;
    Button loginButton;
    String user, pass;
    SQLiteDatabase mydatabase;
    String DB_NAME = "User";
    public Login() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        loginButton = (Button) view.findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = username.getText().toString();
                pass = password.getText().toString();
                if (user.equals("")) {
                    username.setError("can't be blank");
                } else if (pass.equals("")) {
                    password.setError("can't be blank");
                } else {
                    String url = "https://chat-610be.firebaseio.com//User.json";
                    final ProgressDialog pd = new ProgressDialog(getActivity(), R.style.Progress);
                    pd.setMessage("Loading...");
                    pd.show();

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            if (s.equals("null")) {
                                Toast.makeText(getActivity(), "user not found", Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if (!obj.has(user)) {
                                        Toast.makeText(getActivity(), "user not found", Toast.LENGTH_LONG).show();
                                    } else if (obj.getJSONObject(user).getString("password").equals(pass)) {
                                        mydatabase =  getActivity().openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
                                        mydatabase.execSQL("DROP TABLE IF EXISTS UserDetails");
                                        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS UserDetails(Branch VARCHAR);");
                                        mydatabase.execSQL("INSERT INTO UserDetails VALUES('" + user + "');");
                                        UserDetails.username = user;
                                        UserDetails.password = pass;
                                        startActivity(new Intent(getActivity(), UserActivity.class));
                                    } else {
                                        Toast.makeText(getActivity(), "incorrect password", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            pd.dismiss();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError);
                            pd.dismiss();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(getActivity());
                    rQueue.add(request);
                }

            }
        });
        Check();

        return view;
    }
    public void Check() {
        try {
            mydatabase =getActivity().openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
            Cursor c = mydatabase.rawQuery("select * from UserDetails", null);
            if (c != null) {
                c.moveToFirst();
                String label = c.getString(0);
                Toast.makeText(getActivity(), label, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), UserActivity.class));
            }
        } catch (Exception e) {

        }
    }
}


