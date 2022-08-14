package org.techtown.showbook.bookinfo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import org.techtown.showbook.auth.IntroActivity
import org.techtown.showbook.bookinfo.adapter.BookCommentAdapter


import org.techtown.showbook.bookinfo.model.Book
import org.techtown.showbook.bookinfo.model.BookComment
import org.techtown.showbook.databinding.ActivityBookinfoReviewBinding

class BookReviewActivity :AppCompatActivity(){
    private lateinit var binding:ActivityBookinfoReviewBinding
    private lateinit var adapter:BookCommentAdapter

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

        binding.closeBtn.setOnClickListener {
            finish()
        }

        initView()

        binding.evaluationBtn.setOnClickListener {
            val intent = Intent(this, BookReviewActivity2::class.java)
            startActivity(intent)
        }



    }


    fun initView(){
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager.setReverseLayout(true)
        layoutManager.setStackFromEnd(true)
        binding.bookReviewRecyclerView.layoutManager = layoutManager

        adapter= BookCommentAdapter(itemClickedListener = {

        })



        binding.bookReviewRecyclerView.adapter = adapter
        var bookComments:MutableList<BookComment> = mutableListOf(
            BookComment("0", "sky2****", "1분전", "5.0", "정말 스릴넘치는 영화였어요. 한 번 더 보고 싶은 영화!!!",
                "사용됨", "도움됨"),
            BookComment(
                "1", "john****", "3분전", "4.0", "재미있어요.",
                "사용됨", "도움됨"))


        adapter.submitList(bookComments)


    }




}

