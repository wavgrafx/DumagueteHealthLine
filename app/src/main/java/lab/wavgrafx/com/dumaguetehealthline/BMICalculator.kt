package lab.wavgrafx.com.dumaguetehealthline

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_bmicalculator.*
import java.math.RoundingMode
import java.text.DecimalFormat

class BMICalculator : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmicalculator)

        var seekbar_weight = findViewById<View>(R.id.seekbar_weight) as SeekBar
        var seekbar_height = findViewById<View>(R.id.seekbar_height) as SeekBar
        var bmiResult = findViewById<View>(R.id.bmi_Result) as TextView

        fun calcuBMI(){
            var userWeight = seekbar_weight.progress.toDouble()
            var userHeight = seekbar_height.progress.toDouble()

            var calcresult:Double = (userWeight/userHeight/userHeight) * 10000

            val num = calcresult
            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING

            bmiResult.text = (df.format(num))
            var cc = bmiResult.text.toString()
            var vc = cc.toDouble()

            if (vc <= 15){ bmiDescription.text = "Very severely underweight" }
            if (vc >= 15 && vc <= 15.9){ bmiDescription.text = "Severely underweight" }
            if (vc >= 16 && vc <= 18.4){ bmiDescription.text = "Underweight" }
            if (vc >= 18.5 && vc <= 24.9){ bmiDescription.text = "Normal (healthy weight)" }
            if (vc >= 25 && vc <= 29.9){ bmiDescription.text = "Overweight" }
            if (vc >= 30 && vc <= 34.9){ bmiDescription.text = "Obese Class I (Moderately obese)" }
            if (vc >= 35 && vc <= 39.9){ bmiDescription.text = "Obese Class II (Severely obese)" }
            if (vc >= 40 && vc <= 44.9){ bmiDescription.text = "Obese Class III (Very severely obese)" }
            if (vc >= 45 && vc <= 49.9){ bmiDescription.text = "Obese Class IV (Morbidly Obese)" }
            if (vc >= 50 && vc <= 59.9){ bmiDescription.text = "Obese Class V (Super Obese)" }
            if (vc >= 60){ bmiDescription.text = "Obese Class VI (Hyper Obese)" }

            if (vc <= 18.5){ bmi_healthRisk.text = "Risk of developing problems such as nutritional deficiency and osteoporosis" }
            if (vc >= 18.6 && vc <= 22.9){ bmi_healthRisk.text = "Low Risk (healthy range)" }
            if (vc >= 23 && vc <= 27.4){ bmi_healthRisk.text = "Moderate risk of developing heart disease, high blood pressure, stroke, diabetes" }
            if (vc >= 27.5){ bmi_healthRisk.text = "High risk of developing heart disease, high blood pressure, stroke, diabetes" }
        }

        seekbar_height.progress.coerceIn(48, 251)
        seekbar_weight.progress.coerceIn(3, 640)

        seekbar_weight.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                bmi_weight.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                calcuBMI()
            }

        })

        seekbar_height.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                bmi_height.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                calcuBMI()

            }

        })

        weight_minus.setOnClickListener {
            seekbar_weight.progress = seekbar_weight.progress - 1
            calcuBMI()
        }

        weight_plus.setOnClickListener {
            seekbar_weight.progress = seekbar_weight.progress + 1
            calcuBMI()
        }

        height_minus.setOnClickListener {
            seekbar_height.progress = seekbar_height.progress - 1
            calcuBMI()
        }

        height_plus.setOnClickListener {
            seekbar_height.progress = seekbar_height.progress + 1
            calcuBMI()
        }

    }
}
