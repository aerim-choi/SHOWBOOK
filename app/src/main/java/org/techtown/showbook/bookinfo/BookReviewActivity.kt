package org.techtown.showbook.bookinfo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import org.techtown.showbook.R


import org.techtown.showbook.bookinfo.model.Book
import org.techtown.showbook.bookinfo.model.Lecture
import org.techtown.showbook.databinding.ActivityBookinfoReviewBinding

class BookReviewActivity :AppCompatActivity(){
    private lateinit var binding:ActivityBookinfoReviewBinding
    private lateinit var lectures:ArrayList<Lecture>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookinfoReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lectures.add(Lecture("고급자료구조(001)","C++로 쉽게 풀어쓴 자료구조","홍의석"))
        lectures.add(Lecture("고급자바프로그래밍(001)","쉽게 배우는 자바 프로그래밍 = Java Programming","우종정"))
        lectures.add(Lecture("고급자바프로그래밍(002)","쉽게 배우는 자바 프로그래밍 = Java Programming","이윤경"))
        lectures.add(Lecture("프로젝트설계(001)","디자인 씽킹 for 컨셉노트","김규영"))

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

