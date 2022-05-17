package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PostponeActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_postpone)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Postpone Appointment"
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)
        val appointmentNumber = findViewById<TextView>(R.id.appointmentNumber)
        val appointmentNo : String = intent.getStringExtra("appointmentNo").toString()
        appointmentNumber.text = appointmentNo
        buttonSubmit.setOnClickListener {
            updateAppointment(appointmentNo)
        }
    }

    private fun updateAppointment(appointmentNo: String) {

        val name: String = intent.getStringExtra("name").toString()
        val surname: String= intent.getStringExtra("surname").toString()
        val number: String= intent.getStringExtra("number").toString()
        val username :String= intent.getStringExtra("username").toString()
        val date = findViewById<EditText>(R.id.editTextDate).text.toString()
        val availability = findViewById<Spinner>(R.id.spinnerAvailability).selectedItem.toString()

        database = FirebaseDatabase.getInstance().getReference("Appointment")
        val updateData = mapOf<String,String>(
            "availability" to availability,
            "date" to date,
        )
        database.child(appointmentNo).updateChildren(updateData)
        Toast.makeText(this,"Updated", Toast.LENGTH_SHORT)
        intent = Intent(this,DoctorActivity::class.java)
        intent.putExtra("username",username)
        intent.putExtra("name",name)
        intent.putExtra("surname",surname)
        intent.putExtra("number",number)
        startActivity(intent)
    }
}