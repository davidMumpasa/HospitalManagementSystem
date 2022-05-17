
package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditRecordActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_record)

        val recordNo:String = intent.getStringExtra("recordNo").toString()

        val user = findViewById<TextView>(R.id.textViewRecordNo)
        user.text = recordNo
        val buttonEdit = findViewById<Button>(R.id.buttonEdit)

        buttonEdit.setOnClickListener {
            updateRecord(recordNo)
        }
    }

    private fun updateRecord(recordNo: String) {
        val illness = findViewById<TextInputEditText>(R.id.textInputEditTextIllness).text.toString()
        val medicalNo = findViewById<TextInputEditText>(R.id.textInputEditMedicalNo).text.toString()
        val medication = findViewById<TextInputEditText>(R.id.textInputEditTextMedication).text.toString()
        val allergy = findViewById<Spinner>(R.id.spinnerAllergy).selectedItem.toString()
        val treatment = findViewById<TextInputEditText>(R.id.textInputEditTextTreatment).text.toString()

        database = FirebaseDatabase.getInstance().getReference("PatientRecord")
        var updateRecord = mapOf<String,String>(
            "illness" to illness,
            "medicalNo" to medicalNo,
            "medication" to medication,
            "allergy" to allergy,
            "treatment" to treatment
        )

        database.child(recordNo).updateChildren(updateRecord)
        Toast.makeText(this,"Record updated",Toast.LENGTH_SHORT)

        intent = Intent(this,RecordActivity::class.java)
        startActivity(intent)
    }
}