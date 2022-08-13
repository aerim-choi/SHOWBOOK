package org.techtown.showbook.mypage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.techtown.showbook.R
import org.techtown.showbook.databinding.ActivityLogin2Binding


class Login2Activity:AppCompatActivity() {


    private lateinit var binding: ActivityLogin2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogin2Binding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.signupBtn.setOnClickListener {
            val intent = Intent(this, Signup2Activity::class.java)
            startActivity(intent)
        }
    }
}