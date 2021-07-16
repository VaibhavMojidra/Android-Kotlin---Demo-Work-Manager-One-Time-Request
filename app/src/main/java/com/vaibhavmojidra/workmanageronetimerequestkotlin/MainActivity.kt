package com.vaibhavmojidra.workmanageronetimerequestkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.vaibhavmojidra.workmanageronetimerequestkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.startWork.setOnClickListener {
            oneTimeWorkRequest()
        }
    }

    private fun oneTimeWorkRequest() {
        val workManager=WorkManager.getInstance(applicationContext)     
        val data=Data.Builder() //Defining Input Data for worker
            .putInt("Total_Seconds",5)
            .build()

        val constraints=Constraints.Builder() //Defining Constraints
            .setRequiresCharging(true)
            .build()
        val oneTimeWorkRequest=OneTimeWorkRequest.Builder(MyWorker::class.java)
            .setConstraints(constraints) //Setting Constraints
            .setInputData(data) //Setting Input Data for Worker
            .build()
        workManager.enqueue(oneTimeWorkRequest)
        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id).observe(this,{ //Getting Status
            binding.status.text=it.state.name
            if(it.state.isFinished){
                val data=it.outputData
                val message=data.getString("TIME")
                Toast.makeText(this,message,Toast.LENGTH_LONG).show()
            }
        })
    }
}