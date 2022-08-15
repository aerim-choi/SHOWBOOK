package org.techtown.showbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import nl.joery.animatedbottombar.AnimatedBottomBar
import org.techtown.showbook.usedbookstore.home.UsedBookHomeFragment
import org.techtown.showbook.bookinfo.BookSearchFragment
import org.techtown.showbook.home.HomeFragment
import org.techtown.showbook.lectureinfo.LectureFragment
import org.techtown.showbook.mypage.MyPageFragment

public class MainActivity : AppCompatActivity() {
    private lateinit var email: String
    private lateinit var id: String
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment=HomeFragment()
        val lectureFragment=LectureFragment()
        val bookSearchFragment = BookSearchFragment()
        val usedBookHomeFragment = UsedBookHomeFragment()
        val myPageFragment=MyPageFragment()
        val bottomNavigationView = findViewById<AnimatedBottomBar>(R.id.bottom_bar)
        auth = Firebase.auth
        replaceFragment(homeFragment)
        bottomNavigationView.selectTab(bottomNavigationView.tabs[0],true)


        var emailtemp = auth.currentUser?.email?.split('@')
        email = auth.currentUser?.email.toString()
        id = emailtemp?.get(0).toString()

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
                    1->replaceFragment(lectureFragment)
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
    public fun getEmail():String{
        return email
    }
    public fun getId():String{
        return id
    }

    public fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.fragmentContainer, fragment)
                commit()
            }
    }
}


