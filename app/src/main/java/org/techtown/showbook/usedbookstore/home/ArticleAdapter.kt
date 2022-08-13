package org.techtown.showbook.usedbookstore.home


import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import org.techtown.showbook.databinding.ItemArticleBinding
import org.techtown.showbook.lectureinfo.model.Lecture
import org.techtown.showbook.usedbookstore.chatdetail.ChatRoomActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.coroutineContext

class ArticleAdapter(val onItemClicked: (ArticleModel)->Unit) : ListAdapter<ArticleModel, ArticleAdapter.ViewHolder>(
    diffUtil
) {

    inner class ViewHolder(private val binding : ItemArticleBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(articleModel: ArticleModel){

            val format = SimpleDateFormat("MM월 dd일")
            val date = Date(articleModel.createdAt)

            binding.titleTextView.text = articleModel.title
            binding.dateTextView.text = format.format(date).toString()
            binding.priceTextView.text = articleModel.price
            binding.bookCondition.text = "책상태: ${articleModel.bookCondition}"
            binding.writeCondition.text ="|필기여부: ${articleModel.writeCondition}"

            if(articleModel.imageUrl.isNotEmpty()){
                Glide.with(binding.thumbnailImageView)
                    .load(articleModel.imageUrl)
                    .into(binding.thumbnailImageView)

            }
            binding.root.setOnClickListener{
                onItemClicked(articleModel)
            }




        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemArticleBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])

        holder.itemView.setOnClickListener { v->

            var intent = Intent(holder.itemView?.context, UsedBookDetail::class.java)
            intent.putExtra("sellerId",currentList[position].sellerId)
            intent.putExtra("title",currentList[position].title)
            intent.putExtra("중고책 가격",currentList[position].price)
            intent.putExtra("책상태",currentList[position].bookCondition)
            intent.putExtra("구매시기",currentList[position].buyDay)
            intent.putExtra("필기여부",currentList[position].writeCondition)
            intent.putExtra("중고책사진",currentList[position].imageUrl)
            intent.putExtra("중고책 상세글",currentList[position].sellDescription)
            intent.putExtra("책이름",currentList[position].bookName)
            intent.putExtra("강의 이름",currentList[position].lectureName)

            v.context.startActivity(intent)
        }
    }

    companion object{
        val diffUtil = object :DiffUtil.ItemCallback<ArticleModel>(){
            override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem.createdAt == newItem.createdAt
            }

            override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}

