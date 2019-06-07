package lab.wavgrafx.com.dumaguetehealthline

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_da__messenger.*
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
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
import lab.wavgrafx.com.dumaguetehealthline.model.MessengerModel
import java.text.SimpleDateFormat
import java.util.*

class DA_Messenger : AppCompatActivity() {

    lateinit var inboxcivilian:String
    lateinit var inboxdoctor:String
    lateinit var inboxmessage:String
    lateinit var inboxdatetime:String
    lateinit var documName:String
    lateinit var fullName: String
    lateinit var medPractice: String
    lateinit var address: String
    lateinit var currentdatetime: String
    lateinit var messenger_editmessage: EditText
    lateinit var messenger_sendmessage: Button
    lateinit var currentuser: String

    lateinit var mAuth: FirebaseAuth
    lateinit var db: DocumentReference
    lateinit var rootRef: DocumentReference
    private var adapter: DA_Messenger.MessengerFirestoreRecyclerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_da__messenger)


        val bundle:Bundle=intent.extras
        if(bundle!=null)
        {

            inboxcivilian = bundle.getString("inboxcivilian")
            inboxdoctor = bundle.getString("inboxdoctor")
            inboxmessage = bundle.getString("inboxmessage")
            inboxdatetime = bundle.getString("inboxdatetime")


            messenger_civilian.text = inboxcivilian

        }


        db = FirebaseFirestore.getInstance().document("Messenger/List")

        messenger_recyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        messenger_recyclerView.smoothScrollToPosition(0)
        messenger_editmessage = findViewById(R.id.messenger_editmessage)
        messenger_sendmessage = findViewById(R.id.messenger_sendmessage)




        messenger_sendmessage.setOnClickListener {
            sendMessage()

        }


        currentuser = FirebaseAuth.getInstance().currentUser!!.phoneNumber.toString()
        mAuth = FirebaseAuth.getInstance()
        messenger_recyclerView.layoutManager = LinearLayoutManager(this)

        rootRef = FirebaseFirestore.getInstance().document("Messenger/List")
        val query = rootRef!!.collection("Messages")
            .whereEqualTo("Civilian", inboxcivilian)
            .whereEqualTo("Doctor", inboxdoctor)
            .orderBy("Timestamp", Query.Direction.ASCENDING)

        val options = FirestoreRecyclerOptions.Builder<MessengerModel>().setQuery(query, MessengerModel::class.java).build()
        adapter = MessengerFirestoreRecyclerAdapter(options)




    }
    private inner class MessengerViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
        internal fun setMessengerDetails(Civilian: String, Doctor: String, Message: String, DateTimeReceive: String,
                                         ByDoc:Boolean) {



            val mesenger_message = view.findViewById<TextView>(R.id.mesenger_message)
            val messenger_datetime = view.findViewById<TextView>(R.id.messenger_datetime)
            val messenger_recyclerView = view.findViewById<RecyclerView>(R.id.messenger_recyclerView)
            val messenger_box = view.findViewById<LinearLayout>(R.id.messenger_box)



            mesenger_message.text = Message
            messenger_datetime.text = DateTimeReceive


            if (ByDoc == true){
                messenger_box.gravity = Gravity.END
                messenger_datetime.gravity = Gravity.END
                mesenger_message.setBackgroundResource(R.drawable.message_dialogbox_right)
            }

        }

    }

    private inner class MessengerFirestoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<MessengerModel>) : FirestoreRecyclerAdapter<MessengerModel, MessengerViewHolder>(options) {
        override fun onBindViewHolder(messengerViewHolder: MessengerViewHolder, position: Int, messengerModel: MessengerModel) {



            messengerViewHolder.setMessengerDetails(messengerModel.Civilian, messengerModel.Doctor,
                messengerModel.Message, messengerModel.DateTimeReceive, messengerModel.ByDoc)


        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessengerViewHolder {

            val view = LayoutInflater.from(parent.context).inflate(R.layout.da_messages, parent, false)
            return MessengerViewHolder(view)


        }
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()

        messenger_recyclerView.adapter = adapter
        Handler().postDelayed({
            messenger_recyclerView.smoothScrollToPosition(adapter!!.itemCount)
        }, 1000)
    }

    override fun onRestart() {
        super.onRestart()
        Handler().postDelayed({
            messenger_recyclerView.smoothScrollToPosition(adapter!!.itemCount)
        }, 1000)
    }



    override fun onStop() {
        super.onStop()

        if (adapter != null) {
            adapter!!.stopListening()
        }


    }


    //----------------------------------------------------send message------------------------------------------------------------
    private fun sendMessage(){

        //---------------------------------Appointment Date Declarations---------------------------------

        currentdatetime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()

        val myFormat = "yyyy-MM-dd HH:mm:ss.SSS" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        currentdatetime = sdf.format(cal.time)
//--------------------------------------------------------------------------------------------------

        messenger_editmessage = findViewById(R.id.messenger_editmessage)

        var message:String = messenger_editmessage.text.toString()
        var message_doctor:String = inboxdoctor
        var civilian:String = inboxcivilian
        var messagedatetime:String = currentdatetime

        if (!message.isEmpty()) {
            try {
                messenger_editmessage.setText("")
                messenger_recyclerView.scrollToPosition(adapter!!.itemCount - 1)

                val items = HashMap<String, Any>()
                items.put("Civilian", civilian)
                items.put("Doctor", message_doctor)
                items.put("Message", message)
                items.put("DateTimeReceive", messagedatetime)
                items.put("Timestamp", FieldValue.serverTimestamp())
                items.put("Id", messagedatetime)
                items.put("ByDoc", true)


                db.collection("Messages").document(messagedatetime).set(items).addOnSuccessListener {
                        void: Void? -> Toast.makeText(this, "Message sent.", Toast.LENGTH_SHORT).show()
                    messenger_recyclerView.smoothScrollToPosition(adapter!!.itemCount)


                }.addOnFailureListener {
                        exception: java.lang.Exception -> Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                }
            }catch (e:Exception) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }
        }else {
            Toast.makeText(this, "Please type a message first.", Toast.LENGTH_LONG).show()
        }


    }
}