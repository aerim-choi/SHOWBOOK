package org.techtown.showbook.bookinfo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.techtown.showbook.bookinfo.adapter.BookCommentAdapter


import org.techtown.showbook.bookinfo.model.Book
import org.techtown.showbook.bookinfo.model.BookComment
import org.techtown.showbook.databinding.ActivityBookinfoReviewBinding
import org.techtown.showbook.usedbookstore.DBKey
import org.techtown.showbook.usedbookstore.chatdetail.ChatItem
import org.techtown.showbook.usedbookstore.chatlist.ChatListItem
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class BookReviewActivity :AppCompatActivity(){
    private lateinit var binding:ActivityBookinfoReviewBinding
    private lateinit var adapter:BookCommentAdapter
    private lateinit var databaseRef: DatabaseReference
    private val auth : FirebaseAuth by lazy {
        Firebase.auth
    }
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
        binding.detailTextView.text = model?.description

        binding.closeBtn.setOnClickListener {
            finish()
        }

        initView()
        //한줄평 작성하기
        binding.evaluationBtn.setOnClickListener {
            val intent = Intent(this, BookReviewActivity2::class.java)
            intent.putExtra("title",model?.title.toString())
            startActivity(intent)
        }
        //구매처로 이동
        binding.websiteGoBtn.setOnClickListener {
            val shopIntent =Intent(Intent.ACTION_VIEW, Uri.parse(model?.link))
            startActivity(shopIntent)
        }

        val commentDB = Firebase.database.reference.child("comment").child(model!!.title)

        commentDB.addListenerForSingleValueEvent(object:ValueEventListener{
            var bookComment:MutableList<BookComment> =  mutableListOf()
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    val model = it.getValue(BookComment::class.java)
                    model ?:return
                    bookComment.add(model)
                    adapter.submitList(bookComment)
                }
                binding.commentTextView.isVisible = bookComment.isEmpty()
                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })





    }



    fun initView(){
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        binding.bookReviewRecyclerView.layoutManager = layoutManager

        adapter= BookCommentAdapter(itemClickedListener = {

        })



        binding.bookReviewRecyclerView.adapter = adapter



    }



    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged() //뷰 갱신
    }

    override fun onRestart() {
        super.onRestart()
        adapter.notifyDataSetChanged() //뷰 갱신
    }


}

