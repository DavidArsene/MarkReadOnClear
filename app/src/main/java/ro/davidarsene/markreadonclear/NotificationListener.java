package ro.davidarsene.markreadonclear;

import android.app.PendingIntent;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

import java.util.Arrays;

public class NotificationListener extends NotificationListenerService {

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn, RankingMap rankingMap, int reason) {
        if(reason != REASON_CANCEL && reason != REASON_CANCEL_ALL) return;
        if(sbn.getNotification().actions == null) return;

        Arrays.stream(sbn.getNotification().actions)
                .filter(action -> action.title.toString().equalsIgnoreCase("Mark as read"))
                .forEach(action -> {
                    try {
                        action.actionIntent.send();
                        Toast.makeText(this, "Marked as read", Toast.LENGTH_SHORT).show();
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
