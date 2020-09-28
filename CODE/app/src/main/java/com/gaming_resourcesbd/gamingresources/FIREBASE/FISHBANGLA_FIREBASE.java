package com.gaming_resourcesbd.gamingresources.FIREBASE;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
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
