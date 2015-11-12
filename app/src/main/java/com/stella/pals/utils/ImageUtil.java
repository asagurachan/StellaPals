package com.stella.pals.utils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.stella.pals.R;

/**
 * Created by DJ on 11/11/15.
 * Project: Stella Pals
 */
public class ImageUtil {

    public static final DisplayImageOptions displayFemalePhotoOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.female_default)
//            .showImageForEmptyUri(R.drawable.profile_icon)
//            .showImageOnFail(R.drawable.profile_icon)
        .cacheOnDisk(true)
        .considerExifParams(true)
        .displayer(new RoundedBitmapDisplayer(180))
        .build();

    public static final DisplayImageOptions displayMalePhotoOptions = new DisplayImageOptions.Builder()
//            .showImageOnLoading(R.drawable.empty_female_180x180)
//            .showImageForEmptyUri(R.drawable.profile_icon)
//            .showImageOnFail(R.drawable.profile_icon)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .displayer(new RoundedBitmapDisplayer(180))
            .build();
}
