package com.gaming_resourcesbd.gamingresources.ADAPTER;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gaming_resourcesbd.gamingresources.MODEL.Banner_Data;
import com.gaming_resourcesbd.gamingresources.R;
import com.opensooq.pluto.base.PlutoAdapter;
import com.opensooq.pluto.base.PlutoViewHolder;

import java.util.List;

public class HomepageBannerAdapter extends PlutoAdapter<Banner_Data, HomepageBannerAdapter.Homepage_Banner_Viewmodel> {

    public HomepageBannerAdapter(List<Banner_Data> items) {
        super(items);
    }

    @Override
    public HomepageBannerAdapter.Homepage_Banner_Viewmodel getViewHolder(ViewGroup parent, int viewType) {
        return new Homepage_Banner_Viewmodel(parent, R.layout.banner_home_page);
    }

    public static class Homepage_Banner_Viewmodel extends PlutoViewHolder<Banner_Data> {
        ImageView banner_image;
        TextView banner_title;

        public Homepage_Banner_Viewmodel(ViewGroup parent, int itemLayoutId) {
            super(parent, itemLayoutId);
            banner_image = getView(R.id.banner_home_image);
            banner_title = getView(R.id.banner_home_text);
        }

        @Override
        public void set(Banner_Data item, int pos) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.drawable.ic_appicon);
            Glide.with(getContext()).load(item.getBannerImageURL()).apply(requestOptions).into(banner_image);
            banner_title.setText(item.getBannerTitle());
        }
    }
}
