package lab.wavgrafx.com.dumaguetehealthline

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
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
import kotlinx.android.synthetic.main.da_sched_queue.view.*
import lab.wavgrafx.com.dumaguetehealthline.model.AppointmentModel
import java.text.SimpleDateFormat
import java.util.*



class DA_ViewQueue : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var db:DocumentReference
    lateinit var rootRef: DocumentReference
    private var adapter: DA_ViewQueue.ViewQueueFirestoreRecyclerAdapter? = null
    lateinit var xxdMonth: String
    lateinit var currentuser: String
    lateinit var dateselect:String
    lateinit var dayOfTheWeek: String
    lateinit var supertimer: String

    //-----------------------declaring intent variables-----------------------
    lateinit var intdoctor: String
    lateinit var intspecialty: String
    lateinit var intaddress: String
    lateinit var prevtime:String
    //-----------------------declaring intent variables-----------------------------


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_da__view_queue)
        supertimer = "false"
        prevtime = ""




        currentuser = FirebaseAuth.getInstance().currentUser!!.phoneNumber.toString()
        mAuth = FirebaseAuth.getInstance()
        viewqueue_recyclerview.layoutManager = LinearLayoutManager(this)


        //---------------------------------Appointment Date Declarations---------------------------------
        val appoint_selectDate: TextView  = findViewById(R.id.viewqueue_Date)
        val currentlocaldate = SimpleDateFormat("yyyy.MM.dd").format(System.currentTimeMillis())
        viewqueue_Date.text = currentlocaldate
        dateselect = currentlocaldate
        val ddd = SimpleDateFormat("EEEE")
        val d = Date()
        dayOfTheWeek = ddd.format(d)

        var Qyear = dateselect.substring(0,4)
        var Qmonth =  dateselect.substring(5,7)
        var Qday =  dateselect.substring(8,10)
        var QmonthName = ""

        if (Qmonth == "01"){QmonthName = "January"}
        if (Qmonth == "02"){QmonthName = "February"}
        if (Qmonth == "03"){QmonthName = "March"}
        if (Qmonth == "04"){QmonthName = "April"}
        if (Qmonth == "05"){QmonthName = "May"}
        if (Qmonth == "06"){QmonthName = "June"}
        if (Qmonth == "07"){QmonthName = "July"}
        if (Qmonth == "08"){QmonthName = "August"}
        if (Qmonth == "09"){QmonthName = "September"}
        if (Qmonth == "10"){QmonthName = "October"}
        if (Qmonth == "11"){QmonthName = "November"}
        if (Qmonth == "12"){QmonthName = "December"}

        var QDate = "$QmonthName $Qday, $Qyear, $dayOfTheWeek"

        viewqueue_Date.text = QDate

        val xdMonth = currentlocaldate.substring(5,7)
        if (xdMonth == "01"){xxdMonth = "January"}
        if (xdMonth == "02"){xxdMonth = "February"}
        if (xdMonth == "03"){xxdMonth = "March"}
        if (xdMonth == "04"){xxdMonth = "April"}
        if (xdMonth == "05"){xxdMonth = "May"}
        if (xdMonth == "06"){xxdMonth = "June"}
        if (xdMonth == "07"){xxdMonth = "July"}
        if (xdMonth == "08"){xxdMonth = "August"}
        if (xdMonth == "09"){xxdMonth = "September"}
        if (xdMonth == "10"){xxdMonth = "October"}
        if (xdMonth == "11"){xxdMonth = "November"}
        if (xdMonth == "12"){xxdMonth = "September"}
        val xdYear = currentlocaldate.substring(0,4)
        val xdDay = currentlocaldate.substring(8,10)
        viewqueue_Date.text = "$xxdMonth $xdDay, $xdYear, $dayOfTheWeek"




//--------------------------------------------------------------------------------------------------


//-------------------------Retrieving Intent data----------------------------
        val bundle:Bundle=intent.extras
        if(bundle!=null)
        {
            intdoctor = bundle.getString("nameofDoc")
            intspecialty = bundle.getString("pracofDoc")
            intaddress =  bundle.getString("addofDoc")

        }
//-------------------------Retrieving Intent data----------------------------



        viewqueue_DoctorName.text = intdoctor
        viewqueue_Specialty.text = intspecialty


        rootRef = FirebaseFirestore.getInstance().document("Clinics/Appointments")
        val query = rootRef!!.collection("AppointmentList")
            .whereEqualTo("Doctor",intdoctor)
            .whereEqualTo("Status", "PENDING")
            .whereEqualTo("AppointDate",dateselect)
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

            viewqueue_time.text = TimeTrans
            if (prevtime == TimeTrans){
                date_hider.visibility = View.INVISIBLE
                date_hider.layoutParams = LinearLayout.LayoutParams(0, 0)
                prevtime = TimeTrans
            }
            prevtime = TimeTrans


            viewqueue_patientname.text = "$FName ${MName.substring(0,1)}. $LName"
            viewqueue_status.text = Status
            viewqueue_sex.text = Sex
            viewqueue_bday.text = Bday
            viewqueue_host.text = Host
            viewqueue_time.text = TimeTrans


            view.viewqueue_done.setOnClickListener {
                val items = HashMap<String, Any>()
                var documentName:String = FName + MName + LName
                items.put("Status", "DONE")
                var itemdb = FirebaseFirestore.getInstance().document("Clinics/Appointments")
                var itemref = itemdb.collection("AppointmentList").document(documentName)
                    .update("Status", "DONE")
                    .addOnSuccessListener {Toast.makeText(this@DA_ViewQueue, "DocumentSnapshot successfully updated!", Toast.LENGTH_SHORT).show()}
                    .addOnFailureListener { e -> Toast.makeText(this@DA_ViewQueue, e.toString(), Toast.LENGTH_SHORT).show() }
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

            val view = LayoutInflater.from(parent.context).inflate(R.layout.da_sched_queue, parent, false)
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

