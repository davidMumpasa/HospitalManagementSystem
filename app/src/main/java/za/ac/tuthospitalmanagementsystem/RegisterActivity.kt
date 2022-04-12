package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val textViewBack = findViewById<TextView>(R.id.textViewBack)
        val buttonPatientRegistration = findViewById<Button>(R.id.buttonPatientRegistration)
        val buttonDoctorRegistration = findViewById<Button>(R.id.buttonDoctorRegistration)

        buttonDoctorRegistration.setOnClickListener {
            goToDoctorRegister()
        }
        buttonPatientRegistration.setOnClickListener {
            goToPatientRegister()
        }
        textViewBack.setOnClickListener {
            goToLoginPage()
        }

    }

    private fun goToDoctorRegister() {
        val intent = Intent(this,DoctorRegisterActivity::class.java)
        startActivity(intent)
    }

    private fun goToPatientRegister() {
        val intent = Intent(this,PatientRegisterActivity::class.java)
        startActivity(intent)
    }

    private fun goToLoginPage() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
}