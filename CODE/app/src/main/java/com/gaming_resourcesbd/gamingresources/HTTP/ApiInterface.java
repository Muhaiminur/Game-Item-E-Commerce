package com.gaming_resourcesbd.gamingresources.HTTP;

import com.gaming_resourcesbd.gamingresources.MODEL.SEND_EDIT;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_FORGET;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_HISTORY;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_ORDER;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_PRODUCTID;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_REGISTRATION;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_SUBCATEGORY;
import com.gaming_resourcesbd.gamingresources.MODEL.SEND_USER;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    //1 get list category
    @GET("categories/")
    Call<JsonElement> Get_category();

    // 2 get sub category
    @POST("products")
    Call<JsonElement> Get_subcategiry(@Body SEND_SUBCATEGORY send_subcategory);

    // 3 get product details
    @POST("product-details")
    Call<JsonElement> Get_product_details(@Body SEND_PRODUCTID send_productid);

    // 4 User Registration
    @POST("register")
    Call<JsonElement> user_registration(@Body SEND_REGISTRATION send_registration);

    // 5 User Login
    @POST("login")
    Call<JsonElement> user_login(@Body SEND_USER send_user);

    //6 get order history
    @POST("order-lists")
    Call<JsonElement> Get_Order_history(@Body SEND_HISTORY send_history);

    //7 forget password
    @POST("lost-password")
    Call<JsonElement> forget_password(@Body SEND_FORGET send_forget);

    //8 Create Order
    @POST("create-order")
    Call<JsonElement> Create_Order(@Body SEND_ORDER send_order);

    //9 Profile Edit
    @POST("edit-profile")
    Call<JsonElement> Profile_Edit(@Body SEND_EDIT send_edit);
    //10 get privacy policy
    @GET("privacy-policy")
    Call<JsonElement> Get_Provicy_Policy();
    //11 get contact page
    @GET("contact-us")
    Call<JsonElement> Get_Contact_Page();

    //12 get list category
    @GET("category-lists")
    Call<JsonElement> Get_category_2();

    //11 get banner
    @GET("sliders")
    Call<JsonElement> Get_Banner_data();

    //12 get notice
    @GET("app-notice")
    Call<JsonElement> Get_notice();

    //13 get payment
    @GET("payment-methods")
    Call<JsonElement> Get_payment_methods();


}
