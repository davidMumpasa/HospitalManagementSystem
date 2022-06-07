package za.ac.tuthospitalmanagementsystem

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class EditAppointmentActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_appointment)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Update Appointment"
        val appointmentNo: String = intent.getStringExtra("appointmentNo").toString()
        val date = findViewById<EditText>(R.id.editTextDate)

        val myCalender = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR,year)
            myCalender.set(Calendar.MONTH,month)
            myCalender.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            updateMyCalender(myCalender)
        }

        date.setOnClickListener {
            DatePickerDialog(this,datePicker,myCalender.get(Calendar.YEAR),myCalender.get(Calendar.MONTH),myCalender.get(
                Calendar.DAY_OF_MONTH)).show()
        }

        val appointmentNumber = findViewById<TextView>(R.id.appointmentNumber)
        appointmentNumber.text = appointmentNo

        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            updateAppointment(appointmentNo)
        }
    }
    private fun updateMyCalender(myCalender: Calendar) {
        val date = findViewById<EditText>(R.id.editTextDate)
        val dFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        date.setText(dFormat.format(myCalender.time))
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