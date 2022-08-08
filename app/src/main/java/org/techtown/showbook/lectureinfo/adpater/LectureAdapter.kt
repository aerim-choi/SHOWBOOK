package org.techtown.showbook.lectureinfo.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import org.techtown.showbook.databinding.ItemLectureBinding
import org.techtown.showbook.lectureinfo.model.Lecture

class LectureAdapter(private val itemClickedListener:(Lecture) -> Unit):
    ListAdapter<Lecture, LectureAdapter.LectureItemViewHolder>(
    diffUtil
){

    inner class LectureItemViewHolder(private val binding: ItemLectureBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(lectureModel: Lecture){
            binding.lectureNameTextView.text =  lectureModel.lecName


            binding.root.setOnClickListener {
                itemClickedListener(lectureModel)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LectureItemViewHolder {
        return LectureItemViewHolder(ItemLectureBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: LectureItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil=object : DiffUtil.ItemCallback<Lecture>(){
            override fun areItemsTheSame(oldItem: Lecture, newItem: Lecture): Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(oldItem: Lecture, newItem: Lecture): Boolean {
                return oldItem.lecName == newItem.lecName
            }

        }
    }
}