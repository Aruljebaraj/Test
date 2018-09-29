package com.example.ab0034.firebasechat.Notification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    String refreshedToken;

    @Override
    public void onTokenRefresh() {
        Log.d("mylog", "Inside on token refresh");
        //Getting registration token
        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Displaying token on logcat
        Log.d("mylog", "Refreshed token: " + refreshedToken);
    }
}