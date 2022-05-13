package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.StringBuilder

class RecordActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private lateinit var recordNo : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        //val record = findViewById<TextView>(R.id.textViewRecord)
        //val recordNo = findViewById<EditText>(R.id.editTextRecordNo).text.toString()
        val buttonEdit = findViewById<Button>(R.id.buttonEdit)
        viewAllRecords()
        buttonEdit.setOnClickListener {
            goToEdit()
        }
    }

    private fun viewAllRecords() {
        val record = findViewById<TextView>(R.id.textViewRecord)
        database = FirebaseDatabase.getInstance().getReference("PatientRecord")
        database.get().addOnSuccessListener {
            var sb = StringBuilder()
            for(i in it.children){

                var username = i.child("username").value
                var illness = i.child("illness").value
                var allergy = i.child("allergy").value
                var treatment = i.child("treatment").value
                var id = i.key

                sb.append("Record No: $id\nUsername: $username\nIllness: $illness\nAllergy: $allergy\nTreatment: $treatment\n_____________________________\n")
            }
            record.text = sb
        }.addOnFailureListener {
            Toast.makeText(this,"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun goToEdit() {
        recordNo = findViewById<EditText>(R.id.editTextRecordNo).text.toString()
        var intent = Intent(this,EditRecordActivity::class.java)
        intent.putExtra("recordNo",recordNo)
        startActivity(intent)
    }
}