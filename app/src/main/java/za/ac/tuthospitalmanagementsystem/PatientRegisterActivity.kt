package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PatientRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_register)
        val textViewBack = findViewById<TextView>(R.id.textViewBack)

        textViewBack.setOnClickListener {
            goToLoginPage()
        }
    }

    private fun goToLoginPage() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
}