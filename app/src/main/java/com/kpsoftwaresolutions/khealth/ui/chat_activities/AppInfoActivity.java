package com.kpsoftwaresolutions.khealth.ui.chat_activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kpsoftwaresolutions.khealth.R;
import com.quickblox.auth.session.QBSettings;



public class AppInfoActivity extends BaseActivity {

    private static final String TAG = "AppInfoActivity";
    private TextView appVersionTextView;
    private TextView sdkVersionTextView;
    private TextView appIDTextView;
    private TextView authKeyTextView;
    private TextView authSecretTextView;
    private TextView accountKeyTextView;
    private TextView apiDomainTextView;
    private TextView chatDomainTextView;
    private TextView appQAVersionTextView;

    public static void start(Context context) {
        Intent intent = new Intent(context, AppInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appinfo);
        Log.d(TAG, "onCreate: ");

        initUI();
        fillUI();
    }

    private void initUI() {
        appVersionTextView = findViewById(R.id.tv_app_version);
        sdkVersionTextView = findViewById(R.id.tv_sdk_version);
        appIDTextView = findViewById(R.id.tv_app_id);
        authKeyTextView = findViewById(R.id.tv_auth_key);
        authSecretTextView = findViewById(R.id.tv_auth_secret);
        accountKeyTextView = findViewById(R.id.tv_account_key);
        apiDomainTextView = findViewById(R.id.tv_api_domain);
        chatDomainTextView = findViewById(R.id.tv_chat_domain);
        appQAVersionTextView = findViewById(R.id.tv_qa_version);
    }

    public void fillUI() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.appinfo_title));
        }
        sdkVersionTextView.setText(com.quickblox.BuildConfig.VERSION_NAME);
        appIDTextView.setText(QBSettings.getInstance().getApplicationId());
        authKeyTextView.setText(QBSettings.getInstance().getAuthorizationKey());
        authSecretTextView.setText(QBSettings.getInstance().getAuthorizationSecret());
        accountKeyTextView.setText(QBSettings.getInstance().getAccountKey());
        apiDomainTextView.setText(QBSettings.getInstance().getServerApiDomain());
        chatDomainTextView.setText(QBSettings.getInstance().getChatEndpoint());


    }
}