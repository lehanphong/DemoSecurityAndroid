package lhp.ducnt.demosecurityactivity.noti;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import lhp.ducnt.demosecurityactivity.MainActivity;
import lhp.ducnt.demosecurityactivity.R;

/**
 * Created by ducnt on 4/11/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebase";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...
        Log.d(TAG, "From: " + "vao onMessageReceived");
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification("NTD Noti Security",remoteMessage.getNotification().getBody());
        }
    }

    private void sendNotification(String messageTitle,String messageBody) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0 /* request code */, intent,PendingIntent.FLAG_UPDATE_CURRENT);
//
//        long[] pattern = {500,500,500,500,500};
//
//        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle(messageTitle)
//                .setContentText(messageBody)
//                .setAutoCancel(true)
//                .setVibrate(pattern)
//                .setLights(Color.BLUE,1,1)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());



        // *** POINT 1 *** When preparing a Notification that includes private information, prepare an additional
        //          Noficiation for public display (displayed when the screen is locked).
        Notification.Builder publicNotificationBuilder = new Notification.Builder(this).setContentTitle("Notification : Public");
        if (Build.VERSION.SDK_INT >= 21)
            publicNotificationBuilder.setVisibility(Notification.VISIBILITY_PUBLIC);
        // *** POINT 2 *** Do not include private information in Notifications prepared for public display (displayed
        //        when the screen is locked).
        publicNotificationBuilder.setContentText("Visibility Public : Omitting sensitive data.");
        publicNotificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        Notification publicNotification = publicNotificationBuilder.build();

        // Construct a Notification that includes private information.
        Notification.Builder privateNotificationBuilder = new Notification.Builder(this).setContentTitle("Notification : Private");
        // *** POINT 3 *** Explicitly set Visibility to Private when creating Notifications.
        if (Build.VERSION.SDK_INT >= 21)
            privateNotificationBuilder.setVisibility(Notification.VISIBILITY_PRIVATE);
        // *** POINT 4 *** When Visibility is set to Private, Notifications may contain private information.
        privateNotificationBuilder.setContentText("Visibility Private : Including user info.");
        privateNotificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        // When creating a Notification with Visibility=Private, we also create and register a separate Notification
        //        with Visibility=Public for public display.
        if (Build.VERSION.SDK_INT >= 21)
            privateNotificationBuilder.setPublicVersion(publicNotification);
        Notification privateNotification = privateNotificationBuilder.build();
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, privateNotification);
    }
}
