package lab.wavgrafx.com.dumaguetehealthline

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_get_appointment.*
import android.widget.RadioGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import android.widget.DatePicker
import com.google.firebase.firestore.FieldValue
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList
import android.widget.Toast
import android.widget.RadioButton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import lab.wavgrafx.com.dumaguetehealthline.model.SlotsModel
import java.lang.reflect.Member


class GetAppointment : AppCompatActivity() {



    lateinit var fullName: String
    lateinit var medPractice: String
    lateinit var address: String
    lateinit var monday: String
    lateinit var tuesday: String
    lateinit var wednesday: String
    lateinit var thursday: String
    lateinit var friday: String
    lateinit var saturday: String
    lateinit var sunday: String
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
    lateinit var appoint_dayOfWeek:TextView
    lateinit var appoint_translate:TextView
    lateinit var maxpat:String
    lateinit var intslots:Any
    lateinit var appoint_slots:TextView
    lateinit var allowedBookDays:String
    lateinit var calendarView: CalendarView
    lateinit var selectedbyuser: String
    lateinit var currentlocaldate:String
    lateinit var dayOfTheWeek:String
    lateinit var viewnotempty:String
    lateinit var selectedappointtime:String
    lateinit var finalstime:Date
    lateinit var finaletime:Date
    lateinit var options1:Array<String>
    lateinit var options2:Array<String>
    lateinit var options3:Array<String>
    lateinit var options4:Array<String>
    lateinit var options5:Array<String>
    lateinit var options6:Array<String>
    lateinit var options7:Array<String>
    lateinit var timeloop1:Calendar
    lateinit var timeloop2:Calendar
    lateinit var appoint_txtview_time:TextView
    lateinit var ll_main:LinearLayout
    lateinit var rg:RadioGroup
    var previousdate:Long = 0
    lateinit var appoint_edittext_fname: EditText
    lateinit var appoint_edittext_mname: EditText
    lateinit var appoint_edittext_lname: EditText
    lateinit var appoint_titleofdate: TextView
    lateinit var appoint_back: Button
    lateinit var appoint_saveAppointment: Button
    lateinit var appoint_pBar: ProgressBar
    lateinit var xxdMonth: String

    lateinit var selectedGender: String
    lateinit var selected_bday:String
    lateinit var documName:String
    lateinit var currentuser:String

    lateinit var mAuth: FirebaseAuth
    lateinit var db: DocumentReference
    lateinit var slotsdb: DocumentReference
    lateinit var slotsrootRef: DocumentReference
    lateinit var rootRef: DocumentReference
    lateinit var collpath:String
    lateinit var docpath:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_appointment)
        collpath = ""
        docpath = ""



        viewnotempty = "false"
        appoint_txtview_time = findViewById(R.id.appoint_txtview_time)


        appoint_bday.maxDate = Calendar.getInstance().timeInMillis

        val bundle:Bundle=intent.extras
        if(bundle!=null)
        {
            documName = bundle.getString("docuName")
            fullName = bundle.getString("nameofDoc")
            medPractice =  bundle.getString("pracofDoc")
            address = bundle.getString("addofDoc")
            monday = bundle.getString("Monday")
            tuesday = bundle.getString("Tuesday")
            wednesday = bundle.getString("Wednesday")
            thursday = bundle.getString("Thursday")
            friday = bundle.getString("Friday")
            saturday = bundle.getString("Saturday")
            sunday = bundle.getString("Sunday")

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

            options1 = arrayOf("none")
            options2 = arrayOf("none")
            options3 = arrayOf("none")
            options4 = arrayOf("none")
            options5 = arrayOf("none")
            options6 = arrayOf("none")
            options7 = arrayOf("none")



            appoint_NameofDoctor.text = fullName
            appoint_Practice.text = medPractice
            appoint_Address.text = address


        }

        appoint_slots = findViewById(R.id.appoint_slots)
        appoint_translate = findViewById(R.id.appoint_translate)
        appoint_dayOfWeek = findViewById(R.id.appoint_dayOfWeek)
        appoint_edittext_fname = findViewById(R.id.appoint_edittext_fname)
        appoint_edittext_mname = findViewById(R.id.appoint_edittext_mname)
        appoint_edittext_lname = findViewById(R.id.appoint_edittext_lname)
        appoint_titleofdate = findViewById(R.id.appoint_titleofdate)
        calendarView = findViewById(R.id.calendarView)
        selectedbyuser = ""
        calendarView.firstDayOfWeek = 2



        val now = Calendar.getInstance()
        now.set(Calendar.YEAR, SimpleDateFormat("yyyy").format(System.currentTimeMillis()).toInt())
        now.set(Calendar.MONTH, SimpleDateFormat("MM").format(System.currentTimeMillis()).toInt())
        now.set(Calendar.DAY_OF_MONTH, SimpleDateFormat("dd").format(System.currentTimeMillis()).toInt())


        val maxnow = Calendar.getInstance()
        maxnow.set(Calendar.YEAR, SimpleDateFormat("yyyy").format(System.currentTimeMillis()).toInt())
        maxnow.set(Calendar.MONTH, SimpleDateFormat("MM").format(System.currentTimeMillis()).toInt())
        maxnow.set(Calendar.DAY_OF_MONTH, SimpleDateFormat("dd").format(System.currentTimeMillis()).toInt())
        var limit = allowedBookDays.toInt()
        maxnow.add(Calendar.MONTH, -1)
        maxnow.add(Calendar.DAY_OF_MONTH, limit)


        var minnow = Calendar.getInstance()

        val myFormat = "yyyy.MM.dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        now.add(Calendar.MONTH, -1)
        selectedbyuser = sdf.format(now.time)
        collpath = selectedbyuser


        do {

            val ddd = SimpleDateFormat("EEEE")
            dayOfTheWeek = ddd.format(now.time)
            currentlocaldate = sdf.format(now.time)
            now.add(Calendar.DAY_OF_MONTH, 1)
            previousdate = now.timeInMillis
            minnow.timeInMillis = now.timeInMillis
            minnow.add(Calendar.DAY_OF_MONTH, -1)
            calendarView.minDate = minnow.timeInMillis
            calendarView.date = minnow.timeInMillis
            previousdate = minnow.timeInMillis



        }
        while
        (dayOfTheWeek == "Monday" && monday == "false" ||
            dayOfTheWeek == "Tuesday" && tuesday == "false" ||
            dayOfTheWeek == "Wednesday" && wednesday == "false" ||
            dayOfTheWeek == "Thursday" && thursday == "false" ||
            dayOfTheWeek == "Friday" && friday == "false" ||
            dayOfTheWeek == "Saturday" && saturday == "false" ||
            dayOfTheWeek == "Sunday" && sunday == "false")

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

        calendarView.maxDate = maxnow.timeInMillis

        appoint_dayOfWeek.text = "$xxdMonth $xdDay, $xdYear, $dayOfTheWeek"
        getdays()






//---------------------------------Appointment Date Declarations---------------------------------



        calendarView.setOnClickListener {
            appoint_edittext_fname.clearFocus()
            appoint_edittext_mname.clearFocus()
            appoint_edittext_lname.clearFocus()
        }



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


            if (dayOfTheWeek == "Monday" && monday == "false" ||
                dayOfTheWeek == "Tuesday" && tuesday == "false" ||
                dayOfTheWeek == "Wednesday" && wednesday == "false" ||
                dayOfTheWeek == "Thursday" && thursday == "false" ||
                dayOfTheWeek == "Friday" && friday == "false" ||
                dayOfTheWeek == "Saturday" && saturday == "false" ||
                dayOfTheWeek == "Sunday" && sunday == "false"){

                Toast.makeText(this, "Clinic is close during this day. Please select another date.", Toast.LENGTH_LONG).show()
                calendarView.date = previousdate

            }else{
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
                var finalselect = "$xxdMonth $xdDay, $xdYear, $dayOfTheWeek"
                val msg = "You selected $finalselect"
                appoint_titleofdate.text = "Selected Appointment Date"
                Toast.makeText(this@GetAppointment, msg, Toast.LENGTH_LONG).show()
                appoint_dayOfWeek.text = finalselect
                previousdate = cal.timeInMillis
                appoint_edittext_fname.clearFocus()
                appoint_edittext_mname.clearFocus()
                appoint_edittext_lname.clearFocus()
                getdays()
            }
//--------------------------------------------------------------------------------------------------



        }




        selectedGender = "Male"
        selected_bday = ""
        mAuth = FirebaseAuth.getInstance()
        currentuser = FirebaseAuth.getInstance().currentUser!!.phoneNumber.toString()
        db = FirebaseFirestore.getInstance().document("Clinics/Appointments")

        appoint_back = findViewById(R.id.appoint_back)
        appoint_saveAppointment = findViewById(R.id.appoint_saveAppointment)
        appoint_pBar = findViewById(R.id.appoint_pBar)


        appoint_saveAppointment.setOnClickListener {

                view: View? ->
            if (collpath == "" || docpath == ""){
                Toast.makeText(this@GetAppointment, "Please check the Appointment Form", Toast.LENGTH_LONG).show()
            }else{
                slotsdb = FirebaseFirestore.getInstance().document("Appointment/Slot")
                slotsrootRef = slotsdb.collection("$collpath").document("$docpath")
                slotsrootRef.get()
                slotsrootRef.get().addOnCompleteListener(OnCompleteListener<DocumentSnapshot> { task ->
                    if (task.isSuccessful) {
                        val document = task.result!!.data
                        if (document != null) {
//                                            Log.d(TAG, "DocumentSnapshot data: " + task.result.data)
                            intslots = document.getValue("slots")
                            if (intslots == "0"){
                                appoint_slots.setText("$intslots")
                                Toast.makeText(this, "Selected time slot if full. Please select another schedule.", Toast.LENGTH_SHORT).show()
                            }else{
                                saveAppointment()
                            }

                        } else {
                            Toast.makeText(this, "No such document", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "There's an error accessing the document.", Toast.LENGTH_SHORT).show()
                    }
                })
            }

        }



        appoint_back.setOnClickListener {
            finish()
        }




        appoint_radio_gender.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                selectedGender = radio.text.toString()
            })






