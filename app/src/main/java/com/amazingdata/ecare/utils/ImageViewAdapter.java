package com.amazingdata.ecare.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author Xiong
 * @date 2019/1/19 - 11:30
 */
public class ImageViewAdapter {

    @BindingAdapter(value = {"url", "placeholderRes", "errorRes"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, int placeholderRes, int errorRes) {
        if (!TextUtils.isEmpty(url)) {
            //使用Glide框架加载图片
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions().placeholder(placeholderRes))
                    .apply(new RequestOptions().error(errorRes))
                    .into(imageView);
        }
    }
}
