package com.stella.pals.utils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by DJ on 11/11/15.
 * Project: Stella Pals
 */
public class ImageUtil {

    public static final DisplayImageOptions displayPhotoOptions = new DisplayImageOptions.Builder()
//            .showImageOnLoading(R.drawable.profile_icon)
//            .showImageForEmptyUri(R.drawable.profile_icon)
//            .showImageOnFail(R.drawable.profile_icon)
        .cacheOnDisk(true)
        .considerExifParams(true)
        .displayer(new RoundedBitmapDisplayer(180))
        .build();
}
