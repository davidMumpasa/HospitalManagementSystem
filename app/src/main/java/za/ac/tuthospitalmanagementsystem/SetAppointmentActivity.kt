package za.ac.tuthospitalmanagementsystem

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random

class SetAppointmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_appointment)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Set Appointment"
        val buttonBack = findViewById<Button>(R.id.buttonBack)
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)
        val editTextDate  = findViewById<TextInputEditText>(R.id.textInputEditTextDate)
        val username : String = intent.getStringExtra("userName").toString()
        val name= intent.getStringExtra("name")
        val surname= intent.getStringExtra("surname")
        val number= intent.getStringExtra("number")

        val myCalender = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth ->
            myCalender.set(Calendar.YEAR,year)
            myCalender.set(Calendar.MONTH,month)
            myCalender.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            updateMyCalender(myCalender)
        }

        editTextDate.setOnClickListener {
            DatePickerDialog(this,datePicker,myCalender.get(Calendar.YEAR),myCalender.get(Calendar.MONTH),myCalender.get(Calendar.DAY_OF_MONTH)).show()
        }
        buttonSubmit.setOnClickListener {
            val randomValues = Random.nextInt(1000)
            submitAppointment(randomValues,username,name,surname,number)
        }

        buttonBack.setOnClickListener {
            goToPatientActivity(username,name,surname,number)
        }
    }

    private fun updateMyCalender(myCalender: Calendar) {
        val editTextDate  = findViewById<TextInputEditText>(R.id.textInputEditTextDate)
        val dFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

        editTextDate.setText(dFormat.format(myCalender.time))
    }

    private fun submitAppointment(appointmentNumber: Int, username: String, name: String?, surname: String?, number: String?) {
        var record = true
        val editTextDisease :String = findViewById<TextInputEditText>(R.id.textInputEditTextAppointment).text.toString()
        val spinnerAvailability :String = findViewById<Spinner>(R.id.spinnerAvailability).selectedItem.toString()
        val editTextDate : String = findViewById<TextInputEditText>(R.id.textInputEditTextDate).text.toString()
        if(editTextDate.isNotEmpty()) {
            val date = Date()
            val dFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
            val currentDate = dFormat.format(date).toString()

            val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

            val current: LocalDate = LocalDate.parse(currentDate, formatter)

            val chosenDate: LocalDate = LocalDate.parse(editTextDate, formatter)

            if (current.isAfter(chosenDate)) {
                record = false
                val dateContainer = findViewById<TextInputLayout>(R.id.dateContainer)
                dateContainer.helperText = "Invalid date"
            }
        }else{
            record = false
            val dateContainer = findViewById<TextInputLayout>(R.id.dateContainer)
            dateContainer.helperText = "Invalid date"
        }
        if(editTextDisease.isEmpty()){
            record = false
            val descriptionContainer = findViewById<TextInputLayout>(R.id.descriptionContainer)
            descriptionContainer.helperText = "Appointment description needed"
        }
        if(spinnerAvailability == "Select the time you are available"){
            record = false
            Toast.makeText(this,"Available time is needed",Toast.LENGTH_SHORT).show()
        }
        if(record){
            val database  = Firebase.database
            val myref = database.getReference("Appointment").child(appointmentNumber.toString())

            myref.setValue(Appointment(username, doctor="null",editTextDisease,spinnerAvailability,editTextDate))

            val intent = Intent(this,PatientActivity::class.java)
            intent.putExtra("userName",username)
            intent.putExtra("userName",username)
            intent.putExtra("name",name)
            intent.putExtra("surname",surname)
            intent.putExtra("number",number)
            startActivity(intent)
        }

    }

    private fun goToPatientActivity(
        username: String,
        name: String?,
        surname: String?,
        number: String?
    ) {
        val intent = Intent(this,PatientActivity::class.java)
        intent.putExtra("userName",username)
        intent.putExtra("userName",username)
        intent.putExtra("name",name)
        intent.putExtra("surname",surname)
        intent.putExtra("number",number)
        startActivity(intent)
    }
}