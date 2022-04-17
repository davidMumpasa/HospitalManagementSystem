package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SetAppointmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_appointment)

        var buttonBack = findViewById<Button>(R.id.buttonBack)
        var buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        buttonSubmit.setOnClickListener {
            submitAppointment()
        }

        buttonBack.setOnClickListener {
            goToPatientActivity()
        }
    }

    private fun submitAppointment() {
        val intent = Intent(this,AppointmentActivity::class.java)
        startActivity(intent)
    }

    private fun goToPatientActivity() {
        val intent = Intent(this,PatientActivity::class.java)
        startActivity(intent)
    }
}