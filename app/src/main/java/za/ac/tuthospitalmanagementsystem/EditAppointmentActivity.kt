package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditAppointmentActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_appointment)

        val appointmentNo: String = intent.getStringExtra("appointmentNo").toString()

        val appointmentNumber = findViewById<TextView>(R.id.appointmentNumber)
        appointmentNumber.text = appointmentNo

        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            updateAppointment(appointmentNo)
        }
    }

    private fun updateAppointment(appointmentNo: String?) {
        val availability = findViewById<Spinner>(R.id.spinnerAvailability).selectedItem.toString()
        val date = findViewById<EditText>(R.id.editTextDate).text.toString()
        val doctor = findViewById<EditText>(R.id.textViewDoctor).text.toString()

        database = FirebaseDatabase.getInstance().getReference("Appointment")
        val updateData = mapOf<String,String>(
            "availability" to availability,
            "date" to date,
            "doctor" to doctor
        )
        database.child(appointmentNo.toString()).updateChildren(updateData)
        Toast.makeText(this,"Updated",Toast.LENGTH_SHORT)
        intent = Intent(this,AdminAppointmentActivity::class.java)
        startActivity(intent)
    }
}