package com.kpsoftwaresolutions.khealth.ui.fragments.home;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.view.ActionMode;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.kpsoftwaresolutions.khealth.MainActivity;
import com.kpsoftwaresolutions.khealth.MainViewModel;
import com.kpsoftwaresolutions.khealth.R;
import com.kpsoftwaresolutions.khealth.async.BaseAsyncTask;
import com.kpsoftwaresolutions.khealth.databinding.FragmentHomeBinding;
import com.kpsoftwaresolutions.khealth.managers.DialogsManager;
import com.kpsoftwaresolutions.khealth.services.LoginService;
import com.kpsoftwaresolutions.khealth.ui.call_activities.OpponentsActivity;
import com.kpsoftwaresolutions.khealth.ui.chat_activities.DialogsActivity;
import com.kpsoftwaresolutions.khealth.ui.fragments.home.HomeViewModel;
import com.kpsoftwaresolutions.khealth.utils.ErrorUtils;
import com.kpsoftwaresolutions.khealth.utils.FcmConsts;
import com.kpsoftwaresolutions.khealth.utils.SharedPrefsHelper;
import com.kpsoftwaresolutions.khealth.utils.ToastUtils;
import com.kpsoftwaresolutions.khealth.utils.chat.ChatHelper;
import com.kpsoftwaresolutions.khealth.utils.network.NetworkStateReciever;
import com.kpsoftwaresolutions.khealth.utils.qb.QbChatDialogMessageListenerImp;
import com.kpsoftwaresolutions.khealth.utils.qb.QbDialogHolder;
import com.kpsoftwaresolutions.khealth.utils.qb.QbDialogUtils;
import com.kpsoftwaresolutions.khealth.utils.qb.VerboseQbChatConnectionListener;
import com.kpsoftwaresolutions.khealth.utils.qb.callback.QBPushSubscribeListenerImpl;
import com.quickblox.auth.session.QBSessionManager;
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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.webrtc.ContextUtils.getApplicationContext;


public class HomeFragment extends Fragment implements ChatOptionAdapter.OnClickListener {


    private static final int CHAT_CONSULTANCY = 0;
    private static final int AUDIO_CONSULTANCY = 2;
    private static final int VIDEO_CONSULTANCY = 1;
    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home,container, false);

        List<String> chatOptions = new ArrayList<>();
        chatOptions.add("Chat Consultancy");
        chatOptions.add("Video Consultancy");
        chatOptions.add("Audio Consultancy");
        binding.homeRecycler.setLayoutManager(new LinearLayoutManager(requireActivity()));
        binding.homeRecycler.setAdapter(new ChatOptionAdapter(chatOptions,this ));


        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        homeViewModel = new
                ViewModelProvider(this).get(HomeViewModel.class);
        binding.setViewmodel(homeViewModel);

    }


    @Override
    public void onClickChatOptions(int position) {

        switch (position){
            case CHAT_CONSULTANCY:
                startActivity(new Intent(requireActivity(), DialogsActivity.class));
                break;
            case VIDEO_CONSULTANCY:
                if (SharedPrefsHelper.getInstance().hasQbUser()) {
                    LoginService.start(requireActivity(), SharedPrefsHelper.getInstance().getQbUser());
                    OpponentsActivity.start(requireActivity());
                }
                break;
            case AUDIO_CONSULTANCY:
        }
    }


}
