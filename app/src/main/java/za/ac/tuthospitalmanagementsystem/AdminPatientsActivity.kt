package za.ac.tuthospitalmanagementsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.StringBuilder

class AdminPatientsActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_patients)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Patients"
        loadingPatient()

        
        val buttonDelete = findViewById<Button>(R.id.buttonDelete)

        buttonDelete.setOnClickListener {
            val editTextPatientId = findViewById<TextView>(R.id.editTextDoctorId)

            val patientEmail : String = editTextPatientId.text.toString()
            if(patientEmail.isNotEmpty()){
                deleteRecord(patientEmail)
                editTextPatientId.setText("")
            }else{
                Toast.makeText(this,"Enter patient ID",Toast.LENGTH_LONG).show()
            }
            loadingPatient()
        }
    }

    private fun loadingPatient() {
        val textViewAppointments = findViewById<TextView>(R.id.textViewDoctors)

        database = FirebaseDatabase.getInstance().getReference("Patient")
        database.get().addOnSuccessListener {
            var sb = StringBuilder()
            for(i in it.children){

                var name = i.child("name").getValue()
                var surname = i.child("surname").getValue()
                var gender = i.child("gender").getValue()
                var id = i.key

                sb.append("Username $id\nName $name\nSurname $surname\nGender $gender\n_____________________________\n")
            }
            textViewAppointments.setText(sb)
        }.addOnFailureListener {
            Toast.makeText(this,"failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteRecord(patientEmail: String) {

        database = FirebaseDatabase.getInstance().getReference("Patient").child(patientEmail)
        database.removeValue().addOnSuccessListener {
            Toast.makeText(this,"Patient removed",Toast.LENGTH_LONG).show()
        }
    }
}