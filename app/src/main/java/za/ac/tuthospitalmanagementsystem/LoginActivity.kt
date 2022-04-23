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

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val textViewRegister = findViewById<TextView>(R.id.textViewRegister)
        val loginButton = findViewById<Button>(R.id.buttonLogin)

        textViewRegister.setOnClickListener{
            goToRegisterPage()
        }

        loginButton.setOnClickListener {

            val userName : String = findViewById<TextInputEditText>(R.id.textInputEditTextEmail).text.toString().trim()


            if(userName.isNotEmpty()){


                goToPatient(userName)
            }else{
                 Toast.makeText(this,"Enter your email address",Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun goToPatient(userName : String) {

        database = FirebaseDatabase.getInstance().getReference("Admin").child(userName)
        database.get().addOnSuccessListener {
            if(it.exists()){
                val password : String = findViewById<TextInputEditText>(R.id.textInputEditTextPassword).text.toString()

                val passwordData : String = it.child("password").value.toString()

                if(password.contentEquals(passwordData)){

                    val intent = Intent(this,AdminActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"Login successful",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"Email and password are incorrect",Toast.LENGTH_LONG).show()
                }

            }else{
                Toast.makeText(this,"username doesn't exist",Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this,"failed",Toast.LENGTH_LONG).show()
        }
    }

    private fun goToRegisterPage() {
        val intent = Intent(this,PatientRegisterActivity::class.java)
        startActivity(intent)
    }
}