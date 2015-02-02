package rarolabs.com.br.rvp.notifications;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.Date;
import java.util.List;

import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.activities.MainActivity;
import rarolabs.com.br.rvp.models.Notificacao;

/**
 * Created by rodrigosol on 2/2/15.
 */
public class RVPNotificationManager {

    public static void sendNotification(Context contex, String messageType, Bundle extras) {
        if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
            notify(contex, extras);
        }
    }

    private static void notify(Context context, Bundle extras) {
        Notificacao notificacao = new Notificacao(extras);
        notificacao.save();
        if (!appIsRunning(context)) {
            NotificationManager mNotificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);

            PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                    new Intent(context, MainActivity.class), 0);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_drawer_notificacoes_normal)
                            .setContentTitle(notificacao.getTitulo(context))
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(notificacao.getTexto(context)))
                            .setGroup("RVP")
                            .setContentText(notificacao.getTexto(context));


            mBuilder.setContentIntent(contentIntent);
            mNotificationManager.notify(notificacao.getId().intValue(), mBuilder.build());

        }

    }


    private static boolean appIsRunning(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager
                .getRunningTasks(Integer.MAX_VALUE);
        boolean isActivityFound = false;

        if (services.get(0).topActivity.getPackageName().toString()
                .equalsIgnoreCase(context.getPackageName().toString())) {
            isActivityFound = true;
        }

        return isActivityFound;
    }

}
