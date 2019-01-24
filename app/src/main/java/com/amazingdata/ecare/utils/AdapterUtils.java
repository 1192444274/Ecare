package com.amazingdata.ecare.utils;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author Xiong
 * @date 2019/1/21 - 14:31
 */
// Databinding的ViewAdapter的自定义辅助类
public class AdapterUtils {

    @BindingAdapter(value = {"xbind:imageUrl", "xbind:errorRes", "xbind:placeHolderRes"}, requireAll = false)
    public static void loadImage(ImageView imageView, String url, int errorRes, int placeHolderRes) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions().error(errorRes))
                    .apply(new RequestOptions().placeholder(placeHolderRes))
                    .into(imageView);
        }
    }

    @BindingAdapter(value = {"xbind:backgroundResId"})
    public static void loadBackGround(ViewGroup viewGroup, int backgroundResId) {
        if (backgroundResId != 0)
            viewGroup.setBackgroundResource(backgroundResId);
    }
}
