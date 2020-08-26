package com.kpsoftwaresolutions.khealth.services.fcm;

import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.kpsoftwaresolutions.khealth.services.LoginService;
import com.kpsoftwaresolutions.khealth.utils.SharedPrefsHelper;
import com.quickblox.messages.services.fcm.QBFcmPushListenerService;
import com.quickblox.users.model.QBUser;

import java.util.Map;


public class CallsPushListenerService extends QBFcmPushListenerService {
    private static final String TAG = CallsPushListenerService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        SharedPrefsHelper sharedPrefsHelper = SharedPrefsHelper.getInstance();
        if (sharedPrefsHelper.hasQbUser()) {
            QBUser qbUser = sharedPrefsHelper.getQbUser();
            Log.d(TAG, "App has logged user" + qbUser.getId());
            LoginService.start(this, qbUser);
        }
    }

    @Override
    protected void sendPushMessage(Map data, String from, String message) {
        super.sendPushMessage(data, from, message);
        Log.v(TAG, "From: " + from);
        Log.v(TAG, "Message: " + message);
    }
}