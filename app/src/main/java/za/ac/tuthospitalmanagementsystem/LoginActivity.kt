package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val textViewRegister = findViewById<TextView>(R.id.textViewRegister)

        textViewRegister.setOnClickListener{
            goToRegisterPage()
        }

    }

    private fun goToRegisterPage() {
        val intent = Intent(this,RegisterActivity::class.java)
        startActivity(intent)
    }
}