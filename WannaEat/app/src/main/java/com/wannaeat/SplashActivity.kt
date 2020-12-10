package com.wannaeat

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.wannaeat.fragments.MapFragment


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)

        supportActionBar?.hide();

        Handler().postDelayed({
            val intent= Intent(this@SplashActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        },1000)
    }
}