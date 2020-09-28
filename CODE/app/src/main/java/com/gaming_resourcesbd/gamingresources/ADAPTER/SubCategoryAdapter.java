package com.gaming_resourcesbd.gamingresources.ADAPTER;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gaming_resourcesbd.gamingresources.LIBRARY.Utility;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_SUBCATEGORY;
import com.gaming_resourcesbd.gamingresources.R;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.Todo_View_Holder> {
    Context context;
    List<GET_SUBCATEGORY> cat_list;
    Utility utility;


    public SubCategoryAdapter(List<GET_SUBCATEGORY> to, Context c) {
        cat_list = to;
        context = c;
        utility = new Utility(context);
    }

    public class Todo_View_Holder extends RecyclerView.ViewHolder {
        CardView cat_view;
        ImageView cat_image;
        TextView cat_name;

        public Todo_View_Holder(View view) {
            super(view);
            cat_view = view.findViewById(R.id.subcategory_view);
            cat_image = view.findViewById(R.id.subcategory_image);
            cat_name = view.findViewById(R.id.subcategory_titles);
        }
    }

    @Override
    public SubCategoryAdapter.Todo_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_subcategory, parent, false);

        return new SubCategoryAdapter.Todo_View_Holder(itemView);
    }

    @Override
    public void onBindViewHolder(final SubCategoryAdapter.Todo_View_Holder holder, int position) {
        final GET_SUBCATEGORY bodyResponse = cat_list.get(position);
        try {
            Glide.with(context).load(bodyResponse.getProductthumb()).apply(utility.Glide_Cache_On()).into(holder.cat_image);
            holder.cat_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("product_id", bodyResponse.getProductid().toString());
                    Fragment navhost = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    NavController c = NavHostFragment.findNavController(navhost);
                    //c.popBackStack();
                    c.navigate(R.id.frag_product_details, bundle);
                }
            });
            if (TextUtils.isEmpty(bodyResponse.getProducttitle())) {
                holder.cat_name.setVisibility(View.GONE);
            } else {
                holder.cat_name.setVisibility(View.VISIBLE);
                holder.cat_name.setText(bodyResponse.getProducttitle());
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public int getItemCount() {
        return cat_list.size();
    }
}