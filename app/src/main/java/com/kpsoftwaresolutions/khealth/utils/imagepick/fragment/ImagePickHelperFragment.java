package com.kpsoftwaresolutions.khealth.utils.imagepick.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.kpsoftwaresolutions.khealth.utils.ImageUtils;
import com.kpsoftwaresolutions.khealth.utils.imagepick.OnImagePickedListener;
import com.kpsoftwaresolutions.khealth.utils.imagepick.GetFilepathFromUriTask;
import com.kpsoftwaresolutions.khealth.utils.imagepick.OnImagePickedListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.io.File;

public class ImagePickHelperFragment extends Fragment {

    private static final String ARG_REQUEST_CODE = "requestCode";
    private static final String ARG_PARENT_FRAGMENT = "parentFragment";

    private static final String TAG = ImagePickHelperFragment.class.getSimpleName();

    private OnImagePickedListener listener;

    public static ImagePickHelperFragment start(Fragment fragment, int requestCode) {
        Bundle args = new Bundle();
        args.putInt(ARG_REQUEST_CODE, requestCode);
        args.putString(ARG_PARENT_FRAGMENT, fragment.getClass().getSimpleName());

        return start(fragment.getActivity().getSupportFragmentManager(), args);
    }

    public static ImagePickHelperFragment start(FragmentActivity activity, int requestCode) {
        Bundle args = new Bundle();
        args.putInt(ARG_REQUEST_CODE, requestCode);

        return start(activity.getSupportFragmentManager(), args);
    }

    private static ImagePickHelperFragment start(FragmentManager fm, Bundle args) {
        ImagePickHelperFragment fragment = (ImagePickHelperFragment) fm.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new ImagePickHelperFragment();
            fm.beginTransaction().add(fragment, TAG).commitAllowingStateLoss();
            fragment.setArguments(args);
        }
        return fragment;
    }

    public static void stop(FragmentManager fm) {
        Fragment fragment = fm.findFragmentByTag(TAG);
        if (fragment != null) {
            fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
        }
    }

    public ImagePickHelperFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment fragment = ((AppCompatActivity) context).getSupportFragmentManager()
                .findFragmentByTag(getArguments().getString(ARG_PARENT_FRAGMENT));
        if (fragment != null) {
            if (fragment instanceof OnImagePickedListener) {
                listener = (OnImagePickedListener) fragment;
            }
        } else {
            if (context instanceof OnImagePickedListener) {
                listener = (OnImagePickedListener) context;
                Log.d(TAG, "onAttach: file listener added");
            }
        }

        if (listener == null) {
            throw new IllegalStateException("Either activity or fragment should implement OnImagePickedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: file");
        super.onActivityResult(requestCode, resultCode, data);
        if (isResultFromImagePick(requestCode, resultCode, data)) {
            if (requestCode == ImageUtils.CAMERA_REQUEST_CODE && (data == null || data.getData() == null)) {
                // Hacky way to get EXTRA_OUTPUT param to work.
                // When setting EXTRA_OUTPUT param in the camera intent there is a chance that data will return as null
                // So we just pass temporary camera file as a data, because RESULT_OK means that photo was written in the file.

                if (listener != null){
                    File file = ImageUtils.getLastUsedCameraFile();
                    if (file !=null){
                        listener.onImagePicked(getArguments().getInt(ARG_REQUEST_CODE), file);
                    }else
                        Log.d(TAG, "onActivityResult: file null");
                    Log.d(TAG, "onActivityResult: file inside onImagePicked");
                }
                else
                    Log.d(TAG, "onActivityResult: file listener null");

            } else if (requestCode == ImageUtils.CAMERA_REQUEST_CODE && data.getData() != null) {

                if (listener != null){
                    File file = new File(ImageUtils.getFilePath(getContext(),data.getData()));
                    listener.onImagePicked(getArguments().getInt(ARG_REQUEST_CODE), file);
                    Log.d(TAG, "onActivityResult: file inside onImagePicked");
                }
                else
                    Log.d(TAG, "onActivityResult: file listener null");

            } else if (requestCode == ImageUtils.GALLERY_REQUEST_CODE && data != null && data.getData() != null){
                if (listener != null){
                    File file = new File(ImageUtils.getFilePath(getContext(),data.getData()));
                    listener.onImagePicked(getArguments().getInt(ARG_REQUEST_CODE), file);
                    Log.d(TAG, "onActivityResult: file inside onImagePicked");
                }
                else
                    Log.d(TAG, "onActivityResult: file listener null");
            }
            else{
                boolean isDataNotNull = data != null;
                boolean isDataGetDataNotNull = data.getData() != null;
                Log.d(TAG, String.valueOf("onActivityResult: file requestCode: "+requestCode+" data: "+isDataNotNull +" data.getData(): "+isDataGetDataNotNull));

            }
            //new GetFilepathFromUriTask(getChildFragmentManager(), listener, getArguments().getInt(ARG_REQUEST_CODE)).execute(data);
        } else {
            stop(getChildFragmentManager());
            if (listener != null) {
                listener.onImagePickClosed(getArguments().getInt(ARG_REQUEST_CODE));
            }
        }
    }

    public void setListener(OnImagePickedListener listener) {
        this.listener = listener;
    }

    private boolean isResultFromImagePick(int requestCode, int resultCode, Intent data) {
        return resultCode == Activity.RESULT_OK
                && ((requestCode == ImageUtils.CAMERA_REQUEST_CODE)
                || (requestCode == ImageUtils.GALLERY_REQUEST_CODE && data != null));
    }
}