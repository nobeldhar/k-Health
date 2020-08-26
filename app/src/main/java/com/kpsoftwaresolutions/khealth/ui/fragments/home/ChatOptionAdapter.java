package com.kpsoftwaresolutions.khealth.ui.fragments.home;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kpsoftwaresolutions.khealth.R;
import com.kpsoftwaresolutions.khealth.utils.UiUtils;
import com.kpsoftwaresolutions.khealth.utils.qb.QbDialogUtils;
import com.quickblox.chat.model.QBChatDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class ChatOptionAdapter extends RecyclerView.Adapter<com.kpsoftwaresolutions.khealth.ui.fragments.home.ChatOptionAdapter.MyViewHolder> {

    private static final String MAX_MESSAGES_TEXT = "99+";
    private static final int MAX_MESSAGES = 99;

    List<String> chatOptions;
    private boolean isSelectMode = false;
    Context context;
    OnClickListener onClickListener;

    public ChatOptionAdapter(List<String> chatOptions, OnClickListener onClickListener) {
        this.chatOptions = chatOptions;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat_options, parent, false);
        context = parent.getContext();
        return new MyViewHolder(view,this.onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.root.setBackgroundColor(UiUtils.getCircleColor(position));
        holder.textView.setText(chatOptions.get(position));

    }

    @Override
    public int getItemCount() {
        return chatOptions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private CardView root;
        private OnClickListener onClickListener;
        public MyViewHolder(@NonNull View itemView, OnClickListener onClickListener) {
            super(itemView);

            textView = itemView.findViewById(R.id.chat_options_item);
            root = itemView.findViewById(R.id.chat_options_root);
            this.onClickListener = onClickListener;
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClickChatOptions(getAdapterPosition());
                }
            });


        }
    }





    public interface OnClickListener {
        void onClickChatOptions(int position);
    }
}
