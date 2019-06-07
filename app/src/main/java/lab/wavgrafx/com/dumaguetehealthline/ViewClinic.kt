package lab.wavgrafx.com.dumaguetehealthline

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_view_clinic.*

class ViewClinic : AppCompatActivity() {
    lateinit var fullName: String
    lateinit var medPractice: String
    lateinit var address: String
    lateinit var documName:String
    lateinit var monday: String
    lateinit var tuesday: String
    lateinit var wednesday: String
    lateinit var thursday: String
    lateinit var friday: String
    lateinit var saturday: String
    lateinit var sunday:String
    lateinit var openofMon: String
    lateinit var closeofMon: String
    lateinit var openofTue: String
    lateinit var closeofTue: String
    lateinit var openofWed: String
    lateinit var closeofWed: String
    lateinit var openofThu: String
    lateinit var closeofThu: String
    lateinit var openofFri: String
    lateinit var closeofFri: String
    lateinit var openofSat: String
    lateinit var closeofSat: String
    lateinit var openofSun: String
    lateinit var closeofSun: String
    lateinit var break1: String
    lateinit var break2: String
    lateinit var break1start: String
    lateinit var break1end: String
    lateinit var break2start: String
    lateinit var break2end: String
    lateinit var maxpat:String
    lateinit var allowedBookDays:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_clinic)


        val bundle:Bundle=intent.extras
        if(bundle!=null)
        {
            documName = bundle.getString("docuName")
            fullName = bundle.getString("nameofDoc")
            medPractice =  bundle.getString("pracofDoc")
            var contactnumber = bundle.getString("telofDoc")
            var certificates = bundle.getString("certofDoc")
            address = bundle.getString("addofDoc")
            var docGender = bundle.getString("genderofDoc")

            if (docGender == "Male"){
                view_profileAvatar.setImageResource(R.drawable.male_avatar)
            }else{
                view_profileAvatar.setImageResource(R.drawable.female_avatar)
            }
            view_fullName.text = fullName
            view_medPractice.text = medPractice
            view_Contact.text = contactnumber
            view_Cert.text = certificates
            view_Address.text = address

            var monofDoc = bundle.getString("monofDoc")
            var tueofDoc = bundle.getString("tueofDoc")
            var wedofDoc = bundle.getString("wedofDoc")
            var thuofDoc = bundle.getString("thuofDoc")
            var friofDoc = bundle.getString("friofDoc")
            var satofDoc = bundle.getString("satofDoc")
            var sunofDoc = bundle.getString("sunofDoc")

            monday = bundle.getString("monofDoc")
            tuesday = bundle.getString("tueofDoc")
            wednesday = bundle.getString("wedofDoc")
            thursday = bundle.getString("thuofDoc")
            friday = bundle.getString("friofDoc")
            saturday = bundle.getString("satofDoc")
            sunday = bundle.getString("sunofDoc")

            openofMon = bundle.getString("openofMon")
            closeofMon = bundle.getString("closeofMon")
            openofTue = bundle.getString("openofTue")
            closeofTue = bundle.getString("closeofTue")
            openofWed = bundle.getString("openofWed")
            closeofWed = bundle.getString("closeofWed")
            openofThu = bundle.getString("openofThu")
            closeofThu = bundle.getString("closeofThu")
            openofFri = bundle.getString("openofFri")
            closeofFri = bundle.getString("closeofFri")
            openofSat = bundle.getString("openofSat")
            closeofSat = bundle.getString("closeofSat")
            openofSun = bundle.getString("openofSun")
            closeofSun = bundle.getString("closeofSun")

            break1 = bundle.getString("Break1")
            break2 = bundle.getString("Break2")
            break1start = bundle.getString("Break1start")
            break1end = bundle.getString("Break1end")
            break2start = bundle.getString("Break2start")
            break2end = bundle.getString("Break2end")


            maxpat = bundle.getString("maxpat")
            allowedBookDays = bundle.getString("allowedBookDays")



            if(monofDoc == "false"){
                view_availMON.setTextColor(Color.parseColor("#50ffffff"))
                monopentime.setText("-")
                monclosetime.setText("-")
            }else{
                monopentime.setText("$openofMon")
                monclosetime.setText("$closeofMon")
            }


            if(tueofDoc == "false"){
                view_availTUE.setTextColor(Color.parseColor("#50ffffff"))
                tueopentime.setText("-")
                tueclosetime.setText("-")
            }else{
                tueopentime.setText("$openofTue")
                tueclosetime.setText("$closeofTue")
            }

            if(wedofDoc == "false"){
                view_availWED.setTextColor(Color.parseColor("#50ffffff"))
                wedopentime.setText("-")
                wedclosetime.setText("-")
            }else{
                wedopentime.setText("$openofWed")
                wedclosetime.setText("$closeofWed")
            }

            if(thuofDoc == "false"){
                view_availTHU.setTextColor(Color.parseColor("#50ffffff"))
                thuopentime.setText("-")
                thuclosetime.setText("-")
            }else{
                thuopentime.setText("$openofThu")
                thuclosetime.setText("$closeofThu")
            }

            if(friofDoc == "false"){
                view_availFRI.setTextColor(Color.parseColor("#50ffffff"))
                friopentime.setText("-")
                friclosetime.setText("-")
            }else{
                friopentime.setText("$openofFri")
                friclosetime.setText("$closeofFri")
            }

            if(satofDoc == "false"){
                view_availSAT.setTextColor(Color.parseColor("#50ffffff"))
                satopentime.setText("-")
                satclosetime.setText("-")
            }else{
                satopentime.setText("$openofSat")
                satclosetime.setText("$closeofSat")
            }

            if(sunofDoc == "false"){
                view_availSUN.setTextColor(Color.parseColor("#50ffffff"))
                sunopentime.setText("-")
                sunclosetime.setText("-")
            }else{
                sunopentime.setText("$openofSun")
                sunclosetime.setText("$closeofSun")
            }


//            view_openingTime.text = bundle.getString("openofDoc")
//            view_closingTime.text = bundle.getString("closeofDoc")

        }

        view_location.setOnClickListener {  view: View? -> progressBar.visibility = View.VISIBLE
            val intent = Intent(this@ViewClinic,Locator::class.java)

            intent.putExtra("nameofDoc", "$fullName")
            intent.putExtra("pracofDoc", "$medPractice")
            intent.putExtra("addofDoc", "$address")
            startActivity(intent)
            progressBar.visibility = View.INVISIBLE

        }

        btnGetAppointment.setOnClickListener { view: View? -> progressBar.visibility = View.VISIBLE
            val intent = Intent(this@ViewClinic,GetAppointment::class.java)

            intent.putExtra("nameofDoc", "$fullName")
            intent.putExtra("pracofDoc", "$medPractice")
            intent.putExtra("addofDoc", "$address")
            intent.putExtra("docuName", "$documName")
            intent.putExtra("Monday", "$monday")
            intent.putExtra("Tuesday", "$tuesday")
            intent.putExtra("Wednesday", "$wednesday")
            intent.putExtra("Thursday", "$thursday")
            intent.putExtra("Friday", "$friday")
            intent.putExtra("Saturday", "$saturday")
            intent.putExtra("Sunday", "$sunday")
            intent.putExtra("openofMon", "$openofMon")
            intent.putExtra("closeofMon", "$closeofMon")
            intent.putExtra("openofTue", "$openofTue")
            intent.putExtra("closeofTue", "$closeofTue")
            intent.putExtra("openofWed", "$openofWed")
            intent.putExtra("closeofWed", "$closeofWed")
            intent.putExtra("openofThu", "$openofThu")
            intent.putExtra("closeofThu", "$closeofThu")
            intent.putExtra("openofFri", "$openofFri")
            intent.putExtra("closeofFri", "$closeofFri")
            intent.putExtra("openofSat", "$openofSat")
            intent.putExtra("closeofSat", "$closeofSat")
            intent.putExtra("openofSun", "$openofSun")
            intent.putExtra("closeofSun", "$closeofSun")
            intent.putExtra("Break1", "$break1")
            intent.putExtra("Break2", "$break2")
            intent.putExtra("Break1start", "$break1start")
            intent.putExtra("Break1end", "$break1end")
            intent.putExtra("Break2start", "$break2start")
            intent.putExtra("Break2end", "$break2end")
            intent.putExtra("maxpat", "$maxpat")
            intent.putExtra("allowedBookDays", "$allowedBookDays")



            startActivity(intent)
            progressBar.visibility = View.INVISIBLE
        }

        btnMessage.setOnClickListener { view: View? -> progressBar.visibility = View.VISIBLE
            val intent = Intent(this@ViewClinic,Messenger::class.java)

            intent.putExtra("nameofDoc", "$fullName")
            intent.putExtra("addofDoc", "$address")
            startActivity(intent)
            progressBar.visibility = View.INVISIBLE

        }

        btnCloseDocView.setOnClickListener { view: View? -> progressBar.visibility = View.VISIBLE
            finish()
            progressBar.visibility = View.INVISIBLE

        }
    }
}
