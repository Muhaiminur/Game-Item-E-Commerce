package com.gaming_resourcesbd.gamingresources.FRAGMENT;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gaming_resourcesbd.gamingresources.ADAPTER.CategoryAdapter;
import com.gaming_resourcesbd.gamingresources.ADAPTER.HomeCategoryAdapter;
import com.gaming_resourcesbd.gamingresources.ADAPTER.HomepageBannerAdapter;
import com.gaming_resourcesbd.gamingresources.ADAPTER.SubCategoryAdapter;
import com.gaming_resourcesbd.gamingresources.HTTP.ApiClient;
import com.gaming_resourcesbd.gamingresources.HTTP.ApiInterface;
import com.gaming_resourcesbd.gamingresources.LIBRARY.KeyWord;
import com.gaming_resourcesbd.gamingresources.LIBRARY.Utility;
import com.gaming_resourcesbd.gamingresources.MODEL.Banner_Data;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_CATEGORY;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_CATEGORY2;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_NOTICE;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_SUBCATEGORY;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_SUBCATEGORY;
import com.gaming_resourcesbd.gamingresources.R;
import com.gaming_resourcesbd.gamingresources.databinding.FragmentHomeBinding;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    Gson gson;
    Context context;
    Utility utility;
    ApiInterface apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
    FragmentHomeBinding homeBinding;
    /*CategoryAdapter categoryAdapter;
    SubCategoryAdapter subCategoryAdapter;*/
    HomeCategoryAdapter homeCategoryAdapter;
    HomepageBannerAdapter banner_adapter;
    List<Banner_Data> banner_list = new ArrayList<>();
    String cat_string = "";
    String ban_string = "";
    String notice_string = "";

    public static String FACEBOOK_URL = "https://www.facebook.com/gamingro2/";
    public static String FACEBOOK_PAGE_ID = "103892414684669";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (homeBinding == null) {
            homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                gson = new Gson();
                cat_string = utility.getCATEGORY();
                ban_string = utility.getBANNER();
                notice_string = utility.getNOTICE();
                if (!TextUtils.isEmpty(cat_string)) {
                    initial_category();
                }
                if (!TextUtils.isEmpty(ban_string)) {
                    utility.logger("banner paisi");
                    initial_banner();
                } else {
                    utility.logger("banner painai");
                }
                if (!TextUtils.isEmpty(notice_string)) {
                    GET_NOTICE getNotice = gson.fromJson(notice_string, GET_NOTICE.class);
                    utility.logger("notice paisi"+ getNotice.toString());
                    if (getNotice.getShowAppNotice()) {
                        homeBinding.homeNotice.setVisibility(View.VISIBLE);
                        homeBinding.homeNotice.setText(getNotice.getAppNotice());
                    }
                } else {
                    utility.logger("notice painai");
                }
                if (banner_adapter != null) {
                    homeBinding.homeBanner.setCustomIndicator(homeBinding.customIndicator);
                }
                //initial_category();
                homeBinding.homeCatMsg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        open_msg();
                    }
                });

            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return homeBinding.getRoot();
    }

    private void initial_category() {
        try {
            Type listType = new TypeToken<List<GET_CATEGORY2>>() {
            }.getType();
            List<GET_CATEGORY2> get_category = gson.fromJson(cat_string, listType);
            utility.logger("Category List Size" + get_category.size() + "");
            if (get_category.size() > 0) {
                homeCategoryAdapter = new HomeCategoryAdapter(get_category, context);
                homeCategoryAdapter.notifyDataSetChanged();
                // adding inbuilt divider line
                homeBinding.homeCatRecyclerview.setLayoutManager(new LinearLayoutManager(context));
                homeBinding.homeCatRecyclerview.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
                homeBinding.homeCatRecyclerview.setItemAnimator(new DefaultItemAnimator());
                homeBinding.homeCatRecyclerview.setAdapter(homeCategoryAdapter);
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    private void initial_banner() {
        try {
            Type listType = new TypeToken<List<Banner_Data>>() {
            }.getType();
            List<Banner_Data> banner_data = gson.fromJson(ban_string, listType);
            utility.logger("Banner List Size" + banner_data.size() + "");
            banner_list.clear();
            banner_list.addAll(banner_data);
            banner_adapter = new HomepageBannerAdapter(banner_list);
            homeBinding.homeBanner.create(banner_adapter, getLifecycle());
            homeBinding.homeBanner.setCustomIndicator(homeBinding.customIndicator);
            banner_adapter.setOnItemClickListener((item, position) -> {
                //handle click here
                banner_click_work(item);
            });
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void banner_click_work(Banner_Data banner_data) {
        try {
            if (banner_data.getMetadataBrowse().equalsIgnoreCase(KeyWord.EXTERNAL)) {
                try {
                    String url = banner_data.getMetadata();
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorAccent));
                    CustomTabsIntent customTabsIntent = builder.build();
                    customTabsIntent.launchUrl(context, Uri.parse(url));
                } catch (Exception e) {
                    Log.d("Error Line Number", Log.getStackTraceString(e));
                    try {
                        Uri uri = Uri.parse(banner_data.getMetadata()); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                    } catch (Exception e2) {
                        Log.d("Error Line Number", Log.getStackTraceString(e2));
                        utility.showDialog(context.getResources().getString(R.string.no_browser_string));
                    }
                }
            } else if (banner_data.getMetadataBrowse().equalsIgnoreCase(KeyWord.INTERNAL)) {
                /*if (banner_data.getMetadata().contains(KeyWord.INAPP_SELLER_LIST)) {
                 *//*Bundle bundle = new Bundle();
                            bundle.putString("product_id", bodyResponse.getProductId());*//*
                    Fragment navhost = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    NavController c = NavHostFragment.findNavController(navhost);
                    //c.popBackStack();
                    c.navigate(R.id.nav_seller);
                } else if (banner_data.getMetadata().contains(KeyWord.INAPP_SELLER_DETAILS)) {
                    String[] parts = banner_data.getMetadata().split("-");
                    String id = parts[1];
                    utility.logger("seller_id" + id);
                    Bundle bundle = new Bundle();
                    bundle.putString("seller_id", id);
                    Fragment navhost = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    NavController c = NavHostFragment.findNavController(navhost);
                    //c.popBackStack();
                    c.navigate(R.id.nav_seller_details, bundle);
                } else if (banner_data.getMetadata().contains(KeyWord.INAPP_PRODUCT_DETAILS)) {
                    String[] parts = banner_data.getMetadata().split("-");
                    String id = parts[1];
                    utility.logger("product_id" + id);
                    Bundle bundle = new Bundle();
                    bundle.putString("product_id", id);
                    Fragment navhost = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    NavController c = NavHostFragment.findNavController(navhost);
                    //c.popBackStack();
                    c.navigate(R.id.frag_product_details, bundle);
                } else if (banner_data.getMetadata().contains(KeyWord.INAPP_NOTIFICATION)) {
                    Fragment navhost = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    NavController c = NavHostFragment.findNavController(navhost);
                    //c.popBackStack();
                    c.navigate(R.id.frag_notification);
                } else if (banner_data.getMetadata().contains(KeyWord.INAPP_CART)) {
                    context.startActivity(new Intent(context, Cart_Activity.class));
                } else if (banner_data.getMetadata().contains(KeyWord.INAPP_DISCOUNT)) {
                    context.startActivity(new Intent(context, Cart_Activity.class));
                } else if (banner_data.getMetadata().contains(KeyWord.INAPP_CATEGORY)) {
                    String[] parts = banner_data.getMetadata().split("-");
                    String id = parts[1];
                    utility.logger("category_id" + id);
                    Bundle bundle = new Bundle();
                    bundle.putString("category_id", id);
                    Fragment navhost = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    NavController c = NavHostFragment.findNavController(navhost);
                    //c.popBackStack();
                    c.navigate(R.id.frag_category_list, bundle);
                } else if (banner_data.getMetadata().contains(KeyWord.INAPP_PROFILE)) {
                    Fragment navhost = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    NavController c = NavHostFragment.findNavController(navhost);
                    //c.popBackStack();
                    c.navigate(R.id.frag_profile);
                } else if (banner_data.getMetadata().contains(KeyWord.INAPP_BUY_HISTORY)) {
                    Fragment navhost = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    NavController c = NavHostFragment.findNavController(navhost);
                    //c.popBackStack();
                    c.navigate(R.id.nav_buy);
                } else if (banner_data.getMetadata().contains(KeyWord.INAPP_SELL_HISTORY)) {
                    Fragment navhost = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    NavController c = NavHostFragment.findNavController(navhost);
                    //c.popBackStack();
                    c.navigate(R.id.nav_sell);
                } else if (banner_data.getMetadata().contains(KeyWord.INAPP_OWN_PRODUCT)) {
                    Fragment navhost = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                    NavController c = NavHostFragment.findNavController(navhost);
                    //c.popBackStack();
                    c.navigate(R.id.nav_Own_product);
                } else if (banner_data.getMetadata().contains(KeyWord.INAPP_RATTING)) {
                    context.startActivity(new Intent(context, Landing_Page.class));
                }*/

            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (banner_adapter != null) {
            homeBinding.homeBanner.setCustomIndicator(homeBinding.customIndicator);
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

    /*private void initial_category() {
        try {
            if (utility.isNetworkAvailable()) {
                utility.showProgress(false, getResources().getString(R.string.wait_string));
                Call<JsonElement> call = apiInterface.Get_category_2();
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            utility.hideProgress();
                            utility.logger("GET Category" + response.toString());
                            if (response.isSuccessful() && response != null && response.code() == 200) {
                                Type listType = new TypeToken<List<GET_CATEGORY2>>() {
                                }.getType();
                                List<GET_CATEGORY2> get_category = gson.fromJson(response.body(), listType);
                                utility.logger("Category List Size" + get_category.size() + "");
                                if (get_category.size() > 0) {
                                    homeCategoryAdapter = new HomeCategoryAdapter(get_category, context);
                                    homeCategoryAdapter.notifyDataSetChanged();
                                    // adding inbuilt divider line
                                    homeBinding.homeCatRecyclerview.setLayoutManager(new LinearLayoutManager(context));
                                    homeBinding.homeCatRecyclerview.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
                                    homeBinding.homeCatRecyclerview.setItemAnimator(new DefaultItemAnimator());
                                    homeBinding.homeCatRecyclerview.setAdapter(homeCategoryAdapter);
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
    }*/

    /*private void initial_category() {
        try {
            if (utility.isNetworkAvailable()) {
                utility.showProgress(false, getResources().getString(R.string.wait_string));
                Call<JsonElement> call = apiInterface.Get_category();
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            utility.hideProgress();
                            utility.logger("GET Category" + response.toString());
                            if (response.isSuccessful() && response != null && response.code() == 200) {
                                Type listType = new TypeToken<List<GET_CATEGORY>>() {
                                }.getType();
                                List<GET_CATEGORY> get_category = gson.fromJson(response.body(), listType);
                                utility.logger("Category List Size" + get_category.size() + "");
                                if (get_category.size() > 0) {
                                    categoryAdapter = new CategoryAdapter(get_category, context);
                                    categoryAdapter.notifyDataSetChanged();
                                    homeBinding.categoryRecyclerview.setLayoutManager(new GridLayoutManager(context, 2));
                                    homeBinding.categoryRecyclerview.setAdapter(categoryAdapter);
                                }
                                game_topup();
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

    private void game_topup() {
        try {
            if (utility.isNetworkAvailable()) {
                utility.showProgress(false, getResources().getString(R.string.wait_string));
                Call<JsonElement> call = apiInterface.Get_subcategiry(new SEND_SUBCATEGORY("29"));
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
                                    //homeBinding.topupRecyclerview.setLayoutManager(new LinearLayoutManager(context));
                                    homeBinding.topupRecyclerview.setLayoutManager(new GridLayoutManager(context, 2));
                                    homeBinding.topupRecyclerview.setAdapter(subCategoryAdapter);
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
    }*/
}