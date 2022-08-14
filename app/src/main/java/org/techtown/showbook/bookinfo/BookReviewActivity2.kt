package org.techtown.showbook.bookinfo

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil.setContentView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import org.techtown.showbook.R
import org.techtown.showbook.bookinfo.model.BookComment
import org.techtown.showbook.databinding.ActivityBookinfoReview2Binding
import java.sql.Timestamp
import java.util.concurrent.TimeUnit


class BookReviewActivity2 :AppCompatActivity() {
    private lateinit var binding: ActivityBookinfoReview2Binding
    private val auth : FirebaseAuth by lazy {
        Firebase.auth
    }
    private lateinit var usecommets:String
    private lateinit var helpcomments:String
    private lateinit var ratingcomments:String
    private lateinit var databaseRef:DatabaseReference
    private lateinit var title:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookinfoReview2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        title= intent.getStringExtra("title").toString()
        hideProgress()
        binding.usebook.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.usebookBtn-> usecommets="책 사용함"
                R.id.unusebookBtn-> usecommets= "책 사용안함"
            }
        }

        binding.helpbook.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.bookhelpBtn-> helpcomments = "책 도움됨"
                R.id.bookunhelpBtn-> helpcomments = "책 도움안됨"
            }
        }

        binding.bookstage.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.upstage-> ratingcomments = "상"
                R.id.midstage-> ratingcomments = "중"
                R.id.downstage-> ratingcomments = "하"
            }
        }


        binding.reviewSaveBtn.setOnClickListener {
            var comments:String = binding.bookReviewEditText.text.toString()
            saveComment(usecommets,helpcomments,ratingcomments,comments)
            showProgress()
            finish()
        }
        binding.closeBtn.setOnClickListener {
            finish()
        }
        databaseRef=FirebaseDatabase.getInstance().reference


    }
    fun saveComment(use:String, help:String,rating:String,comments:String){

        val key:String?=databaseRef.child("comments").push().key

        val comment=BookComment(key!!, auth.currentUser?.uid.orEmpty() ,"",rating,comments, use, help,0)

        val commentValues:HashMap<String,Any> = comment.toMap()
        commentValues["timestamp"]=ServerValue.TIMESTAMP

        val childUpdates:MutableMap<String,Any> = HashMap()
        childUpdates["/comment/${title}/$key"]=commentValues

        databaseRef.updateChildren(childUpdates)
    }
    private fun showProgress() {
        binding.progressBar.isVisible = true
    }

    private fun hideProgress() {
        binding.progressBar.isVisible = false
    }




}