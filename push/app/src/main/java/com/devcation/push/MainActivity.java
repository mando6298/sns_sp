package com.devcation.push;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM",  task.getException());
                            return;
                        }

                        String newToken = task.getResult();
                        Log.d("FCM", newToken);

                        //Toast.makeText(MainActivity.this, newToken, Toast.LENGTH_SHORT).show();
                    }
                });

    }



//
//    @Override
//    protected void onNewIntent(Intent intent) {
//
//        if (intent != null) {
//            processIntent(intent);
//        }
//        super.onNewIntent(intent);
//    }
//
//    private void processIntent(Intent intent) {
//        String from = intent.getStringExtra("from");
//        if (from == null) {
//
//            return;
//        }
//
//        String contents = intent.getStringExtra("contents");
//        Log.d("FCM","DATA : " + from + ", " + contents);
//
//    }

}
