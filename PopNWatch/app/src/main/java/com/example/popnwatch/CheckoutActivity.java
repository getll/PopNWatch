package com.example.popnwatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CheckoutActivity extends AppCompatActivity {
    TextView totalTextView;
    Button payButton;
    CartDb cartDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        cartDb = new CartDb(getApplicationContext());

        totalTextView = findViewById(R.id.totalTextView);
        payButton = findViewById(R.id.payButton);

        Intent intent = getIntent();

        String cartId = intent.getStringExtra("cartId");
        double total = intent.getDoubleExtra("total", 0);

        totalTextView.setText(String.format("Total: %.2f", total));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("CheckoutNotification", "Checkout Notification Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartDb.checkoutCart(cartId)) {
                    Toast.makeText(CheckoutActivity.this, "Payment Made.", Toast.LENGTH_SHORT).show();

                    sendNotification();
                    finish();
                }
                else {
                    Toast.makeText(CheckoutActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(CheckoutActivity.this, "CheckoutNotification");
        builder.setContentTitle("PopNWatch");
        builder.setContentText("Checkout Complete! Click here to view all tickets.");
        builder.setSmallIcon(R.drawable.popnwatch);
        builder.setAutoCancel(false);

        Intent notificationIntent = new Intent(CheckoutActivity.this, PastCartActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(CheckoutActivity.this,0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}