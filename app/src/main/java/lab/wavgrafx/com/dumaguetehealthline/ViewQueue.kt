package lab.wavgrafx.com.dumaguetehealthline

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_view_queue.*
import kotlinx.android.synthetic.main.sched_queue.*
import lab.wavgrafx.com.dumaguetehealthline.model.AppointmentModel

class ViewQueue : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var rootRef: DocumentReference
    private var adapter: ViewQueue.ViewQueueFirestoreRecyclerAdapter? = null
    lateinit var currentuser: String


    //-----------------------declaring intent variables-----------------------
    lateinit var intdoctor: String
    lateinit var intspecialty: String
    lateinit var intaddress: String
    lateinit var intpatientname: String
    lateinit var intsex: String
    lateinit var intbday: String
    lateinit var intappointdate: String
    lateinit var intent_datetrans: String
    lateinit var intent_appointtime: String
    lateinit var intent_timetrans: String
    lateinit var intstatus: String
    lateinit var prevtime:String


    //-----------------------declaring intent variables-----------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_queue)

        prevtime = ""


        currentuser = FirebaseAuth.getInstance().currentUser!!.phoneNumber.toString()
        mAuth = FirebaseAuth.getInstance()
        viewqueue_recyclerview.layoutManager = LinearLayoutManager(this)

//-------------------------Retrieving Intent data----------------------------
        val bundle:Bundle=intent.extras
        if(bundle!=null)
        {
            intdoctor = bundle.getString("intent_doctor")
            intspecialty = bundle.getString("intent_specialty")
            intaddress =  bundle.getString("intent_address")
            intpatientname = bundle.getString("intent_patientname")
            intsex = bundle.getString("intent_sex")
            intbday = bundle.getString("intent_bday")
            intappointdate = bundle.getString("intent_appointdate")
            intent_datetrans = bundle.getString("intent_datetrans")
            intent_appointtime = bundle.getString("intent_appointtime")
            intent_timetrans = bundle.getString("intent_timetrans")
            intstatus = bundle.getString("intent_status")
        }
//-------------------------Retrieving Intent data----------------------------
        viewqueue_DoctorName.text = intdoctor
        viewqueue_Specialty.text = intspecialty

        viewqueue_Date.text = intent_datetrans



        rootRef = FirebaseFirestore.getInstance().document("Clinics/Appointments")
        val query = rootRef!!.collection("AppointmentList")
            .whereEqualTo("Doctor",intdoctor)
            .whereEqualTo("AppointDate",intappointdate)
            .whereEqualTo("Status", "PENDING")
            .orderBy("AppointTime")
            .orderBy("Timestamp", Query.Direction.ASCENDING)

        val options = FirestoreRecyclerOptions.Builder<AppointmentModel>().setQuery(query, AppointmentModel::class.java).build()
        adapter = ViewQueueFirestoreRecyclerAdapter(options)


    }

    private inner class ViewQueueViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
        internal fun setViewQueue(FName: String, MName: String, LName: String, Sex: String, Bday: String, AppointDate: String,
                                  DateTrans: String, AppointTime: String, TimeTrans: String,
                                  Docu: String, Doctor: String, DocPrac: String, Address: String, Host: String, Status: String) {



            val viewqueue_patientname = view.findViewById<TextView>(R.id.viewqueue_patientname)
            val viewqueue_status = view.findViewById<TextView>(R.id.viewqueue_status)
            val viewqueue_recyclerview = view.findViewById<RecyclerView>(R.id.viewqueue_recyclerview)
            val viewqueue_DoctorName = view.findViewById<TextView>(R.id.viewqueue_DoctorName)
            val viewqueue_Specialty = view.findViewById<TextView>(R.id.viewqueue_Specialty)
            val viewqueue_Date = view.findViewById<TextView>(R.id.viewqueue_Date)
            val viewqueue_bar = view.findViewById<LinearLayout>(R.id.viewqueue_bar)
            val viewqueue_time = view.findViewById<TextView>(R.id.viewqueue_time)
            val queue_shader = view.findViewById<LinearLayout>(R.id.queue_shader)
            val date_hider = view.findViewById<LinearLayout>(R.id.date_hider)
            viewqueue_time.text = TimeTrans
            if (prevtime == TimeTrans){
                date_hider.visibility = View.INVISIBLE
                date_hider.layoutParams = LinearLayout.LayoutParams(0, 0)
                prevtime = TimeTrans
            }
            prevtime = TimeTrans



            var sextitle:String = ""
            if (Sex == "Male"){sextitle = "Mr."}else{sextitle = "Ms."}
            viewqueue_patientname.text = "$sextitle $FName ${MName.substring(0,1)}. $LName"
            viewqueue_status.text = Status



            if (Host == currentuser){ queue_shader.setBackgroundColor(Color.parseColor("#48CFAD")) }
            else{
                var sextitle:String = ""
                if (Sex == "Male"){sextitle = "Mr."}else{sextitle = "Ms."}
                viewqueue_patientname.text = "$sextitle $LName"
            }



        }
    }

    private inner class ViewQueueFirestoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<AppointmentModel>) : FirestoreRecyclerAdapter<AppointmentModel, ViewQueueViewHolder>(options) {
        override fun onBindViewHolder(viewPointViewHolder: ViewQueueViewHolder, position: Int, viewqueueModel: AppointmentModel) {



            viewPointViewHolder.setViewQueue(viewqueueModel.Fname, viewqueueModel.Mname, viewqueueModel.Lname, viewqueueModel.Sex, viewqueueModel.Bday,
                viewqueueModel.AppointDate, viewqueueModel.DateTrans, viewqueueModel.AppointTime, viewqueueModel.TimeTrans, viewqueueModel.Docu,
                viewqueueModel.Doctor, viewqueueModel.DocPrac, viewqueueModel.Address, viewqueueModel.Host, viewqueueModel.Status)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewQueueViewHolder {

            val view = LayoutInflater.from(parent.context).inflate(R.layout.sched_queue, parent, false)
            return ViewQueueViewHolder(view)

        }


    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()

        viewqueue_recyclerview.adapter = adapter



    }

    override fun onStop() {
        super.onStop()

        if (adapter != null) {
            adapter!!.stopListening()
        }
    }


}
