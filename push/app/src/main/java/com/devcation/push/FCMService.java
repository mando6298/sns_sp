package com.devcation.push;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class FCMService extends FirebaseMessagingService {
    private static final String TAG = "FCM";
    private static final String CHANNEL_ID = "FCMTest";
    private static final String  CHANNEL_NAME = "test";

    public FCMService() {
    }

    @Override
    public void onNewToken(@NonNull  String s) {
        super.onNewToken(s);
        Log.e(TAG, "FirebaseInstanceIDService : " + s);

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Map<String, String> data = remoteMessage.getData();
        String pushData = data.get("pushData");
        Log.d(TAG, "pushData: " + pushData);

        String resMsg = data.get("resultMsg");
        Log.d(TAG, "resMsg: " + resMsg);

        //안드 화면 상단에 메세지 표시를 하기위한 알림
        //상단 알림은 NotificationCompat.Builder, NotificationManagerCompat 이용해 생성하고 등록

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //오레오 버전 이후 모든 알림은 채널을 할당

            if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
                //채널은 고유한 ID, 채널이름 , 중요도
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel); // 채널 등록
//                notificationManager.createNotificationChannel(channel); 채널 삭제
            }

            builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID); // 채널ID 등록
        }else {
            builder = new NotificationCompat.Builder(getApplicationContext());
        }

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        //알림 내용과 아이콘 설
        builder.setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_launcher_foreground);

        Notification notification = builder.build();
        notificationManager.notify(1, notification);


    }

}