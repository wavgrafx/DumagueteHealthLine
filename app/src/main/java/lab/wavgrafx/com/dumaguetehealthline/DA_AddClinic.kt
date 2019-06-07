package lab.wavgrafx.com.dumaguetehealthline

import android.app.TimePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_da__add_clinic.*
import java.text.SimpleDateFormat
import java.util.*

class DA_AddClinic : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    lateinit var db:DocumentReference

    lateinit var addClinic_editText_FirstName: EditText
    lateinit var addClinic_editText_MiddleName: EditText
    lateinit var addClinic_editText_LastName: EditText
    lateinit var addClinic_spinner_Gender: Spinner
    lateinit var addClinic_spinner_degree: Spinner
    lateinit var addClinic_spinner_medPractice:Spinner
    lateinit var addClinic_editText_contactNumber:EditText
    lateinit var addClinic_editText_certificates: EditText
    lateinit var addClinic_editText_clinicAddress: EditText
    lateinit var addClinic_checkbox_Mon: CheckBox
    lateinit var addClinic_checkbox_Tue: CheckBox
    lateinit var addClinic_checkbox_Wed: CheckBox
    lateinit var addClinic_checkbox_Thu: CheckBox
    lateinit var addClinic_checkbox_Fri: CheckBox
    lateinit var addClinic_checkbox_Sat: CheckBox
    lateinit var addClinic_checkbox_Sun: CheckBox
    lateinit var addClinic_brk1:CheckBox
    lateinit var addClinic_brk2:CheckBox
    lateinit var brkstart1:TextView
    lateinit var brkend1:TextView
    lateinit var brkstart2:TextView
    lateinit var brkend2:TextView
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
    lateinit var maximum_patients: TextView
    lateinit var book_days: TextView
    lateinit var seekbar_patients: SeekBar
    lateinit var seekbar_bookdays: SeekBar
    lateinit var textview_patienttitle: TextView
    lateinit var textview_bookdaystitle: TextView
    lateinit var addClinic_button_SaveClinic: Button
    lateinit var progressBar:ProgressBar


    private var timeFormat = SimpleDateFormat("HH:mm", Locale.US)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_da__add_clinic)
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance().document("Clinics/List")
        addClinic_button_SaveClinic = findViewById(R.id.addClinic_button_SaveClinic)
        addClinic_checkbox_Mon = findViewById(R.id.addClinic_checkbox_Mon)
        addClinic_checkbox_Tue = findViewById(R.id.addClinic_checkbox_Tue)
        addClinic_checkbox_Wed = findViewById(R.id.addClinic_checkbox_Wed)
        addClinic_checkbox_Thu = findViewById(R.id.addClinic_checkbox_Thu)
        addClinic_checkbox_Fri = findViewById(R.id.addClinic_checkbox_Fri)
        addClinic_checkbox_Sat = findViewById(R.id.addClinic_checkbox_Sat)
        addClinic_checkbox_Sun = findViewById(R.id.addClinic_checkbox_Sun)
        addClinic_brk1 = findViewById(R.id.addClinic_brk1)
        addClinic_brk2 = findViewById(R.id.addClinic_brk2)
        brkstart1 = findViewById(R.id.brkstart1)
        brkend1 = findViewById(R.id.brkend1)
        brkstart2 = findViewById(R.id.brkstart2)
        brkend2 = findViewById(R.id.brkend2)
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
        maximum_patients = findViewById(R.id.maximum_patients)
        book_days = findViewById(R.id.book_days)
        seekbar_bookdays = findViewById(R.id.seekbar_bookdays)
        seekbar_patients = findViewById(R.id.seekbar_patients)
        textview_patienttitle = findViewById(R.id.textview_patienttitle)
        textview_bookdaystitle = findViewById(R.id.textview_bookdaystitle)



        progressBar =  findViewById(R.id.progressbar)

        addClinic_button_SaveClinic.setOnClickListener {
                view: View? -> saveClinic()
        }

        addClinic_button_Close.setOnClickListener {
            finish()
        }

        addClinic_checkbox_Mon.setOnClickListener {
            if(addClinic_checkbox_Mon.isChecked){
                monopentime.visibility = View.VISIBLE
                monclosetime.visibility = View.VISIBLE
            }else{
                monopentime.visibility = View.INVISIBLE
                monclosetime.visibility = View.INVISIBLE
            }
        }

        addClinic_checkbox_Tue.setOnClickListener {
            if(addClinic_checkbox_Tue.isChecked){
                tueopentime.visibility = View.VISIBLE
                tueclosetime.visibility = View.VISIBLE
            }else{
                tueopentime.visibility = View.INVISIBLE
                tueclosetime.visibility = View.INVISIBLE
            }
        }

        addClinic_checkbox_Wed.setOnClickListener {
            if(addClinic_checkbox_Wed.isChecked){
                wedopentime.visibility = View.VISIBLE
                wedclosetime.visibility = View.VISIBLE
            }else{
                wedopentime.visibility = View.INVISIBLE
                wedclosetime.visibility = View.INVISIBLE
            }
        }

        addClinic_checkbox_Thu.setOnClickListener {
            if(addClinic_checkbox_Thu.isChecked){
                thuopentime.visibility = View.VISIBLE
                thuclosetime.visibility = View.VISIBLE
            }else{
                thuopentime.visibility = View.INVISIBLE
                thuclosetime.visibility = View.INVISIBLE
            }
        }

        addClinic_checkbox_Fri.setOnClickListener {
            if(addClinic_checkbox_Fri.isChecked){
                friopentime.visibility = View.VISIBLE
                friclosetime.visibility = View.VISIBLE
            }else{
                friopentime.visibility = View.INVISIBLE
                friclosetime.visibility = View.INVISIBLE
            }
        }

        addClinic_checkbox_Sat.setOnClickListener {
            if(addClinic_checkbox_Sat.isChecked){
                satopentime.visibility = View.VISIBLE
                satclosetime.visibility = View.VISIBLE
            }else{
                satopentime.visibility = View.INVISIBLE
                satclosetime.visibility = View.INVISIBLE
            }
        }

        addClinic_checkbox_Sun.setOnClickListener {
            if(addClinic_checkbox_Sun.isChecked){
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


    private fun saveClinic(){
        progressBar.setVisibility(View.VISIBLE)

        addClinic_editText_FirstName = findViewById(R.id.addClinic_editText_FirstName)
        addClinic_editText_MiddleName = findViewById(R.id.addClinic_editText_MiddleName)
        addClinic_editText_LastName = findViewById(R.id.addClinic_editText_LastName)
        addClinic_spinner_Gender = findViewById(R.id.addClinic_spinner_Gender)
        addClinic_spinner_degree = findViewById(R.id.addClinic_spinner_degree)
        addClinic_spinner_medPractice = findViewById(R.id.addClinic_spinner_medPractice)
        addClinic_editText_contactNumber = findViewById(R.id.addClinic_editText_contactNum)
        addClinic_editText_certificates = findViewById(R.id.addClinic_editText_certificates)
        addClinic_editText_clinicAddress = findViewById(R.id.addClinic_editText_clinicAddress)
        addClinic_checkbox_Mon = findViewById(R.id.addClinic_checkbox_Mon)
        addClinic_checkbox_Tue = findViewById(R.id.addClinic_checkbox_Tue)
        addClinic_checkbox_Wed = findViewById(R.id.addClinic_checkbox_Wed)
        addClinic_checkbox_Thu = findViewById(R.id.addClinic_checkbox_Thu)
        addClinic_checkbox_Fri = findViewById(R.id.addClinic_checkbox_Fri)
        addClinic_checkbox_Sat = findViewById(R.id.addClinic_checkbox_Sat)
        addClinic_checkbox_Sun = findViewById(R.id.addClinic_checkbox_Sun)



        var firstName:String = addClinic_editText_FirstName.text.toString()
        var middleName:String = addClinic_editText_MiddleName.text.toString()
        var lastName:String = addClinic_editText_LastName.text.toString()
        var gender:String = addClinic_spinner_Gender.selectedItem.toString()
        var doctorDegree:String = addClinic_spinner_degree.selectedItem.toString()
        var medPractice:String = addClinic_spinner_medPractice.selectedItem.toString()
        var contNum:String = addClinic_editText_contactNumber.text.toString()
        var cert:String = addClinic_editText_certificates.text.toString()
        var address:String = addClinic_editText_clinicAddress.text.toString()
        var mon:Boolean = addClinic_checkbox_Mon.isChecked
        var tue:Boolean = addClinic_checkbox_Tue.isChecked
        var wed:Boolean = addClinic_checkbox_Wed.isChecked
        var thu:Boolean = addClinic_checkbox_Thu.isChecked
        var fri:Boolean = addClinic_checkbox_Fri.isChecked
        var sat:Boolean = addClinic_checkbox_Sat.isChecked
        var sun:Boolean = addClinic_checkbox_Sun.isChecked
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





        if (!firstName.isEmpty() && !middleName.isEmpty() && !lastName.isEmpty() && !contNum.isEmpty() && !cert.isEmpty() && !address.isEmpty()) {
            try {
                val items = HashMap<String, Any>()
                items.put("Fname", firstName)
                items.put("Mname", middleName)
                items.put("Lname", lastName)
                items.put("Gender", gender)
                items.put("Degree", doctorDegree)
                items.put("Practice", medPractice)
                items.put("CNum", contNum)
                items.put("Cert", cert)
                items.put("Address", address)
                items.put("Mon", mon)
                items.put("Tue", tue)
                items.put("Wed", wed)
                items.put("Thu", thu)
                items.put("Fri", fri)
                items.put("Sat", sat)
                items.put("Sun", sun)
                items.put("MonOpen", monot)
                items.put("MonClose", monct)
                items.put("TueOpen", tueot)
                items.put("TueClose", tuect)
                items.put("WedOpen", wedot)
                items.put("WedClose", wedct)
                items.put("ThuOpen", thuot)
                items.put("ThuClose", thuct)
                items.put("FriOpen", friot)
                items.put("FriClose", frict)
                items.put("SatOpen", satot)
                items.put("SatClose", satct)
                items.put("SunOpen", sunot)
                items.put("SunClose", sunct)
                items.put("Break1", brk1)
                items.put("Break2", brk2)
                items.put("Break1start", brk1start)
                items.put("Break1end", brk1end)
                items.put("Break2start", brk2start)
                items.put("Break2end", brk2end)
                items.put("MaxPat", maxpat)
                items.put("AllowedBookDays", bookingdays)
                items.put("Creator", clinicCreator)

                var documentName:String = firstName + middleName + lastName
                db.collection("DoctorsList").document(documentName).set(items).addOnSuccessListener {
                        void: Void? -> Toast.makeText(this, "Successfully uploaded to the database.", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@DA_AddClinic,DA_ManageClinic::class.java)
                    progressBar.setVisibility(View.INVISIBLE)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                        exception: java.lang.Exception -> Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show()
                }
            }catch (e:Exception) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }
        }else {
            progressBar.setVisibility(View.INVISIBLE)
            Toast.makeText(this, "Please fill up all fields.", Toast.LENGTH_LONG).show()
        }






    }
}