package com.gaming_resourcesbd.gamingresources.FRAGMENT;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaming_resourcesbd.gamingresources.HTTP.ApiClient;
import com.gaming_resourcesbd.gamingresources.HTTP.ApiInterface;
import com.gaming_resourcesbd.gamingresources.LIBRARY.KeyWord;
import com.gaming_resourcesbd.gamingresources.LIBRARY.Utility;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_APIERROR;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_USER;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_EDIT;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_REGISTRATION;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_USER;
import com.gaming_resourcesbd.gamingresources.R;
import com.gaming_resourcesbd.gamingresources.databinding.FragmentLoginPageBinding;
import com.gaming_resourcesbd.gamingresources.databinding.FragmentProfileEditBinding;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileEdit extends Fragment {
    Gson gson;
    Context context;
    Utility utility;
    ApiInterface apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
    FragmentProfileEditBinding editBinding;
    GET_USER user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (editBinding == null) {
            editBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_edit, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                gson = new Gson();
                user = utility.getUSER();
                if (user != null && !TextUtils.isEmpty(user.getCustomerEmail()) && !TextUtils.isEmpty(user.getCustomerId().toString())) {
                    set_profile();
                }
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return editBinding.getRoot();
    }

    private void set_profile() {
        editBinding.editEmail.setText(user.getCustomerEmail());
        editBinding.editMbl.setText(user.getCustomerPhone());
        editBinding.editName.setText(user.getCustomerFullName());
        editBinding.editUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editBinding.editName.getText().toString())) {
                    if (!TextUtils.isEmpty(editBinding.editEmail.getText().toString())) {
                        if (!TextUtils.isEmpty(editBinding.editMbl.getText().toString())) {
                            user_update(new SEND_EDIT(user.getCustomerId().toString(), editBinding.editEmail.getText().toString(), editBinding.editName.getText().toString(), editBinding.editMbl.getText().toString()));
                        } else {
                            editBinding.editMbl.setError(getResources().getString(R.string.mbl_string));
                            editBinding.editMbl.requestFocusFromTouch();
                        }
                    } else {
                        editBinding.editEmail.setError(getResources().getString(R.string.email_string));
                        editBinding.editEmail.requestFocusFromTouch();
                    }
                } else {
                    editBinding.editName.setError(getResources().getString(R.string.name_string));
                    editBinding.editName.requestFocusFromTouch();
                }
            }
        });
        editBinding.editLoglut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logutshowDialog(context.getResources().getString(R.string.logout_confirm_string));
            }
        });
    }

    private void user_update(SEND_EDIT s) {
        try {
            if (utility.isNetworkAvailable()) {
                utility.logger("user_update" + s.toString());
                utility.showProgress(false, getResources().getString(R.string.wait_string));
                Call<JsonElement> call = apiInterface.Profile_Edit(s);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            utility.hideProgress();
                            utility.logger("user_update" + response.toString());
                            if (response.isSuccessful() && response != null && response.code() == 200) {
                                GET_USER user = gson.fromJson(response.body(), GET_USER.class);
                                utility.logger("user_update" + user.toString());
                                if (!TextUtils.isEmpty(user.getCustomerEmail()) && !TextUtils.isEmpty(user.getCustomerId().toString())) {
                                    utility.setUSER(user);
                                    showDialog(context.getResources().getString(R.string.update_suc_string));
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

    public void logutshowDialog(String message) {
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
                utility.clearUSER_ID();
                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);
                startActivity(getActivity().getIntent());
                getActivity().overridePendingTransition(0, 0);
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }
}