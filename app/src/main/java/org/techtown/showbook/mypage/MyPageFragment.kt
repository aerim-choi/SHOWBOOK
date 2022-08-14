package org.techtown.showbook.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.techtown.showbook.MainActivity
import org.techtown.showbook.R
import org.techtown.showbook.bookinfo.MyBookActivity
import org.techtown.showbook.databinding.FragmentMypageBinding
import org.techtown.showbook.lectureinfo.LectureFragment

class MyPageFragment: Fragment(R.layout.fragment_mypage) {
    private var binding: FragmentMypageBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentMyPageBinding = FragmentMypageBinding.bind(view)
        binding = fragmentMyPageBinding

        binding!!.myLectureBtn.setOnClickListener {
            val mainActivity = activity as MainActivity
            mainActivity.replaceFragment(LectureFragment())
        }
        binding!!.myBookList.setOnClickListener{
            val intent = Intent(activity, MyBookActivity::class.java)
            startActivity(intent)
        }

    }
}