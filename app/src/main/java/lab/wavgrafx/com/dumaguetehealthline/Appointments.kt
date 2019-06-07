package lab.wavgrafx.com.dumaguetehealthline


import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_appointments.*
import kotlinx.android.synthetic.main.sched_appointment.*
import lab.wavgrafx.com.dumaguetehealthline.model.AppointmentModel

class Appointments : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var rootRef: DocumentReference





    private var adapter: Appointments.ViewPointFirestoreRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)





        val currentuser = FirebaseAuth.getInstance().currentUser!!.phoneNumber.toString()
        mAuth = FirebaseAuth.getInstance()
        viewpoint_recyclerview.layoutManager = LinearLayoutManager(this)

        rootRef = FirebaseFirestore.getInstance().document("Clinics/Appointments")
        val query = rootRef!!.collection("AppointmentList")
            .whereEqualTo("Host",currentuser)
            .orderBy("AppointDate", Query.Direction.DESCENDING)

        val options = FirestoreRecyclerOptions.Builder<AppointmentModel>().setQuery(query, AppointmentModel::class.java).build()
        adapter = ViewPointFirestoreRecyclerAdapter(options)




    }



    private inner class ViewPointViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
        internal fun setViewPoint(FName: String, MName: String, LName: String, Sex: String, Bday: String, AppointDate: String,
                                  DateTrans: String, AppointTime: String, TimeTrans: String,
                                  Docu: String, Doctor: String, DocPrac: String, Address: String, Host: String, Status: String) {



            val viewpoint_doctor= view.findViewById<TextView>(R.id.viewpoint_doctor)
            val viewpoint_specialty = view.findViewById<TextView>(R.id.viewpoint_specialty)
            val viewpoint_address = view.findViewById<TextView>(R.id.viewpoint_address)
            val viewpoint_patientname = view.findViewById<TextView>(R.id.viewpoint_patientname)
            val viewpoint_sex = view.findViewById<TextView>(R.id.viewpoint_sex)
            val viewpoint_bday = view.findViewById<TextView>(R.id.viewpoint_bday)
            val viewpoint_appointdate = view.findViewById<TextView>(R.id.viewpoint_appointdate)
            val viewpoint_appointtime = view.findViewById<TextView>(R.id.viewpoint_appointtime)
            val viewpoint_status = view.findViewById<TextView>(R.id.viewpoint_status)
            val viewpoint_recyclerview = view.findViewById<RecyclerView>(R.id.viewpoint_recyclerview)
            val datetimeshader = view.findViewById<LinearLayout>(R.id.datetimeshader)



            viewpoint_doctor.text = Doctor
            viewpoint_specialty.text = DocPrac
            viewpoint_address.text = Address

            viewpoint_appointdate.text = DateTrans
            viewpoint_appointtime.text = TimeTrans
            viewpoint_patientname.text = "$FName ${MName.substring(0,1)}. $LName"
            viewpoint_sex.text = Sex
            viewpoint_bday.text = Bday
            viewpoint_status.text = Status

//            if (Status == "DONE"){datetimeshader.setBackgroundColor(Color.parseColor("#50ffffff"))}


            //------------------------Sending intent data------------------------------------
            view.setOnClickListener {view: View? ->
                val intent = Intent(this@Appointments,ViewQueue::class.java)

                intent.putExtra("intent_doctor", "$Doctor")
                intent.putExtra("intent_specialty", "$DocPrac")
                intent.putExtra("intent_address", "$Address")
                intent.putExtra("intent_patientname", "$FName ${MName.substring(0,1)} $LName")
                intent.putExtra("intent_sex", "$Sex")
                intent.putExtra("intent_bday", "$Bday")
                intent.putExtra("intent_appointdate", "$AppointDate")
                intent.putExtra("intent_datetrans", "$DateTrans")
                intent.putExtra("intent_appointtime", "$AppointTime")
                intent.putExtra("intent_timetrans", "$TimeTrans")
                intent.putExtra("intent_status", "$Status")
                startActivity(intent)}

            //-----------------------Sending intent data---------------------------------




        }
    }

    private inner class ViewPointFirestoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<AppointmentModel>) : FirestoreRecyclerAdapter<AppointmentModel, ViewPointViewHolder>(options) {
        override fun onBindViewHolder(viewPointViewHolder: ViewPointViewHolder, position: Int, viewpointModel: AppointmentModel) {



            viewPointViewHolder.setViewPoint(viewpointModel.Fname, viewpointModel.Mname, viewpointModel.Lname, viewpointModel.Sex, viewpointModel.Bday,
                viewpointModel.AppointDate, viewpointModel.DateTrans, viewpointModel.AppointTime, viewpointModel.TimeTrans, viewpointModel.Docu,
                viewpointModel.Doctor, viewpointModel.DocPrac, viewpointModel.Address, viewpointModel.Host, viewpointModel.Status)


        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPointViewHolder {

            val view = LayoutInflater.from(parent.context).inflate(R.layout.sched_appointment, parent, false)
            return ViewPointViewHolder(view)

        }
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()

        viewpoint_recyclerview.adapter = adapter
    }

    override fun onStop() {
        super.onStop()

        if (adapter != null) {
            adapter!!.stopListening()
        }
    }
}
