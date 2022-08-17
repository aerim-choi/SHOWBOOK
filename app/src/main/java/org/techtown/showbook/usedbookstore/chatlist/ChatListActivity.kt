package org.techtown.showbook.usedbookstore.chatlist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.techtown.showbook.databinding.ActivityChatlistBinding
import org.techtown.showbook.usedbookstore.DBKey.Companion.CHILD_CHAT
import org.techtown.showbook.usedbookstore.DBKey.Companion.DB_USERS
import org.techtown.showbook.usedbookstore.chatdetail.ChatRoomActivity

class ChatListActivity: AppCompatActivity(){
    private lateinit var binding:ActivityChatlistBinding
    private lateinit var chatListAdapter:ChatListAdapter

    private val auth: FirebaseAuth by lazy{
        Firebase.auth
    }
    private val chatRoomList = mutableListOf<ChatListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =ActivityChatlistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatListAdapter =ChatListAdapter(onItemClicked = { chatRoom->
            //채팅방으로 이동하는 코드
            val intent = Intent(this, ChatRoomActivity::class.java)
            intent.putExtra("chatKey",chatRoom.key)
            startActivity(intent)
        })

        chatRoomList.clear()
        binding.chatListRecyclerView.adapter=chatListAdapter
        binding.chatListRecyclerView.layoutManager=LinearLayoutManager(this)
        binding.closeBtn.setOnClickListener {
            finish()
        }
        //로그인이 안되어있을 경우
        if(auth.currentUser==null){
            Log.d("로그인 안됨","로그인해주세요")
            return
        }
        val chatDB = Firebase.database.reference.child(DB_USERS).child(auth.currentUser!!.uid).child(CHILD_CHAT)
        chatDB.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    val model = it.getValue(ChatListItem::class.java)
                    model ?:return

                    chatRoomList.add(model)
                }
                chatListAdapter.submitList(chatRoomList)
                chatListAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })



    }
    override fun onResume() {
        super.onResume()
        chatListAdapter.notifyDataSetChanged() //뷰 갱신
    }
}