package org.techtown.showbook.usedbookstore.chatdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.techtown.showbook.databinding.ItemChatBinding
import java.text.SimpleDateFormat
import java.util.*


class ChatItemAdapter: ListAdapter<ChatItem, ChatItemAdapter.ViewHolder>(
    diffUtil
) {

    inner class ViewHolder(private val binding : ItemChatBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(chatItem: ChatItem){
            binding.timeTextView.text = getTime()
            binding.messageTextView.text = chatItem.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemChatBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<ChatItem>(){
            override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean {
                return oldItem == newItem
            }

        }
    }
    private fun getTime() :String{
        val now = System.currentTimeMillis();
        val date:Date = Date(now);
        val dateFormat= SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        val getTime:String = dateFormat.format(date);

        return getTime;
    }

}
