package com.alleycat.vfbsacnet


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser == null)
            startActivity(Intent(this, loginActivity::class.java))
        //looks creepy, will fix someday
        else {
            setContentView(R.layout.activity_main)
            val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
            bottomNav.setOnNavigationItemSelectedListener(navListener)

            //crutch that makes DeadlinesFragment run instead of MainActivity when the app starts
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                DeadlinesFragment()
            ).commit()
            //mAuth.signOut()
            /* val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword("ivan@ivan.com","password")
            .addOnCompleteListener{
                if(it.isSuccessful) {
                    Log.d(TAG, "signIn:success")
                } else {
                    Log.e(TAG,"SignIn: Failure")
                }

            }*/


        }
    }



    private val navListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val selectedFragment: Fragment = when (item.itemId) {
                R.id.nav_lessons -> LessonsFragment()
                R.id.nav_teachers -> TeachersFragment()
                R.id.nav_notes -> NotesFragment()
                R.id.nav_deadlines -> DeadlinesFragment()
                R.id.nav_personal -> PersonalFragment()
                else -> PersonalFragment()
            }

            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                selectedFragment
            ).commit()
            true
        }
}