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
import com.gaming_resourcesbd.gamingresources.MODEL.GET_CATEGORY;
import com.gaming_resourcesbd.gamingresources.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Todo_View_Holder> {
    Context context;
    List<GET_CATEGORY> cat_list;
    Utility utility;


    public CategoryAdapter(List<GET_CATEGORY> to, Context c) {
        cat_list = to;
        context = c;
        utility = new Utility(context);
    }

    public class Todo_View_Holder extends RecyclerView.ViewHolder {
        CardView cat_view;
        ImageView cat_image;
        TextView cat_name;
        TextView cat_detail;

        public Todo_View_Holder(View view) {
            super(view);
            cat_view = view.findViewById(R.id.category_view);
            cat_image = view.findViewById(R.id.category_image);
            cat_name = view.findViewById(R.id.category_titles);
            cat_detail = view.findViewById(R.id.category_details);
        }
    }

    @Override
    public CategoryAdapter.Todo_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category, parent, false);

        return new CategoryAdapter.Todo_View_Holder(itemView);
    }

    @Override
    public void onBindViewHolder(final CategoryAdapter.Todo_View_Holder holder, int position) {
        final GET_CATEGORY bodyResponse = cat_list.get(position);
        try {
            Glide.with(context).load(bodyResponse.getCatthumb()).apply(utility.Glide_Cache_On()).into(holder.cat_image);
            holder.cat_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("category_id", bodyResponse.getCatid().toString());
                    Fragment navhost = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    NavController c = NavHostFragment.findNavController(navhost);
                    //c.popBackStack();
                    c.navigate(R.id.frag_subcategory, bundle);
                }
            });
            if (TextUtils.isEmpty(bodyResponse.getCatname())) {
                holder.cat_name.setVisibility(View.GONE);
            } else {
                holder.cat_name.setVisibility(View.VISIBLE);
                holder.cat_name.setText(bodyResponse.getCatname());
            }
            if (TextUtils.isEmpty(bodyResponse.getDescription())) {
                holder.cat_detail.setVisibility(View.GONE);
            } else {
                holder.cat_detail.setVisibility(View.GONE);
                holder.cat_detail.setText(bodyResponse.getDescription());
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