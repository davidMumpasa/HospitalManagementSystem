package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.random.Random

class PatientRecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_record)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Patient Record"
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            saveRecord();
        }
    }

    private fun saveRecord() {
        val username = findViewById<TextInputEditText>(R.id.textInputEditTextUsername)
        val illness = findViewById<TextInputEditText>(R.id.textInputEditTextIllness)
        val medicalNo = findViewById<TextInputEditText>(R.id.textInputEditMedicalNo)
        val medication = findViewById<TextInputEditText>(R.id.textInputEditTextMedication)
        val allergy = findViewById<Spinner>(R.id.spinnerAllergy)
        val treatment = findViewById<TextInputEditText>(R.id.textInputEditTextTreatment)

        val randomValues = Random.nextInt(1000)
        //val record = "12367"
        val database  = Firebase.database
        val myref = database.getReference("PatientRecord").child(randomValues.toString())

        myref.setValue(PatientRecord(username.text.toString(),illness.text.toString(),medicalNo.text.toString(),medication.text.toString(),allergy.selectedItem.toString(),treatment.text.toString()))

        val intent = Intent(this,DoctorActivity::class.java)
        startActivity(intent)
    }
}