package org.techtown.showbook.usedbookstore.chatlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.techtown.showbook.R
import org.techtown.showbook.databinding.FragmentChatlistBinding
import org.techtown.showbook.usedbookstore.DBKey.Companion.CHILD_CHAT
import org.techtown.showbook.usedbookstore.DBKey.Companion.DB_USERS
import org.techtown.showbook.usedbookstore.chatdetail.ChatRoomActivity

class ChatListFragment: Fragment(R.layout.fragment_chatlist) {
    private var binding:FragmentChatlistBinding?=null
    private lateinit var chatListAdapter:ChatListAdapter

    private val auth: FirebaseAuth by lazy{
        Firebase.auth
    }
    private val chatRoomList = mutableListOf<ChatListItem>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentChatlistBinding=FragmentChatlistBinding.bind(view)
        binding=fragmentChatlistBinding

        chatListAdapter =ChatListAdapter(onItemClicked = { chatRoom->
            //채팅방으로 이동하는 코드
            context?.let {  //프레그먼트 인텐트하는법 context?.let 쓴다.
                val intent = Intent(it, ChatRoomActivity::class.java)
                intent.putExtra("chatKey",chatRoom.key)
                startActivity(intent)
            }

        })

        chatRoomList.clear()
        fragmentChatlistBinding.chatListRecyclerView.adapter=chatListAdapter
        fragmentChatlistBinding.chatListRecyclerView.layoutManager=LinearLayoutManager(context)
        //로그인이 안되어있을 경우
        if(auth.currentUser==null){
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