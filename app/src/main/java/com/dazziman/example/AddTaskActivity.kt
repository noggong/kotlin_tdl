package com.dazziman.example;

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY
import android.content.Context.INPUT_METHOD_SERVICE
import android.support.v4.content.ContextCompat.getSystemService
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.support.v4.content.ContextCompat.getSystemService
import android.R.attr.key
import android.support.v4.content.ContextCompat.getSystemService






class AddTaskActivity : AppCompatActivity() {

    lateinit var description : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        description = findViewById(R.id.task_description) as EditText
        val submit = findViewById(R.id.submit) as Button

        submit.setOnClickListener {
            if (description.text?.toString().isNullOrBlank()) {
                description.error = "내용을 입력해주세요."
            } else {
                val data = Intent()
                data.putExtra(MainActivity.DESCRIPTION_TEXT, description.text.toString())
                setResult(Activity.RESULT_OK, data)
                hideSoftKeyboard()

                finish()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyboard()
    }

    fun hideSoftKeyboard()
    {
        Log.e("dazziman", "hide")
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(description.getWindowToken(), 0)
    }

}