//Date Picker for Birhtday
        val datePicker = findViewById(R.id.appoint_bday) as DatePicker
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        datePicker.init(
            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        ) { datePicker, year, month, dayOfMonth ->

            selected_bday = "" + month + "/" + dayOfMonth + "/" + year


        }


    }


    fun radio_button_click(view: View){ val radio: RadioButton = findViewById(appoint_radio_gender.checkedRadioButtonId) }


    private fun getdays(){


//        **************************************************MONDAY***************************************
        if (dayOfTheWeek == "Monday"){
            if (viewnotempty == "true"){ll_main.removeAllViews()
                appoint_txtview_time.setText("")}
            opentime.text = openofMon
            closetime.text = closeofMon

            var xxxopenofMon = openofMon
            var xxxcloseofMon = closeofMon

            var ohour1 = xxxopenofMon.substring(0,2)
            var ominute1 = xxxopenofMon.substring(3,5)


            var chour1 = xxxcloseofMon.substring(0,2)
            var cminute1 = xxxcloseofMon.substring(3,5)



            var x1 = ohour1.toInt()
            var y1 = chour1.toInt()
            var x2 = x1

            while(x2<y1){

                ll_main = findViewById(R.id.ll_main_layout) as LinearLayout

                options1 = arrayOf("$xxxopenofMon")
                var x3 = ++x2
                var x4 = x3.toString().length
                var x5:String = x3.toString()
                if (x4 == 1){x5 = "0$x3"}
                var x6 = x5
                xxxopenofMon = "$x6:$ominute1"
                options1.plusElement("$xxxopenofMon")


                rg = RadioGroup(this)
                rg.orientation = RadioGroup.VERTICAL
                rg.gravity = Gravity.CENTER
                for (i in options1.indices) {
                    val rb = TextView(this)
                    rb.setBackgroundResource(R.drawable.bg_appoint_time_select)
                    rb.text = options1[i]
                    rb.id = View.generateViewId()
                    rb.gravity = Gravity.CENTER
                    ll_main.addView(rb)

                    rb.setOnClickListener {
                        appoint_edittext_fname.clearFocus()
                        appoint_edittext_mname.clearFocus()
                        appoint_edittext_lname.clearFocus()
                        if (break1 == "true" || break2 == "true"){
                            var a = rb.text.toString().substring(0,2).toInt()
                            var b = break1start.substring(0,2).toInt()
                            var c = break1end.substring(0,2).toInt()

                            var x = rb.text.toString().substring(0,2).toInt()
                            var y = break2start.substring(0,2).toInt()
                            var z = break2end.substring(0,2).toInt()
                            if (a >= b && a < c || x >= y && x < z){
                                Toast.makeText(this, "This is a clinic break time",Toast.LENGTH_LONG).show()
                                appoint_slots.setText("0")
                                appoint_txtview_time.setText("")
                                appoint_translate.setText("")
                            }else{
                                collpath = selectedbyuser.toString()
                                docpath = rb.text.toString()
                                getslotsAvailable()

                                Toast.makeText(this, "You selected ${rb.text.toString()}",Toast.LENGTH_LONG).show()
                                selectedappointtime = rb.text.toString()
                                appoint_txtview_time.setText("$selectedappointtime")
                                var thtrans = rb.text.toString().substring(0,2)
                                var tmtrans = rb.text.toString().substring(3,5)
                                var daytime0 = "AM Midnight"
                                var daytime00 = "AM Nightime"
                                var daytime1 = "AM Morning"
                                var daytime2 = "PM Noon time"
                                var daytime3 = "PM Afternoon"
                                var daytime4 = "PM Evening"
                                if (thtrans == "00"){appoint_translate.setText("12:$tmtrans $daytime0")}
                                if (thtrans == "01"){appoint_translate.setText("1:$tmtrans $daytime00")}
                                if (thtrans == "02"){appoint_translate.setText("2:$tmtrans $daytime00")}
                                if (thtrans == "03"){appoint_translate.setText("3:$tmtrans $daytime00")}
                                if (thtrans == "04"){appoint_translate.setText("4:$tmtrans $daytime00")}
                                if (thtrans == "05"){appoint_translate.setText("5:$tmtrans $daytime00")}
                                if (thtrans == "06"){appoint_translate.setText("6:$tmtrans $daytime1")}
                                if (thtrans == "07"){appoint_translate.setText("7:$tmtrans $daytime1")}
                                if (thtrans == "08"){appoint_translate.setText("8:$tmtrans $daytime1")}
                                if (thtrans == "09"){appoint_translate.setText("9:$tmtrans $daytime1")}
                                if (thtrans == "10"){appoint_translate.setText("10:$tmtrans $daytime1")}
                                if (thtrans == "11"){appoint_translate.setText("11:$tmtrans $daytime1")}
                                if (thtrans == "12"){appoint_translate.setText("12:$tmtrans $daytime2")}
                                if (thtrans == "13"){appoint_translate.setText("1:$tmtrans $daytime3")}
                                if (thtrans == "14"){appoint_translate.setText("2:$tmtrans $daytime3")}
                                if (thtrans == "15"){appoint_translate.setText("3:$tmtrans $daytime3")}
                                if (thtrans == "16"){appoint_translate.setText("4:$tmtrans $daytime3")}
                                if (thtrans == "17"){appoint_translate.setText("5:$tmtrans $daytime3")}
                                if (thtrans == "18"){appoint_translate.setText("6:$tmtrans $daytime4")}
                                if (thtrans == "19"){appoint_translate.setText("7:$tmtrans $daytime4")}
                                if (thtrans == "20"){appoint_translate.setText("8:$tmtrans $daytime4")}
                                if (thtrans == "21"){appoint_translate.setText("9:$tmtrans $daytime4")}
                                if (thtrans == "22"){appoint_translate.setText("10:$tmtrans $daytime4")}
                                if (thtrans == "23"){appoint_translate.setText("11:$tmtrans $daytime4")}
                            }
                        }else{
                            collpath = selectedbyuser.toString()
                            docpath = rb.text.toString()
                            getslotsAvailable()


                            Toast.makeText(this, "You selected ${rb.text.toString()}",Toast.LENGTH_LONG).show()
                            selectedappointtime = rb.text.toString()
                            appoint_txtview_time.setText("$selectedappointtime")
                            var thtrans = rb.text.toString().substring(0,2)
                            var tmtrans = rb.text.toString().substring(3,5)
                            var daytime0 = "AM Midnight"
                            var daytime00 = "AM Nightime"
                            var daytime1 = "AM Morning"
                            var daytime2 = "PM Noon time"
                            var daytime3 = "PM Afternoon"
                            var daytime4 = "PM Evening"
                            if (thtrans == "00"){appoint_translate.setText("12:$tmtrans $daytime0")}
                            if (thtrans == "01"){appoint_translate.setText("1:$tmtrans $daytime00")}
                            if (thtrans == "02"){appoint_translate.setText("2:$tmtrans $daytime00")}
                            if (thtrans == "03"){appoint_translate.setText("3:$tmtrans $daytime00")}
                            if (thtrans == "04"){appoint_translate.setText("4:$tmtrans $daytime00")}
                            if (thtrans == "05"){appoint_translate.setText("5:$tmtrans $daytime00")}
                            if (thtrans == "06"){appoint_translate.setText("6:$tmtrans $daytime1")}
                            if (thtrans == "07"){appoint_translate.setText("7:$tmtrans $daytime1")}
                            if (thtrans == "08"){appoint_translate.setText("8:$tmtrans $daytime1")}
                            if (thtrans == "09"){appoint_translate.setText("9:$tmtrans $daytime1")}
                            if (thtrans == "10"){appoint_translate.setText("10:$tmtrans $daytime1")}
                            if (thtrans == "11"){appoint_translate.setText("11:$tmtrans $daytime1")}
                            if (thtrans == "12"){appoint_translate.setText("12:$tmtrans $daytime2")}
                            if (thtrans == "13"){appoint_translate.setText("1:$tmtrans $daytime3")}
                            if (thtrans == "14"){appoint_translate.setText("2:$tmtrans $daytime3")}
                            if (thtrans == "15"){appoint_translate.setText("3:$tmtrans $daytime3")}
                            if (thtrans == "16"){appoint_translate.setText("4:$tmtrans $daytime3")}
                            if (thtrans == "17"){appoint_translate.setText("5:$tmtrans $daytime3")}
                            if (thtrans == "18"){appoint_translate.setText("6:$tmtrans $daytime4")}
                            if (thtrans == "19"){appoint_translate.setText("7:$tmtrans $daytime4")}
                            if (thtrans == "20"){appoint_translate.setText("8:$tmtrans $daytime4")}
                            if (thtrans == "21"){appoint_translate.setText("9:$tmtrans $daytime4")}
                            if (thtrans == "22"){appoint_translate.setText("10:$tmtrans $daytime4")}
                            if (thtrans == "23"){appoint_translate.setText("11:$tmtrans $daytime4")}
                        }
                    }



                }

            }
            viewnotempty = "true"
        }




//        ************************************************TUESDAY********************************************
        if (dayOfTheWeek == "Tuesday"){
            if (viewnotempty == "true"){ll_main.removeAllViews()
                appoint_txtview_time.setText("")}
            opentime.text = openofTue
            closetime.text = closeofTue

            var xxxopenofTue = openofTue
            var xxxcloseofTue = closeofTue

            var ohour2 = xxxopenofTue.substring(0,2)
            var ominute2 = xxxopenofTue.substring(3,5)


            var chour2 = xxxcloseofTue.substring(0,2)
            var cminute2 = xxxcloseofTue.substring(3,5)



            var x1 = ohour2.toInt()
            var y1 = chour2.toInt()
            var x2 = x1

            while(x2<y1){

                ll_main = findViewById(R.id.ll_main_layout) as LinearLayout

                options2 = arrayOf("$xxxopenofTue")
                var x3 = ++x2
                var x4 = x3.toString().length
                var x5:String = x3.toString()
                if (x4 == 1){x5 = "0$x3"}
                var x6 = x5
                xxxopenofTue = "$x6:$ominute2"
                options2.plusElement("$xxxopenofTue")


                rg = RadioGroup(this)
                rg.orientation = RadioGroup.VERTICAL
                rg.gravity = Gravity.CENTER
                for (i in options2.indices) {
                    val rb = TextView(this)
                    rb.setBackgroundResource(R.drawable.bg_appoint_time_select)
                    rb.text = options2[i]
                    rb.id = View.generateViewId()
                    rb.gravity = Gravity.CENTER
                    ll_main.addView(rb)

                    rb.setOnClickListener {
                        appoint_edittext_fname.clearFocus()
                        appoint_edittext_mname.clearFocus()
                        appoint_edittext_lname.clearFocus()
                        if (break1 == "true" || break2 == "true"){
                            var a = rb.text.toString().substring(0,2).toInt()
                            var b = break1start.substring(0,2).toInt()
                            var c = break1end.substring(0,2).toInt()

                            var x = rb.text.toString().substring(0,2).toInt()
                            var y = break2start.substring(0,2).toInt()
                            var z = break2end.substring(0,2).toInt()
                            if (a >= b && a < c || x >= y && x < z){
                                Toast.makeText(this, "This is a clinic break time",Toast.LENGTH_LONG).show()
                                appoint_slots.setText("0")
                                appoint_txtview_time.setText("")
                                appoint_translate.setText("")
                            }else{
                                collpath = selectedbyuser.toString()
                                docpath = rb.text.toString()
                                getslotsAvailable()

                                Toast.makeText(this, "You selected ${rb.text.toString()}",Toast.LENGTH_LONG).show()
                                selectedappointtime = rb.text.toString()
                                appoint_txtview_time.setText("$selectedappointtime")
                                var thtrans = rb.text.toString().substring(0,2)
                                var tmtrans = rb.text.toString().substring(3,5)
                                var daytime0 = "AM Midnight"
                                var daytime00 = "AM Nightime"
                                var daytime1 = "AM Morning"
                                var daytime2 = "PM Noon time"
                                var daytime3 = "PM Afternoon"
                                var daytime4 = "PM Evening"
                                if (thtrans == "00"){appoint_translate.setText("12:$tmtrans $daytime0")}
                                if (thtrans == "01"){appoint_translate.setText("1:$tmtrans $daytime00")}
                                if (thtrans == "02"){appoint_translate.setText("2:$tmtrans $daytime00")}
                                if (thtrans == "03"){appoint_translate.setText("3:$tmtrans $daytime00")}
                                if (thtrans == "04"){appoint_translate.setText("4:$tmtrans $daytime00")}
                                if (thtrans == "05"){appoint_translate.setText("5:$tmtrans $daytime00")}
                                if (thtrans == "06"){appoint_translate.setText("6:$tmtrans $daytime1")}
                                if (thtrans == "07"){appoint_translate.setText("7:$tmtrans $daytime1")}
                                if (thtrans == "08"){appoint_translate.setText("8:$tmtrans $daytime1")}
                                if (thtrans == "09"){appoint_translate.setText("9:$tmtrans $daytime1")}
                                if (thtrans == "10"){appoint_translate.setText("10:$tmtrans $daytime1")}
                                if (thtrans == "11"){appoint_translate.setText("11:$tmtrans $daytime1")}
                                if (thtrans == "12"){appoint_translate.setText("12:$tmtrans $daytime2")}
                                if (thtrans == "13"){appoint_translate.setText("1:$tmtrans $daytime3")}
                                if (thtrans == "14"){appoint_translate.setText("2:$tmtrans $daytime3")}
                                if (thtrans == "15"){appoint_translate.setText("3:$tmtrans $daytime3")}
                                if (thtrans == "16"){appoint_translate.setText("4:$tmtrans $daytime3")}
                                if (thtrans == "17"){appoint_translate.setText("5:$tmtrans $daytime3")}
                                if (thtrans == "18"){appoint_translate.setText("6:$tmtrans $daytime4")}
                                if (thtrans == "19"){appoint_translate.setText("7:$tmtrans $daytime4")}
                                if (thtrans == "20"){appoint_translate.setText("8:$tmtrans $daytime4")}
                                if (thtrans == "21"){appoint_translate.setText("9:$tmtrans $daytime4")}
                                if (thtrans == "22"){appoint_translate.setText("10:$tmtrans $daytime4")}
                                if (thtrans == "23"){appoint_translate.setText("11:$tmtrans $daytime4")}
                            }
                        }else{
                            collpath = selectedbyuser.toString()
                            docpath = rb.text.toString()
                            getslotsAvailable()

                            Toast.makeText(this, "You selected ${rb.text.toString()}",Toast.LENGTH_LONG).show()
                            selectedappointtime = rb.text.toString()
                            appoint_txtview_time.setText("$selectedappointtime")
                            var thtrans = rb.text.toString().substring(0,2)
                            var tmtrans = rb.text.toString().substring(3,5)
                            var daytime0 = "AM Midnight"
                            var daytime00 = "AM Nightime"
                            var daytime1 = "AM Morning"
                            var daytime2 = "PM Noon time"
                            var daytime3 = "PM Afternoon"
                            var daytime4 = "PM Evening"
                            if (thtrans == "00"){appoint_translate.setText("12:$tmtrans $daytime0")}
                            if (thtrans == "01"){appoint_translate.setText("1:$tmtrans $daytime00")}
                            if (thtrans == "02"){appoint_translate.setText("2:$tmtrans $daytime00")}
                            if (thtrans == "03"){appoint_translate.setText("3:$tmtrans $daytime00")}
                            if (thtrans == "04"){appoint_translate.setText("4:$tmtrans $daytime00")}
                            if (thtrans == "05"){appoint_translate.setText("5:$tmtrans $daytime00")}
                            if (thtrans == "06"){appoint_translate.setText("6:$tmtrans $daytime1")}
                            if (thtrans == "07"){appoint_translate.setText("7:$tmtrans $daytime1")}
                            if (thtrans == "08"){appoint_translate.setText("8:$tmtrans $daytime1")}
                            if (thtrans == "09"){appoint_translate.setText("9:$tmtrans $daytime1")}
                            if (thtrans == "10"){appoint_translate.setText("10:$tmtrans $daytime1")}
                            if (thtrans == "11"){appoint_translate.setText("11:$tmtrans $daytime1")}
                            if (thtrans == "12"){appoint_translate.setText("12:$tmtrans $daytime2")}
                            if (thtrans == "13"){appoint_translate.setText("1:$tmtrans $daytime3")}
                            if (thtrans == "14"){appoint_translate.setText("2:$tmtrans $daytime3")}
                            if (thtrans == "15"){appoint_translate.setText("3:$tmtrans $daytime3")}
                            if (thtrans == "16"){appoint_translate.setText("4:$tmtrans $daytime3")}
                            if (thtrans == "17"){appoint_translate.setText("5:$tmtrans $daytime3")}
                            if (thtrans == "18"){appoint_translate.setText("6:$tmtrans $daytime4")}
                            if (thtrans == "19"){appoint_translate.setText("7:$tmtrans $daytime4")}
                            if (thtrans == "20"){appoint_translate.setText("8:$tmtrans $daytime4")}
                            if (thtrans == "21"){appoint_translate.setText("9:$tmtrans $daytime4")}
                            if (thtrans == "22"){appoint_translate.setText("10:$tmtrans $daytime4")}
                            if (thtrans == "23"){appoint_translate.setText("11:$tmtrans $daytime4")}
                        }
                    }

                }

            }
            viewnotempty = "true"


        }


//        **************************************************WEDNESDAY***********************************************
        if (dayOfTheWeek == "Wednesday"){
            if (viewnotempty == "true"){ll_main.removeAllViews()
                appoint_txtview_time.setText("")}
            opentime.text = openofWed
            closetime.text = closeofWed

            var xxxopenofWed = openofWed
            var xxxcloseofWed = closeofWed

            var ohour3 = xxxopenofWed.substring(0,2)
            var ominute3 = xxxopenofWed.substring(3,5)


            var chour3 = xxxcloseofWed.substring(0,2)
            var cminute3 = xxxcloseofWed.substring(3,5)



            var x1 = ohour3.toInt()
            var y1 = chour3.toInt()
            var x2 = x1

            while(x2!=y1){

                ll_main = findViewById(R.id.ll_main_layout) as LinearLayout

                options3 = arrayOf("$xxxopenofWed")
                var x3 = ++x2
                var x4 = x3.toString().length
                var x5:String = x3.toString()
                if (x4 == 1){x5 = "0$x3"}
                var x6 = x5
                xxxopenofWed = "$x6:$ominute3"
                options3.plusElement("$xxxopenofWed")


                rg = RadioGroup(this)
                rg.orientation = RadioGroup.VERTICAL
                rg.gravity = Gravity.CENTER
                for (i in options3.indices) {
                    val rb = TextView(this)
                    rb.setBackgroundResource(R.drawable.bg_appoint_time_select)
                    rb.text = options3[i]
                    rb.id = View.generateViewId()
                    rb.gravity = Gravity.CENTER
                    ll_main.addView(rb)

                    rb.setOnClickListener {
                        appoint_edittext_fname.clearFocus()
                        appoint_edittext_mname.clearFocus()
                        appoint_edittext_lname.clearFocus()
                        if (break1 == "true" || break2 == "true"){
                            var a = rb.text.toString().substring(0,2).toInt()
                            var b = break1start.substring(0,2).toInt()
                            var c = break1end.substring(0,2).toInt()

                            var x = rb.text.toString().substring(0,2).toInt()
                            var y = break2start.substring(0,2).toInt()
                            var z = break2end.substring(0,2).toInt()
                            if (a >= b && a < c || x >= y && x < z){
                                Toast.makeText(this, "This is a clinic break time",Toast.LENGTH_LONG).show()
                                appoint_slots.setText("0")
                                appoint_txtview_time.setText("")
                                appoint_translate.setText("")
                            }else{
                                collpath = selectedbyuser.toString()
                                docpath = rb.text.toString()
                                getslotsAvailable()

                                Toast.makeText(this, "You selected ${rb.text.toString()}",Toast.LENGTH_LONG).show()
                                selectedappointtime = rb.text.toString()
                                appoint_txtview_time.setText("$selectedappointtime")
                                var thtrans = rb.text.toString().substring(0,2)
                                var tmtrans = rb.text.toString().substring(3,5)
                                var daytime0 = "AM Midnight"
                                var daytime00 = "AM Nightime"
                                var daytime1 = "AM Morning"
                                var daytime2 = "PM Noon time"
                                var daytime3 = "PM Afternoon"
                                var daytime4 = "PM Evening"
                                if (thtrans == "00"){appoint_translate.setText("12:$tmtrans $daytime0")}
                                if (thtrans == "01"){appoint_translate.setText("1:$tmtrans $daytime00")}
                                if (thtrans == "02"){appoint_translate.setText("2:$tmtrans $daytime00")}
                                if (thtrans == "03"){appoint_translate.setText("3:$tmtrans $daytime00")}
                                if (thtrans == "04"){appoint_translate.setText("4:$tmtrans $daytime00")}
                                if (thtrans == "05"){appoint_translate.setText("5:$tmtrans $daytime00")}
                                if (thtrans == "06"){appoint_translate.setText("6:$tmtrans $daytime1")}
                                if (thtrans == "07"){appoint_translate.setText("7:$tmtrans $daytime1")}
                                if (thtrans == "08"){appoint_translate.setText("8:$tmtrans $daytime1")}
                                if (thtrans == "09"){appoint_translate.setText("9:$tmtrans $daytime1")}
                                if (thtrans == "10"){appoint_translate.setText("10:$tmtrans $daytime1")}
                                if (thtrans == "11"){appoint_translate.setText("11:$tmtrans $daytime1")}
                                if (thtrans == "12"){appoint_translate.setText("12:$tmtrans $daytime2")}
                                if (thtrans == "13"){appoint_translate.setText("1:$tmtrans $daytime3")}
                                if (thtrans == "14"){appoint_translate.setText("2:$tmtrans $daytime3")}
                                if (thtrans == "15"){appoint_translate.setText("3:$tmtrans $daytime3")}
                                if (thtrans == "16"){appoint_translate.setText("4:$tmtrans $daytime3")}
                                if (thtrans == "17"){appoint_translate.setText("5:$tmtrans $daytime3")}
                                if (thtrans == "18"){appoint_translate.setText("6:$tmtrans $daytime4")}
                                if (thtrans == "19"){appoint_translate.setText("7:$tmtrans $daytime4")}
                                if (thtrans == "20"){appoint_translate.setText("8:$tmtrans $daytime4")}
                                if (thtrans == "21"){appoint_translate.setText("9:$tmtrans $daytime4")}
                                if (thtrans == "22"){appoint_translate.setText("10:$tmtrans $daytime4")}
                                if (thtrans == "23"){appoint_translate.setText("11:$tmtrans $daytime4")}
                            }
                        }else{
                            collpath = selectedbyuser.toString()
                            docpath = rb.text.toString()
                            getslotsAvailable()

                            Toast.makeText(this, "You selected ${rb.text.toString()}",Toast.LENGTH_LONG).show()
                            selectedappointtime = rb.text.toString()
                            appoint_txtview_time.setText("$selectedappointtime")
                            var thtrans = rb.text.toString().substring(0,2)
                            var tmtrans = rb.text.toString().substring(3,5)
                            var daytime0 = "AM Midnight"
                            var daytime00 = "AM Nightime"
                            var daytime1 = "AM Morning"
                            var daytime2 = "PM Noon time"
                            var daytime3 = "PM Afternoon"
                            var daytime4 = "PM Evening"
                            if (thtrans == "00"){appoint_translate.setText("12:$tmtrans $daytime0")}
                            if (thtrans == "01"){appoint_translate.setText("1:$tmtrans $daytime00")}
                            if (thtrans == "02"){appoint_translate.setText("2:$tmtrans $daytime00")}
                            if (thtrans == "03"){appoint_translate.setText("3:$tmtrans $daytime00")}
                            if (thtrans == "04"){appoint_translate.setText("4:$tmtrans $daytime00")}
                            if (thtrans == "05"){appoint_translate.setText("5:$tmtrans $daytime00")}
                            if (thtrans == "06"){appoint_translate.setText("6:$tmtrans $daytime1")}
                            if (thtrans == "07"){appoint_translate.setText("7:$tmtrans $daytime1")}
                            if (thtrans == "08"){appoint_translate.setText("8:$tmtrans $daytime1")}
                            if (thtrans == "09"){appoint_translate.setText("9:$tmtrans $daytime1")}
                            if (thtrans == "10"){appoint_translate.setText("10:$tmtrans $daytime1")}
                            if (thtrans == "11"){appoint_translate.setText("11:$tmtrans $daytime1")}
                            if (thtrans == "12"){appoint_translate.setText("12:$tmtrans $daytime2")}
                            if (thtrans == "13"){appoint_translate.setText("1:$tmtrans $daytime3")}
                            if (thtrans == "14"){appoint_translate.setText("2:$tmtrans $daytime3")}
                            if (thtrans == "15"){appoint_translate.setText("3:$tmtrans $daytime3")}
                            if (thtrans == "16"){appoint_translate.setText("4:$tmtrans $daytime3")}
                            if (thtrans == "17"){appoint_translate.setText("5:$tmtrans $daytime3")}
                            if (thtrans == "18"){appoint_translate.setText("6:$tmtrans $daytime4")}
                            if (thtrans == "19"){appoint_translate.setText("7:$tmtrans $daytime4")}
                            if (thtrans == "20"){appoint_translate.setText("8:$tmtrans $daytime4")}
                            if (thtrans == "21"){appoint_translate.setText("9:$tmtrans $daytime4")}
                            if (thtrans == "22"){appoint_translate.setText("10:$tmtrans $daytime4")}
                            if (thtrans == "23"){appoint_translate.setText("11:$tmtrans $daytime4")}
                        }
                    }

                }

            }
            viewnotempty = "true"
        }

//        **********************************************THURSDAY*********************************************

        if (dayOfTheWeek == "Thursday"){
            if (viewnotempty == "true"){ll_main.removeAllViews()
                appoint_txtview_time.setText("")}
            opentime.text = openofThu
            closetime.text = closeofThu

            var xxxopenofThu = openofThu
            var xxxcloseofThu = closeofThu

            var ohour4 = xxxopenofThu.substring(0,2)
            var ominute4 = xxxopenofThu.substring(3,5)


            var chour4 = xxxcloseofThu.substring(0,2)
            var cminute4 = xxxcloseofThu.substring(3,5)



            var x1 = ohour4.toInt()
            var y1 = chour4.toInt()
            var x2 = x1

            while(x2<y1){

                ll_main = findViewById(R.id.ll_main_layout) as LinearLayout

                options4 = arrayOf("$xxxopenofThu")
                var x3 = ++x2
                var x4 = x3.toString().length
                var x5:String = x3.toString()
                if (x4 == 1){x5 = "0$x3"}
                var x6 = x5
                xxxopenofThu = "$x6:$ominute4"
                options4.plusElement("$xxxopenofThu")


                rg = RadioGroup(this)
                rg.orientation = RadioGroup.VERTICAL
                rg.gravity = Gravity.CENTER
                for (i in options4.indices) {
                    val rb = TextView(this)
                    rb.setBackgroundResource(R.drawable.bg_appoint_time_select)
                    rb.text = options4[i]
                    rb.id = View.generateViewId()
                    rb.gravity = Gravity.CENTER
                    ll_main.addView(rb)

                    rb.setOnClickListener {
                        appoint_edittext_fname.clearFocus()
                        appoint_edittext_mname.clearFocus()
                        appoint_edittext_lname.clearFocus()
                        if (break1 == "true" || break2 == "true"){
                            var a = rb.text.toString().substring(0,2).toInt()
                            var b = break1start.substring(0,2).toInt()
                            var c = break1end.substring(0,2).toInt()

                            var x = rb.text.toString().substring(0,2).toInt()
                            var y = break2start.substring(0,2).toInt()
                            var z = break2end.substring(0,2).toInt()
                            if (a >= b && a < c || x >= y && x < z){
                                Toast.makeText(this, "This is a clinic break time",Toast.LENGTH_LONG).show()
                                appoint_slots.setText("0")
                                appoint_txtview_time.setText("")
                                appoint_translate.setText("")
                            }else{
                                collpath = selectedbyuser.toString()
                                docpath = rb.text.toString()
                                getslotsAvailable()

                                Toast.makeText(this, "You selected ${rb.text.toString()}",Toast.LENGTH_LONG).show()
                                selectedappointtime = rb.text.toString()
                                appoint_txtview_time.setText("$selectedappointtime")
                                var thtrans = rb.text.toString().substring(0,2)
                                var tmtrans = rb.text.toString().substring(3,5)
                                var daytime0 = "AM Midnight"
                                var daytime00 = "AM Nightime"
                                var daytime1 = "AM Morning"
                                var daytime2 = "PM Noon time"
                                var daytime3 = "PM Afternoon"
                                var daytime4 = "PM Evening"
                                if (thtrans == "00"){appoint_translate.setText("12:$tmtrans $daytime0")}
                                if (thtrans == "01"){appoint_translate.setText("1:$tmtrans $daytime00")}
                                if (thtrans == "02"){appoint_translate.setText("2:$tmtrans $daytime00")}
                                if (thtrans == "03"){appoint_translate.setText("3:$tmtrans $daytime00")}
                                if (thtrans == "04"){appoint_translate.setText("4:$tmtrans $daytime00")}
                                if (thtrans == "05"){appoint_translate.setText("5:$tmtrans $daytime00")}
                                if (thtrans == "06"){appoint_translate.setText("6:$tmtrans $daytime1")}
                                if (thtrans == "07"){appoint_translate.setText("7:$tmtrans $daytime1")}
                                if (thtrans == "08"){appoint_translate.setText("8:$tmtrans $daytime1")}
                                if (thtrans == "09"){appoint_translate.setText("9:$tmtrans $daytime1")}
                                if (thtrans == "10"){appoint_translate.setText("10:$tmtrans $daytime1")}
                                if (thtrans == "11"){appoint_translate.setText("11:$tmtrans $daytime1")}
                                if (thtrans == "12"){appoint_translate.setText("12:$tmtrans $daytime2")}
                                if (thtrans == "13"){appoint_translate.setText("1:$tmtrans $daytime3")}
                                if (thtrans == "14"){appoint_translate.setText("2:$tmtrans $daytime3")}
                                if (thtrans == "15"){appoint_translate.setText("3:$tmtrans $daytime3")}
                                if (thtrans == "16"){appoint_translate.setText("4:$tmtrans $daytime3")}
                                if (thtrans == "17"){appoint_translate.setText("5:$tmtrans $daytime3")}
                                if (thtrans == "18"){appoint_translate.setText("6:$tmtrans $daytime4")}
                                if (thtrans == "19"){appoint_translate.setText("7:$tmtrans $daytime4")}
                                if (thtrans == "20"){appoint_translate.setText("8:$tmtrans $daytime4")}
                                if (thtrans == "21"){appoint_translate.setText("9:$tmtrans $daytime4")}
                                if (thtrans == "22"){appoint_translate.setText("10:$tmtrans $daytime4")}
                                if (thtrans == "23"){appoint_translate.setText("11:$tmtrans $daytime4")}
                            }
                        }else{
                            collpath = selectedbyuser.toString()
                            docpath = rb.text.toString()
                            getslotsAvailable()

                            Toast.makeText(this, "You selected ${rb.text.toString()}",Toast.LENGTH_LONG).show()
                            selectedappointtime = rb.text.toString()
                            appoint_txtview_time.setText("$selectedappointtime")
                            var thtrans = rb.text.toString().substring(0,2)
                            var tmtrans = rb.text.toString().substring(3,5)
                            var daytime0 = "AM Midnight"
                            var daytime00 = "AM Nightime"
                            var daytime1 = "AM Morning"
                            var daytime2 = "PM Noon time"
                            var daytime3 = "PM Afternoon"
                            var daytime4 = "PM Evening"
                            if (thtrans == "00"){appoint_translate.setText("12:$tmtrans $daytime0")}
                            if (thtrans == "01"){appoint_translate.setText("1:$tmtrans $daytime00")}
                            if (thtrans == "02"){appoint_translate.setText("2:$tmtrans $daytime00")}
                            if (thtrans == "03"){appoint_translate.setText("3:$tmtrans $daytime00")}
                            if (thtrans == "04"){appoint_translate.setText("4:$tmtrans $daytime00")}
                            if (thtrans == "05"){appoint_translate.setText("5:$tmtrans $daytime00")}
                            if (thtrans == "06"){appoint_translate.setText("6:$tmtrans $daytime1")}
                            if (thtrans == "07"){appoint_translate.setText("7:$tmtrans $daytime1")}
                            if (thtrans == "08"){appoint_translate.setText("8:$tmtrans $daytime1")}
                            if (thtrans == "09"){appoint_translate.setText("9:$tmtrans $daytime1")}
                            if (thtrans == "10"){appoint_translate.setText("10:$tmtrans $daytime1")}
                            if (thtrans == "11"){appoint_translate.setText("11:$tmtrans $daytime1")}
                            if (thtrans == "12"){appoint_translate.setText("12:$tmtrans $daytime2")}
                            if (thtrans == "13"){appoint_translate.setText("1:$tmtrans $daytime3")}
                            if (thtrans == "14"){appoint_translate.setText("2:$tmtrans $daytime3")}
                            if (thtrans == "15"){appoint_translate.setText("3:$tmtrans $daytime3")}
                            if (thtrans == "16"){appoint_translate.setText("4:$tmtrans $daytime3")}
                            if (thtrans == "17"){appoint_translate.setText("5:$tmtrans $daytime3")}
                            if (thtrans == "18"){appoint_translate.setText("6:$tmtrans $daytime4")}
                            if (thtrans == "19"){appoint_translate.setText("7:$tmtrans $daytime4")}
                            if (thtrans == "20"){appoint_translate.setText("8:$tmtrans $daytime4")}
                            if (thtrans == "21"){appoint_translate.setText("9:$tmtrans $daytime4")}
                            if (thtrans == "22"){appoint_translate.setText("10:$tmtrans $daytime4")}
                            if (thtrans == "23"){appoint_translate.setText("11:$tmtrans $daytime4")}
                        }
                    }


                }

            }
            viewnotempty = "true"
        }


//        *****************************************************FRIDAY********************************************

        if (dayOfTheWeek == "Friday"){
            if (viewnotempty == "true"){ll_main.removeAllViews()
                appoint_txtview_time.setText("")}
            opentime.text = openofFri
            closetime.text = closeofFri

            var xxxopenofFri = openofFri
            var xxxcloseofFri = closeofFri

            var ohour5 = xxxopenofFri.substring(0,2)
            var ominute5 = xxxopenofFri.substring(3,5)


            var chour5 = xxxcloseofFri.substring(0,2)
            var cminute5 = xxxcloseofFri.substring(3,5)



            var x1 = ohour5.toInt()
            var y1 = chour5.toInt()
            var x2 = x1

            while(x2<y1){

                ll_main = findViewById(R.id.ll_main_layout) as LinearLayout

                options5 = arrayOf("$xxxopenofFri")
                var x3 = ++x2
                var x4 = x3.toString().length
                var x5:String = x3.toString()
                if (x4 == 1){x5 = "0$x3"}
                var x6 = x5
                xxxopenofFri = "$x6:$ominute5"
                options5.plusElement("$xxxopenofFri")


                rg = RadioGroup(this)
                rg.orientation = RadioGroup.VERTICAL
                rg.gravity = Gravity.CENTER
                for (i in options5.indices) {
                    val rb = TextView(this)
                    rb.setBackgroundResource(R.drawable.bg_appoint_time_select)
                    rb.text = options5[i]
                    rb.id = View.generateViewId()
                    rb.gravity = Gravity.CENTER
                    ll_main.addView(rb)

                    rb.setOnClickListener {
                        appoint_edittext_fname.clearFocus()
                        appoint_edittext_mname.clearFocus()
                        appoint_edittext_lname.clearFocus()
                        if (break1 == "true" || break2 == "true"){
                            var a = rb.text.toString().substring(0,2).toInt()
                            var b = break1start.substring(0,2).toInt()
                            var c = break1end.substring(0,2).toInt()

                            var x = rb.text.toString().substring(0,2).toInt()
                            var y = break2start.substring(0,2).toInt()
                            var z = break2end.substring(0,2).toInt()
                            if (a >= b && a < c || x >= y && x < z){
                                Toast.makeText(this, "This is a clinic break time",Toast.LENGTH_LONG).show()
                                appoint_slots.setText("0")
                                appoint_txtview_time.setText("")
                                appoint_translate.setText("")
                            }else{
                                collpath = selectedbyuser.toString()
                                docpath = rb.text.toString()
                                getslotsAvailable()

                                Toast.makeText(this, "You selected ${rb.text.toString()}",Toast.LENGTH_LONG).show()
                                selectedappointtime = rb.text.toString()
                                appoint_txtview_time.setText("$selectedappointtime")
                                var thtrans = rb.text.toString().substring(0,2)
                                var tmtrans = rb.text.toString().substring(3,5)
                                var daytime0 = "AM Midnight"
                                var daytime00 = "AM Nightime"
                                var daytime1 = "AM Morning"
                                var daytime2 = "PM Noon time"
                                var daytime3 = "PM Afternoon"
                                var daytime4 = "PM Evening"
                                if (thtrans == "00"){appoint_translate.setText("12:$tmtrans $daytime0")}
                                if (thtrans == "01"){appoint_translate.setText("1:$tmtrans $daytime00")}
                                if (thtrans == "02"){appoint_translate.setText("2:$tmtrans $daytime00")}
                                if (thtrans == "03"){appoint_translate.setText("3:$tmtrans $daytime00")}
                                if (thtrans == "04"){appoint_translate.setText("4:$tmtrans $daytime00")}
                                if (thtrans == "05"){appoint_translate.setText("5:$tmtrans $daytime00")}
                                if (thtrans == "06"){appoint_translate.setText("6:$tmtrans $daytime1")}
                                if (thtrans == "07"){appoint_translate.setText("7:$tmtrans $daytime1")}
                                if (thtrans == "08"){appoint_translate.setText("8:$tmtrans $daytime1")}
                                if (thtrans == "09"){appoint_translate.setText("9:$tmtrans $daytime1")}
                                if (thtrans == "10"){appoint_translate.setText("10:$tmtrans $daytime1")}
                                if (thtrans == "11"){appoint_translate.setText("11:$tmtrans $daytime1")}
                                if (thtrans == "12"){appoint_translate.setText("12:$tmtrans $daytime2")}
                                if (thtrans == "13"){appoint_translate.setText("1:$tmtrans $daytime3")}
                                if (thtrans == "14"){appoint_translate.setText("2:$tmtrans $daytime3")}
                                if (thtrans == "15"){appoint_translate.setText("3:$tmtrans $daytime3")}
                                if (thtrans == "16"){appoint_translate.setText("4:$tmtrans $daytime3")}
                                if (thtrans == "17"){appoint_translate.setText("5:$tmtrans $daytime3")}
                                if (thtrans == "18"){appoint_translate.setText("6:$tmtrans $daytime4")}
                                if (thtrans == "19"){appoint_translate.setText("7:$tmtrans $daytime4")}
                                if (thtrans == "20"){appoint_translate.setText("8:$tmtrans $daytime4")}
                                if (thtrans == "21"){appoint_translate.setText("9:$tmtrans $daytime4")}
                                if (thtrans == "22"){appoint_translate.setText("10:$tmtrans $daytime4")}
                                if (thtrans == "23"){appoint_translate.setText("11:$tmtrans $daytime4")}
                            }
                        }else{
                            collpath = selectedbyuser.toString()
                            docpath = rb.text.toString()
                            getslotsAvailable()

                            Toast.makeText(this, "You selected ${rb.text.toString()}",Toast.LENGTH_LONG).show()
                            selectedappointtime = rb.text.toString()
                            appoint_txtview_time.setText("$selectedappointtime")
                            var thtrans = rb.text.toString().substring(0,2)
                            var tmtrans = rb.text.toString().substring(3,5)
                            var daytime0 = "AM Midnight"
                            var daytime00 = "AM Nightime"
                            var daytime1 = "AM Morning"
                            var daytime2 = "PM Noon time"
                            var daytime3 = "PM Afternoon"
                            var daytime4 = "PM Evening"
                            if (thtrans == "00"){appoint_translate.setText("12:$tmtrans $daytime0")}
                            if (thtrans == "01"){appoint_translate.setText("1:$tmtrans $daytime00")}
                            if (thtrans == "02"){appoint_translate.setText("2:$tmtrans $daytime00")}
                            if (thtrans == "03"){appoint_translate.setText("3:$tmtrans $daytime00")}
                            if (thtrans == "04"){appoint_translate.setText("4:$tmtrans $daytime00")}
                            if (thtrans == "05"){appoint_translate.setText("5:$tmtrans $daytime00")}
                            if (thtrans == "06"){appoint_translate.setText("6:$tmtrans $daytime1")}
                            if (thtrans == "07"){appoint_translate.setText("7:$tmtrans $daytime1")}
                            if (thtrans == "08"){appoint_translate.setText("8:$tmtrans $daytime1")}
                            if (thtrans == "09"){appoint_translate.setText("9:$tmtrans $daytime1")}
                            if (thtrans == "10"){appoint_translate.setText("10:$tmtrans $daytime1")}
                            if (thtrans == "11"){appoint_translate.setText("11:$tmtrans $daytime1")}
                            if (thtrans == "12"){appoint_translate.setText("12:$tmtrans $daytime2")}
                            if (thtrans == "13"){appoint_translate.setText("1:$tmtrans $daytime3")}
                            if (thtrans == "14"){appoint_translate.setText("2:$tmtrans $daytime3")}
                            if (thtrans == "15"){appoint_translate.setText("3:$tmtrans $daytime3")}
                            if (thtrans == "16"){appoint_translate.setText("4:$tmtrans $daytime3")}
                            if (thtrans == "17"){appoint_translate.setText("5:$tmtrans $daytime3")}
                            if (thtrans == "18"){appoint_translate.setText("6:$tmtrans $daytime4")}
                            if (thtrans == "19"){appoint_translate.setText("7:$tmtrans $daytime4")}
                            if (thtrans == "20"){appoint_translate.setText("8:$tmtrans $daytime4")}
                            if (thtrans == "21"){appoint_translate.setText("9:$tmtrans $daytime4")}
                            if (thtrans == "22"){appoint_translate.setText("10:$tmtrans $daytime4")}
                            if (thtrans == "23"){appoint_translate.setText("11:$tmtrans $daytime4")}
                        }
                    }

                }

            }
            viewnotempty = "true"
        }

//        ***********************************************************SATURDAY*************************************

        if (dayOfTheWeek == "Saturday"){
            if (viewnotempty == "true"){ll_main.removeAllViews()
                appoint_txtview_time.setText("")}
            opentime.text = openofSat
            closetime.text = closeofSat

            var xxxopenofSat = openofSat
            var xxxcloseofSat = closeofSat

            var ohour6 = xxxopenofSat.substring(0,2)
            var ominute6 = xxxopenofSat.substring(3,5)


            var chour6 = xxxcloseofSat.substring(0,2)
            var cminute6 = xxxcloseofSat.substring(3,5)



            var x1 = ohour6.toInt()
            var y1 = chour6.toInt()
            var x2 = x1

            while(x2<y1){

                ll_main = findViewById(R.id.ll_main_layout) as LinearLayout

                options6 = arrayOf("$xxxopenofSat")
                var x3 = ++x2
                var x4 = x3.toString().length
                var x5:String = x3.toString()
                if (x4 == 1){x5 = "0$x3"}
                var x6 = x5
                xxxopenofSat = "$x6:$ominute6"
                options6.plusElement("$xxxopenofSat")


                rg = RadioGroup(this)
                rg.orientation = RadioGroup.VERTICAL
                rg.gravity = Gravity.CENTER
                for (i in options6.indices) {
                    val rb = TextView(this)
                    rb.setBackgroundResource(R.drawable.bg_appoint_time_select)
                    rb.text = options6[i]
                    rb.id = View.generateViewId()
                    rb.gravity = Gravity.CENTER
                    ll_main.addView(rb)

                    rb.setOnClickListener {
                        appoint_edittext_fname.clearFocus()
                        appoint_edittext_mname.clearFocus()
                        appoint_edittext_lname.clearFocus()
                        if (break1 == "true" || break2 == "true"){
                            var a = rb.text.toString().substring(0,2).toInt()
                            var b = break1start.substring(0,2).toInt()
                            var c = break1end.substring(0,2).toInt()

                            var x = rb.text.toString().substring(0,2).toInt()
                            var y = break2start.substring(0,2).toInt()
                            var z = break2end.substring(0,2).toInt()
                            if (a >= b && a < c || x >= y && x < z){
                                Toast.makeText(this, "This is a clinic break time",Toast.LENGTH_LONG).show()
                                appoint_slots.setText("0")
                                appoint_txtview_time.setText("")
                                appoint_translate.setText("")
                            }else{
                                collpath = selectedbyuser.toString()
                                docpath = rb.text.toString()
                                getslotsAvailable()

                                Toast.makeText(this, "You selected ${rb.text.toString()}",Toast.LENGTH_LONG).show()
                                selectedappointtime = rb.text.toString()
                                appoint_txtview_time.setText("$selectedappointtime")
                                var thtrans = rb.text.toString().substring(0,2)
                                var tmtrans = rb.text.toString().substring(3,5)
                                var daytime0 = "AM Midnight"
                                var daytime00 = "AM Nightime"
                                var daytime1 = "AM Morning"
                                var daytime2 = "PM Noon time"
                                var daytime3 = "PM Afternoon"
                                var daytime4 = "PM Evening"
                                if (thtrans == "00"){appoint_translate.setText("12:$tmtrans $daytime0")}
                                if (thtrans == "01"){appoint_translate.setText("1:$tmtrans $daytime00")}
                                if (thtrans == "02"){appoint_translate.setText("2:$tmtrans $daytime00")}
                                if (thtrans == "03"){appoint_translate.setText("3:$tmtrans $daytime00")}
                                if (thtrans == "04"){appoint_translate.setText("4:$tmtrans $daytime00")}
                                if (thtrans == "05"){appoint_translate.setText("5:$tmtrans $daytime00")}
                                if (thtrans == "06"){appoint_translate.setText("6:$tmtrans $daytime1")}
                                if (thtrans == "07"){appoint_translate.setText("7:$tmtrans $daytime1")}
                                if (thtrans == "08"){appoint_translate.setText("8:$tmtrans $daytime1")}
                                if (thtrans == "09"){appoint_translate.setText("9:$tmtrans $daytime1")}
                                if (thtrans == "10"){appoint_translate.setText("10:$tmtrans $daytime1")}
                                if (thtrans == "11"){appoint_translate.setText("11:$tmtrans $daytime1")}
                                if (thtrans == "12"){appoint_translate.setText("12:$tmtrans $daytime2")}
                                if (thtrans == "13"){appoint_translate.setText("1:$tmtrans $daytime3")}
                                if (thtrans == "14"){appoint_translate.setText("2:$tmtrans $daytime3")}
                                if (thtrans == "15"){appoint_translate.setText("3:$tmtrans $daytime3")}
                                if (thtrans == "16"){appoint_translate.setText("4:$tmtrans $daytime3")}
                                if (thtrans == "17"){appoint_translate.setText("5:$tmtrans $daytime3")}
                                if (thtrans == "18"){appoint_translate.setText("6:$tmtrans $daytime4")}
                                if (thtrans == "19"){appoint_translate.setText("7:$tmtrans $daytime4")}
                                if (thtrans == "20"){appoint_translate.setText("8:$tmtrans $daytime4")}
                                if (thtrans == "21"){appoint_translate.setText("9:$tmtrans $daytime4")}
                                if (thtrans == "22"){appoint_translate.setText("10:$tmtrans $daytime4")}
                                if (thtrans == "23"){appoint_translate.setText("11:$tmtrans $daytime4")}
                            }
                        }else{
                            collpath = selectedbyuser.toString()
                            docpath = rb.text.toString()
                            getslotsAvailable()

                            Toast.makeText(this, "You selected ${rb.text.toString()}",Toast.LENGTH_LONG).show()
                            selectedappointtime = rb.text.toString()
                            appoint_txtview_time.setText("$selectedappointtime")
                            var thtrans = rb.text.toString().substring(0,2)
                            var tmtrans = rb.text.toString().substring(3,5)
                            var daytime0 = "AM Midnight"
                            var daytime00 = "AM Nightime"
                            var daytime1 = "AM Morning"
                            var daytime2 = "PM Noon time"
                            var daytime3 = "PM Afternoon"
                            var daytime4 = "PM Evening"
                            if (thtrans == "00"){appoint_translate.setText("12:$tmtrans $daytime0")}
                            if (thtrans == "01"){appoint_translate.setText("1:$tmtrans $daytime00")}
                            if (thtrans == "02"){appoint_translate.setText("2:$tmtrans $daytime00")}
                            if (thtrans == "03"){appoint_translate.setText("3:$tmtrans $daytime00")}
                            if (thtrans == "04"){appoint_translate.setText("4:$tmtrans $daytime00")}
                            if (thtrans == "05"){appoint_translate.setText("5:$tmtrans $daytime00")}
                            if (thtrans == "06"){appoint_translate.setText("6:$tmtrans $daytime1")}
                            if (thtrans == "07"){appoint_translate.setText("7:$tmtrans $daytime1")}
                            if (thtrans == "08"){appoint_translate.setText("8:$tmtrans $daytime1")}
                            if (thtrans == "09"){appoint_translate.setText("9:$tmtrans $daytime1")}
                            if (thtrans == "10"){appoint_translate.setText("10:$tmtrans $daytime1")}
                            if (thtrans == "11"){appoint_translate.setText("11:$tmtrans $daytime1")}
                            if (thtrans == "12"){appoint_translate.setText("12:$tmtrans $daytime2")}
                            if (thtrans == "13"){appoint_translate.setText("1:$tmtrans $daytime3")}
                            if (thtrans == "14"){appoint_translate.setText("2:$tmtrans $daytime3")}
                            if (thtrans == "15"){appoint_translate.setText("3:$tmtrans $daytime3")}
                            if (thtrans == "16"){appoint_translate.setText("4:$tmtrans $daytime3")}
                            if (thtrans == "17"){appoint_translate.setText("5:$tmtrans $daytime3")}
                            if (thtrans == "18"){appoint_translate.setText("6:$tmtrans $daytime4")}
                            if (thtrans == "19"){appoint_translate.setText("7:$tmtrans $daytime4")}
                            if (thtrans == "20"){appoint_translate.setText("8:$tmtrans $daytime4")}
                            if (thtrans == "21"){appoint_translate.setText("9:$tmtrans $daytime4")}
                            if (thtrans == "22"){appoint_translate.setText("10:$tmtrans $daytime4")}
                            if (thtrans == "23"){appoint_translate.setText("11:$tmtrans $daytime4")}
                        }
                    }

                }

            }
            viewnotempty = "true"
        }

//        ******************************************************SUNDAY*************************************************

        if (dayOfTheWeek == "Sunday"){
            if (viewnotempty == "true"){ll_main.removeAllViews()
                appoint_txtview_time.setText("")}
            opentime.text = openofSun
            closetime.text = closeofSun

            var xxxopenofSun = openofSun
            var xxxcloseofSun = closeofSun

            var ohour7 = xxxopenofSun.substring(0,2)
            var ominute7 = xxxopenofSun.substring(3,5)


            var chour7 = xxxcloseofSun.substring(0,2)
            var cminute7 = xxxcloseofSun.substring(3,5)



            var x1 = ohour7.toInt()
            var y1 = chour7.toInt()
            var x2 = x1

            while(x2<y1){

                ll_main = findViewById(R.id.ll_main_layout) as LinearLayout

                options7 = arrayOf("$xxxopenofSun")
                var x3 = ++x2
                var x4 = x3.toString().length
                var x5:String = x3.toString()
                if (x4 == 1){x5 = "0$x3"}
                var x6 = x5
                xxxopenofSun = "$x6:$ominute7"
                options7.plusElement("$xxxopenofSun")


                rg = RadioGroup(this)
                rg.orientation = RadioGroup.VERTICAL
                rg.gravity = Gravity.CENTER
                for (i in options7.indices) {
                    val rb = TextView(this)
                    rb.setBackgroundResource(R.drawable.bg_appoint_time_select)
                    rb.text = options7[i]
                    rb.id = View.generateViewId()
                    rb.gravity = Gravity.CENTER
                    ll_main.addView(rb)

                    rb.setOnClickListener {
                        appoint_edittext_fname.clearFocus()
                        appoint_edittext_mname.clearFocus()
                        appoint_edittext_lname.clearFocus()
                        if (break1 == "true" || break2 == "true"){
                            var a = rb.text.toString().substring(0,2).toInt()
                            var b = break1start.substring(0,2).toInt()
                            var c = break1end.substring(0,2).toInt()

                            var x = rb.text.toString().substring(0,2).toInt()
                            var y = break2start.substring(0,2).toInt()
                            var z = break2end.substring(0,2).toInt()
                            if (a >= b && a < c || x >= y && x < z){
                                Toast.makeText(this, "This is a clinic break time",Toast.LENGTH_LONG).show()
                                appoint_slots.setText("0")
                                appoint_txtview_time.setText("")
                                appoint_translate.setText("")
                            }else{
                                collpath = selectedbyuser.toString()
                                docpath = rb.text.toString()
                                getslotsAvailable()

                                Toast.makeText(this, "You selected ${rb.text.toString()}",Toast.LENGTH_LONG).show()
                                selectedappointtime = rb.text.toString()
                                appoint_txtview_time.setText("$selectedappointtime")
                                var thtrans = rb.text.toString().substring(0,2)
                                var tmtrans = rb.text.toString().substring(3,5)
                                var daytime0 = "AM Midnight"
                                var daytime00 = "AM Nightime"
                                var daytime1 = "AM Morning"
                                var daytime2 = "PM Noon time"
                                var daytime3 = "PM Afternoon"
                                var daytime4 = "PM Evening"
                                if (thtrans == "00"){appoint_translate.setText("12:$tmtrans $daytime0")}
                                if (thtrans == "01"){appoint_translate.setText("1:$tmtrans $daytime00")}
                                if (thtrans == "02"){appoint_translate.setText("2:$tmtrans $daytime00")}
                                if (thtrans == "03"){appoint_translate.setText("3:$tmtrans $daytime00")}
                                if (thtrans == "04"){appoint_translate.setText("4:$tmtrans $daytime00")}
                                if (thtrans == "05"){appoint_translate.setText("5:$tmtrans $daytime00")}
                                if (thtrans == "06"){appoint_translate.setText("6:$tmtrans $daytime1")}
                                if (thtrans == "07"){appoint_translate.setText("7:$tmtrans $daytime1")}
                                if (thtrans == "08"){appoint_translate.setText("8:$tmtrans $daytime1")}
                                if (thtrans == "09"){appoint_translate.setText("9:$tmtrans $daytime1")}
                                if (thtrans == "10"){appoint_translate.setText("10:$tmtrans $daytime1")}
                                if (thtrans == "11"){appoint_translate.setText("11:$tmtrans $daytime1")}
                                if (thtrans == "12"){appoint_translate.setText("12:$tmtrans $daytime2")}
                                if (thtrans == "13"){appoint_translate.setText("1:$tmtrans $daytime3")}
                                if (thtrans == "14"){appoint_translate.setText("2:$tmtrans $daytime3")}
                                if (thtrans == "15"){appoint_translate.setText("3:$tmtrans $daytime3")}
                                if (thtrans == "16"){appoint_translate.setText("4:$tmtrans $daytime3")}
                                if (thtrans == "17"){appoint_translate.setText("5:$tmtrans $daytime3")}
                                if (thtrans == "18"){appoint_translate.setText("6:$tmtrans $daytime4")}
                                if (thtrans == "19"){appoint_translate.setText("7:$tmtrans $daytime4")}
                                if (thtrans == "20"){appoint_translate.setText("8:$tmtrans $daytime4")}
                                if (thtrans == "21"){appoint_translate.setText("9:$tmtrans $daytime4")}
                                if (thtrans == "22"){appoint_translate.setText("10:$tmtrans $daytime4")}
                                if (thtrans == "23"){appoint_translate.setText("11:$tmtrans $daytime4")}
                            }
                        }else{
                            collpath = selectedbyuser.toString()
                            docpath = rb.text.toString()
                            getslotsAvailable()

                            Toast.makeText(this, "You selected ${rb.text.toString()}",Toast.LENGTH_LONG).show()
                            selectedappointtime = rb.text.toString()
                            appoint_txtview_time.setText("$selectedappointtime")
                            var thtrans = rb.text.toString().substring(0,2)
                            var tmtrans = rb.text.toString().substring(3,5)
                            var daytime0 = "AM Midnight"
                            var daytime00 = "AM Nightime"
                            var daytime1 = "AM Morning"
                            var daytime2 = "PM Noon time"
                            var daytime3 = "PM Afternoon"
                            var daytime4 = "PM Evening"
                            if (thtrans == "00"){appoint_translate.setText("12:$tmtrans $daytime0")}
                            if (thtrans == "01"){appoint_translate.setText("1:$tmtrans $daytime00")}
                            if (thtrans == "02"){appoint_translate.setText("2:$tmtrans $daytime00")}
                            if (thtrans == "03"){appoint_translate.setText("3:$tmtrans $daytime00")}
                            if (thtrans == "04"){appoint_translate.setText("4:$tmtrans $daytime00")}
                            if (thtrans == "05"){appoint_translate.setText("5:$tmtrans $daytime00")}
                            if (thtrans == "06"){appoint_translate.setText("6:$tmtrans $daytime1")}
                            if (thtrans == "07"){appoint_translate.setText("7:$tmtrans $daytime1")}
                            if (thtrans == "08"){appoint_translate.setText("8:$tmtrans $daytime1")}
                            if (thtrans == "09"){appoint_translate.setText("9:$tmtrans $daytime1")}
                            if (thtrans == "10"){appoint_translate.setText("10:$tmtrans $daytime1")}
                            if (thtrans == "11"){appoint_translate.setText("11:$tmtrans $daytime1")}
                            if (thtrans == "12"){appoint_translate.setText("12:$tmtrans $daytime2")}
                            if (thtrans == "13"){appoint_translate.setText("1:$tmtrans $daytime3")}
                            if (thtrans == "14"){appoint_translate.setText("2:$tmtrans $daytime3")}
                            if (thtrans == "15"){appoint_translate.setText("3:$tmtrans $daytime3")}
                            if (thtrans == "16"){appoint_translate.setText("4:$tmtrans $daytime3")}
                            if (thtrans == "17"){appoint_translate.setText("5:$tmtrans $daytime3")}
                            if (thtrans == "18"){appoint_translate.setText("6:$tmtrans $daytime4")}
                            if (thtrans == "19"){appoint_translate.setText("7:$tmtrans $daytime4")}
                            if (thtrans == "20"){appoint_translate.setText("8:$tmtrans $daytime4")}
                            if (thtrans == "21"){appoint_translate.setText("9:$tmtrans $daytime4")}
                            if (thtrans == "22"){appoint_translate.setText("10:$tmtrans $daytime4")}
                            if (thtrans == "23"){appoint_translate.setText("11:$tmtrans $daytime4")}
                        }
                    }

                }

            }
            viewnotempty = "true"
        }
    }

    fun getslotsAvailable(){
        slotsdb = FirebaseFirestore.getInstance().document("Appointment/Slot")
        slotsrootRef = slotsdb.collection("$collpath").document("$docpath")
        slotsrootRef.get()
        slotsrootRef.get().addOnCompleteListener(OnCompleteListener<DocumentSnapshot> { task ->
            if (task.isSuccessful) {
                val document = task.result!!.data
                if (document != null) {
//                                            Log.d(TAG, "DocumentSnapshot data: " + task.result.data)
                    intslots = document.getValue("slots")
                    appoint_slots.setText("$intslots")

                } else {
//                                            Log.d(TAG, "No such document")
                    try{
                        val items = HashMap<String, Any>()
                        items.put("slots", "$maxpat")

                        slotsdb.collection("$collpath").document("$docpath").set(items).addOnSuccessListener {
                                void: Void? -> appoint_slots.setText("$maxpat")
                        }.addOnFailureListener {
                                exception: java.lang.Exception -> Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                        }
                    }catch (e:Exception) {
                        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            } else {
//                                        Log.d(TAG, "get failed with ", task.exception)
            }
        })
    }

    fun updateslotsAvailable(){
        slotsdb = FirebaseFirestore.getInstance().document("Appointment/Slot")
        slotsrootRef = slotsdb.collection("$collpath").document("$docpath")

        try{
            val items = HashMap<String, Any>()
            var takeslot:Int = intslots.toString().toInt()
            var newslot = --takeslot
            var upslot = newslot.toString()
            items.put("slots", "$upslot")

            slotsdb.collection("$collpath").document("$docpath").set(items).addOnSuccessListener {
                    void: Void? -> appoint_slots.setText("$newslot")
            }.addOnFailureListener {
                    exception: java.lang.Exception -> Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
            }
        }catch (e:Exception) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }
    }


    private fun saveAppointment(){
        appoint_pBar.setVisibility(View.VISIBLE)

        appoint_edittext_fname = findViewById(R.id.appoint_edittext_fname)
        appoint_edittext_mname = findViewById(R.id.appoint_edittext_mname)
        appoint_edittext_lname = findViewById(R.id.appoint_edittext_lname)

        var pat_fname:String = appoint_edittext_fname.text.toString()
        var pat_mname:String = appoint_edittext_mname.text.toString()
        var pat_lname:String = appoint_edittext_lname.text.toString()
        var pat_sex:String = selectedGender
        var pat_bday:String = selected_bday
        var pat_appointDate:String = selectedbyuser
        var pat_datetranslate:String = appoint_dayOfWeek.text.toString()
        var pat_appointTime:String = appoint_txtview_time.text.toString()
        var pat_timetranslate:String = appoint_translate.text.toString()
        var pat_docu:String = documName
        var pat_doctor:String = fullName
        var pat_docPrac:String = medPractice
        var pat_docAddress:String = address
        var pat_host:String = currentuser





        if (!pat_fname.isEmpty() && !pat_mname.isEmpty() && !pat_lname.isEmpty() && pat_bday != "" && pat_appointDate != "" && pat_appointTime != "" && collpath != "" && docpath != "") {
            val any = try {
                val items = HashMap<String, Any>()
                items.put("Fname", pat_fname)
                items.put("Mname", pat_mname)
                items.put("Lname", pat_lname)
                items.put("Sex", pat_sex)
                items.put("Bday", pat_bday)
                items.put("AppointDate", pat_appointDate)
                items.put("DateTrans", pat_datetranslate)
                items.put("AppointTime", pat_appointTime)
                items.put("TimeTrans", pat_timetranslate)
                items.put("Docu", pat_docu)
                items.put("Doctor", pat_doctor)
                items.put("DocPrac", pat_docPrac)
                items.put("Address", pat_docAddress)
                items.put("Host", pat_host)
                items.put("Status", "PENDING")
                items.put("Timestamp", FieldValue.serverTimestamp())


                var documentName: String = pat_fname + pat_mname + pat_lname
                db.collection("AppointmentList").document(documentName).set(items).addOnSuccessListener { void: Void? ->
                    Toast.makeText(this, "Appointment has been scheduled.", Toast.LENGTH_LONG).show()
                    updateslotsAvailable()
                    appoint_pBar.setVisibility(View.INVISIBLE)
                    val intent = Intent(this@GetAppointment, Appointments::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener { exception: java.lang.Exception ->
                    Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }
        }else {
            appoint_pBar.setVisibility(View.INVISIBLE)
            Toast.makeText(this, "Please check the Appointment Form and try again.", Toast.LENGTH_LONG).show()
        }


    }

}
