package org.techtown.showbook.bookinfo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.showbook.R
import org.techtown.showbook.bookinfo.adapter.BookAdapter
import org.techtown.showbook.bookinfo.adapter.HistoryAdapter
import org.techtown.showbook.bookinfo.api.BookService
import org.techtown.showbook.bookinfo.model.BestSellerDto
import org.techtown.showbook.bookinfo.model.History
import org.techtown.showbook.bookinfo.model.SearchBookDto
import org.techtown.showbook.databinding.FragmentBookinfoMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookSearchFragment : Fragment(R.layout.fragment_bookinfo_main) {

    private var binding: FragmentBookinfoMainBinding? = null
    private lateinit var adapter: BookAdapter
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var bookService: BookService
    private lateinit var db: AppDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentBookinfoMainBinding = FragmentBookinfoMainBinding.bind(view)
        binding = fragmentBookinfoMainBinding

        db = getAppDatabase(context)

        initBookRecyclerView()
        initHistoryRecyclerView()
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
                    response.body()?.let {
//                        Log.d(TAG, it.toString())
//                        it.books.forEach { book ->
//                            Log.d(TAG, book.toString())
//                        }
                        adapter.submitList(it.books)
                    }
                }

                override fun onFailure(call: Call<BestSellerDto>, t: Throwable) {
                    //TODO 실패처리
                    Log.e(TAG, t.toString())
                }

            })


    }

    private fun search(keyword: String) {
        bookService.getBooksByName(getString(R.string.interParkAPIKey), keyword)
            .enqueue(object : Callback<SearchBookDto> {

                override fun onResponse(
                    call: Call<SearchBookDto>,
                    response: Response<SearchBookDto>
                ) {
                    //TODO 성공처리

                    hideHistoryView()
                    saveSearchKeyword(keyword)

                    if (response.isSuccessful.not()) {
                        return
                    }
                    response.body()?.let {
                        adapter.submitList(response.body()?.books.orEmpty())
                    }
                }

                override fun onFailure(call: Call<SearchBookDto>, t: Throwable) {
                    //TODO 실패처리
                    hideHistoryView()
                    Log.e(TAG, t.toString())
                }

            })
    }

    private fun initBookRecyclerView() {
        adapter = BookAdapter(itemClickedListener = {
            val intent= Intent(activity,BookReviewActivity::class.java)
            intent.putExtra("bookModel",it)
            startActivity(intent)

        })


        binding!!.bookRecyclerView.layoutManager = LinearLayoutManager(context)
        binding!!.bookRecyclerView.adapter = adapter
    }

    private fun showHistoryView() {
        Thread {
            val keywords = db.historyDao().getAll().reversed() //최신순으로 reverse
            activity?.runOnUiThread{
            binding!!.historyRecyclerView.isVisible = true
            historyAdapter.submitList(keywords.orEmpty())
            }
        }.start()
        binding!!.historyRecyclerView.isVisible = true
    }

    private fun hideHistoryView() {
        binding!!.historyRecyclerView.isVisible = false
    }

    private fun initHistoryRecyclerView() {
        historyAdapter = HistoryAdapter(historyDeleteClickedListener = {
            deleteSearchKeyword(it)
        })
        binding!!.historyRecyclerView.layoutManager = LinearLayoutManager(context)
        binding!!.historyRecyclerView.adapter = historyAdapter
        initSearchEditText()
    }

    private fun initSearchEditText() {
        binding!!.searchEditText.setOnKeyListener { view, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN) {
                search(binding!!.searchEditText.text.toString())
                return@setOnKeyListener true
            }
            return@setOnKeyListener false

        }
        binding!!.searchEditText.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                showHistoryView()
            }
            return@setOnTouchListener false
        }
    }

    private fun saveSearchKeyword(keyword: String) {
        Thread {
            db.historyDao().insertHistory(History(null, keyword))
        }.start()
    }

    private fun deleteSearchKeyword(keyword: String) {
        Thread {
            db.historyDao().delete(keyword)
            showHistoryView()
        }.start()
    }

    companion object {
        private const val TAG = "BookSearchFragment"

    }

}


