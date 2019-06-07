package lab.wavgrafx.com.dumaguetehealthline

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_da__view_queue.*
import kotlinx.android.synthetic.main.da_sched_queue_nodone.view.*
import lab.wavgrafx.com.dumaguetehealthline.model.AppointmentModel
import java.util.*

class DA_ViewAppointment : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var rootRef: DocumentReference
    private var adapter: DA_ViewAppointment.ViewQueueFirestoreRecyclerAdapter? = null
    lateinit var currentuser: String


    //-----------------------declaring intent variables-----------------------
    lateinit var dateselected:String
    lateinit var inttransdate:String
    lateinit var intdoctor: String
    lateinit var intspecialty: String
    lateinit var intaddress: String
    //-----------------------declaring intent variables-----------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_da__view_appointment)




        currentuser = FirebaseAuth.getInstance().currentUser!!.phoneNumber.toString()
        mAuth = FirebaseAuth.getInstance()
        viewqueue_recyclerview.layoutManager = LinearLayoutManager(this)







//-------------------------Retrieving Intent data----------------------------
        val bundle:Bundle=intent.extras
        if(bundle!=null)
        {
            dateselected = bundle.getString("dateselected")
            intdoctor = bundle.getString("intdoctor")
            intspecialty = bundle.getString("intspecialty")
            intaddress =  bundle.getString("intaddress")
            inttransdate =  bundle.getString("inttransdate")


        }
//-------------------------Retrieving Intent data----------------------------



        viewqueue_DoctorName.text = intdoctor
        viewqueue_Specialty.text = intspecialty
        viewqueue_Date.text = inttransdate

        rootRef = FirebaseFirestore.getInstance().document("Clinics/Appointments")
        val query = rootRef!!.collection("AppointmentList")
            .whereEqualTo("Doctor",intdoctor)
            .whereEqualTo("AppointDate",dateselected)
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
            val viewqueue_done = view.findViewById<TextView>(R.id.viewqueue_done)
            val viewqueue_sex = view.findViewById<TextView>(R.id.viewqueue_sex)
            val viewqueue_bday= view.findViewById<TextView>(R.id.viewqueue_bday)
            val viewqueue_host= view.findViewById<TextView>(R.id.viewqueue_host)
            val date_hider = view.findViewById<LinearLayout>(R.id.date_hider)
            val viewqueue_time = view.findViewById<TextView>(R.id.viewqueue_time)

            viewqueue_patientname.text = "$FName ${MName.substring(0,1)}. $LName"
            viewqueue_status.text = Status
            viewqueue_sex.text = Sex
            viewqueue_bday.text = Bday
            viewqueue_host.text = Host
            viewqueue_time.text = TimeTrans
        }
    }

    private inner class ViewQueueFirestoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<AppointmentModel>) : FirestoreRecyclerAdapter<AppointmentModel, ViewQueueViewHolder>(options) {
        override fun onBindViewHolder(viewPointViewHolder: ViewQueueViewHolder, position: Int, viewqueueModel: AppointmentModel) {



            viewPointViewHolder.setViewQueue(viewqueueModel.Fname, viewqueueModel.Mname, viewqueueModel.Lname, viewqueueModel.Sex, viewqueueModel.Bday,
                viewqueueModel.AppointDate, viewqueueModel.DateTrans, viewqueueModel.AppointTime, viewqueueModel.TimeTrans, viewqueueModel.Docu,
                viewqueueModel.Doctor, viewqueueModel.DocPrac, viewqueueModel.Address, viewqueueModel.Host, viewqueueModel.Status)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewQueueViewHolder {

            val view = LayoutInflater.from(parent.context).inflate(R.layout.da_sched_queue_nodone, parent, false)
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