package org.techtown.showbook.usedbookstore.home

data class ArticleModel(

    var sellerId : String,
    val title : String,
    val createdAt : Long,
    val price : String,
    val imageUrl : String,
    val selldescription:String,
    val bookCondition:String,
    val buyDay:String,
    val writeCondition:String
){
    constructor():this("","",0,"","","","","","")
}
