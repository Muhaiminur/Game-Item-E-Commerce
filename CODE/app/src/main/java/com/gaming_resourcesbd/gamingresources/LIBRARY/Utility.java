package com.gaming_resourcesbd.gamingresources.LIBRARY;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.TransitionDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.StatFs;
import android.os.StrictMode;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.gaming_resourcesbd.gamingresources.MODEL.GET_USER;
import com.gaming_resourcesbd.gamingresources.R;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class Utility {

    Context context;
    ProgressDialog mProgressDialog;

    public Utility(Context context) {
        this.context = context;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        freeMemory();
    }

    //================ Show Progress Dialog ===============
    public void showProgress(boolean isCancelable, String message) {
        mProgressDialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(isCancelable);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    //================ Hide Progress Dialog ===============
    public void hideProgress() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }


    /*
    ================ Show Toast Message ===============
    */
    public void showToast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /*
    =============== Set Window FullScreen ===============
    */
    public void setFullScreen() {
        Activity activity = ((Activity) context);
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /*
        ================ Get Screen Width ===============
        */
    public HashMap<String, Integer> getScreenRes() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        map.put(KeyWord.SCREEN_WIDTH, width);
        map.put(KeyWord.SCREEN_HEIGHT, height);
        map.put(KeyWord.SCREEN_DENSITY, (int) metrics.density);
        return map;
    }


    /*
    ================ Log function ===============
     */
    public void logger(String message) {
        Log.d(context.getString(R.string.app_name), message);
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        String date = sdf.format(new Date());
        //writeToFile(date+" -> "+message);
    }

    /*
    ================ Clear Text for EditText, Button, TextView ===============
    */
    public void clearText(View[] view) {
        for (View v : view) {
            if (v instanceof EditText) {
                ((EditText) v).setText("");
            } else if (v instanceof Button) {
                ((Button) v).setText("");
            } else if (v instanceof TextView) {
                ((TextView) v).setText("");
            }
        }
    }

    /*
    ================ Hide Keyboard from Screen ===============
    */
    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    /*
    ================ Show Keyboard to Screen ===============
    */
    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    /*
    ================ Hide & Show Views ===============
    */
    public void hideAndShowView(View[] views, View view) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.GONE);
        }
        view.setVisibility(View.VISIBLE);
    }

    public void hideViews(View[] views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setVisibility(View.GONE);
        }
    }

    public void freeMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    public HashMap<String, String> getDeviceInfo() {
        HashMap<String, String> map = new HashMap<>();
        map.put("Serial", Build.SERIAL);
        map.put("Model", Build.MODEL);
        //map.put("Id", Build.ID);
        map.put("Id", Build.SERIAL);
        map.put("Manufacture", Build.MANUFACTURER);
        map.put("Type", Build.TYPE);
        map.put("User", Build.USER);
        map.put("Base", String.valueOf(Build.VERSION_CODES.BASE));
        map.put("Incremental", Build.VERSION.INCREMENTAL);
        map.put("Board", Build.BOARD);
        map.put("Brand", Build.BRAND);
        map.put("Host", Build.HOST);
        map.put("Version Code", Build.VERSION.RELEASE);
        return map;
    }

    public void showDialog(String message) {
        HashMap<String, Integer> screen = getScreenRes();
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
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    public RequestOptions Glide_Cache_On() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.drawable.ic_loading);
        return requestOptions;
    }

    public RequestOptions Glide_Cache_Off() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_loading);
        requestOptions.error(R.drawable.ic_loading);
        requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
        requestOptions.skipMemoryCache(true);
        return requestOptions;
    }

    /*public RequestOptions Glide_Cache_On_profile() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_loading);
        requestOptions.error(R.drawable.ic_user2);
        return requestOptions;
    }*/

    public int getAppVersionCode() {
        int versionCode = 1;
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public void setUSER(GET_USER user) {
        SharedPreferences sharedPref = context.getSharedPreferences("USER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("USER_ID", json);
        editor.commit();
    }

    public GET_USER getUSER() {
        GET_USER user = null;
        SharedPreferences sharedPref = context.getSharedPreferences("USER", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString("USER_ID", "");
        user = gson.fromJson(json, GET_USER.class);
        return user;
    }

    public void clearUSER_ID() {
        SharedPreferences sharedPref = context.getSharedPreferences("USER", Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();
    }

    public void setCATEGORY(String token) {
        SharedPreferences sharedPref = context.getSharedPreferences("CATEGORY", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("CATEGORY_list", token);
        editor.commit();
    }

    public String getCATEGORY() {
        SharedPreferences sharedPref = context.getSharedPreferences("CATEGORY", Context.MODE_PRIVATE);
        return sharedPref.getString("CATEGORY_list", "");
    }

    public void clearCATEGORY() {
        SharedPreferences sharedPref = context.getSharedPreferences("CATEGORY", Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();
    }

    public void setBANNER(String token) {
        SharedPreferences sharedPref = context.getSharedPreferences("BANNER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("BANNER_list", token);
        editor.commit();
    }

    public String getBANNER() {
        SharedPreferences sharedPref = context.getSharedPreferences("BANNER", Context.MODE_PRIVATE);
        return sharedPref.getString("BANNER_list", "");
    }

    public void clearBANNER() {
        SharedPreferences sharedPref = context.getSharedPreferences("BANNER", Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();
    }

    public void setNOTICE(String token) {
        SharedPreferences sharedPref = context.getSharedPreferences("NOTICE", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("NOTICE_list", token);
        editor.commit();
    }

    public String getNOTICE() {
        SharedPreferences sharedPref = context.getSharedPreferences("NOTICE", Context.MODE_PRIVATE);
        return sharedPref.getString("NOTICE_list", "");
    }

    public void clearNOTICE() {
        SharedPreferences sharedPref = context.getSharedPreferences("NOTICE", Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();
    }

    public void setFirebaseToken(String token) {
        SharedPreferences sharedPref = context.getSharedPreferences("FCM", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", token);
        editor.commit();
    }

    public String getFirebaseToken() {
        SharedPreferences sharedPref = context.getSharedPreferences("FCM", Context.MODE_PRIVATE);
        return sharedPref.getString("token", "");
    }
}
