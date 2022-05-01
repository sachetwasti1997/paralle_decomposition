package com.sachet.paralleldecomposition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CoroutineScope(IO).launch {
//            val stock1 = getStock1() //takes 10 seconds
//            val stock2 = getStock2() //takes 8 seconds
//            val total = stock1 + stock2
//            Log.i("TAGReturnTotal", "onCreate: $total") // this will take a total of 18 seconds
            val stock1 = async { getStock1() } //takes 10 seconds
            val stock2 = async { getStock2() } //takes 8 seconds
            val total = stock1.await() + stock2.await()
            Log.i("TAGReturnTotal", "onCreate: $total") // takes 10 seconds
        }

        CoroutineScope(Main).launch {
            val stock1 = async(IO) { getStock1() } //takes 10 seconds
            val stock2 = async(IO) { getStock2() } //takes 8 seconds
            val total = stock1.await() + stock2.await()
            Toast.makeText(applicationContext, "Total is $total", Toast.LENGTH_LONG).show()
        }
    }

    private suspend fun getStock1() : Int{
        delay(10000)
        Log.i("TAGStock1", "getStock1: returning")
        return 5500
    }

    private suspend fun getStock2() : Int{
        delay(8000)
        Log.i("TAGStock2", "getStock2: returning")
        return 5500
    }

}