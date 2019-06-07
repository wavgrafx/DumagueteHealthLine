package lab.wavgrafx.com.dumaguetehealthline

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_da__messenger.*
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import lab.wavgrafx.com.dumaguetehealthline.model.MessengerModel
class DA_Inbox : AppCompatActivity() {


    lateinit var fullName: String
    lateinit var address: String
    lateinit var currentuser: String

    lateinit var mAuth: FirebaseAuth
    lateinit var db: DocumentReference
    lateinit var rootRef: DocumentReference
    private var adapter: DA_Inbox.MessengerFirestoreRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_da__inbox)

        db = FirebaseFirestore.getInstance().document("Messenger/List")

        messenger_recyclerView.layoutManager = LinearLayoutManager(this)
        messenger_recyclerView.smoothScrollToPosition(0)



        val bundle:Bundle=intent.extras
        if(bundle!=null)
        {
            fullName = bundle.getString("nameofDoc")
            address = bundle.getString("addofDoc")

        }

        currentuser = FirebaseAuth.getInstance().currentUser!!.phoneNumber.toString()
        mAuth = FirebaseAuth.getInstance()
        messenger_recyclerView.layoutManager = LinearLayoutManager(this)

        rootRef = FirebaseFirestore.getInstance().document("Messenger/List")
        val query = rootRef!!.collection("Messages")
            .whereEqualTo("Doctor", fullName)
            .whereEqualTo("ByDoc", false)
            .orderBy("Timestamp", Query.Direction.DESCENDING)

        val options = FirestoreRecyclerOptions.Builder<MessengerModel>().setQuery(query, MessengerModel::class.java).build()
        adapter = MessengerFirestoreRecyclerAdapter(options)




    }
    private inner class MessengerViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
        internal fun setMessengerDetails(Civilian: String, Doctor: String, Message: String, DateTimeReceive: String) {



            val mesenger_message = view.findViewById<TextView>(R.id.mesenger_message)
            val messenger_datetime = view.findViewById<TextView>(R.id.messenger_datetime)
            val messenger_civilian = view.findViewById<TextView>(R.id.messenger_civilian)
            val messenger_recyclerView = view.findViewById<RecyclerView>(R.id.messenger_recyclerView)
            val messenger_box = view.findViewById<LinearLayout>(R.id.messenger_box)


            messenger_civilian.text = Civilian
            mesenger_message.text = Message
            messenger_datetime.text = DateTimeReceive


            view.setOnClickListener {
                val intent = Intent(this@DA_Inbox, DA_Messenger::class.java)
                intent.putExtra("inboxcivilian", Civilian)
                intent.putExtra("inboxdoctor", Doctor)
                intent.putExtra("inboxmessage", Message)
                intent.putExtra("inboxdatetime", DateTimeReceive)
                startActivity(intent)
            }



        }

    }

    private inner class MessengerFirestoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<MessengerModel>) : FirestoreRecyclerAdapter<MessengerModel, MessengerViewHolder>(options) {
        override fun onBindViewHolder(messengerViewHolder: MessengerViewHolder, position: Int, messengerModel: MessengerModel) {



            messengerViewHolder.setMessengerDetails(messengerModel.Civilian, messengerModel.Doctor,
                messengerModel.Message, messengerModel.DateTimeReceive)


        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessengerViewHolder {

            val view = LayoutInflater.from(parent.context).inflate(R.layout.da_inbox, parent, false)
            return MessengerViewHolder(view)


        }
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()

        messenger_recyclerView.adapter = adapter
        Handler().postDelayed({
            messenger_recyclerView.smoothScrollToPosition(0)
        }, 1000)
    }

    override fun onRestart() {
        super.onRestart()
        Handler().postDelayed({
            messenger_recyclerView.smoothScrollToPosition(0)
        }, 1000)
    }



    override fun onStop() {
        super.onStop()

        if (adapter != null) {
            adapter!!.stopListening()
        }


    }

}
