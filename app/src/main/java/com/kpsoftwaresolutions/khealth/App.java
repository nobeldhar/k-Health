package com.kpsoftwaresolutions.khealth;

import android.app.Application;
import android.util.Log;

//import com.crashlytics.android.Crashlytics;
import com.kpsoftwaresolutions.khealth.managers.BackgroundListener;
import com.kpsoftwaresolutions.khealth.utils.ActivityLifecycle;
import com.kpsoftwaresolutions.khealth.utils.QBResRequestExecutor;
import com.quickblox.auth.session.QBSession;
import com.quickblox.auth.session.QBSessionManager;
import com.quickblox.auth.session.QBSessionParameters;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.StoringMechanism;
/*import com.quickblox.sample.chat.java.managers.BackgroundListener;
import com.quickblox.sample.chat.java.utils.ActivityLifecycle;*/

import androidx.lifecycle.ProcessLifecycleOwner;

//import io.fabric.sdk.android.Fabric;

public class App extends Application {

    private static final String TAG = "App";

    //Chat settings
    public static final String USER_DEFAULT_PASSWORD = "quickblox";
    public static final int CHAT_PORT = 5223;
    public static final int SOCKET_TIMEOUT = 300;
    public static final boolean KEEP_ALIVE = true;
    public static final boolean USE_TLS = true;
    public static final boolean AUTO_JOIN = false;
    public static final boolean AUTO_MARK_DELIVERED = true;
    public static final boolean RECONNECTION_ALLOWED = true;
    public static final boolean ALLOW_LISTEN_NETWORK = true;

    //App credentials
    private static final String APPLICATION_ID = "84182";
    private static final String AUTH_KEY = "Q4kGHAErDM-Swxp";
    private static final String AUTH_SECRET = "OLjbgYkUuNdVzxT";
    private static final String ACCOUNT_KEY = "sqRHjz3Pcnr28vT7dXxs";

    //Chat settings range
    private static final int MAX_PORT_VALUE = 65535;
    private static final int MIN_PORT_VALUE = 1000;
    private static final int MIN_SOCKET_TIMEOUT = 300;
    private static final int MAX_SOCKET_TIMEOUT = 60000;

    private QBResRequestExecutor qbResRequestExecutor;
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        initApplication();
        ActivityLifecycle.init(this);
        checkAppCredentials();
        checkChatSettings();
        initCredentials();
        initQBSessionManager();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new BackgroundListener());
    }

    private void initApplication() {
        instance = this;
    }



    private void checkAppCredentials() {
        if (APPLICATION_ID.isEmpty() || AUTH_KEY.isEmpty() || AUTH_SECRET.isEmpty() || ACCOUNT_KEY.isEmpty()) {
            throw new AssertionError(getString(R.string.error_qb_credentials_empty));
        }
    }

    private void checkChatSettings() {
        if (USER_DEFAULT_PASSWORD.isEmpty() || (CHAT_PORT < MIN_PORT_VALUE || CHAT_PORT > MAX_PORT_VALUE)
                || (SOCKET_TIMEOUT < MIN_SOCKET_TIMEOUT || SOCKET_TIMEOUT > MAX_SOCKET_TIMEOUT)) {
            throw new AssertionError(getString(R.string.error_chat_credentails_empty));
        }
    }

    private void initCredentials() {
        QBSettings.getInstance().setStoringMehanism(StoringMechanism.UNSECURED);
        QBSettings.getInstance().init(getApplicationContext(), APPLICATION_ID, AUTH_KEY, AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ACCOUNT_KEY);
        QBSettings.getInstance().setAutoCreateSession(true);

        // Uncomment and put your Api and Chat servers endpoints if you want to point the sample
        // against your own server.
        //
        // QBSettings.getInstance().setEndpoints("https://your_api_endpoint.com", "your_chat_endpoint", ServiceZone.PRODUCTION);
        // QBSettings.getInstance().setZone(ServiceZone.PRODUCTION);
    }

    public static App getInstance() {
        return instance;
    }

    private void initQBSessionManager() {
        QBSessionManager.getInstance().addListener(new QBSessionManager.QBSessionListener() {
            @Override
            public void onSessionCreated(QBSession qbSession) {
                Log.d(TAG, "Session Created");
            }

            @Override
            public void onSessionUpdated(QBSessionParameters qbSessionParameters) {
                Log.d(TAG, "Session Updated");
            }


            @Override
            public void onSessionDeleted() {
                Log.d(TAG, "Session Deleted");
            }

            @Override
            public void onSessionRestored(QBSession qbSession) {
                Log.d(TAG, "Session Restored");
            }

            @Override
            public void onSessionExpired() {
                Log.d(TAG, "Session Expired");
            }

            @Override
            public void onProviderSessionExpired(String provider) {
                Log.d(TAG, "Session Expired for provider:" + provider);
            }
        });
    }

    public synchronized QBResRequestExecutor getQbResRequestExecutor() {
        return qbResRequestExecutor == null
                ? qbResRequestExecutor = new QBResRequestExecutor()
                : qbResRequestExecutor;
    }
}