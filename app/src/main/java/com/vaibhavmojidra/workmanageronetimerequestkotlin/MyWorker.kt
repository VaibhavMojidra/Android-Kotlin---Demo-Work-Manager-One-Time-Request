package com.vaibhavmojidra.workmanageronetimerequestkotlin

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*

class MyWorker(context: Context,params:WorkerParameters): Worker(context,params) {
    override fun doWork(): Result {
        return try {
            val totalSeconds=inputData.getInt("Total_Seconds",0) // getting Input Data
            for(i in 0..totalSeconds){
                Thread.sleep(1000)
                Log.i("MyTag","Uploading $i")
            }
            val time=SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate=time.format(Date())
            val outputData=Data.Builder() // defining Output Data
                .putString("TIME",currentDate)
                .build()
            Result.success(outputData) // setting Output Data
        }catch (e:Exception){
            Result.failure()
        }
    }
}