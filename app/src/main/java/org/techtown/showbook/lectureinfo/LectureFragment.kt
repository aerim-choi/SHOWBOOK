package org.techtown.showbook.lectureinfo

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.showbook.R
import org.techtown.showbook.bookinfo.MyBookActivity
import org.techtown.showbook.databinding.FragmentLectureInfoMainBinding
import org.techtown.showbook.lectureinfo.adpater.LectureAdapter
import org.techtown.showbook.lectureinfo.model.Lecture

class LectureFragment : Fragment(R.layout.fragment_lecture_info_main) {

    private var binding: FragmentLectureInfoMainBinding? = null
    private lateinit var adapter: LectureAdapter
    companion object{
        var lectures:ArrayList<Lecture> = arrayListOf(
            Lecture("컴퓨터 네트워크","김규영","컴퓨터 네트워킹 : 하향식 접근"),
            Lecture("고급자료구조","홍의석","C++로 쉽게 풀어쓴 자료구조"),
            Lecture("고급자바프로그래밍(002)","이윤경","쉽게 배우는 자바 프로그래밍 = Java Programming"),
            Lecture("데이터베이스","박지웅","(MySQL로 배우는)데이터베이스 개론과 실습",),
            Lecture("미적분과 벡터해석 기초","연미정","미적분과 벡터해석 기초 with Python"),
            Lecture("인공지능","이재원","인공지능:개념 및 응용")
        )

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentLectureInfoMainBinding = FragmentLectureInfoMainBinding.bind(view)
        binding = fragmentLectureInfoMainBinding
        initLectureRecyclerView()
        val intent = Intent(activity, MyBookActivity::class.java)
        intent.putExtra("lectureModel",lectures)

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


