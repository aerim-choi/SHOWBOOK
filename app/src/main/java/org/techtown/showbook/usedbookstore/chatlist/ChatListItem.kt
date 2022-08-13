package org.techtown.showbook.usedbookstore.chatlist

data class ChatListItem(
    val buyerId: String,
    val sellerID: String,
    val itemTitle:String,
    val key:Long
){
    constructor():this("","","",0)
}
