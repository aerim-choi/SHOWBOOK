package org.techtown.showbook.bookinfo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.google.firebase.database.DatabaseReference
import org.techtown.showbook.databinding.ActivityBookinfoReview2Binding


class BookReviewActivity2 :AppCompatActivity() {
    private lateinit var binding: ActivityBookinfoReview2Binding

    private lateinit var databaseRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookinfoReview2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reviewSaveBtn.setOnClickListener {



        }

    }

}