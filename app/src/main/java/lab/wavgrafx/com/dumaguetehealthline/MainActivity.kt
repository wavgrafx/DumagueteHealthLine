package lab.wavgrafx.com.dumaguetehealthline

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.util.HashMap

class MainActivity : AppCompatActivity() {


    lateinit var mAuth: FirebaseAuth
    lateinit var usermodedb: DocumentReference
    lateinit var usermoderootRef: DocumentReference
    lateinit var loginmode: String
    lateinit var cuser:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null){
            val currentuser = FirebaseAuth.getInstance().currentUser!!.phoneNumber
            usermodedb = FirebaseFirestore.getInstance().document("User/Type")
            usermoderootRef = usermodedb.collection("Access").document("$currentuser")
        }



        btnStart.setOnClickListener{

            if (checkbox_privacypolicy.isChecked == true){
                val intent = Intent(this@MainActivity,Login::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Please confirm you have read the Privacy Policy above.", Toast.LENGTH_LONG).show()
            }


        }

        privacypolicy.setOnClickListener{
            val url = "https://www.privacypolicies.com/privacy/view/3924e5ad0cd83a928fa7d62f702930d9"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser != null){

            Toast.makeText(this, "Welcome to Dumaguete Health Line", Toast.LENGTH_LONG).show()
            val currentuser = FirebaseAuth.getInstance().currentUser!!.phoneNumber
            cuser = currentuser.toString()
            usermodeChecker()
        }

        checkConnectivity()
    }

    var backButtonCount:Int = 0

    override fun onBackPressed() {

        if (backButtonCount >= 1) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT)
                .show()
            backButtonCount++
        }
    }

    fun usermodeChecker(){


        usermoderootRef.get()
        usermoderootRef.get().addOnCompleteListener(OnCompleteListener<DocumentSnapshot> { task ->
            if (task.isSuccessful) {
                val document = task.result!!.data
                if (document != null) {
//                                            Log.d(TAG, "DocumentSnapshot data: " + task.result.data)
                    var umode:String = document.getValue("usermode").toString()
                    if (umode == "Patient"){
                        val intent = Intent(this@MainActivity, Dashboard::class.java)
                        startActivity(intent)
                        finish()
                    }

                    if(umode == "DocAssist"){
                        val intent = Intent(this@MainActivity, DA_Dashboard::class.java)
                        startActivity(intent)
                        finish()
                    }


                } else {
//                                            Log.d(TAG, "No such document")
                    try{
                        val items = HashMap<String, Any>()
                        items.put("usermode", "")

                        usermodedb.collection("Access").document("$cuser").set(items).addOnSuccessListener {
                                void: Void? -> loginmode = ""
                        }.addOnFailureListener {
                                exception: java.lang.Exception -> Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }catch (e:Exception) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            } else {
//                                        Log.d(TAG, "get failed with ", task.exception)
            }
        })
    }

    private fun checkConnectivity(){
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnected
        if (!isConnected){
            Toast.makeText(this, "Check network connection", Toast.LENGTH_SHORT).show()
        }
    }







}
