package rarolabs.com.br.rvp.notifications;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.List;


import rarolabs.com.br.rvp.R;
import rarolabs.com.br.rvp.activities.MainActivity;
import rarolabs.com.br.rvp.config.Constants;
import rarolabs.com.br.rvp.models.Comentario;

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
        Log.d("GCM", "Chegou push:" + extras.toString());
        if(extras.containsKey("msg")){
            //Mensagem mensagem = new Mensagem(extras);

        }else{
            Notificacao notificacao = new Notificacao(extras);
            criaNotificacao(notificacao, context);
        }
    }

    private static void criaNotificacao(Notificacao notificacao, Context context){
        if (!appIsRunning(context)) {
            NotificationManager mNotificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Log.d("Notificacao", "Tipo:" + notificacao.getTipo());
            if(notificacao.getTipo().toString().equals("ALERTA")){
                i.putExtra(Constants.FRAGMENT_NOTIFICACOES,"ALERTA");
            }else{
                i.putExtra(Constants.FRAGMENT_NOTIFICACOES,"NOTIFICACAO");
            }

            PendingIntent contentIntent = PendingIntent.getActivity(context, 0,i, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.ic_rvp_status)
                            .setContentTitle(notificacao.getTitulo(context))
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(notificacao.getTexto(context)))
                            .setGroup("RVP")
                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                            .setContentText(notificacao.getTexto(context));



            mBuilder.setContentIntent(contentIntent);
            Notification notification = mBuilder.build();
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            mNotificationManager.notify(notificacao.getId().intValue(), notification);

        }else{
            Log.d("Notificacao:", "Tentando notificar activity");
            Intent i = new Intent();
            i.setAction("rarolabs.com.br.rvp.broadcast.MOSTRA_ALERTA");
            i.putExtra(Constants.EXTRA_NOTIFICACAO_TIPO,notificacao.getTipo().toString());
            if(notificacao.getTipoAlerta()!=null) {
                i.putExtra(Constants.EXTRA_NOTIFICACAO_TIPO_ALERTA, notificacao.getTipoAlerta().toString());
            }
            context.sendBroadcast(i);
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
