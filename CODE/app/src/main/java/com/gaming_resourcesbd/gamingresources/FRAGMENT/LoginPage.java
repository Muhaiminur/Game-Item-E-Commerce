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
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_FORGET;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_REGISTRATION;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_USER;
import com.gaming_resourcesbd.gamingresources.R;
import com.gaming_resourcesbd.gamingresources.databinding.FragmentLoginPageBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends Fragment {
    Gson gson;
    Context context;
    Utility utility;
    ApiInterface apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
    FragmentLoginPageBinding loginPageBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (loginPageBinding == null) {
            loginPageBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_page, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                gson = new Gson();
                loginPageBinding.loginReg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Fragment navhost = ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                            NavController c = NavHostFragment.findNavController(navhost);
                            //c.popBackStack();
                            c.navigate(R.id.frag_registration);
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                        }
                    }
                });
                loginPageBinding.loginForgot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        forget_email_dialouge();
                    }
                });
                loginPageBinding.loginLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!TextUtils.isEmpty(loginPageBinding.loginEmail.getText().toString())) {
                            if (!TextUtils.isEmpty(loginPageBinding.loginPassword.getText().toString())) {
                                user_login(new SEND_USER(loginPageBinding.loginEmail.getText().toString(), loginPageBinding.loginPassword.getText().toString()));

                            } else {
                                loginPageBinding.loginPassword.setError(getResources().getString(R.string.password_string));
                                loginPageBinding.loginPassword.requestFocusFromTouch();
                            }
                        } else {
                            loginPageBinding.loginEmail.setError(getResources().getString(R.string.email_string));
                            loginPageBinding.loginEmail.requestFocusFromTouch();
                        }
                    }
                });
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return loginPageBinding.getRoot();
    }

    private void user_login(SEND_USER s) {
        try {
            if (utility.isNetworkAvailable()) {
                utility.logger("user login" + s.toString());
                utility.showProgress(false, getResources().getString(R.string.wait_string));
                Call<JsonElement> call = apiInterface.user_login(s);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            utility.hideProgress();
                            utility.logger("user login" + response.toString());
                            if (response.isSuccessful() && response != null && response.code() == 200) {
                                GET_USER user = gson.fromJson(response.body(), GET_USER.class);
                                utility.logger("user login" + user.toString());
                                if (!TextUtils.isEmpty(user.getCustomerEmail()) && !TextUtils.isEmpty(user.getCustomerId().toString())) {
                                    utility.setUSER(user);
                                    showDialog(context.getResources().getString(R.string.login_suc_string));
                                }
                            } else {
                                Gson gson = new Gson();
                                GET_APIERROR apierror = gson.fromJson(response.body(), GET_APIERROR.class);
                                if (apierror != null) {
                                    utility.showDialog(apierror.getMessage());
                                } else {
                                    utility.showDialog(context.getResources().getString(R.string.login_unsuc_string));
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

    private void forget_password(SEND_FORGET s) {
        try {
            if (utility.isNetworkAvailable()) {
                utility.logger("forget pass" + s.toString());
                utility.showProgress(false, getResources().getString(R.string.wait_string));
                Call<JsonElement> call = apiInterface.forget_password(s);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            utility.hideProgress();
                            utility.logger("forget pass" + response.toString());
                            if (response.isSuccessful() && response != null && response.code() == 200) {
                                utility.showDialog(getResources().getString(R.string.forget_suc_string));
                            } else {
                                Gson gson = new Gson();
                                GET_APIERROR apierror = gson.fromJson(response.body(), GET_APIERROR.class);
                                if (apierror != null) {
                                    utility.showDialog(apierror.getMessage());
                                } else {
                                    utility.showDialog(context.getResources().getString(R.string.forget_unsuc_string));
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

    public void forget_email_dialouge() {
        try {
            HashMap<String, Integer> screen = utility.getScreenRes();
            int width = screen.get(KeyWord.SCREEN_WIDTH);
            int height = screen.get(KeyWord.SCREEN_HEIGHT);
            int mywidth = (width / 10) * 7;
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setContentView(R.layout.dialog_forget_password);
            Button yes = dialog.findViewById(R.id.btn_ok);
            Button no = dialog.findViewById(R.id.btn_no);
            TextInputEditText forgetemail = dialog.findViewById(R.id.forget_email);
            LinearLayout ll = dialog.findViewById(R.id.dialog_layout_size);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll.getLayoutParams();
            params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            params.width = mywidth;
            ll.setLayoutParams(params);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(forgetemail.getText().toString())) {
                        dialog.dismiss();
                        forget_password(new SEND_FORGET(forgetemail.getText().toString()));
                    } else {
                        forgetemail.setError(getResources().getString(R.string.validation_string));
                        forgetemail.requestFocusFromTouch();
                    }
                }
            });
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }
}