package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val textViewRegister = findViewById<TextView>(R.id.textViewRegister)
        val loginButton = findViewById<Button>(R.id.buttonLogin)

        textViewRegister.setOnClickListener{
            goToRegisterPage()
        }
        loginButton.setOnClickListener {
            goToPatient()
        }


    }

    private fun goToPatient() {
        val intent = Intent(this,PatientActivity::class.java)
        startActivity(intent)
    }

    private fun goToRegisterPage() {
        val intent = Intent(this,RegisterActivity::class.java)
        startActivity(intent)
    }
}