package org.techtown.showbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.techtown.showbook.usedbookstore.home.UsedBookHomeFragment
import org.techtown.showbook.bookinfo.BookSearchFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bookSearchFragment = BookSearchFragment()
        val usedBookHomeFragment = UsedBookHomeFragment()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        replaceFragment(bookSearchFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home ->replaceFragment(bookSearchFragment)
                R.id.evaluation->replaceFragment(bookSearchFragment)
                R.id.search->replaceFragment(bookSearchFragment)
                R.id.usedbookstore->replaceFragment(usedBookHomeFragment)
                R.id.myPage->replaceFragment(bookSearchFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction()
            .apply{
                replace(R.id.fragmentContainer,fragment)
                commit()
            }
    }

}