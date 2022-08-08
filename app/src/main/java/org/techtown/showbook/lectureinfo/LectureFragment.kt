package org.techtown.showbook.lectureinfo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.showbook.R
import org.techtown.showbook.bookinfo.AppDatabase
import org.techtown.showbook.bookinfo.BookReviewActivity
import org.techtown.showbook.bookinfo.adapter.BookAdapter
import org.techtown.showbook.bookinfo.adapter.HistoryAdapter
import org.techtown.showbook.bookinfo.api.BookService
import org.techtown.showbook.bookinfo.getAppDatabase
import org.techtown.showbook.bookinfo.model.BestSellerDto
import org.techtown.showbook.bookinfo.model.History
import org.techtown.showbook.bookinfo.model.SearchBookDto
import org.techtown.showbook.databinding.FragmentBookinfoMainBinding
import org.techtown.showbook.databinding.FragmentLectureInfoMainBinding
import org.techtown.showbook.lectureinfo.adpater.LectureAdapter
import org.techtown.showbook.lectureinfo.model.Lecture
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LectureFragment : Fragment(R.layout.fragment_lecture_info_main) {

    private var binding: FragmentLectureInfoMainBinding? = null
    private lateinit var adapter: LectureAdapter
    private var lectures:ArrayList<Lecture> = arrayListOf(
    Lecture("고급자료구조(001)","C++로 쉽게 풀어쓴 자료구조","홍의석"),
    Lecture("고급자바프로그래밍(001)","쉽게 배우는 자바 프로그래밍 = Java Programming","우종정"),
    Lecture("고급자바프로그래밍(002)","쉽게 배우는 자바 프로그래밍 = Java Programming","이윤경"),
    Lecture("프로젝트설계(001)","디자인 씽킹 for 컨셉노트","김규영")
    )
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentLectureInfoMainBinding = FragmentLectureInfoMainBinding.bind(view)
        binding = fragmentLectureInfoMainBinding
        initLectureRecyclerView()
        adapter.submitList(lectures)




    }
    private fun initLectureRecyclerView() {
        adapter = LectureAdapter (itemClickedListener = {
            // TODO

        })


        binding!!.lectureRecyclerView.layoutManager = LinearLayoutManager(context)
        binding!!.lectureRecyclerView.adapter = adapter
    }

}


