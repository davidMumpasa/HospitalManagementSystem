package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

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

                val userName = findViewById<TextInputEditText>(R.id.textInputEditTextEmail).text.toString()
                if(userName.isNotEmpty()) {
                goToPatient(userName)
                }else{
                    Toast.makeText(this,"Incorrect enter username ",Toast.LENGTH_LONG).show()
                }
            }

    }

    private fun goToPatient(emailLf : String) {

        val password = findViewById<TextInputEditText>(R.id.textInputEditTextPassword)


    }

    private fun goToRegisterPage() {
        val intent = Intent(this,PatientRegisterActivity::class.java)
        startActivity(intent)
    }
}