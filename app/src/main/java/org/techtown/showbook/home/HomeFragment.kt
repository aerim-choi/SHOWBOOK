package org.techtown.showbook.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.techtown.showbook.R
import org.techtown.showbook.bookinfo.BookSearchFragment
import org.techtown.showbook.bookinfo.api.BookService
import org.techtown.showbook.bookinfo.model.BestSellerDto
import org.techtown.showbook.bookinfo.model.BookComment
import org.techtown.showbook.databinding.ActivityAddArticleBinding.bind
import org.techtown.showbook.databinding.ActivityAllSplashBinding.bind
import org.techtown.showbook.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment: Fragment(R.layout.fragment_home)  {
    private var bookComment:MutableList<BookComment> =  mutableListOf()
    private var binding: FragmentHomeBinding? = null
    private lateinit var bookService: BookService

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeFragment = FragmentHomeBinding.bind(view)
        binding = homeFragment


        val retrofit = Retrofit.Builder()
            .baseUrl("https://book.interpark.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bookService = retrofit.create(BookService::class.java)

        bookService.getBestSellerBooks(getString(R.string.interParkAPIKey))
            .enqueue(object : Callback<BestSellerDto> {

                override fun onResponse(
                    call: Call<BestSellerDto>,
                    response: Response<BestSellerDto>
                ) {
                    //TODO 성공처리
                    if (response.isSuccessful.not()) {
                        return
                    }
                    //베스트 셀러 1위 이미지 띄우기
                   response.body()?.let {
                        Glide.with(binding!!.best1Image.context)
                            .load(it.books[0].coverLargeUrl)
                            .into(binding!!.best1Image)

                    }
                }

                override fun onFailure(call: Call<BestSellerDto>, t: Throwable) {
                    //TODO 실패처리
                }

            })


        val commentDB = Firebase.database.reference.child("comment").child("Hello Coding 한입에 쏙 파이썬")

        commentDB.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    val model = it.getValue(BookComment::class.java)
                    model ?:return
                    bookComment.add(model)
                    if(bookComment.size>=3){

                        binding!!.homeComment1.text = bookComment[0].contents
                        binding!!.homedate1.text="${bookComment[0].date} 수강생"
                        binding!!.homeComment2.text = bookComment[1].contents
                        binding!!.homedate2.text="${bookComment[1].date} 수강생"
                        binding!!.homeComment3.text = bookComment[2].contents
                        binding!!.homedate3.text="${bookComment[2].date} 수강생"

                    }
                    else{
                        binding!!.homeComment1.text = "감상평 없음"
                        binding!!.homeComment2.text = "감상평 없음"
                        binding!!.homeComment3.text = "감상평 없음"
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



    }


}