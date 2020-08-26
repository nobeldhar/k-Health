package com.kpsoftwaresolutions.khealth.ui.fragments.login;

import androidx.annotation.StringRes;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kpsoftwaresolutions.khealth.R;
import com.kpsoftwaresolutions.khealth.databinding.FragmentLoginBinding;
import com.kpsoftwaresolutions.khealth.services.LoginService;
import com.kpsoftwaresolutions.khealth.ui.chat_activities.DialogsActivity;
import com.kpsoftwaresolutions.khealth.utils.Consts;
import com.kpsoftwaresolutions.khealth.utils.ErrorUtils;
import com.kpsoftwaresolutions.khealth.utils.SharedPrefsHelper;
import com.quickblox.auth.session.QBSessionManager;
import com.quickblox.users.model.QBUser;


public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment";
    private LoginViewModel mViewModel;
    private FragmentLoginBinding binding;
    private NavController mNavController;
    View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login,container,false);
        root = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding.setViewmodel(mViewModel);
        mNavController = NavHostFragment.findNavController(this);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final NavController navController = NavHostFragment.findNavController(this);
        boolean isLoggedIn = SharedPrefsHelper.getInstance().hasQbUser();

        Log.d(TAG, "onActivityCreated: ");
        NavDirections direction = LoginFragmentDirections.actionNavigationLoginToNavigationHome();
        ;
        if (isLoggedIn){
            navController.navigate(direction);
        }
        //Log.d(TAG, "onActivityCreated: "+isLoggedIn);


        mViewModel.getLoginProgressbar().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean)
                binding.loginLoading.setVisibility(View.VISIBLE);
            else
                binding.loginLoading.setVisibility(View.GONE);

        });
        mViewModel.getLoginResponse().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.loginLoading.setVisibility(View.GONE);
                if (s.equals("Logged In")){
                    startLoginService(SharedPrefsHelper.getInstance().getQbUser());
                    navController.navigate(direction);
                }
                Toast.makeText(getActivity(),s, Toast.LENGTH_LONG).show();
            }
        });
    }
    private void startLoginService(QBUser qbUser) {
        Intent tempIntent = new Intent(requireActivity(), LoginService.class);
        PendingIntent pendingIntent = requireActivity().createPendingResult(Consts.EXTRA_LOGIN_RESULT_CODE, tempIntent, 0);
        LoginService.start(requireActivity(), qbUser, pendingIntent);
    }



}
