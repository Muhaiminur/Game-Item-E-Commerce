package com.gaming_resourcesbd.gamingresources.FRAGMENT;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaming_resourcesbd.gamingresources.ADAPTER.SubCategoryAdapter;
import com.gaming_resourcesbd.gamingresources.HTTP.ApiClient;
import com.gaming_resourcesbd.gamingresources.HTTP.ApiInterface;
import com.gaming_resourcesbd.gamingresources.LIBRARY.Utility;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_SUBCATEGORY;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_SUBCATEGORY;
import com.gaming_resourcesbd.gamingresources.R;
import com.gaming_resourcesbd.gamingresources.databinding.FragmentSubCategoryBinding;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubCategoryFragment extends Fragment {
    Gson gson;
    Context context;
    Utility utility;
    ApiInterface apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
    FragmentSubCategoryBinding subCategoryBinding;
    SubCategoryAdapter subCategoryAdapter;
    String category_id;
    public static String FACEBOOK_URL = "https://www.facebook.com/gamingro2/";
    public static String FACEBOOK_PAGE_ID = "103892414684669";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (subCategoryBinding == null) {
            subCategoryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sub_category, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                gson = new Gson();
                if (getArguments() != null) {
                    category_id = getArguments().getString("category_id");
                }
                if (!TextUtils.isEmpty(category_id)) {
                    initial_subcategory();
                }
                subCategoryBinding.subcatMsg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        open_msg();
                    }
                });
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return subCategoryBinding.getRoot();
    }

    private void initial_subcategory() {
        try {
            if (utility.isNetworkAvailable()) {
                utility.showProgress(false, getResources().getString(R.string.wait_string));
                Call<JsonElement> call = apiInterface.Get_subcategiry(new SEND_SUBCATEGORY(category_id));
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            utility.hideProgress();
                            utility.logger("GET SUB Category" + response.toString());
                            if (response.isSuccessful() && response != null && response.code() == 200) {
                                Type listType = new TypeToken<List<GET_SUBCATEGORY>>() {
                                }.getType();
                                List<GET_SUBCATEGORY> get_category = gson.fromJson(response.body(), listType);
                                utility.logger("SUB Category List Size" + get_category.size() + "");
                                if (get_category.size() > 0) {
                                    subCategoryAdapter = new SubCategoryAdapter(get_category, context);
                                    subCategoryAdapter.notifyDataSetChanged();
                                    subCategoryBinding.subcatRecyclerview.setLayoutManager(new GridLayoutManager(context, 3));
                                    subCategoryBinding.subcatRecyclerview.setAdapter(subCategoryAdapter);
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

    public void open_msg() {
        try {
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
            String facebookUrl = getFacebookPageURL(context);
            facebookIntent.setData(Uri.parse(facebookUrl));
            startActivity(facebookIntent);
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }
}