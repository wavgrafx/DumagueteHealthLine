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
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_da__clinic_queue.*
import lab.wavgrafx.com.dumaguetehealthline.model.ClinicModel

class DA_SelectClinic : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var rootRef: DocumentReference
    private var adapter: DA_SelectClinic.ClinicFirestoreRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_da__select_clinic)
        val currentuser = FirebaseAuth.getInstance().currentUser!!.phoneNumber.toString()
        mAuth = FirebaseAuth.getInstance()
        recycler_view.layoutManager = LinearLayoutManager(this)

        rootRef = FirebaseFirestore.getInstance().document("Clinics/List")
        val query = rootRef!!.collection("DoctorsList").whereEqualTo("Creator", currentuser)

        val options = FirestoreRecyclerOptions.Builder<ClinicModel>().setQuery(query, ClinicModel::class.java).build()
        adapter = ClinicFirestoreRecyclerAdapter(options)

    }



    private inner class ClinicViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
        internal fun setClinicDetails(FName: String, MName: String, LName: String, Gender: String, Degree: String, Practice: String,
                                      CNum: String, Cert: String, Address: String, Mon: Boolean, Tue: Boolean, Wed: Boolean,
                                      Thu: Boolean, Fri: Boolean, Sat: Boolean, Sun: Boolean, MonOpen: String, MonClose: String,
                                      TueOpen: String, TueClose: String, WedOpen: String, WedClose: String, ThuOpen: String,
                                      ThuClose: String, FriOpen: String, FriClose: String, SatOpen: String, SatClose: String,
                                      SunOpen: String, SunClose: String, Creator: String) {




            val textViewName = view.findViewById<TextView>(R.id.textViewName)
            val textViewMedPractice = view.findViewById<TextView>(R.id.textViewMedPractice)
            val textViewClinicAddress = view.findViewById<TextView>(R.id.textViewClinicAddress)
            val availMON = view.findViewById<TextView>(R.id.availMON)
            val availTUE = view.findViewById<TextView>(R.id.availTUE)
            val availWED = view.findViewById<TextView>(R.id.availWED)
            val availTHU = view.findViewById<TextView>(R.id.availTHU)
            val availFRI = view.findViewById<TextView>(R.id.availFRI)
            val availSAT = view.findViewById<TextView>(R.id.availSAT)
            val availSUN = view.findViewById<TextView>(R.id.availSUN)
            val malefemaleavatar = view.findViewById<ImageView>(R.id.malefemaleavatar)
            val clinicUpdate = view.findViewById<LinearLayout>(R.id.openclinicUpdate)
            val textView_ContNum = view.findViewById<TextView>(R.id.textView_ContNum)
            val recycler_view = view.findViewById<RecyclerView>(R.id.recycler_view)
            val fullNameOf = view.findViewById<TextView>(R.id.docName)





            textViewName.text = ("Dr. $FName ${MName.substring(0, 1)}. $LName, $Degree")
            textViewMedPractice.text = Practice
            textViewClinicAddress.text = Address
            textView_ContNum.text = "Contact Number: $CNum"
            fullNameOf.text = FName + MName + LName
            var fullName_intent:String = fullNameOf.text.toString()

            if (Mon == false){ availMON.setTextColor(Color.parseColor("#50ffffff")) }
            if (Tue == false){ availTUE.setTextColor(Color.parseColor("#50ffffff")) }
            if (Wed == false){ availWED.setTextColor(Color.parseColor("#50ffffff")) }
            if (Thu == false){ availTHU.setTextColor(Color.parseColor("#50ffffff")) }
            if (Fri == false){ availFRI.setTextColor(Color.parseColor("#50ffffff")) }
            if (Sat == false){ availSAT.setTextColor(Color.parseColor("#50ffffff")) }
            if (Sun == false){ availSUN.setTextColor(Color.parseColor("#50ffffff")) }

            if (Gender == "Male"){
                malefemaleavatar.setImageResource(R.drawable.male_avatar)
            }else{
                malefemaleavatar.setImageResource(R.drawable.female_avatar)
            }

            view.setOnClickListener {
                val intent = Intent(this@DA_SelectClinic, DA_Appointments::class.java)
                intent.putExtra("docuName", "$fullName_intent")
                intent.putExtra("genderofDoc", "$Gender")
                intent.putExtra("nameofDoc", "Dr. $FName ${MName.substring(0,1)}. $LName, $Degree")
                intent.putExtra("pracofDoc", "$Practice")
                intent.putExtra("telofDoc", "$CNum")
                intent.putExtra("certofDoc", "$Cert")
                intent.putExtra("addofDoc", "$Address")
                startActivity(intent)
            }
        }
    }

    private inner class ClinicFirestoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<ClinicModel>) : FirestoreRecyclerAdapter<ClinicModel, ClinicViewHolder>(options) {
        override fun onBindViewHolder(clinicViewHolder: ClinicViewHolder, position: Int, clinicModel: ClinicModel) {



            clinicViewHolder.setClinicDetails(clinicModel.Fname, clinicModel.Mname, clinicModel.Lname, clinicModel.Gender, clinicModel.Degree,
                clinicModel.Practice, clinicModel.CNum, clinicModel.Cert, clinicModel.Address, clinicModel.Mon, clinicModel.Tue,
                clinicModel.Wed, clinicModel.Thu, clinicModel.Fri, clinicModel.Sat, clinicModel.Sun, clinicModel.MonOpen,
                clinicModel.MonClose, clinicModel.TueOpen, clinicModel.TueClose, clinicModel.WedOpen, clinicModel.WedClose,
                clinicModel.ThuOpen, clinicModel.ThuClose, clinicModel.FriOpen, clinicModel.FriClose, clinicModel.SatOpen,
                clinicModel.SatClose, clinicModel.SunOpen, clinicModel.SunClose,
                clinicModel.Creator)


        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicViewHolder {

            val view = LayoutInflater.from(parent.context).inflate(R.layout.da_clinics, parent, false)
            return ClinicViewHolder(view)

        }

    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()

        recycler_view.adapter = adapter
    }

    override fun onStop() {
        super.onStop()

        if (adapter != null) {
            adapter!!.stopListening()
        }
    }
}