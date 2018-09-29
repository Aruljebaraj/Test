package com.example.ab0034.firebasechat.Fragment;


import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ab0034.firebasechat.R;
import com.firebase.client.Firebase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Register extends Fragment {
    EditText EdtEmailId, EdtPassword, EdtConfirmpassword;
    Button BtnSignup;
    String Email, pass, Confirmpass;

    public Register() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        EdtEmailId = (EditText) view.findViewById(R.id.EdtEmailId);
        EdtPassword = (EditText) view.findViewById(R.id.EdtPassword);
        EdtConfirmpassword = (EditText) view.findViewById(R.id.EdtConfirmpassword);
        BtnSignup = (Button) view.findViewById(R.id.BtnSignup);
        Firebase.setAndroidContext(getActivity());

        BtnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email = EdtEmailId.getText().toString();
                pass = EdtPassword.getText().toString();
                Confirmpass = EdtConfirmpassword.getText().toString();
                if (!Confirmpass.equals(pass)) {
                    EdtConfirmpassword.setError("Password Mismatch");
                } else {
                    final ProgressDialog pd = new ProgressDialog(getActivity());
                    pd.setMessage("Loading...");
                    pd.setCancelable(false);
                    pd.show();
                    String url = "https://chat-610be.firebaseio.com//User.json";
                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            Firebase reference = new Firebase("https://chat-610be.firebaseio.com/User");

                            if (s.equals("null")) {
                                reference.child(Email).child("user").setValue(Email);
                                reference.child(Email).child("password").setValue(pass);
                                Toast.makeText(getActivity(), "registration successful", Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if (!obj.has(Email)) {
                                        reference.child(Email).child("password").setValue(pass);
                                        reference.child(Email).child("user").setValue(Email);
                                        Toast.makeText(getActivity(), "registration successful", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), "username already exists", Toast.LENGTH_LONG).show();
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


        return view;
    }
}



