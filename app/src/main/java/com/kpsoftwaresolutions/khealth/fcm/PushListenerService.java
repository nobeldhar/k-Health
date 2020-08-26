package com.kpsoftwaresolutions.khealth.fcm;

import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;
import com.kpsoftwaresolutions.khealth.App;
import com.kpsoftwaresolutions.khealth.MainActivity;
import com.kpsoftwaresolutions.khealth.R;
import com.kpsoftwaresolutions.khealth.services.LoginService;
import com.kpsoftwaresolutions.khealth.utils.ActivityLifecycle;
import com.kpsoftwaresolutions.khealth.utils.NotificationUtils;
import com.kpsoftwaresolutions.khealth.utils.SharedPrefsHelper;
import com.quickblox.messages.services.fcm.QBFcmPushListenerService;
import com.quickblox.users.model.QBUser;


import java.util.Map;

public class PushListenerService extends QBFcmPushListenerService {
    private static final String TAG = PushListenerService.class.getSimpleName();
    private static final int NOTIFICATION_ID = 1;

    protected void showNotification(String message) {
        NotificationUtils.showNotification(App.getInstance(), MainActivity.class,
                "K-Health Chat Consultancy", message,
                R.mipmap.ic_launcher, NOTIFICATION_ID);
    }

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
        if (ActivityLifecycle.getInstance().isBackground()) {
            showNotification(message);
        }
    }
}