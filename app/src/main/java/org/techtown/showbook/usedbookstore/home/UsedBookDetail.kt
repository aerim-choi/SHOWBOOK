package org.techtown.showbook.usedbookstore.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.techtown.showbook.databinding.ActivityBookinfoReviewBinding

import org.techtown.showbook.databinding.ActivityUsedbookdetailBinding
import org.techtown.showbook.mypage.Signup2Activity
import org.techtown.showbook.usedbookstore.DBKey
import org.techtown.showbook.usedbookstore.DBKey.Companion.CHILD_CHAT
import org.techtown.showbook.usedbookstore.DBKey.Companion.DB_CHATS
import org.techtown.showbook.usedbookstore.chatlist.ChatListActivity
import org.techtown.showbook.usedbookstore.chatlist.ChatListItem

class UsedBookDetail : AppCompatActivity() {

    private lateinit var binding: ActivityUsedbookdetailBinding
    private lateinit var articleDB : DatabaseReference
    private lateinit var userDB : DatabaseReference
    private val auth : FirebaseAuth by lazy {
        Firebase.auth
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUsedbookdetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userDB = Firebase.database.reference.child(DBKey.DB_USERS)

        articleDB = Firebase.database.reference.child(DBKey.DB_ARTICLES)

        binding.usedBookDescription.text = intent.getStringExtra("중고책 상세글")
        binding.usedBookTitleTextView.text =intent.getStringExtra("책이름")
        binding.detailLectureName.text = "강의명 : ${intent.getStringExtra("강의 이름")}"
        binding.detailbookPrice.text= intent.getStringExtra("중고책 가격")
        binding.detailbookCondition.text= "책 상태 : ${intent.getStringExtra("책상태")}"
        binding.detailbookBuy.text= "구매시기 : ${intent.getStringExtra("구매시기") }이내"
        binding.detailbookWrite.text = "필기여부 : ${intent.getStringExtra("필기여부")}"

        val sellerId = intent.getStringExtra("sellerId")
        val title=intent.getStringExtra("title")

        binding.sellerId.text = sellerId

        val detailImageUrl = intent.getStringExtra("중고책사진")
        if (detailImageUrl != null) {
            if(detailImageUrl.isNotEmpty()){
                Glide.with(binding.usedBookImageView)
                    .load(detailImageUrl)
                    .into(binding.usedBookImageView)

            }
        }
        binding.usedBookChatRoomBtn.setOnClickListener {
            val intent = Intent(this,ChatListActivity::class.java)
            if (auth.currentUser?.uid != sellerId ) {
                    //채팅 방 만들기
                    val chatRoom = ChatListItem(
                        buyerId = auth.currentUser!!.uid,
                        sellerID = sellerId!!,
                        itemTitle = title!!,
                        key = System.currentTimeMillis()
                    )
                    userDB.child(auth.currentUser!!.uid)
                        .child(CHILD_CHAT)
                        .push()
                        .setValue(chatRoom)

                if (sellerId != null) {
                    userDB.child(sellerId)
                        .child(CHILD_CHAT)
                        .push()
                        .setValue(chatRoom)
                }
                    Toast.makeText(applicationContext, "채팅방이 생성되었습니다. 채팅탭에서 확인해주세요.", Toast.LENGTH_SHORT).show()
                }else{
                    //내가 올린 아이템
                     Toast.makeText(applicationContext, "내가 올린 아이템 입니다.", Toast.LENGTH_SHORT).show()
                }


            startActivity(intent)
        }
    }

}