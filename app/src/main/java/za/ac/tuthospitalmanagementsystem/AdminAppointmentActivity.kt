package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.StringBuilder

class AdminAppointmentActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_appointment)
        val buttonEdit = findViewById<Button>(R.id.buttonEdit)
        val buttonDelete = findViewById<Button>(R.id.buttonDelete)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Appointments"
        loadAppointment()
        buttonEdit.setOnClickListener {
            goToEditAppointment()
        }
        buttonDelete.setOnClickListener {
            deleteAppointment()
        }
        loadAppointment()
    }

    private fun deleteAppointment() {
        val editTextAppointmentNo = findViewById<EditText>(R.id.editTextAppointmentNo).text.toString()
        database = FirebaseDatabase.getInstance().getReference("Appointment").child(editTextAppointmentNo)
        database.removeValue().addOnSuccessListener {
            Toast.makeText(this,"Appointment removed",Toast.LENGTH_LONG).show()
        }
    }

    private fun goToEditAppointment() {
        val editTextAppointmentNo = findViewById<EditText>(R.id.editTextAppointmentNo).text.toString()

        intent = Intent(this,EditAppointmentActivity::class.java)
        intent.putExtra("appointmentNo",editTextAppointmentNo)
        startActivity(intent)
    }

    private fun loadAppointment() {
        val textViewAppointments = findViewById<TextView>(R.id.textViewAppointments)

        database = FirebaseDatabase.getInstance().getReference("Appointment")
        database.get().addOnSuccessListener {
            var sb = StringBuilder()
            for(i in it.children){

                var availability = i.child("availability").value
                var date = i.child("date").value
                var disease = i.child("disease").value
                var doctor : String = i.child("doctor").value.toString()
                if(doctor.contentEquals("null")){
                    doctor = "Not yet assigned"
                }
                var patient = i.child("patient").value
                var id = i.key

                sb.append("Appointment number: $id\nPatient: $patient\nDoctor: $doctor\nAvailability: $availability\nDate: $date\nDisease: $disease\n_____________________________\n")
            }
            textViewAppointments.text = sb
        }.addOnFailureListener {
            Toast.makeText(this,"failed", Toast.LENGTH_LONG).show()
        }
    }
}