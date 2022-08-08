package org.techtown.showbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import nl.joery.animatedbottombar.AnimatedBottomBar
import org.techtown.showbook.usedbookstore.home.UsedBookHomeFragment
import org.techtown.showbook.bookinfo.BookSearchFragment
import org.techtown.showbook.home.HomeFragment
import org.techtown.showbook.mypage.MyPageFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment=HomeFragment()
        val bookSearchFragment = BookSearchFragment()
        val usedBookHomeFragment = UsedBookHomeFragment()
        val myPageFragment=MyPageFragment()
        val bottomNavigationView = findViewById<AnimatedBottomBar>(R.id.bottom_bar)

        replaceFragment(homeFragment)
        bottomNavigationView.selectTab(bottomNavigationView.tabs[0],true)


        bottomNavigationView.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {

            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                Log.d("bottom_bar", "Selected index: $newIndex, title: ${newTab.title}")
                when(newIndex){
                    0->replaceFragment(homeFragment)
                    1->replaceFragment(bookSearchFragment)
                    2->replaceFragment(bookSearchFragment)
                    3->replaceFragment(usedBookHomeFragment)
                    4->replaceFragment(myPageFragment)
                }
            }

            // An optional method that will be fired whenever an already selected tab has been selected again.
            override fun onTabReselected(index: Int, tab: AnimatedBottomBar.Tab) {
                Log.d("bottom_bar", "Reselected index: $index, title: ${tab.title}")

            }
        })


    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragmentContainer, fragment)
                commit()
            }
    }
}


