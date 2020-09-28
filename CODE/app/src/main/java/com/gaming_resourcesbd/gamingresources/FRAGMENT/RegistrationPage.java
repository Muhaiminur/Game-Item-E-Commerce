package com.gaming_resourcesbd.gamingresources.FRAGMENT;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaming_resourcesbd.gamingresources.ADAPTER.HistoryAdapter;
import com.gaming_resourcesbd.gamingresources.HTTP.ApiClient;
import com.gaming_resourcesbd.gamingresources.HTTP.ApiInterface;
import com.gaming_resourcesbd.gamingresources.LIBRARY.KeyWord;
import com.gaming_resourcesbd.gamingresources.LIBRARY.Utility;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_HISTORY;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_PRODUCTDETAILS;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_USER;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_HISTORY;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_REGISTRATION;
import com.gaming_resourcesbd.gamingresources.R;
import com.gaming_resourcesbd.gamingresources.databinding.FragmentRegistrationPageBinding;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationPage extends Fragment {
    Gson gson;
    Context context;
    Utility utility;
    ApiInterface apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
    FragmentRegistrationPageBinding registrationPageBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (registrationPageBinding == null) {
            registrationPageBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_registration_page, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                gson = new Gson();
                registrationPageBinding.regReg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(registrationPageBinding.regName.getText().toString())) {
                            if (!TextUtils.isEmpty(registrationPageBinding.regEmail.getText().toString())) {
                                if (!TextUtils.isEmpty(registrationPageBinding.regMbl.getText().toString())) {
                                    if (!TextUtils.isEmpty(registrationPageBinding.regPassword.getText().toString())) {
                                        user_registration(new SEND_REGISTRATION(registrationPageBinding.regName.getText().toString(), registrationPageBinding.regEmail.getText().toString(), registrationPageBinding.regPassword.getText().toString(), registrationPageBinding.regMbl.getText().toString()));
                                    } else {
                                        registrationPageBinding.regPassword.setError(getResources().getString(R.string.password_string));
                                        registrationPageBinding.regPassword.requestFocusFromTouch();
                                    }
                                } else {
                                    registrationPageBinding.regMbl.setError(getResources().getString(R.string.mbl_string));
                                    registrationPageBinding.regMbl.requestFocusFromTouch();
                                }
                            } else {
                                registrationPageBinding.regEmail.setError(getResources().getString(R.string.email_string));
                                registrationPageBinding.regEmail.requestFocusFromTouch();
                            }
                        } else {
                            registrationPageBinding.regName.setError(getResources().getString(R.string.name_string));
                            registrationPageBinding.regName.requestFocusFromTouch();
                        }
                    }
                });
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return registrationPageBinding.getRoot();
    }

    private void user_registration(SEND_REGISTRATION s) {
        try {
            if (utility.isNetworkAvailable()) {
                utility.logger("user registration" + s.toString());
                utility.showProgress(false, getResources().getString(R.string.wait_string));
                Call<JsonElement> call = apiInterface.user_registration(s);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            utility.hideProgress();
                            utility.logger("user registration" + response.toString());
                            if (response.isSuccessful() && response != null && response.code() == 200) {
                                GET_USER user = gson.fromJson(response.body(), GET_USER.class);
                                utility.logger("user registration" + user.toString());
                                if (!TextUtils.isEmpty(user.getCustomerEmail()) && !TextUtils.isEmpty(user.getCustomerId().toString())) {
                                    utility.setUSER(user);
                                    showDialog(context.getResources().getString(R.string.reg_success_string));
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


    public void showDialog(String message) {
        HashMap<String, Integer> screen = utility.getScreenRes();
        int width = screen.get(KeyWord.SCREEN_WIDTH);
        int height = screen.get(KeyWord.SCREEN_HEIGHT);
        int mywidth = (width / 10) * 7;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_toast);
        TextView tvMessage = dialog.findViewById(R.id.tv_message);
        Button btnOk = dialog.findViewById(R.id.btn_ok);
        tvMessage.setText(message);
        LinearLayout ll = dialog.findViewById(R.id.dialog_layout_size);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll.getLayoutParams();
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.width = mywidth;
        ll.setLayoutParams(params);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);
                startActivity(getActivity().getIntent());
                getActivity().overridePendingTransition(0, 0);
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
}