
package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditRecordActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_record)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Update Record"
        val recordNo:String = intent.getStringExtra("recordNo").toString()
        val name: String = intent.getStringExtra("name").toString()
        val surname: String= intent.getStringExtra("surname").toString()
        val number: String= intent.getStringExtra("number").toString()
        val username: String= intent.getStringExtra("username").toString()

        val user = findViewById<TextView>(R.id.textViewRecordNo)
        user.text = recordNo
        val buttonEdit = findViewById<Button>(R.id.buttonEdit)

        buttonEdit.setOnClickListener {
            updateRecord(recordNo,username,name,surname,number)
        }
    }

    private fun updateRecord(recordNo: String, username: String, name: String, surname: String, number: String) {
        var record = true
        val illness = findViewById<TextInputEditText>(R.id.textInputEditTextIllness).text.toString()
        val medicalNo = findViewById<TextInputEditText>(R.id.textInputEditMedicalNo).text.toString()
        val medication = findViewById<TextInputEditText>(R.id.textInputEditTextMedication).text.toString()
        val allergy = findViewById<Spinner>(R.id.spinnerAllergy).selectedItem.toString()
        val treatment = findViewById<TextInputEditText>(R.id.textInputEditTextTreatment).text.toString()

        if(illness.isEmpty()){
            record = false
            val illnessContainer = findViewById<TextInputLayout>(R.id.illnessContainer)
            illnessContainer.helperText = "Enter patient illness"
        }
        if(medicalNo.isEmpty()){
            record = false
            val medicalNoContainer = findViewById<TextInputLayout>(R.id.medicalNoContainer)
            medicalNoContainer.helperText = "Enter medical number or N/A"
        }
        if(medication.isEmpty()){
            record = false
            val medicationContainer = findViewById<TextInputLayout>(R.id.medicationContainer)
            medicationContainer.helperText = "Select the patient username"
        }
        if(treatment.isEmpty()){
            record = false
            val treatmentContainer = findViewById<TextInputLayout>(R.id.treatmentContainer)
            treatmentContainer.helperText = "Enter treatment"
        }
        if(allergy == "Patient has allergy"){
            record = false
            Toast.makeText(this,"Specify if patient has allergy or not",Toast.LENGTH_SHORT).show()
        }
        if(record){
            database = FirebaseDatabase.getInstance().getReference("PatientRecord")
            val updateRecord = mapOf(
                "illness" to illness,
                "medicalNo" to medicalNo,
                "medication" to medication,
                "allergy" to allergy,
                "treatment" to treatment
            )

            database.child(recordNo).updateChildren(updateRecord)
            Toast.makeText(this,"Record updated",Toast.LENGTH_SHORT).show()

            intent = Intent(this,DoctorActivity::class.java)
            intent.putExtra("username",username)
            intent.putExtra("name",name)
            intent.putExtra("surname",surname)
            intent.putExtra("number",number)
            startActivity(intent)
        }
    }
}