package org.techtown.showbook.bookinfo.model

import com.google.firebase.database.Exclude
import java.sql.Timestamp
//firebase에 map형태로 저장
data class BookComment(
    var objectId:String,
    var author:String,
    var date:String,
    var rating:String,
    var contents:String,
    var use:String,
    var help:String,

){
    @Exclude
    fun toMap():HashMap<String,Any>{
        val result:HashMap<String,Any> = HashMap()
        result["objectId"]=objectId
        result["author"]=author
        result["date"]=date
        result["rating"]=rating
        result["contents"]=contents
        result["use"]=use
        result["help"]=help

        return result
    }
}
