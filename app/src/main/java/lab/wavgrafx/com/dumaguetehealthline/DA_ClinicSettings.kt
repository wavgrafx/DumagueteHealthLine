package lab.wavgrafx.com.dumaguetehealthline

import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.view.View
import android.widget.*
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_da__clinic_settings.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*

class DA_ClinicSettings : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var db:DocumentReference
    lateinit var rootRef: DocumentReference
    lateinit var cfname: String
    lateinit var cmname:String
    lateinit var clname:String
    lateinit var genderofDoc:String
    lateinit var pracofDoc:String
    lateinit var medPractice:TextView
    lateinit var degreeofDoc:String
    lateinit var monopentime: TextView
    lateinit var monclosetime: TextView
    lateinit var tueopentime: TextView
    lateinit var tueclosetime: TextView
    lateinit var wedopentime: TextView
    lateinit var wedclosetime: TextView
    lateinit var thuopentime: TextView
    lateinit var thuclosetime: TextView
    lateinit var friopentime: TextView
    lateinit var friclosetime: TextView
    lateinit var satopentime: TextView
    lateinit var satclosetime: TextView
    lateinit var sunopentime: TextView
    lateinit var sunclosetime: TextView
    lateinit var addClinic_brk1:CheckBox
    lateinit var addClinic_brk2:CheckBox
    lateinit var brkstart1:TextView
    lateinit var brkend1:TextView
    lateinit var brkstart2:TextView
    lateinit var brkend2:TextView
    lateinit var maxpat:String
    lateinit var allowedBookDays:String
    lateinit var maximum_patients: TextView
    lateinit var book_days: TextView
    lateinit var seekbar_patients: SeekBar
    lateinit var seekbar_bookdays: SeekBar
    lateinit var textview_patienttitle: TextView
    lateinit var textview_bookdaystitle: TextView
    lateinit var fullName: TextView
    lateinit var update_Clinic: Button
    lateinit var delete_Clinic: Button
    lateinit var documName: String



    private var timeFormat = SimpleDateFormat("HH:mm", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_da__clinic_settings)
        val currentuser = FirebaseAuth.getInstance().currentUser!!.phoneNumber.toString()


        monopentime = findViewById(R.id.monopentime)
        monclosetime = findViewById(R.id.monclosetime)
        tueopentime = findViewById(R.id.tueopentime)
        tueclosetime = findViewById(R.id.tueclosetime)
        wedopentime = findViewById(R.id.wedopentime)
        wedclosetime = findViewById(R.id.wedclosetime)
        thuopentime = findViewById(R.id.thuopentime)
        thuclosetime = findViewById(R.id.thuclosetime)
        friopentime = findViewById(R.id.friopentime)
        friclosetime = findViewById(R.id.friclosetime)
        satopentime = findViewById(R.id.satopentime)
        satclosetime = findViewById(R.id.satclosetime)
        sunopentime = findViewById(R.id.sunopentime)
        sunclosetime = findViewById(R.id.sunclosetime)
        addClinic_brk1 = findViewById(R.id.addClinic_brk1)
        addClinic_brk2 = findViewById(R.id.addClinic_brk2)
        brkstart1 = findViewById(R.id.brkstart1)
        brkend1 = findViewById(R.id.brkend1)
        brkstart2 = findViewById(R.id.brkstart2)
        brkend2 = findViewById(R.id.brkend2)
        maximum_patients = findViewById(R.id.maximum_patients)
        book_days = findViewById(R.id.book_days)
        seekbar_bookdays = findViewById(R.id.seekbar_bookdays)
        seekbar_patients = findViewById(R.id.seekbar_patients)
        textview_patienttitle = findViewById(R.id.textview_patienttitle)
        textview_bookdaystitle = findViewById(R.id.textview_bookdaystitle)

        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance().document("Clinics/List")
        update_Clinic = findViewById(R.id.update_Clinic)
        delete_Clinic = findViewById(R.id.delete_Clinic)
        fullName = findViewById(R.id.fullName)
        medPractice = findViewById(R.id.medPractice)


        val bundle:Bundle=intent.extras
        val documName:String
        if(bundle!=null)
        {
            documName = bundle.getString("docuName")
            var docGender = bundle.getString("genderofDoc")
            if (docGender == "Male"){
                profileAvatar.setImageResource(R.drawable.male_avatar)
            }else{
                profileAvatar.setImageResource(R.drawable.female_avatar)
            }
            fullName.text = bundle.getString("nameofDoc")
            cfname = bundle.getString("FName")
            cmname = bundle.getString("MName")
            clname = bundle.getString("LName")
            genderofDoc = bundle.getString("genderofDoc")
            pracofDoc = bundle.getString("pracofDoc")
            degreeofDoc = bundle.getString("Degree")
            update_contactNum.text = bundle.getString("telofDoc").toEditable()
            update_cert.text = bundle.getString("certofDoc").toEditable()
            update_address.text = bundle.getString("addofDoc").toEditable()
            update_Mon.isChecked = bundle.getString("monofDoc").toBoolean()
            update_Tue.isChecked = bundle.getString("tueofDoc").toBoolean()
            update_Wed.isChecked = bundle.getString("wedofDoc").toBoolean()
            update_Thu.isChecked = bundle.getString("thuofDoc").toBoolean()
            update_Fri.isChecked = bundle.getString("friofDoc").toBoolean()
            update_Sat.isChecked = bundle.getString("satofDoc").toBoolean()
            update_Sun.isChecked = bundle.getString("sunofDoc").toBoolean()
            medPractice.text = bundle.getString("pracofDoc")
            monopentime.text = bundle.getString("openofMon")
            monclosetime.text = bundle.getString("closeofMon")
            tueopentime.text = bundle.getString("openofTue")
            tueclosetime.text = bundle.getString("closeofTue")
            wedopentime.text = bundle.getString("openofWed")
            wedclosetime.text = bundle.getString("closeofWed")
            thuopentime.text = bundle.getString("openofThu")
            thuclosetime.text = bundle.getString("closeofThu")
            friopentime.text = bundle.getString("openofFri")
            friclosetime.text = bundle.getString("closeofFri")
            satopentime.text = bundle.getString("openofSat")
            satclosetime.text = bundle.getString("closeofSat")
            sunopentime.text = bundle.getString("openofSun")
            sunclosetime.text = bundle.getString("closeofSun")
            addClinic_brk1.isChecked = bundle.getString("break1").toBoolean()
            if(addClinic_brk1.isChecked){
                brkstart1.visibility = View.VISIBLE
                brkend1.visibility = View.VISIBLE
            }else{
                brkstart1.visibility = View.INVISIBLE
                brkend1.visibility = View.INVISIBLE
            }
            addClinic_brk2.isChecked = bundle.getString("break2").toBoolean()
            if(addClinic_brk2.isChecked){
                brkstart2.visibility = View.VISIBLE
                brkend2.visibility = View.VISIBLE
            }else{
                brkstart2.visibility = View.INVISIBLE
                brkend2.visibility = View.INVISIBLE
            }
            brkstart1.text = bundle.getString("break1start")
            brkend1.text = bundle.getString("break1end")
            brkstart2.text = bundle.getString("break2start")
            brkend2.text = bundle.getString("break2end")
            maxpat = bundle.getString("maxpat")
            maximum_patients.text = maxpat
            seekbar_patients.progress = maxpat.toInt()
            allowedBookDays = bundle.getString("allowedBookDays")
            book_days.text =  allowedBookDays
            seekbar_bookdays.progress = allowedBookDays.toInt()

        }


        update_Mon.setOnClickListener {
            if(update_Mon.isChecked){
                monopentime.visibility = View.VISIBLE
                monclosetime.visibility = View.VISIBLE
            }else{
                monopentime.visibility = View.INVISIBLE
                monclosetime.visibility = View.INVISIBLE
            }
        }

        update_Tue.setOnClickListener {
            if(update_Tue.isChecked){
                tueopentime.visibility = View.VISIBLE
                tueclosetime.visibility = View.VISIBLE
            }else{
                tueopentime.visibility = View.INVISIBLE
                tueclosetime.visibility = View.INVISIBLE
            }
        }

        update_Wed.setOnClickListener {
            if(update_Wed.isChecked){
                wedopentime.visibility = View.VISIBLE
                wedclosetime.visibility = View.VISIBLE
            }else{
                wedopentime.visibility = View.INVISIBLE
                wedclosetime.visibility = View.INVISIBLE
            }
        }

        update_Thu.setOnClickListener {
            if(update_Thu.isChecked){
                thuopentime.visibility = View.VISIBLE
                thuclosetime.visibility = View.VISIBLE
            }else{
                thuopentime.visibility = View.INVISIBLE
                thuclosetime.visibility = View.INVISIBLE
            }
        }

        update_Fri.setOnClickListener {
            if(update_Fri.isChecked){
                friopentime.visibility = View.VISIBLE
                friclosetime.visibility = View.VISIBLE
            }else{
                friopentime.visibility = View.INVISIBLE
                friclosetime.visibility = View.INVISIBLE
            }
        }

        update_Sat.setOnClickListener {
            if(update_Sat.isChecked){
                satopentime.visibility = View.VISIBLE
                satclosetime.visibility = View.VISIBLE
            }else{
                satopentime.visibility = View.INVISIBLE
                satclosetime.visibility = View.INVISIBLE
            }
        }

        update_Sun.setOnClickListener {
            if(update_Sun.isChecked){
                sunopentime.visibility = View.VISIBLE
                sunclosetime.visibility = View.VISIBLE
            }else{
                sunopentime.visibility = View.INVISIBLE
                sunclosetime.visibility = View.INVISIBLE
            }
        }

        addClinic_brk1.setOnClickListener {
            if(addClinic_brk1.isChecked){
                brkstart1.visibility = View.VISIBLE
                brkend1.visibility = View.VISIBLE
            }else{
                brkstart1.visibility = View.INVISIBLE
                brkend1.visibility = View.INVISIBLE
            }
        }

        addClinic_brk2.setOnClickListener {
            if(addClinic_brk2.isChecked){
                brkstart2.visibility = View.VISIBLE
                brkend2.visibility = View.VISIBLE
            }else{
                brkstart2.visibility = View.INVISIBLE
                brkend2.visibility = View.INVISIBLE
            }
        }

        brkstart1.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)


                var a1 = timeFormat.format(selectedTime.time).substring(0,2).toInt()
                var a2 = brkend1.text.toString().substring(0,2).toInt()
                if (a1>=a2){Toast.makeText(this, "Invalid time input. Please check and try again.", Toast.LENGTH_SHORT).show()}else{
                    brkstart1.text = timeFormat.format(selectedTime.time)
                }
            },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),false)
            timePicker.show()
            timePicker.updateTime(12,0)
        }

        brkend1.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                var a1 = brkstart1.text.toString().substring(0,2).toInt()
                var a2 = timeFormat.format(selectedTime.time).substring(0,2).toInt()
                if (a1>=a2){Toast.makeText(this, "Invalid time input. Please check and try again.", Toast.LENGTH_SHORT).show()}else{
                    brkend1.text = timeFormat.format(selectedTime.time)
                }
            },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),false)
            timePicker.show()
            timePicker.updateTime(13,0)
        }

        brkstart2.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)


                var a1 = timeFormat.format(selectedTime.time).substring(0,2).toInt()
                var a2 = brkend2.text.toString().substring(0,2).toInt()
                if (a1>=a2){Toast.makeText(this, "Invalid time input. Please check and try again.", Toast.LENGTH_SHORT).show()}else{
                    brkstart2.text = timeFormat.format(selectedTime.time)
                }
            },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),false)
            timePicker.show()
            timePicker.updateTime(19,0)
        }

        brkend2.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                var a1 = brkstart2.text.toString().substring(0,2).toInt()
                var a2 = timeFormat.format(selectedTime.time).substring(0,2).toInt()
                if (a1>=a2){Toast.makeText(this, "Invalid time input. Please check and try again.", Toast.LENGTH_SHORT).show()}else{
                    brkend2.text = timeFormat.format(selectedTime.time)
                }
            },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),false)
            timePicker.show()
            timePicker.updateTime(20,0)
        }



        update_Clinic.setOnClickListener {
            updateClinic()
        }

        delete_Clinic.setOnClickListener {
            DeleteAlertDialog()
        }






        monopentime.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                var a1 = timeFormat.format(selectedTime.time).substring(0,2).toInt()
                var a2 = monclosetime.text.toString().substring(0,2).toInt()
                if (a1>=a2){Toast.makeText(this, "Invalid time input. Please check and try again.", Toast.LENGTH_SHORT).show()}else{
                    monopentime.text = timeFormat.format(selectedTime.time)
                }
            },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),false)
            timePicker.show()
            timePicker.updateTime(8,0)
        }

        monclosetime.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                var a1 = monopentime.text.toString().substring(0,2).toInt()
                var a2 = timeFormat.format(selectedTime.time).substring(0,2).toInt()
                if (a1>=a2){Toast.makeText(this, "Invalid time input. Please check and try again.", Toast.LENGTH_SHORT).show()}else{
                    monclosetime.text = timeFormat.format(selectedTime.time)
                }
            },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),false)
            timePicker.show()
            timePicker.updateTime(17,0)
        }

        tueopentime.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                var a1 = timeFormat.format(selectedTime.time).substring(0,2).toInt()
                var a2 = tueclosetime.text.toString().substring(0,2).toInt()
                if (a1>=a2){Toast.makeText(this, "Invalid time input. Please check and try again.", Toast.LENGTH_SHORT).show()}else{
                    tueopentime.text = timeFormat.format(selectedTime.time)
                }
            },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),false)
            timePicker.show()
            timePicker.updateTime(8,0)
        }

        tueclosetime.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                var a1 = tueopentime.text.toString().substring(0,2).toInt()
                var a2 = timeFormat.format(selectedTime.time).substring(0,2).toInt()
                if (a1>=a2){Toast.makeText(this, "Invalid time input. Please check and try again.", Toast.LENGTH_SHORT).show()}else{
                    tueclosetime.text = timeFormat.format(selectedTime.time)
                }
            },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),false)
            timePicker.show()
            timePicker.updateTime(17,0)
        }

        wedopentime.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                var a1 = timeFormat.format(selectedTime.time).substring(0,2).toInt()
                var a2 = wedclosetime.text.toString().substring(0,2).toInt()
                if (a1>=a2){Toast.makeText(this, "Invalid time input. Please check and try again.", Toast.LENGTH_SHORT).show()}else{
                    wedopentime.text = timeFormat.format(selectedTime.time)
                }
            },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),false)
            timePicker.show()
            timePicker.updateTime(8,0)
        }

        wedclosetime.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                var a1 = wedopentime.text.toString().substring(0,2).toInt()
                var a2 = timeFormat.format(selectedTime.time).substring(0,2).toInt()
                if (a1>=a2){Toast.makeText(this, "Invalid time input. Please check and try again.", Toast.LENGTH_SHORT).show()}else{
                    wedclosetime.text = timeFormat.format(selectedTime.time)
                }
            },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),false)
            timePicker.show()
            timePicker.updateTime(17,0)
        }

        thuopentime.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                var a1 = timeFormat.format(selectedTime.time).substring(0,2).toInt()
                var a2 = thuclosetime.text.toString().substring(0,2).toInt()
                if (a1>=a2){Toast.makeText(this, "Invalid time input. Please check and try again.", Toast.LENGTH_SHORT).show()}else{
                    thuopentime.text = timeFormat.format(selectedTime.time)
                }
            },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),false)
            timePicker.show()
            timePicker.updateTime(8,0)
        }

        thuclosetime.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                var a1 = thuopentime.text.toString().substring(0,2).toInt()
                var a2 = timeFormat.format(selectedTime.time).substring(0,2).toInt()
                if (a1>=a2){Toast.makeText(this, "Invalid time input. Please check and try again.", Toast.LENGTH_SHORT).show()}else{
                    thuclosetime.text = timeFormat.format(selectedTime.time)
                }
            },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),false)
            timePicker.show()
            timePicker.updateTime(17,0)
        }

        friopentime.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                var a1 = timeFormat.format(selectedTime.time).substring(0,2).toInt()
                var a2 = friclosetime.text.toString().substring(0,2).toInt()
                if (a1>=a2){Toast.makeText(this, "Invalid time input. Please check and try again.", Toast.LENGTH_SHORT).show()}else{
                    friopentime.text = timeFormat.format(selectedTime.time)
                }
            },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),false)
            timePicker.show()
            timePicker.updateTime(8,0)
        }

        friclosetime.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                var a1 = friopentime.text.toString().substring(0,2).toInt()
                var a2 = timeFormat.format(selectedTime.time).substring(0,2).toInt()
                if (a1>=a2){Toast.makeText(this, "Invalid time input. Please check and try again.", Toast.LENGTH_SHORT).show()}else{
                    friclosetime.text = timeFormat.format(selectedTime.time)
                }
            },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),false)
            timePicker.show()
            timePicker.updateTime(17,0)
        }

        satopentime.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                var a1 = timeFormat.format(selectedTime.time).substring(0,2).toInt()
                var a2 = satclosetime.text.toString().substring(0,2).toInt()
                if (a1>=a2){Toast.makeText(this, "Invalid time input. Please check and try again.", Toast.LENGTH_SHORT).show()}else{
                    satopentime.text = timeFormat.format(selectedTime.time)
                }
            },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),false)
            timePicker.show()
            timePicker.updateTime(8,0)
        }

        satclosetime.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                var a1 = satopentime.text.toString().substring(0,2).toInt()
                var a2 = timeFormat.format(selectedTime.time).substring(0,2).toInt()
                if (a1>=a2){Toast.makeText(this, "Invalid time input. Please check and try again.", Toast.LENGTH_SHORT).show()}else{
                    satclosetime.text = timeFormat.format(selectedTime.time)
                }
            },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),false)
            timePicker.show()
            timePicker.updateTime(17,0)
        }

        sunopentime.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                var a1 = timeFormat.format(selectedTime.time).substring(0,2).toInt()
                var a2 = sunclosetime.text.toString().substring(0,2).toInt()
                if (a1>=a2){Toast.makeText(this, "Invalid time input. Please check and try again.", Toast.LENGTH_SHORT).show()}else{
                    sunopentime.text = timeFormat.format(selectedTime.time)
                }
            },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),false)
            timePicker.show()
            timePicker.updateTime(8,0)
        }

        sunclosetime.setOnClickListener {
            val now = Calendar.getInstance()
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)

                var a1 = sunopentime.text.toString().substring(0,2).toInt()
                var a2 = timeFormat.format(selectedTime.time).substring(0,2).toInt()
                if (a1>=a2){Toast.makeText(this, "Invalid time input. Please check and try again.", Toast.LENGTH_SHORT).show()}else{
                    sunclosetime.text = timeFormat.format(selectedTime.time)
                }
            },
                now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),false)
            timePicker.show()
            timePicker.updateTime(17,0)
        }

        seekbar_patients.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                maximum_patients.text = progress.toString()
                if (maximum_patients.text == "1"){textview_patienttitle.text = "Patient"}else{textview_patienttitle.text = "Patients"}
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        patient_minus.setOnClickListener {
            seekbar_patients.progress = seekbar_patients.progress - 1
        }

        patient_plus.setOnClickListener {
            seekbar_patients.progress = seekbar_patients.progress + 1
        }



        seekbar_bookdays.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                book_days.text = progress.toString()
                if (book_days.text == "1"){textview_bookdaystitle.text = "Days ahead"}else{textview_bookdaystitle.text = "Days ahead"}
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

        days_minus.setOnClickListener {
            seekbar_bookdays.progress = seekbar_bookdays.progress - 1
        }

        days_plus.setOnClickListener {
            seekbar_bookdays.progress = seekbar_bookdays.progress + 1
        }
    }

    fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    private fun updateClinic(){
        progressbar.visibility = View.VISIBLE
        var documentName:String = cfname + cmname + clname
        db = FirebaseFirestore.getInstance().document("Clinics/List")
        rootRef = db.collection("DoctorsList").document("$documentName")


        doAsync {
            var firstName:String = cfname
            var middleName:String = cmname
            var lastName:String = clname
            var gender:String = genderofDoc
            var doctorDegree:String = degreeofDoc
            var medPractice:String = pracofDoc
            var contNum:String = update_contactNum.text.toString()
            var cert:String = update_cert.text.toString()
            var address:String = update_address.text.toString()
            var mon:Boolean = update_Mon.isChecked
            var tue:Boolean = update_Tue.isChecked
            var wed:Boolean = update_Wed.isChecked
            var thu:Boolean = update_Thu.isChecked
            var fri:Boolean = update_Fri.isChecked
            var sat:Boolean = update_Sat.isChecked
            var sun:Boolean = update_Sun.isChecked
            var brk1:Boolean = addClinic_brk1.isChecked
            var brk2:Boolean = addClinic_brk2.isChecked
            var brk1start:String = brkstart1.text.toString()
            var brk1end:String = brkend1.text.toString()
            var brk2start:String = brkstart2.text.toString()
            var brk2end:String = brkend2.text.toString()
            var monot:String = monopentime.text.toString()
            var monct:String = monclosetime.text.toString()
            var tueot:String = tueopentime.text.toString()
            var tuect:String = tueclosetime.text.toString()
            var wedot:String = wedopentime.text.toString()
            var wedct:String = wedclosetime.text.toString()
            var thuot:String = thuopentime.text.toString()
            var thuct:String = thuclosetime.text.toString()
            var friot:String = friopentime.text.toString()
            var frict:String = friclosetime.text.toString()
            var satot:String = satopentime.text.toString()
            var satct:String = satclosetime.text.toString()
            var sunot:String = sunopentime.text.toString()
            var sunct:String = sunclosetime.text.toString()
            var maxpat:String = maximum_patients.text.toString()
            var bookingdays:String = book_days.text.toString()
            var clinicCreator:String = mAuth.currentUser!!.phoneNumber.toString()





            if (!contNum.isEmpty() && !cert.isEmpty() && !address.isEmpty()) {
                try {

                    Tasks.await(rootRef.update("CNum", contNum))
                    Tasks.await(rootRef.update("Cert", cert))
                    Tasks.await(rootRef.update("Address", address))
                    Tasks.await(rootRef.update("Mon", mon))
                    Tasks.await(rootRef.update("Tue", tue))
                    Tasks.await(rootRef.update("Wed", wed))
                    Tasks.await(rootRef.update("Thu", thu))
                    Tasks.await(rootRef.update("Fri", fri))
                    Tasks.await(rootRef.update("Sat", sat))
                    Tasks.await(rootRef.update("Sun", sun))
                    Tasks.await(rootRef.update("MonOpen", monot))
                    Tasks.await(rootRef.update("MonClose", monct))
                    Tasks.await(rootRef.update("TueOpen", tueot))
                    Tasks.await(rootRef.update("TueClose", tuect))
                    Tasks.await(rootRef.update("WedOpen", wedot))
                    Tasks.await(rootRef.update("WedClose", wedct))
                    Tasks.await(rootRef.update("ThuOpen", thuot))
                    Tasks.await(rootRef.update("ThuClose", thuct))
                    Tasks.await(rootRef.update("FriOpen", friot))
                    Tasks.await(rootRef.update("FriClose", frict))
                    Tasks.await(rootRef.update("SatOpen", satot))
                    Tasks.await(rootRef.update("SatClose", satct))
                    Tasks.await(rootRef.update("SunOpen", sunot))
                    Tasks.await(rootRef.update("SunClose", sunct))
                    Tasks.await(rootRef.update("Break1", brk1))
                    Tasks.await(rootRef.update("Break2", brk2))
                    Tasks.await(rootRef.update("Break1start", brk1start))
                    Tasks.await(rootRef.update("Break1end", brk1end))
                    Tasks.await(rootRef.update("Break2start", brk2start))
                    Tasks.await(rootRef.update("Break2end", brk2end))
                    Tasks.await(rootRef.update("MaxPat", maxpat))
                    Tasks.await(rootRef.update("AllowedBookDays", bookingdays))
                    Tasks.await(rootRef.update("Creator", clinicCreator))

                    uiThread {
                        toast("Successfully updated Clinic Info")
                        progressbar.visibility = View.INVISIBLE
                    }

                }catch (e:Exception) {
                    uiThread {
                        toast(e.toString())
                        progressbar.visibility = View.INVISIBLE
                    }
                }
            }else {
                toast("Please fill up all fields.")
                progressbar.visibility = View.INVISIBLE
            }

        }







    }



    private fun deleteClinic(){
        progressbar.visibility = View.VISIBLE
        var documentName:String = cfname + cmname + clname
        db = FirebaseFirestore.getInstance().document("Clinics/List")
        rootRef = db.collection("DoctorsList").document("$documentName")

        doAsync {
            try {
                //Get "PublicProfile" collection reference
                Tasks.await(rootRef.delete())
                uiThread {
                    toast("Successfully deleted Clinic")
                    progressbar.visibility = View.INVISIBLE
                }
                finish()
            } catch (e: Throwable) {
                uiThread {
                    toast(e.toString())
                    progressbar.visibility = View.INVISIBLE
                }
            }
        }


    }

    private fun DeleteAlertDialog(){
        lateinit var dialog: AlertDialog

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Clinic")
        builder.setMessage("Are you sure you want to delete this clinic?")
        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> deleteClinic()
                DialogInterface.BUTTON_NEGATIVE -> dialog.hide()
            }
        }
        builder.setPositiveButton("YES",dialogClickListener)
        builder.setNegativeButton("CANCEL",dialogClickListener)
        dialog = builder.create()
        dialog.show()
    }

}
