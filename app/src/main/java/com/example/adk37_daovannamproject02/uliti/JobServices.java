package com.example.adk37_daovannamproject02.uliti;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.CallLog;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

@SuppressLint("Registered")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobServices extends android.app.job.JobService {
    private static final String TAG = "JobServices";
    public static final int JOB_ID = 1001;
    NotificationHelper notificationHelper;
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");
        //Gán công việc ở đây

        notificationHelper = new NotificationHelper(getApplicationContext());
        notificationHelper.createNotification("Tile","Mess");

        Log.d(TAG, "Job finished");
        jobFinished(params, false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        return true;
    }

}
