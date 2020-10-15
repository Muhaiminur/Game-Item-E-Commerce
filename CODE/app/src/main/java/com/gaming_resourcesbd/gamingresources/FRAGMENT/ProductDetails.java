package com.gaming_resourcesbd.gamingresources.FRAGMENT;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gaming_resourcesbd.gamingresources.ADAPTER.SubCategoryAdapter;
import com.gaming_resourcesbd.gamingresources.HTTP.ApiClient;
import com.gaming_resourcesbd.gamingresources.HTTP.ApiInterface;
import com.gaming_resourcesbd.gamingresources.LIBRARY.KeyWord;
import com.gaming_resourcesbd.gamingresources.LIBRARY.Utility;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_APIERROR;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_PRODUCTDETAILS;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_SUBCATEGORY;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_USER;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_FORGET;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_ORDER;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_PRODUCTID;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_SUBCATEGORY;
import com.gaming_resourcesbd.gamingresources.R;
import com.gaming_resourcesbd.gamingresources.databinding.FragmentProductDetailsBinding;
import com.gaming_resourcesbd.gamingresources.databinding.FragmentSubCategoryBinding;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetails extends Fragment {

    Gson gson;
    Context context;
    Utility utility;
    ApiInterface apiInterface = ApiClient.getBaseClient().create(ApiInterface.class);
    FragmentProductDetailsBinding detailsBinding;
    String product_id;
    GET_PRODUCTDETAILS productDetails;
    GET_USER user;
    String price_id = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (detailsBinding == null) {
            detailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false);
            try {
                context = getActivity();
                utility = new Utility(context);
                gson = new Gson();
                user = utility.getUSER();
                if (getArguments() != null) {
                    product_id = getArguments().getString("product_id");
                }
                if (!TextUtils.isEmpty(product_id)) {
                    initial_product_details();
                }
                detailsBinding.productConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (user != null && !TextUtils.isEmpty(user.getCustomerId().toString()) && !TextUtils.isEmpty(user.getCustomerEmail())) {
                            if (!TextUtils.isEmpty(product_id)) {
                                if (!TextUtils.isEmpty(price_id)) {
                                    if (!TextUtils.isEmpty(detailsBinding.orderInput1.getText().toString())) {
                                        if (!TextUtils.isEmpty(detailsBinding.orderInput2.getText().toString())) {
                                            if (!TextUtils.isEmpty(detailsBinding.orderMobile.getText().toString())) {
                                                if (!TextUtils.isEmpty(detailsBinding.orderTransaction.getText().toString())) {
                                                    SEND_ORDER sendOrder = new SEND_ORDER();
                                                    sendOrder.setCustomer_id(user.getCustomerId().toString());
                                                    sendOrder.setCustomer_email(user.getCustomerEmail().toString());
                                                    sendOrder.setProduct_id(product_id);
                                                    sendOrder.setListid(price_id);
                                                    sendOrder.setPayment_type("bKash");
                                                    sendOrder.setMobile_number(detailsBinding.orderMobile.getText().toString());
                                                    sendOrder.setTransaction_id(detailsBinding.orderTransaction.getText().toString());
                                                    if (!TextUtils.isEmpty(detailsBinding.orderInput1.getText().toString())) {
                                                        if (productDetails.getTopUpType().equalsIgnoreCase(KeyWord.INAGAME)) {
                                                            sendOrder.setAccount_email_username(detailsBinding.orderInput1.getText().toString());
                                                        } else if (productDetails.getTopUpType().equalsIgnoreCase(KeyWord.UID)) {
                                                            sendOrder.setUser_game_id(detailsBinding.orderInput1.getText().toString());
                                                        }
                                                    } else {
                                                        if (productDetails.getTopUpType().equalsIgnoreCase(KeyWord.INAGAME)) {
                                                            sendOrder.setAccount_email_username("");
                                                        } else if (productDetails.getTopUpType().equalsIgnoreCase(KeyWord.UID)) {
                                                            sendOrder.setUser_game_id("");
                                                        }

                                                    }
                                                    if (!TextUtils.isEmpty(detailsBinding.orderInput2.getText().toString())) {
                                                        if (productDetails.getTopUpType().equalsIgnoreCase(KeyWord.INAGAME)) {
                                                            sendOrder.setAccount_passord(detailsBinding.orderInput2.getText().toString());
                                                        } else if (productDetails.getTopUpType().equalsIgnoreCase(KeyWord.UID)) {
                                                            sendOrder.setUser_game_nickname(detailsBinding.orderInput2.getText().toString());
                                                        }
                                                    } else {
                                                        if (productDetails.getTopUpType().equalsIgnoreCase(KeyWord.INAGAME)) {
                                                            sendOrder.setAccount_passord("");
                                                        } else if (productDetails.getTopUpType().equalsIgnoreCase(KeyWord.UID)) {
                                                            sendOrder.setUser_game_nickname("");
                                                        }
                                                    }
                                                    if (!TextUtils.isEmpty(detailsBinding.orderMessage.getText().toString())) {
                                                        sendOrder.setCustomer_note(detailsBinding.orderMessage.getText().toString());
                                                    } else {
                                                        sendOrder.setCustomer_note("");
                                                    }
                                                    sendOrder.setDevice_id(utility.getFirebaseToken());
                                                    order_confirms(sendOrder);
                                                    //order_confirms(new SEND_ORDER(, , product_id, price_id, ));
                                                } else {
                                                    detailsBinding.orderTransaction.setError(getResources().getString(R.string.order_tarn_string));
                                                    detailsBinding.orderTransaction.requestFocusFromTouch();
                                                }
                                            } else {
                                                detailsBinding.orderMobile.setError(getResources().getString(R.string.order_mbl_string));
                                                detailsBinding.orderMobile.requestFocusFromTouch();
                                            }
                                        } else {
                                            if (productDetails.getTopUpType().equalsIgnoreCase(KeyWord.INAGAME)) {
                                                detailsBinding.orderInput2.setError(getResources().getString(R.string.order_ingame2_string));
                                            } else if (productDetails.getTopUpType().equalsIgnoreCase(KeyWord.UID)) {
                                                detailsBinding.orderInput2.setError(getResources().getString(R.string.order_uid2_string));
                                            }
                                            detailsBinding.orderInput2.requestFocusFromTouch();
                                        }
                                    } else {
                                        if (productDetails.getTopUpType().equalsIgnoreCase(KeyWord.INAGAME)) {
                                            detailsBinding.orderInput1.setError(getResources().getString(R.string.order_ingame1_string));
                                        } else if (productDetails.getTopUpType().equalsIgnoreCase(KeyWord.UID)) {
                                            detailsBinding.orderInput1.setError(getResources().getString(R.string.order_uid1_string));
                                        }
                                        detailsBinding.orderInput1.requestFocusFromTouch();
                                    }
                                } else {
                                    utility.showDialog(context.getResources().getString(R.string.order_product_string));
                                }
                            } else {
                                utility.showDialog(context.getResources().getString(R.string.order_product_string));
                            }
                        } else {
                            utility.showDialog(context.getResources().getString(R.string.order_login_string));
                        }
                    }
                });

                detailsBinding.productHowtopay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String link = context.getResources().getString(R.string.payment_how_string);
                            if (!link.startsWith("http://") && !link.startsWith("https://")) {
                                link = "http://" + link;
                            }
                            Uri uri = Uri.parse(link); // missing 'http://' will cause crashed
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            context.startActivity(intent);
                        } catch (Exception e) {
                            Log.d("Error Line Number", Log.getStackTraceString(e));
                        }
                    }
                });
            } catch (Exception e) {
                Log.d("Error Line Number", Log.getStackTraceString(e));
            }
        }
        return detailsBinding.getRoot();
    }

    private void initial_product_details() {
        try {
            if (utility.isNetworkAvailable()) {
                utility.showProgress(false, getResources().getString(R.string.wait_string));
                Call<JsonElement> call = apiInterface.Get_product_details(new SEND_PRODUCTID(product_id));
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            utility.hideProgress();
                            utility.logger("GET SUB Category" + response.toString());
                            if (response.isSuccessful() && response != null && response.code() == 200) {
                                productDetails = gson.fromJson(response.body(), GET_PRODUCTDETAILS.class);
                                if (productDetails != null) {
                                    //utility.logger("GET SUB Category" + productDetails);
                                    Glide.with(context).load(productDetails.getProductthumb()).apply(utility.Glide_Cache_On()).into(detailsBinding.productImage);
                                    detailsBinding.productTitles.setText(productDetails.getProducttitle());
                                    //detailsBinding.productDetails.setText(productDetails.getProductdescription());
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        detailsBinding.productDetails.setText(Html.fromHtml(productDetails.getProductdescription(), Html.FROM_HTML_MODE_LEGACY));
                                    } else {
                                        detailsBinding.productDetails.setText(Html.fromHtml(productDetails.getProductdescription()));
                                    }
                                    if (productDetails.getTopUpType().equalsIgnoreCase(KeyWord.INAGAME)) {
                                        detailsBinding.orderInput1Hint.setHint(context.getResources().getString(R.string.order_ingame1_string));
                                        detailsBinding.orderInput2Hint.setHint(context.getResources().getString(R.string.order_ingame2_string));
                                    } else if (productDetails.getTopUpType().equalsIgnoreCase(KeyWord.UID)) {
                                        detailsBinding.orderInput1Hint.setHint(context.getResources().getString(R.string.order_uid1_string));
                                        detailsBinding.orderInput2Hint.setHint(context.getResources().getString(R.string.order_uid2_string));
                                    } else {
                                        detailsBinding.orderInput1Hint.setVisibility(View.GONE);
                                        detailsBinding.orderInput2Hint.setVisibility(View.GONE);
                                    }
                                    if (productDetails.getProductpricelists().size() > 0) {
                                        ArrayList<CheckBox> mCheckBoxes = new ArrayList<CheckBox>();
                                        for (int i = 0; i < productDetails.getProductpricelists().size(); i++) {
                                            CheckBox checkBox = new CheckBox(context);
                                            checkBox.setPadding(5, 5, 5, 5);
                                            checkBox.setTag(productDetails.getProductpricelists().get(i).getListid());
                                            mCheckBoxes.add(checkBox);
                                            checkBox.setText(productDetails.getProductpricelists().get(i).getListname());
                                            checkBox.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    try {
                                                        if (((CheckBox) v).isChecked()) {
                                                            for (int c = 0; c < mCheckBoxes.size(); c++) {
                                                                if (mCheckBoxes.get(c) == v) {//
                                                                    price_id = mCheckBoxes.get(c).getTag().toString();
                                                                } else {
                                                                    mCheckBoxes.get(c).setChecked(false);
                                                                }
                                                            }
                                                        } else {
                                                            //selected_position = -1;
                                                        }
                                                    } catch (Exception e) {
                                                        Log.d("Error Line Number", Log.getStackTraceString(e));
                                                    }

                                                }
                                            });
                                            detailsBinding.priceList.addView(checkBox);
                                        }
                                    }
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

    private void order_confirms(SEND_ORDER send_order) {
        try {
            if (utility.isNetworkAvailable()) {
                utility.logger("create order" + send_order.toString());
                utility.showProgress(false, getResources().getString(R.string.wait_string));
                Call<JsonElement> call = apiInterface.Create_Order(send_order);
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        try {
                            utility.hideProgress();
                            utility.logger("create order" + response.toString());
                            if (response.isSuccessful() && response != null && response.code() == 200) {
                                showDialog(getResources().getString(R.string.order_suc_string));
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
}