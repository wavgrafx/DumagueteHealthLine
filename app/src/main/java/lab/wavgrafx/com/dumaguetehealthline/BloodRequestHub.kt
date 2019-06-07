package lab.wavgrafx.com.dumaguetehealthline


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_blood_request_hub.*
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import lab.wavgrafx.com.dumaguetehealthline.model.GiveBloodModel
import java.text.SimpleDateFormat
import java.util.*

class BloodRequestHub : AppCompatActivity() {


    lateinit var giveblood_bloodtype: Spinner
    lateinit var giveblood_editmessage: EditText
    lateinit var giveblood_sendrequest: Button
    lateinit var currentuser: String
    lateinit var currentdatetime: String

    lateinit var mAuth: FirebaseAuth
    lateinit var db: DocumentReference
    lateinit var rootRef: DocumentReference
    private var adapter: BloodRequestHub.GiveBloodFirestoreRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_request_hub)


        db = FirebaseFirestore.getInstance().document("GiveBlood/List")

        giveblood_recyclerView.layoutManager = LinearLayoutManager(this)
        giveblood_recyclerView.smoothScrollToPosition(0)
        giveblood_bloodtype = findViewById(R.id.giveblood_bloodtype)
        giveblood_editmessage = findViewById(R.id.giveblood_editmessage)
        giveblood_sendrequest = findViewById(R.id.giveblood_sendrequest)


        giveblood_sendrequest.setOnClickListener {
            sendRequest()

        }


        currentuser = FirebaseAuth.getInstance().currentUser!!.phoneNumber.toString()
        mAuth = FirebaseAuth.getInstance()
        giveblood_recyclerView.layoutManager = LinearLayoutManager(this)

        rootRef = FirebaseFirestore.getInstance().document("GiveBlood/List")
        val query = rootRef!!.collection("Requests")
            .orderBy("Timestamp", Query.Direction.DESCENDING)

        val options = FirestoreRecyclerOptions.Builder<GiveBloodModel>().setQuery(query, GiveBloodModel::class.java).build()
        adapter = GiveBloodFirestoreRecyclerAdapter(options)
    }


    private inner class GiveBloodViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
        internal fun setGiveBloodDetails(BloodType: String, MobileNumber: String, Message: String, Status: String, DateTime: String) {



            val askblood_datetime = view.findViewById<TextView>(R.id.askblood_datetime)
            val askblood_bloodtype = view.findViewById<TextView>(R.id.askblood_bloodtype)
            val askblood_message = view.findViewById<TextView>(R.id.askblood_message)
            val askblood_mobilenumber = view.findViewById<TextView>(R.id.askblood_mobilenumber)
            val giveblood_recyclerView = view.findViewById<RecyclerView>(R.id.giveblood_recyclerView)
            val giveblood_box = view.findViewById<LinearLayout>(R.id.giveblood_box)



            askblood_datetime.text = DateTime
            askblood_bloodtype.text = BloodType
            askblood_message.text = Message
            askblood_mobilenumber.text = MobileNumber




        }

    }

    private inner class GiveBloodFirestoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<GiveBloodModel>) : FirestoreRecyclerAdapter<GiveBloodModel, GiveBloodViewHolder>(options) {
        override fun onBindViewHolder(givebloodViewHolder: GiveBloodViewHolder, position: Int, givebloodModel: GiveBloodModel) {



            givebloodViewHolder.setGiveBloodDetails(givebloodModel.BloodType, givebloodModel.MobileNumber,
                givebloodModel.Message, givebloodModel.Status, givebloodModel.DateTime)


        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiveBloodViewHolder {

            val view = LayoutInflater.from(parent.context).inflate(R.layout.givebloodrequest, parent, false)
            return GiveBloodViewHolder(view)


        }
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()

        giveblood_recyclerView.adapter = adapter
        Handler().postDelayed({
            giveblood_recyclerView.smoothScrollToPosition(0)
        }, 1000)
    }

    override fun onRestart() {
        super.onRestart()
        Handler().postDelayed({
            giveblood_recyclerView.smoothScrollToPosition(0)
        }, 1000)
    }



    override fun onStop() {
        super.onStop()

        if (adapter != null) {
            adapter!!.stopListening()
        }


    }


    //----------------------------------------------------send message------------------------------------------------------------
    private fun sendRequest(){

        //---------------------------------Appointment Date Declarations---------------------------------

        currentdatetime = SimpleDateFormat("yyyy-MM-dd HH:mm").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()

        val myFormat = "yyyy-MM-dd HH:mm" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        currentdatetime = sdf.format(cal.time)
//--------------------------------------------------------------------------------------------------

        giveblood_editmessage = findViewById(R.id.giveblood_editmessage)
        giveblood_bloodtype = findViewById(R.id.giveblood_bloodtype)

        var message:String = giveblood_editmessage.text.toString()
        var bloodtype:String = giveblood_bloodtype.selectedItem.toString()
        var mobilenumber:String = currentuser
        var status:String = "Pending"
        var datetime:String = currentdatetime
        var id:String = datetime.substring(0,14) + bloodtype + mobilenumber

        if (!message.isEmpty()) {
            try {
                giveblood_editmessage.setText("")

                val items = HashMap<String, Any>()
                items.put("BloodType", bloodtype)
                items.put("MobileNumber", mobilenumber)
                items.put("Message", message)
                items.put("Status", status)
                items.put("DateTime", datetime)
                items.put("Timestamp", FieldValue.serverTimestamp())
                items.put("Id", id)


                db.collection("Requests").document(id).set(items).addOnSuccessListener {
                        void: Void? -> Toast.makeText(this, "Request Posted.", Toast.LENGTH_SHORT).show()
                    giveblood_recyclerView.smoothScrollToPosition(0)


                }.addOnFailureListener {
                        exception: java.lang.Exception -> Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                }
            }catch (e:Exception) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }
        }else {
            Toast.makeText(this, "Please type Request message first", Toast.LENGTH_LONG).show()
        }


    }
}
