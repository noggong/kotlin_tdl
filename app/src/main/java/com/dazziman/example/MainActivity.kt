package com.dazziman.example

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("MainActivity.onCreate")
        btn0.setOnClickListener() {
            var intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn1.setOnClickListener() {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        println("MainActivity.onStart")
    }

    override fun onResume() {
        super.onResume()
        println("MainActivity.onResume")
    }

    override fun onPause() {
        super.onPause()
        println("MainActivity.onPause")
    }

    override fun onStop() {
        super.onStop()
        println("MainActivity.onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("MainActivity.onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        println("MainActivity.onRestart")
    }
}

