package com.kpsoftwaresolutions.khealth.utils.imagepick;



import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.kpsoftwaresolutions.khealth.utils.imagepick.fragment.ImagePickHelperFragment;
import com.kpsoftwaresolutions.khealth.utils.imagepick.fragment.ImageSourcePickDialogFragment;

public class ImagePickHelper {

    public void pickAnImage(FragmentActivity activity, int requestCode) {
        ImagePickHelperFragment imagePickHelperFragment = ImagePickHelperFragment.start(activity, requestCode);
        showImageSourcePickerDialog(activity.getSupportFragmentManager(), imagePickHelperFragment);
    }

    private void showImageSourcePickerDialog(FragmentManager fm, ImagePickHelperFragment fragment) {
        ImageSourcePickDialogFragment.show(fm,
                new ImageSourcePickDialogFragment.LoggableActivityImageSourcePickedListener(fragment));
    }
}