package com.kpsoftwaresolutions.khealth.ui.fragments.login;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.kpsoftwaresolutions.khealth.repositories.LoginRepository;
import com.kpsoftwaresolutions.khealth.utils.network.SingleLiveEvent;
import com.quickblox.users.model.QBUser;


public class LoginViewModel extends ViewModel {
    private static final String TAG = "LoginViewModel";
    public String username;
    public String password;
    private LoginRepository loginRepository = new LoginRepository();
    private SingleLiveEvent<Boolean> loginProgressbar = new SingleLiveEvent<>();

    public void onLoginButton(View view){
        Log.d(TAG, "onLoginButton: ");

        loginProgressbar.setValue(true);

        if(username == null || password==null || password.length() <8 ){
            loginRepository.getLoginResponse().setValue("Invalid Credentials");
            return;
        }
        prepareUser();

        /*User user = new User();
        user.setMobile(mobile);
        user.setPassword(password);
        loginRepository.loginUser(user);*/

    }

    private void prepareUser() {
        QBUser qbUser = new QBUser();
        qbUser.setLogin(username);
        qbUser.setPassword(password);
        loginRepository.signIn(qbUser);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginRepository getLoginRepository() {
        return loginRepository;
    }

    public void setLoginRepository(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    public SingleLiveEvent<Boolean> getLoginProgressbar() {
        return loginProgressbar;
    }

    public void setLoginProgressbar(SingleLiveEvent<Boolean> loginProgressbar) {
        this.loginProgressbar = loginProgressbar;
    }

    public SingleLiveEvent<String> getLoginResponse() {
        return loginRepository.getLoginResponse();
    }
}
