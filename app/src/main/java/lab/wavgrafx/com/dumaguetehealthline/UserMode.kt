package lab.wavgrafx.com.dumaguetehealthline

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_user_mode.*
import java.util.HashMap
import kotlin.math.log

class UserMode : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var usermodedb: DocumentReference
    lateinit var usermoderootRef: DocumentReference
    lateinit var loginmode: String
    lateinit var cuser:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_mode)
        mAuth = FirebaseAuth.getInstance()
        var currentuser = FirebaseAuth.getInstance().currentUser!!.phoneNumber
        cuser = currentuser.toString()
        usermodedb = FirebaseFirestore.getInstance().document("User/Type")
        usermoderootRef = usermodedb.collection("Access").document("$currentuser")


        usermodeChecker()

        usermode_docassist.setOnClickListener {
            loginmode = "DocAssist"
            try{
                val items = HashMap<String, Any>()
                items.put("usermode", "$loginmode")

                usermodedb.collection("Access").document("$currentuser").set(items).addOnSuccessListener {
                        void: Void? ->
                    val intent = Intent(this@UserMode, DA_Dashboard::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                        exception: java.lang.Exception -> Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                }
            }catch (e:Exception) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }
            val intent = Intent(this@UserMode, DA_Dashboard::class.java)
            intent.putExtra("usermode", "user_docassist")
            startActivity(intent)
        }

        usermode_patient.setOnClickListener {
            loginmode = "Patient"
            try{
                val items = HashMap<String, Any>()
                items.put("usermode", "$loginmode")

                usermodedb.collection("Access").document("$currentuser").set(items).addOnSuccessListener {
                        void: Void? ->
                    val intent = Intent(this@UserMode, Dashboard::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                        exception: java.lang.Exception -> Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                }
            }catch (e:Exception) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }

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
                        val intent = Intent(this@UserMode, Dashboard::class.java)
                        startActivity(intent)
                        finish()
                    }

                    if(umode == "DocAssist"){
                        val intent = Intent(this@UserMode, DA_Dashboard::class.java)
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
}
