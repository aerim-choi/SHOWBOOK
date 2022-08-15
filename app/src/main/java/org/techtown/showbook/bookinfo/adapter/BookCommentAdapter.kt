package org.techtown.showbook.bookinfo.adapter

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import org.techtown.showbook.bookinfo.model.BookComment

import org.techtown.showbook.databinding.ItemBookcommentBinding


class BookCommentAdapter(private val itemClickedListener:(BookComment) -> Unit):
    ListAdapter<BookComment, BookCommentAdapter.BookCommentItemViewHolder>(
    diffUtil
){

    inner class BookCommentItemViewHolder(private val binding: ItemBookcommentBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(bookCommentModel: BookComment){
            binding.date.text="수강학기 : ${bookCommentModel.date}"
            binding.bookUseTextView.text="강의에서 책 사용 여부 : ${bookCommentModel.use}"
            binding.bookHelpTextView.text="강의에서 책 도움 여부 : ${bookCommentModel.help}"
            binding.bookReviewCommentTextView.text=bookCommentModel.contents
            binding.bookStageTextView.text = "책 난이도 : ${bookCommentModel.rating}"

            binding.root.setOnClickListener {
                itemClickedListener(bookCommentModel)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookCommentItemViewHolder {
        return BookCommentItemViewHolder(ItemBookcommentBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: BookCommentItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil=object : DiffUtil.ItemCallback<BookComment>(){
            override fun areItemsTheSame(oldItem: BookComment, newItem: BookComment): Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(oldItem: BookComment, newItem: BookComment): Boolean {
                return oldItem == newItem
            }

        }
    }
}