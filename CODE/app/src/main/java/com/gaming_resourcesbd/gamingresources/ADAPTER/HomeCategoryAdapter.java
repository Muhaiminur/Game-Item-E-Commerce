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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gaming_resourcesbd.gamingresources.LIBRARY.Utility;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_CATEGORY;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_CATEGORY2;
import com.gaming_resourcesbd.gamingresources.R;

import java.util.ArrayList;
import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.Todo_View_Holder> {
    Context context;
    List<GET_CATEGORY2> cat_list;
    Utility utility;


    CategoryAdapter categoryAdapter;
    List<GET_CATEGORY> child_cat_list;

    public HomeCategoryAdapter(List<GET_CATEGORY2> to, Context c) {
        cat_list = to;
        context = c;
        utility = new Utility(context);
    }

    public class Todo_View_Holder extends RecyclerView.ViewHolder {
        CardView cat_view;
        TextView cat_title;
        RecyclerView cat_recycler;

        public Todo_View_Holder(View view) {
            super(view);
            cat_view = view.findViewById(R.id.category_home_view);
            cat_title = view.findViewById(R.id.category_home_title);
            cat_recycler = view.findViewById(R.id.category_home_recyclerview);
        }
    }

    @Override
    public HomeCategoryAdapter.Todo_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category_home, parent, false);

        return new HomeCategoryAdapter.Todo_View_Holder(itemView);
    }

    @Override
    public void onBindViewHolder(final HomeCategoryAdapter.Todo_View_Holder holder, int position) {
        final GET_CATEGORY2 bodyResponse = cat_list.get(position);
        try {
            holder.cat_title.setText(bodyResponse.getName());
            child_cat_list = new ArrayList<>();
            child_cat_list.addAll(bodyResponse.getCatArray());
            categoryAdapter = new CategoryAdapter(child_cat_list, context);
            categoryAdapter.notifyDataSetChanged();
            holder.cat_recycler.setLayoutManager(new GridLayoutManager(context, 3));
            holder.cat_recycler.setAdapter(categoryAdapter);

            /*holder.cat_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("category_id", bodyResponse.getCatid().toString());
                    Fragment navhost = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    NavController c = NavHostFragment.findNavController(navhost);
                    //c.popBackStack();
                    c.navigate(R.id.frag_subcategory, bundle);
                }
            });*/
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public int getItemCount() {
        return cat_list.size();
    }
}