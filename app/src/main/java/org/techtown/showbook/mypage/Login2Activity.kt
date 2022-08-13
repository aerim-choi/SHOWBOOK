package org.techtown.showbook.mypage

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.techtown.showbook.MainActivity
import org.techtown.showbook.R
import org.techtown.showbook.databinding.ActivityLogin2Binding
import org.techtown.showbook.lectureinfo.LectureFragment


class Login2Activity:AppCompatActivity() {


    private lateinit var binding: ActivityLogin2Binding
    private val auth:FirebaseAuth by lazy{
        Firebase.auth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogin2Binding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.signupBtn.setOnClickListener {
            val intent = Intent(this, Signup2Activity::class.java)
            startActivity(intent)
        }

        binding.loginBtn3.setOnClickListener {

            binding?.let{binding->
                val email=binding.inputEmail.text.toString()
                val password = binding.inputPassward.text.toString()
                //로그인
                if(auth.currentUser== null){
                    auth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(this){ task->
                            if(task.isSuccessful){
                                successSignIn()
                                Toast.makeText(this,"로그인에 성공",Toast.LENGTH_SHORT).show()
                                finish()

                            }else {
                                Toast.makeText(this,"로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show()
                            }

                    }
                }
                //로그이웃
                else{
                    auth.signOut()
                }
            }

        }

//        binding.inputEmail.addTextChangedListener {
//            binding?.let{ binding->
//                val enable = binding.inputEmail.text.isNotEmpty() && binding.inputPassward.text.isNotEmpty()
//            }
//
//        }





//        binding.inputPassward.addTextChangedListener {
//            val enable = binding.inputEmail.text.isNotEmpty() && binding.inputPassward.text.isNotEmpty()
//        }
    }
    private fun successSignIn(){
        if(auth.currentUser==null){
            Toast.makeText(this,"로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요",Toast.LENGTH_SHORT).show()
            return
        }
    }
}