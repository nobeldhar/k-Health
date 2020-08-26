package com.kpsoftwaresolutions.khealth.repositories;

import android.os.Bundle;
import android.util.Log;

import com.kpsoftwaresolutions.khealth.utils.SharedPrefsHelper;
import com.kpsoftwaresolutions.khealth.utils.network.SingleLiveEvent;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import com.kpsoftwaresolutions.khealth.utils.chat.ChatHelper;

public class LoginRepository {
    private static final int UNAUTHORIZED = 401;
    private static final String TAG = "LoginRepository";
    SingleLiveEvent<String> loginResponse = new SingleLiveEvent<>();
    private QBChatService qbChatService = QBChatService.getInstance();
    private QBUser userFromRest;


    public void signIn(QBUser qbUser) {
        ChatHelper.getInstance().login(qbUser, new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser userFromRest, Bundle bundle) {
                if (userFromRest.getLogin().equals(qbUser.getLogin())){
                    qbUser.setFullName(userFromRest.getFullName());
                    qbUser.setId(userFromRest.getId());
                    loginToChat(qbUser);
                }
            }

            @Override
            public void onError(QBResponseException e) {
                if (e.getHttpStatusCode() == UNAUTHORIZED){
                    loginResponse.setValue("UNAUTHORIZED");
                } else
                    loginResponse.setValue("onError: "+e.getMessage());

                Log.d(TAG, "onError: "+e.getMessage());

            }
        });

    }

    private void loginToChat(QBUser qbUser) {

        ChatHelper.getInstance().loginToChat(qbUser, new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {
                SharedPrefsHelper.getInstance().saveQbUser(qbUser);
                loginResponse.setValue("Logged In");
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d(TAG, "onError: "+e);
                loginResponse.setValue("onError: "+e.getMessage());

            }
        });

    }

    public SingleLiveEvent<String> getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(SingleLiveEvent<String> loginResponse) {
        this.loginResponse = loginResponse;
    }

    public QBUser getUserFromRest() {
        return userFromRest;
    }

    public void setUserFromRest(QBUser userFromRest) {
        this.userFromRest = userFromRest;
        Log.d(TAG, "setUserFromRest: "+this.userFromRest.getFullName());
    }
}
