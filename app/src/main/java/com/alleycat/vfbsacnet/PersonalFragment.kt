package com.alleycat.vfbsacnet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alleycat.vfbsacnet.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_personal.*

private val TAG = "PersonalFragment"
private lateinit var mAuth: FirebaseAuth
private lateinit var mUser: User

class PersonalFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_personal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mAuth = FirebaseAuth.getInstance()

        signOut_text.setOnClickListener {
            mAuth.signOut()
        }

        //signOut_text.setOnClickListener { Log.d(TAG, "onViewCreated()"); }



        //old code
        val user = mAuth.currentUser
        val database = FirebaseDatabase.getInstance().reference
        database.child("users").child(user!!.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(data: DataSnapshot) {
                    val user = data.getValue(User::class.java)
                    user_name.text = user!!.name
                    group_name.text = user.group
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "Error", error.toException())
                }

            })



        mAuth.addAuthStateListener {
            if (it.currentUser == null) {
                startActivity(Intent(activity, loginActivity::class.java))
            }
        }
    }
}