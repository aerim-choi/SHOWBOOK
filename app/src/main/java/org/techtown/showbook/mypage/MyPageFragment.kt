package org.techtown.showbook.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.techtown.showbook.R
import org.techtown.showbook.databinding.FragmentMypageBinding

class MyPageFragment: Fragment(R.layout.fragment_mypage) {
    private var binding: FragmentMypageBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentMyPageBinding = FragmentMypageBinding.bind(view)
        binding = fragmentMyPageBinding


        binding!!.loginBtn1.setOnClickListener {
            val intent = Intent(activity, Login2Activity::class.java)
            startActivity(intent)
        }



    }
}