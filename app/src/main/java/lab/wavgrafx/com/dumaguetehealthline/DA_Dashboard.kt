package lab.wavgrafx.com.dumaguetehealthline

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_da__dashboard.*
import java.util.HashMap

class DA_Dashboard : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var usermodedb: DocumentReference
    lateinit var usermoderootRef: DocumentReference
    lateinit var loginmode: String
    lateinit var cuser:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_da__dashboard)
        mAuth = FirebaseAuth.getInstance()
        usermodedb = FirebaseFirestore.getInstance().document("User/Type")
        //Actions when click the dashboard menus
        //Dash_Map opens the Map Activity
        dash_map.setOnClickListener{
            val intent = Intent(this@DA_Dashboard,DA_SelectClinic::class.java)
            startActivity(intent)
        }

        //Dash_AddClinic opens the DA_AddClinic Activity
        dash_addclinic.setOnClickListener{
            val intent = Intent(this@DA_Dashboard,DA_AddClinic::class.java)
            startActivity(intent)
        }

        //Dash_ManageClinic opens the DA_AddClinic Activity
        dash_manageclinic.setOnClickListener{
            val intent = Intent(this@DA_Dashboard,DA_ManageClinic::class.java)
            startActivity(intent)
        }

        //Dash BMI Calculator
        dash_bmicalculator.setOnClickListener{
            val intent = Intent(this@DA_Dashboard,BMICalculator::class.java)
            startActivity(intent)
        }

        dash_viewqueue.setOnClickListener {
            val intent = Intent(this@DA_Dashboard,DA_ClinicQueue::class.java)
            startActivity(intent)
        }

        dash_messenger.setOnClickListener {
            val intent = Intent(this@DA_Dashboard,DA_Contacts::class.java)
            startActivity(intent)
        }



        btnLogout.setOnClickListener(){
            SingoutAlertDialog()
        }

        btnGiveBlood.setOnClickListener {
            val intent = Intent(this@DA_Dashboard,BloodRequestHub::class.java)
            startActivity(intent)
        }

        checkConnectivity()
    }

    override fun onStart() {
        super.onStart()
        if (mAuth.currentUser == null){
            startActivity(Intent(this, Login :: class.java))
        }else{
            Toast.makeText(this, "Welcome to Dumaguete Doc Assistant", Toast.LENGTH_LONG).show()
            val currentuser = FirebaseAuth.getInstance().currentUser!!.phoneNumber
            cuser = currentuser.toString()
            textViewUser.text = "Mobile No: " + currentuser
        }

        checkConnectivity()
    }

    override fun onResume() {
        super.onResume()

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

    private fun checkConnectivity(){
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnected
        if (!isConnected){
            Toast.makeText(this, "Check network connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun SingoutAlertDialog(){
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(this)

        // Set a title for alert dialog
        builder.setTitle("Sign Out")


        // Set a message for alert dialog
        builder.setMessage("Are you sure you want to sign out this Mobile Number?")


        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> signOut()
                DialogInterface.BUTTON_NEGATIVE -> dialog.hide()
            }
        }


        // Set the alert dialog positive/yes button
        builder.setPositiveButton("YES",dialogClickListener)

        // Set the alert dialog negative/no button
        builder.setNegativeButton("NO",dialogClickListener)



        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }

    private fun signOut(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this@DA_Dashboard,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
        try{
            val items = HashMap<String, Any>()
            items.put("usermode", "")
//            usermoderootRef = usermodedb.collection("Access").document("$cuser")
            usermodedb.collection("Access").document("$cuser").set(items).addOnSuccessListener {
                    void: Void? -> loginmode = ""
            }.addOnFailureListener {
                    exception: java.lang.Exception -> Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
            }
        }catch (e:Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
//        finish()

    }


}