package lhp.ducnt.demosecurityactivity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

/**
 * Created by ducnt on 4/11/17.
 */

public class VisibilityPrivateNotificationActivity extends Activity {
    /**
     * Display a private Notification
     */
    private final int mNotificationId = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti);
    }
    public void onSendNotificationClick(View view) {
        try {
            Thread.sleep(3000);
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

            notificationManager.notify(mNotificationId, privateNotification);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}