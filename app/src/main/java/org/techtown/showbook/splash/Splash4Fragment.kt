package org.techtown.showbook.splash

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.techtown.showbook.MainActivity
import org.techtown.showbook.R
import org.techtown.showbook.auth.IntroActivity
import org.techtown.showbook.databinding.FragmentSplash4Binding
import org.techtown.showbook.databinding.FragmentUsedbookstorehomeBinding
class Splash4Fragment : Fragment(R.layout.fragment_splash4) {

    private var binding:FragmentSplash4Binding?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentSplash4Binding= FragmentSplash4Binding.bind(view)

        binding = fragmentSplash4Binding
        binding!!.goBtn.setOnClickListener {
            val intent = Intent(activity, IntroActivity::class.java)
            startActivity(intent)
        }

    }





}