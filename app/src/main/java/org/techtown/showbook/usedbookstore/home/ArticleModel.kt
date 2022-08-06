package org.techtown.showbook.usedbookstore.home

data class ArticleModel(

    var sellerId : String,
    val title : String,
    val createdAt : Long,
    val price : String,
    val imageUrl : String,
    val description:String,
    val bookCondition:Int,
    val buyDay:Int,
    val writeCondition:Int

){
    constructor():this("","",0,"","","",0,0,0)
}
