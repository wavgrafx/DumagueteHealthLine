package lab.wavgrafx.com.dumaguetehealthline

import android.content.Intent
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
import android.widget.Toast
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_contacts.*
import lab.wavgrafx.com.dumaguetehealthline.model.ContactsModel

class Contacts : AppCompatActivity() {


    lateinit var mAuth: FirebaseAuth
    lateinit var rootRef: DocumentReference
    private var adapter: Contacts.ClinicFirestoreRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)


        val currentuser = FirebaseAuth.getInstance().currentUser!!.phoneNumber.toString()
        mAuth = FirebaseAuth.getInstance()
        recycler_view.layoutManager = LinearLayoutManager(this)

        rootRef = FirebaseFirestore.getInstance().document("Clinics/List")
        val query = rootRef!!.collection("DoctorsList").orderBy("Practice", Query.Direction.ASCENDING)

        val options = FirestoreRecyclerOptions.Builder<ContactsModel>().setQuery(query, ContactsModel::class.java).build()
        adapter = ClinicFirestoreRecyclerAdapter(options)

    }
    private inner class ClinicViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
        internal fun setClinicDetails(FName: String, MName: String, LName: String, Gender: String, Degree: String, Practice: String,
                                      CNum: String, Cert: String, Address: String, Mon: Boolean, Tue: Boolean, Wed: Boolean,
                                      Thu: Boolean, Fri: Boolean, Sat: Boolean, Sun: Boolean, Creator: String) {



            val textViewName = view.findViewById<TextView>(R.id.textViewName)
            val textViewMedPractice = view.findViewById<TextView>(R.id.textViewMedPractice)
            val textViewClinicAddress = view.findViewById<TextView>(R.id.textViewClinicAddress)
            val malefemaleavatar = view.findViewById<ImageView>(R.id.malefemaleavatar)
            val clinicUpdate = view.findViewById<LinearLayout>(R.id.openclinicUpdate)
            val textView_ContNum = view.findViewById<TextView>(R.id.textView_ContNum)
            val recycler_view = view.findViewById<RecyclerView>(R.id.recycler_view)




            textViewName.text = ("Dr. $FName ${MName.substring(0, 1)}. $LName, $Degree")
            textViewMedPractice.text = Practice
            textViewClinicAddress.text = Address
            textView_ContNum.text = "Contact Number: $CNum"


            if (Gender == "Male"){
                malefemaleavatar.setImageResource(R.drawable.male_avatar)
            }else{
                malefemaleavatar.setImageResource(R.drawable.female_avatar)
            }

            view.setOnClickListener {
                val intent = Intent(this@Contacts, Messenger::class.java)
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

    private inner class ClinicFirestoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<ContactsModel>) : FirestoreRecyclerAdapter<ContactsModel, ClinicViewHolder>(options) {
        override fun onBindViewHolder(clinicViewHolder: ClinicViewHolder, position: Int, clinicModel: ContactsModel) {



            clinicViewHolder.setClinicDetails(clinicModel.Fname, clinicModel.Mname, clinicModel.Lname, clinicModel.Gender, clinicModel.Degree,
                clinicModel.Practice, clinicModel.CNum, clinicModel.Cert, clinicModel.Address, clinicModel.Mon, clinicModel.Tue,
                clinicModel.Wed, clinicModel.Thu, clinicModel.Fri, clinicModel.Sat, clinicModel.Sun, clinicModel.Creator)


        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicViewHolder {

            val view = LayoutInflater.from(parent.context).inflate(R.layout.contacts, parent, false)
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
