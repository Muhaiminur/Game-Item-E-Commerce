package com.gaming_resourcesbd.gamingresources.FIREBASE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.bumptech.glide.Glide;
import com.gaming_resourcesbd.gamingresources.ACTIVITY.LandingPage;
import com.gaming_resourcesbd.gamingresources.LIBRARY.KeyWord;
import com.gaming_resourcesbd.gamingresources.LIBRARY.Utility;
import com.gaming_resourcesbd.gamingresources.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FISHBANGLA_FIREBASE extends FirebaseMessagingService {

    private static final String TAG = "GamingResources";

    Utility utility = new Utility(this);

    NotificationManager notificationManager;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data
        // messages are the type
        // traditionally used with GCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(2000, VibrationEffect.EFFECT_HEAVY_CLICK));
        } else {
            //deprecated in API 26
            v.vibrate(2000);
        }
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            Map<String, String> data = remoteMessage.getData();
            String title = data.get("title");
            String body = data.get("body");
            String image_url = data.get("image_url");
            String big_text = data.get("big_text");
            String metadataBrowse = data.get("metadataBrowse");
            String metadata = data.get("metadata");
            String small_image = data.get("icon_path");
            Log.d(TAG, "From: " + remoteMessage.getFrom());
            //sendNotification(title, msg, value1, value2);
            //createNotification(title);

            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            SHOW_NOTIFICATION(title, body, image_url, big_text, metadataBrowse, metadata);
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            SHOW_NOTIFICATION(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getTitle(), "data:image/jpeg;base64,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", "no", "", "");
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        //sendNotification("FISHBANGLA");
        //sendNotification(title, msg, value1, value2);
    }
    // [END receive_message]


    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        // If you want to send messages to this application instance or
                        // manage this apps subscriptions on the server side, send the
                        // Instance ID token to your app server.
                        sendRegistrationToServer(token);
                    }
                });
        Log.d(TAG, "Refreshed token: " + token);
    }

    // [END on_new_token]
    private void sendRegistrationToServer(String token) {
        utility.setFirebaseToken(token);
    }

    public void SHOW_NOTIFICATION(String title, String body, String image_url, String big_text, String metadataBrowse, String metadata) {
        try {
            createNotificationChannel();
            int notifyId = (int) System.currentTimeMillis();
            Intent intent = new Intent(this, LandingPage.class);
            if (metadataBrowse != null && !TextUtils.isEmpty(metadataBrowse) && metadata != null && !TextUtils.isEmpty(metadata)) {
                utility.logger("paisi");
                intent.putExtra("NOTIFICATION", "yes");
                intent.putExtra("metadataBrowse", metadataBrowse);
                intent.putExtra("metadata", metadata);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, notifyId, intent, 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, KeyWord.NOTIFICATION_CHANNEL_NAME)
                    .setContentTitle(title)
                    .setSmallIcon(R.drawable.ic_appicon)
                    .setContentText(body)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(title)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_appicon))
                    /*.setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(body))*/
                    /*.setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(*//*BitmapFactory.decodeResource(getResources(), R.drawable.ribbon)*//*Glide
                                    .with(this)
                                    .asBitmap()
                                    .load("http://116.212.109.34:9090/content/resources/images/banner/3/20200318_9.jpg")
                                    .submit()
                                    .get())
                            .bigLargeIcon(null))*/
                    .setPriority(Notification.PRIORITY_MAX);
            if (image_url != null && !TextUtils.isEmpty(image_url)) {
                builder.setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(Glide.with(this).asBitmap().load(image_url).submit().get()));
            } else if (big_text != null && !TextUtils.isEmpty(big_text) && big_text.equalsIgnoreCase(KeyWord.YES)) {
                builder.setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(body));
            }
            notificationManager.notify(notifyId, builder.build());

        } catch (Exception e) {
            Log.d("Error Line Number", Log.getStackTraceString(e));
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(KeyWord.NOTIFICATION_CHANNEL_NAME, name, importance);
            channel.setDescription(description);
            channel.enableVibration(true);
            channel.setLightColor(getColor(R.color.app_orange));
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel);
        }
    }
}
