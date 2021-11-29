package com.example.habot;
/**
 * @Author @sjudd on GitHub
 * @Reference https://github.com/bumptech/glide
 */

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    /**
     * This Module is for displaying image in the app
     * @param context
     * @param glide
     * @param registry
     */
    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        // Register FirebaseImageLoader to handle StorageReference
        registry.append(StorageReference.class, InputStream.class,
                new FirebaseImageLoader.Factory());
    }
}
