package com.kpsoftwaresolutions.khealth.ui.fragments.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kpsoftwaresolutions.khealth.utils.network.SingleLiveEvent;
import com.quickblox.chat.model.QBChatDialog;

public class HomeViewModel extends ViewModel {
    public SingleLiveEvent<String> viewModelResponse = new SingleLiveEvent<>();
    public SingleLiveEvent<String> HomeFragmentResponse = new SingleLiveEvent<>();
    public static final String LOGOUT = "logout";


    public SingleLiveEvent<String> getViewModelResponse() {
        return viewModelResponse;
    }

    public void setViewModelResponse(SingleLiveEvent<String> viewModelResponse) {
        this.viewModelResponse = viewModelResponse;
    }

}