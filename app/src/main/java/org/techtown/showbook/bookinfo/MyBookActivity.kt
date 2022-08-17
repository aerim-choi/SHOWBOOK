package org.techtown.showbook.bookinfo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.annotations.SerializedName
import org.techtown.showbook.R
import org.techtown.showbook.bookinfo.adapter.BookAdapter
import org.techtown.showbook.bookinfo.api.BookService
import org.techtown.showbook.bookinfo.model.Book
import org.techtown.showbook.bookinfo.model.SearchBookDto
import org.techtown.showbook.databinding.ActivityMybookBinding
import org.techtown.showbook.lectureinfo.LectureFragment.Companion.lectures
import org.techtown.showbook.lectureinfo.model.Lecture
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.thread

class MyBookActivity:AppCompatActivity (){
    private lateinit var binding: ActivityMybookBinding
    private lateinit var lectureBooks:ArrayList<Lecture>
    private var bookList:ArrayList<Book> = arrayListOf()
    private lateinit var book: Book
    private lateinit var bookService: BookService
    private lateinit var adapter: BookAdapter
    private var idx:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMybookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://book.interpark.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        bookService = retrofit.create(BookService::class.java)

        for(lecture in lectures){
            search(lecture.bookName.toString())
            Log.d("안녕",lecture.bookName.toString())
        }
        binding.closeBtn.setOnClickListener {
            finish()
        }
        initLectureBookRecyclerView()


    }

    private fun initLectureBookRecyclerView() {

        adapter = BookAdapter(itemClickedListener = {
            val intent= Intent(this,BookReviewActivity::class.java)
            intent.putExtra("bookModel",it)
            startActivity(intent)

        })
        binding!!.lectureBookRecyclerView.layoutManager = LinearLayoutManager(this)
        binding!!.lectureBookRecyclerView.adapter = adapter

    }

    private fun search(keyword: String) {
        thread {

            bookService.getBooksByName(getString(R.string.interParkAPIKey), keyword)
                .enqueue(object : Callback<SearchBookDto> {

                    override fun onResponse(
                        call: Call<SearchBookDto>,
                        response: Response<SearchBookDto>
                    ) {
                        //TODO 성공처리



                        if (response.isSuccessful.not()) {
                            return
                        }
                        response.body()?.let {

                            if(it.books.isNotEmpty()){
                                bookList.add(it.books[0])
                                adapter.submitList(bookList)
                            }
                            else{
                                book=Book(0,keyword,"","교보문고 검색 불가","0","","","","")
                                bookList.add(book)
                                adapter.submitList(bookList)
                            }

                        }
                    }

                    override fun onFailure(call: Call<SearchBookDto>, t: Throwable) {
                        //TODO 실패처리

                        Log.e("BookSearchFragment", t.toString())
                    }

                })

        }

    }





}

