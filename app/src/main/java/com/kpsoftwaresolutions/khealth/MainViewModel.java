package com.kpsoftwaresolutions.khealth;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.view.ActionMode;
import androidx.lifecycle.ViewModel;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavDirections;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.kpsoftwaresolutions.khealth.managers.DialogsManager;
import com.kpsoftwaresolutions.khealth.utils.FcmConsts;
import com.kpsoftwaresolutions.khealth.utils.SharedPrefsHelper;
import com.kpsoftwaresolutions.khealth.utils.ToastUtils;
import com.kpsoftwaresolutions.khealth.utils.chat.ChatHelper;
import com.kpsoftwaresolutions.khealth.utils.network.SingleLiveEvent;
import com.kpsoftwaresolutions.khealth.utils.qb.QbChatDialogMessageListenerImp;
import com.kpsoftwaresolutions.khealth.utils.qb.QbDialogHolder;
import com.kpsoftwaresolutions.khealth.utils.qb.callback.QBPushSubscribeListenerImpl;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBIncomingMessagesManager;
import com.quickblox.chat.QBSystemMessagesManager;
import com.quickblox.chat.exception.QBChatException;
import com.quickblox.chat.listeners.QBChatDialogMessageListener;
import com.quickblox.chat.listeners.QBSystemMessageListener;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.messages.services.QBPushManager;
import com.quickblox.messages.services.SubscribeService;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

import org.jivesoftware.smack.ConnectionListener;

import java.util.ArrayList;

public class MainViewModel extends ViewModel implements DialogsManager.ManagingDialogsCallbacks {

    public static final String UPDATE_ADAPTER = "UPDATE_ADAPTER";
    public static final String SHOW_HOME_LOADING = "SHOW_HOME_LOADING";
    public static final String HIDE_HOME_LOADING = "HIDE_HOME_LOADING";
    public SingleLiveEvent<String> mainViewModelResponse = new SingleLiveEvent<>();



    private QBUser currentUser;
    private ActionMode currentActionMode;
    private boolean hasMoreDialogs = true;
    public boolean isProcessingResultInProgress;



    private void updateDialogsAdapter() {
        mainViewModelResponse.setValue(HIDE_HOME_LOADING);
        mainViewModelResponse.setValue(UPDATE_ADAPTER);
    }

    @Override
    public void onDialogCreated(QBChatDialog chatDialog) {

    }

    @Override
    public void onDialogUpdated(String chatDialog) {

    }

    @Override
    public void onNewDialogLoaded(QBChatDialog chatDialog) {

    }

    public SingleLiveEvent<String> getMainViewModelResponse() {
        return mainViewModelResponse;
    }

    public void setMainViewModelResponse(SingleLiveEvent<String> mainViewModelResponse) {
        this.mainViewModelResponse = mainViewModelResponse;
    }
}
