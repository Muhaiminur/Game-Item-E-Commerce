package com.gaming_resourcesbd.gamingresources.ACTIVITY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;

import com.gaming_resourcesbd.gamingresources.ADAPTER.HomeCategoryAdapter;
import com.gaming_resourcesbd.gamingresources.HTTP.ApiClient;
import com.gaming_resourcesbd.gamingresources.HTTP.ApiInterface;
import com.gaming_resourcesbd.gamingresources.LIBRARY.Utility;
import com.gaming_resourcesbd.gamingresources.MODEL.Banner_Data;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_APIERROR;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_CATEGORY2;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_NOTICE;
import com.gaming_resourcesbd.gamingresources.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandingPage extends AppCompatActivity {
    Gson gson;
    Context context;
    Utility utility;
    ApiInterface apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        try {
            context = LandingPage.this;
            utility = new Utility(context);
            gson = new Gson();
            if (utility.isNetworkAvailable()) {
                initial_category();
                //timeset();
            } else {
                utility.showDialog(context.getResources().getString(R.string.no_internet_string));
            }
            if (TextUtils.isEmpty(utility.getFirebaseToken())) {
                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    utility.logger("FIREBASE TOKEN FAILED");
                                    return;
                                }
                                String token = task.getResult().getToken();
                                utility.setFirebaseToken(token);
                            }
                        });
            }
            utility.logger("fcm " + utility.getFirebaseToken());
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public void timeset() {
        try {
            new CountDownTimer(5000, 1000) {

                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    startActivity(new Intent(LandingPage.this, MainActivity.class));
                    finish();
                }
            }.start();
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    private void initial_category() {
        try {
            if (utility.isNetworkAvailable()) {
                Call<JsonElement> call = apiInterface.Get_category_2();
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            utility.logger("LAND GET Category" + response.toString());
                            if (response.isSuccessful() && response != null && response.code() == 200) {
                                Type listType = new TypeToken<List<GET_CATEGORY2>>() {
                                }.getType();
                                List<GET_CATEGORY2> get_category = gson.fromJson(response.body(), listType);
                                utility.logger("LAND Category List Size" + get_category.size() + "");
                                if (get_category.size() > 0) {
                                    utility.clearCATEGORY();
                                    utility.setCATEGORY(gson.toJson(get_category));
                                }
                                initial_banner();
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
                    }
                });
            } else {
                utility.showDialog(getResources().getString(R.string.no_internet_string));
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }


    private void initial_banner() {
        try {
            if (utility.isNetworkAvailable()) {
                Call<JsonElement> call = apiInterface.Get_Banner_data();
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            utility.logger("LAND GET banner" + response.toString());
                            if (response.isSuccessful() && response != null && response.code() == 200) {
                                Type listType = new TypeToken<List<Banner_Data>>() {
                                }.getType();
                                List<Banner_Data> get_banner = gson.fromJson(response.body(), listType);
                                utility.logger("LAND banner List Size" + get_banner.size() + "");
                                if (get_banner.size() > 0) {
                                    utility.clearBANNER();
                                    utility.setBANNER(gson.toJson(get_banner));
                                }
                                /*startActivity(new Intent(LandingPage.this, MainActivity.class));
                                finish();*/
                                initial_notice();
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
                    }
                });
            } else {
                utility.showDialog(getResources().getString(R.string.no_internet_string));
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    private void initial_notice() {
        try {
            if (utility.isNetworkAvailable()) {
                Call<JsonElement> call = apiInterface.Get_notice();
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            utility.logger("LAND GET notice" + response.toString());
                            if (response.isSuccessful() && response != null && response.code() == 200) {
                                GET_NOTICE get_notice = gson.fromJson(response.body(), GET_NOTICE.class);
                                utility.logger("LAND GET notice 1" + get_notice.toString());
                                if (get_notice != null ) {
                                    utility.clearNOTICE();
                                    utility.setNOTICE(gson.toJson(get_notice));
                                }
                                startActivity(new Intent(LandingPage.this, MainActivity.class));
                                finish();
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