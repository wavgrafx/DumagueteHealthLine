package lab.wavgrafx.com.dumaguetehealthline

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_da__appointments.*
import java.nio.channels.spi.AbstractSelectableChannel
import java.text.SimpleDateFormat
import java.util.*

class DA_Appointments : AppCompatActivity() {

    lateinit var selectedbyuser: String
    lateinit var dayOfTheWeek:String
    lateinit var xxdMonth:String
    lateinit var appoint_dayOfWeek:TextView
    lateinit var intdoctor:String
    lateinit var intspecialty:String
    lateinit var intaddress:String
    lateinit var finalselect:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_da__appointments)

        val bundle:Bundle=intent.extras
        if(bundle!=null)
        {
            intdoctor = bundle.getString("nameofDoc")
            intspecialty = bundle.getString("pracofDoc")
            intaddress =  bundle.getString("addofDoc")

        }

        appoint_dayOfWeek = findViewById(R.id.appoint_dayOfWeek)

        calendarView?.setOnDateChangeListener { view, year, month, dayOfMonth ->


            var cal = Calendar.getInstance()
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy.MM.dd" // mention the format you need

            val sdf = SimpleDateFormat(myFormat, Locale.US)
            selectedbyuser = sdf.format(cal.time)
            val ddd = SimpleDateFormat("EEEE")
            val d = cal.time
            dayOfTheWeek = ddd.format(d)



                val xdMonth = selectedbyuser.substring(5,7)
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
                val xdYear = selectedbyuser.substring(0,4)
                val xdDay = selectedbyuser.substring(8,10)
                finalselect = "$xxdMonth $xdDay, $xdYear, $dayOfTheWeek"
                val msg = "You selected $finalselect"
                Toast.makeText(this@DA_Appointments, msg, Toast.LENGTH_LONG).show()
                appoint_dayOfWeek.text = finalselect
        }

        btn_ViewAppointDate.setOnClickListener {
            if (appoint_dayOfWeek.text.isEmpty()){
                Toast.makeText(this, "Please select a date", Toast.LENGTH_LONG).show()
            }else{
                val intent = Intent(this@DA_Appointments, DA_ViewAppointment::class.java)
                intent.putExtra("dateselected", "$selectedbyuser")
                intent.putExtra("intdoctor", "$intdoctor")
                intent.putExtra("intspecialty", "$intspecialty")
                intent.putExtra("intaddress", "$intaddress")
                intent.putExtra("inttransdate", "$finalselect")
                startActivity(intent)
            }
        }
    }
}
