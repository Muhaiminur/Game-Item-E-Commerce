package com.gaming_resourcesbd.gamingresources.FRAGMENT;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.bumptech.glide.Glide;
import com.gaming_resourcesbd.gamingresources.HTTP.ApiClient;
import com.gaming_resourcesbd.gamingresources.HTTP.ApiInterface;
import com.gaming_resourcesbd.gamingresources.LIBRARY.Utility;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_APIERROR;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_CONTACT_POLICY;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_PRODUCTDETAILS;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_PRODUCTID;
import com.gaming_resourcesbd.gamingresources.R;
import com.gaming_resourcesbd.gamingresources.databinding.FragmentPrivacyPageBinding;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyPage extends Fragment {
    Gson gson;
    Context context;
    Utility utility;
    ApiInterface apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
    FragmentPrivacyPageBinding privacyPageBinding;
    GET_CONTACT_POLICY get_contact_policy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (privacyPageBinding == null) {
            privacyPageBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_privacy_page, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                gson = new Gson();
                utility.logger("abir1");
                initial_privacy_policy();
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }

        }
        return privacyPageBinding.getRoot();
    }

    private void initial_privacy_policy() {
        try {
            if (utility.isNetworkAvailable()) {
                utility.logger("abir2");
                utility.showProgress(false, getResources().getString(R.string.wait_string));
                Call<JsonElement> call = apiInterface.Get_Provicy_Policy();
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            utility.hideProgress();
                            utility.logger("GET privacy policy" + response.toString());
                            if (response.isSuccessful() && response != null && response.code() == 200) {
                                get_contact_policy = gson.fromJson(response.body(), GET_CONTACT_POLICY.class);
                                if (get_contact_policy != null) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        privacyPageBinding.privacyMsg.setText(Html.fromHtml(get_contact_policy.getPageContent(), Html.FROM_HTML_MODE_LEGACY));
                                    } else {
                                        privacyPageBinding.privacyMsg.setText(Html.fromHtml(get_contact_policy.getPageContent()));
                                    }
                                    privacyPageBinding.privacyTitle.setText(get_contact_policy.getPageTitle());
                                }
                            } else {
                                Gson gson = new Gson();
                                GET_APIERROR apierror = gson.fromJson(response.body(), GET_APIERROR.class);
                                if (apierror != null) {
                                    utility.showDialog(apierror.getMessage());
                                } else {
                                    utility.showDialog(apierror.getMessage());
                                }
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