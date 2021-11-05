package com.example.warnermediatest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.warnermediatest.Networking.Request;

public class ViewImageFragment extends Fragment {

    NetworkImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.view_image_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.image_to_view);

        // Get the selected image
        String imageUrl = ViewImageFragmentArgs.fromBundle(getArguments()).getImageUrl();
        imageUrl = imageUrl.replaceAll("_q.jpg", ".jpg");
        ImageLoader loader = Request.Companion.getInstance(getContext().getApplicationContext()).getImageLoader();
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, R.drawable.outline_image_24, R.drawable.outline_broken_image_24);
        loader.get(imageUrl, listener);
        imageView.setImageUrl(imageUrl, loader);
    }
}
