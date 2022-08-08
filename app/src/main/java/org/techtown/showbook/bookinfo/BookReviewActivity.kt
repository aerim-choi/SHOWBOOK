package org.techtown.showbook.bookinfo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide


import org.techtown.showbook.bookinfo.model.Book
import org.techtown.showbook.lectureinfo.model.Lecture
import org.techtown.showbook.databinding.ActivityBookinfoReviewBinding

class BookReviewActivity :AppCompatActivity(){
    private lateinit var binding:ActivityBookinfoReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookinfoReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model = intent.getParcelableExtra<Book>("bookModel")

        binding.titleTextView.text = model?.title.orEmpty()
        binding.authorTextView.text = model?.author
        binding.publisherTextView.text = model?.publisher
        binding.priceTextView.text = "${model?.price}원"

        Glide.with(binding.coverImageView.context)
            .load(model?.coverSmallUrl.orEmpty())
            .into(binding.coverImageView)

    }


}

