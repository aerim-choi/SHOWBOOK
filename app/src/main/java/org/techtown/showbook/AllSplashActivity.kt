package org.techtown.showbook

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import org.techtown.showbook.splash.Splash1Fragment
import org.techtown.showbook.splash.Splash2Fragment
import org.techtown.showbook.splash.Splash3Fragment
import org.techtown.showbook.splash.Splash4Fragment


class AllSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_splash)

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)

        val fragments: ArrayList<Fragment> = arrayListOf(
            Splash1Fragment(),
            Splash2Fragment(),
            Splash3Fragment(),
            Splash4Fragment()
            )

        val adapter = ViewPagerAdapter(fragments, this)
        viewPager.adapter = adapter

    }
}