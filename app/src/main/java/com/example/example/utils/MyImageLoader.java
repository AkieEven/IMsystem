package com.example.example.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cn.jiguang.imui.commons.ImageLoader;

public class MyImageLoader implements ImageLoader {

    /**
     * Load image into avatar's ImageView.
     *
     * @param avatarImageView Avatar's ImageView.
     * @param string          A file path, or a uri or url.
     */
    @Override
    public void loadAvatarImage(ImageView avatarImageView, String string) {
    }

    /**
     * Load image into image message's ImageView.
     *
     * @param imageView Image message's ImageView.
     * @param string    A file path, or a uri or url.
     */
    @Override
    public void loadImage(ImageView imageView, String string) {

    }

    /**
     * Load video to video message's image cover.
     *
     * @param imageCover Video message's image cover
     * @param uri        Local path or url.
     */
    @Override
    public void loadVideo(ImageView imageCover, String uri) {

    }
}
