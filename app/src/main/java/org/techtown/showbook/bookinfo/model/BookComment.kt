package org.techtown.showbook.bookinfo.model

import com.google.firebase.database.Exclude
import java.lang.reflect.Constructor
import java.sql.Timestamp
//firebase에 map형태로 저장
data class BookComment(
    var objectId:String?=null,
    var author:String?=null,
    var date:String?=null,
    var rating:String?=null,
    var contents:String?=null,
    var use:String?=null,
    var help:String?=null,
    var timestamp:Long=0

)
{

    @Exclude
    fun toMap():HashMap<String,Any>{
        val result:HashMap<String,Any> = HashMap()
        result["objectId"]=objectId!!
        result["author"]=author!!
        result["date"]=date!!
        result["rating"]=rating!!
        result["contents"]=contents!!
        result["use"]=use!!
        result["help"]=help!!
        result["timestamp"]=timestamp

        return result
    }
}
