package com.gaming_resourcesbd.gamingresources.ADAPTER;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.gaming_resourcesbd.gamingresources.MODEL.GET_HISTORY;
import com.gaming_resourcesbd.gamingresources.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.Todo_View_Holder> {
    Context context;
    List<GET_HISTORY> history_list;
    Utility utility;


    public HistoryAdapter(List<GET_HISTORY> to, Context c) {
        history_list = to;
        context = c;
        utility = new Utility(context);
    }

    public class Todo_View_Holder extends RecyclerView.ViewHolder {
        CardView history_more;
        TextView history_orderid;
        TextView history_orderstatus;
        Button history_drop;
        LinearLayout history_details;
        TextView history_name;
        TextView history_quantity;
        TextView history_date;
        TextView history_method;
        TextView history_delivery;
        TextView history_total;

        public Todo_View_Holder(View view) {
            super(view);
            history_more = view.findViewById(R.id.history_view);
            history_orderid = view.findViewById(R.id.orderhistory_orderid);
            history_orderstatus = view.findViewById(R.id.orderhistory_orderstatus);
            history_drop = view.findViewById(R.id.order_history_more);
            history_details = view.findViewById(R.id.order_history_details);
            history_name = view.findViewById(R.id.orderhistory_name);
            history_quantity = view.findViewById(R.id.orderhistory_quantity);
            history_date = view.findViewById(R.id.orderhistory_date);
            history_delivery = view.findViewById(R.id.orderhistory_deliverydate);
            history_method = view.findViewById(R.id.orderhistory_payment);
            history_total = view.findViewById(R.id.orderhistory_total);
        }
    }

    @Override
    public HistoryAdapter.Todo_View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_history, parent, false);

        return new HistoryAdapter.Todo_View_Holder(itemView);
    }

    @Override
    public void onBindViewHolder(final HistoryAdapter.Todo_View_Holder holder, int position) {
        final GET_HISTORY bodyResponse = history_list.get(position);
        try {
            holder.history_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.history_details.getVisibility() == View.VISIBLE) {
                        holder.history_details.setVisibility(View.GONE);
                        holder.history_drop.setBackground(context.getResources().getDrawable(R.drawable.ic_down_arrow));
                    } else if (holder.history_details.getVisibility() == View.GONE) {
                        holder.history_details.setVisibility(View.VISIBLE);
                        holder.history_drop.setBackground(context.getResources().getDrawable(R.drawable.ic_up_arrow));
                    }
                }
            });
            holder.history_orderid.setText(context.getResources().getString(R.string.orderid_string) + " " + bodyResponse.getOrderId());
            holder.history_orderstatus.setText(bodyResponse.getOrderStatus());
            holder.history_name.setText(bodyResponse.getOrderName());
            holder.history_quantity.setText(bodyResponse.getOrderQuantity().toString());
            holder.history_date.setText(bodyResponse.getOrderDate());
            holder.history_method.setText(bodyResponse.getOrderPaymentMethod());
            holder.history_delivery.setText(bodyResponse.getOrderEstimatedDeliveryDatetime());
            holder.history_total.setText(context.getResources().getString(R.string.totalprice_string) + " " + bodyResponse.getOrderTotal());
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public int getItemCount() {
        return history_list.size();
    }
}