package com.kpsoftwaresolutions.khealth;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kpsoftwaresolutions.khealth.async.BaseAsyncTask;
import com.kpsoftwaresolutions.khealth.managers.DialogsManager;

import com.kpsoftwaresolutions.khealth.ui.fragments.home.HomeViewModel;
import com.kpsoftwaresolutions.khealth.utils.ErrorUtils;
import com.kpsoftwaresolutions.khealth.utils.SharedPrefsHelper;
import com.kpsoftwaresolutions.khealth.utils.ToastUtils;
import com.kpsoftwaresolutions.khealth.utils.chat.ChatHelper;
import com.quickblox.chat.model.QBChatDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {

    private static final String TAG = "MainActivity";

    private Toolbar mToolbar;
    private BottomNavigationView mBottomNavigationView;
    private NavController mNavController;
    private HomeViewModel mHomeViewModel;
    private MainViewModel mMainViewModel;
    private Fragment currentUser;
    private boolean hasMoreDialogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(mToolbar);

        mHomeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mBottomNavigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, mNavController, appBarConfiguration);
        NavigationUI.setupWithNavController(mToolbar,mNavController,appBarConfiguration);
        NavigationUI.setupWithNavController(mBottomNavigationView, mNavController);
        mNavController.addOnDestinationChangedListener(this);

    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {


        switch (destination.getId()){
            case R.id.navigation_home:
                navigationHome();
                break;
            case R.id.navigation_login:
                navigationLogin();
                break;

           
        }
    }

    private void navigationLogin() {
        mBottomNavigationView.setVisibility(View.GONE);
        mToolbar.setVisibility(View.GONE);
    }

    private void navigationHome() {
        mBottomNavigationView.setVisibility(View.VISIBLE);
        mToolbar.setVisibility(View.VISIBLE);
        setTitleBar();
    }

    private void setTitleBar() {
        if (SharedPrefsHelper.getInstance().hasQbUser()){
            String title = SharedPrefsHelper.getInstance().getQbUser().getFullName();
            Log.d(TAG, "setTitleBar: "+title);
            Objects.requireNonNull(getSupportActionBar()).setTitle(title);
        }else {
            Log.d(TAG, "setTitleBar: no user in sharedPref");
        }

    }


}
