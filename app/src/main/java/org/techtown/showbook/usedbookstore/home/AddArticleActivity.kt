package org.techtown.showbook.usedbookstore.home

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import org.techtown.showbook.R
import org.techtown.showbook.usedbookstore.DBKey.Companion.DB_ARTICLES

class AddArticleActivity:AppCompatActivity() {
    private var selectedUri: Uri? = null
    private val auth : FirebaseAuth by lazy {
        Firebase.auth
    }
    private val storage : FirebaseStorage by lazy{
        Firebase.storage
    }
    private val articleDB:DatabaseReference by lazy{
        Firebase.database.reference.child(DB_ARTICLES)
    }
    private lateinit var bookCondition: String
    private lateinit var buyDay:String
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_article)
        findViewById<Button>(R.id.backBtn).setOnClickListener {
            finish()
        }

         findViewById<RadioGroup>(R.id.bookCondition).setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.con1radioButton-> bookCondition="상"
                R.id.con2radioButton-> bookCondition= "중"
                R.id.con3radioButton-> bookCondition= "하"
            }

        }

        findViewById<RadioGroup>(R.id.bookBuyDay).setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.buy1radioButton-> buyDay = "1년"
                R.id.buy2radioButton-> buyDay = "3년"
                R.id.buy3radioButton-> buyDay = "5년"
            }

        }

        findViewById<Button>(R.id.imageAddButton).setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    startContentProvider()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    showPermissionContextPopup()
                }
                else -> {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        1010
                    )
                }

            }
            //아이템 등록
            findViewById<Button>(R.id.submitButton).setOnClickListener {
                val title = findViewById<EditText>(R.id.titleEditText).text.toString()
                val price = findViewById<EditText>(R.id.priceEditText).text.toString()
                val bookTitle = findViewById<EditText>(R.id.UsedBookATitleEditText).text.toString()
                val lectureTitle = findViewById<EditText>(R.id.lectureTitleEditText).text.toString()
                val sellerId = auth.currentUser?.uid.orEmpty()
                val sellDescription =
                    findViewById<EditText>(R.id.detailEditTextView).text.toString()



                val writeCondition =
                    findViewById<CheckBox>(R.id.bookWriteCon).isChecked.toString()


                showProgress()
                //중간에 이미지가 있으면 업로드 과정을 추가
                if (selectedUri != null) {
                    val photoUri = selectedUri ?: return@setOnClickListener
                    uploadPhoto(photoUri,
                        successHandler = { uri ->
                            //TODO book,writeCondition수정필요
                            uploadArticle(
                                sellerId,
                                title,
                                bookTitle,
                                lectureTitle,
                                price,
                                uri,
                                sellDescription,
                                bookCondition,
                                buyDay,
                                getWriteCon(writeCondition)
                            )

                        },
                        errorHandler = {
                            Toast.makeText(this, "사진 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show()
                            hideProgress()
                        }
                    )
                } else {//TODO book,writeCondition수정필요
                    uploadArticle(
                        sellerId,
                        title,
                        bookTitle,
                        lectureTitle,
                        price,
                        "",
                        sellDescription,
                        bookCondition,
                        buyDay,
                        getWriteCon(writeCondition)
                    )
                }

            }

        }
    }


        private fun getWriteCon(writeCondition:String):String{
            return when(writeCondition){
                "true"->"Y"
                else -> {"N"}
            }
        }
        private fun uploadPhoto(
            uri: Uri,
            successHandler: (String) -> Unit,
            errorHandler: () -> Unit
        ) {
            val fileName = " ${System.currentTimeMillis()}.png"
            storage.reference.child("article/photo").child(fileName)
                .putFile(uri)
                .addOnCompleteListener {
                    if (it.isSuccessful) { //업로드 완료
                        storage.reference.child("article/photo").child(fileName)
                            .downloadUrl.addOnSuccessListener { uri ->
                                successHandler(uri.toString())
                            }.addOnFailureListener {
                                errorHandler()
                            }
                    } else {
                        errorHandler()
                    }
                }
        }

        private fun uploadArticle(
            sellerId: String,
            title: String,
            bookTitle:String,
            lectureTitle:String,
            price: String,
            imageUrl: String,
            sellDescription: String,
            bookCondition: String,
            buyDay: String,
            bookWrite: String,
        ) {
            val model = ArticleModel(
                sellerId,
                title,
                bookTitle,
                lectureTitle,
                System.currentTimeMillis(),
                "$price 원",
                imageUrl,
                sellDescription,
                bookCondition,
                buyDay,
                bookWrite
            )
            articleDB.push().setValue(model)
            hideProgress()
            finish()
        }

        private fun showProgress() {
            findViewById<ProgressBar>(R.id.progressBar).isVisible = true
        }

        private fun hideProgress() {
            findViewById<ProgressBar>(R.id.progressBar).isVisible = false
        }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)

            when (requestCode) {
                1010 ->
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        startContentProvider()
                    } else {
                        Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        private fun startContentProvider() {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, 2020)
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (resultCode != Activity.RESULT_OK) {
                return
            }

            when (requestCode) {
                2020 -> {
                    val uri = data?.data
                    if (uri != null) {
                        findViewById<ImageView>(R.id.photoImageView).setImageURI(uri)
                        selectedUri = uri
                    } else {
                        Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_LONG).show()
                    }
                }
                else -> {
                    Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_LONG).show()
                }

            }
        }

        @RequiresApi(Build.VERSION_CODES.M)
        private fun showPermissionContextPopup() {
            AlertDialog.Builder(this)
                .setTitle("권한이 필요합니다.")
                .setMessage("사진을 가져오기 위해 필요합니다.")
                .setPositiveButton("동의") { _, _ ->
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        1010
                    )
                }
                .create()
                .show()
        }
    }

