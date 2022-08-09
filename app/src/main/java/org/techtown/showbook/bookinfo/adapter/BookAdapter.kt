package org.techtown.showbook.bookinfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.showbook.databinding.ItemBookBinding
import org.techtown.showbook.bookinfo.model.Book

class BookAdapter(private val itemClickedListener:(Book) -> Unit):ListAdapter<Book, BookAdapter.BookItemViewHolder>(
    diffUtil
){

    inner class BookItemViewHolder(private val binding: ItemBookBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(bookModel: Book){
            binding.titleTextView.text = bookModel.title
            binding.authorTextView.text = bookModel.author
            binding.publisherTextView.text = bookModel.publisher
            binding.priceTextView.text = "${bookModel.price}원"

            binding.root.setOnClickListener {
                itemClickedListener(bookModel)
            }

            Glide.with(binding.coverImageView.context)
                .load(bookModel.coverSmallUrl)
                .into(binding.coverImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        return BookItemViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object{
        val diffUtil=object :DiffUtil.ItemCallback<Book>(){
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.title== newItem.title
            }

        }
    }
}