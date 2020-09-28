package com.gaming_resourcesbd.gamingresources.FRAGMENT;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaming_resourcesbd.gamingresources.ADAPTER.HistoryAdapter;
import com.gaming_resourcesbd.gamingresources.ADAPTER.SubCategoryAdapter;
import com.gaming_resourcesbd.gamingresources.HTTP.ApiClient;
import com.gaming_resourcesbd.gamingresources.HTTP.ApiInterface;
import com.gaming_resourcesbd.gamingresources.LIBRARY.Utility;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_HISTORY;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_SUBCATEGORY;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_USER;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_HISTORY;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_SUBCATEGORY;
import com.gaming_resourcesbd.gamingresources.R;
import com.gaming_resourcesbd.gamingresources.databinding.FragmentLoginPageBinding;
import com.gaming_resourcesbd.gamingresources.databinding.FragmentOrderHistoryBinding;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistory extends Fragment {
    Gson gson;
    Context context;
    Utility utility;
    ApiInterface apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
    FragmentOrderHistoryBinding historyBinding;
    HistoryAdapter historyAdapter;
    GET_USER user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (historyBinding == null) {
            historyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_history, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                gson = new Gson();
                user = utility.getUSER();
                if (user != null && !TextUtils.isEmpty(user.getCustomerEmail()) && !TextUtils.isEmpty(user.getCustomerId().toString())) {
                    get_order_history();
                }
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return historyBinding.getRoot();
    }

    private void get_order_history() {
        try {
            if (utility.isNetworkAvailable()) {
                utility.showProgress(false, getResources().getString(R.string.wait_string));
                Call<JsonElement> call = apiInterface.Get_Order_history(new SEND_HISTORY(user.getCustomerId().toString(), user.getCustomerEmail()));
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            utility.hideProgress();
                            utility.logger("order history" + response.toString());
                            if (response.isSuccessful() && response != null && response.code() == 200) {
                                Type listType = new TypeToken<List<GET_HISTORY>>() {
                                }.getType();
                                List<GET_HISTORY> get_history = gson.fromJson(response.body(), listType);
                                utility.logger("order history Size" + get_history.size() + "");
                                if (get_history.size() > 0) {
                                    historyAdapter = new HistoryAdapter(get_history, context);
                                    historyAdapter.notifyDataSetChanged();
                                    historyBinding.historyRecyclerview.setLayoutManager(new LinearLayoutManager(context));
                                    historyBinding.historyRecyclerview.setAdapter(historyAdapter);
                                }
                            } else {
                                utility.showDialog(getResources().getString(R.string.try_again_string));
                            }
                        } catch (Exception ex) {
                            utility.logger(ex.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        utility.logger(t.toString());
                        utility.showToast(getResources().getString(R.string.try_again_string));
                        utility.hideProgress();
                    }
                });
            } else {
                utility.showDialog(getResources().getString(R.string.no_internet_string));
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }
}