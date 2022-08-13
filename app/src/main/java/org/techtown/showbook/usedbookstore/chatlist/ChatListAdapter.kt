package org.techtown.showbook.usedbookstore.chatlist


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import org.techtown.showbook.databinding.ItemArticleBinding
import org.techtown.showbook.databinding.ItemChatListBinding
import java.text.SimpleDateFormat
import java.util.*

class ChatListAdapter(val onItemClicked: (ChatListItem)->Unit) : ListAdapter<ChatListItem, ChatListAdapter.ViewHolder>(
    diffUtil
) {

    inner class ViewHolder(private val binding : ItemChatListBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(chatListItem: ChatListItem){


            binding.root.setOnClickListener{
                onItemClicked(chatListItem)

            }
            binding.chatRoomTitleTextView.text=chatListItem.itemTitle //물건 이름
            binding.sellerIdTextView.text=chatListItem.sellerID //판매자

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChatListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil = object :DiffUtil.ItemCallback<ChatListItem>(){
            override fun areItemsTheSame(oldItem: ChatListItem, newItem: ChatListItem): Boolean {
                return oldItem.sellerID == newItem.sellerID
            }

            override fun areContentsTheSame(oldItem: ChatListItem, newItem: ChatListItem): Boolean {
                return oldItem.sellerID == newItem.sellerID
            }

        }
    }
}

