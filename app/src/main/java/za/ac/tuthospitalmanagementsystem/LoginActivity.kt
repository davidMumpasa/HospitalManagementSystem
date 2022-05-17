package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    private lateinit var radioPatient :RadioButton
    private lateinit var radioAdmin :RadioButton
    private lateinit var radioDoctor : RadioButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Login"
        val textViewRegister = findViewById<TextView>(R.id.textViewRegister)
        val loginButton = findViewById<Button>(R.id.buttonLogin)
        radioPatient = findViewById<RadioButton>(R.id.radioPatient)
        radioAdmin = findViewById<RadioButton>(R.id.radioAdmin)
        radioDoctor = findViewById<RadioButton>(R.id.radioDoctor)
        textViewRegister.setOnClickListener{
            goToRegisterPage()
        }

        loginButton.setOnClickListener {

            val userName : String = findViewById<TextInputEditText>(R.id.textInputEditTextEmail).text.toString().trim()


            if(userName.isNotEmpty()){


                goToPatient(userName)
            }else{
                 Toast.makeText(this,"Enter your your username",Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun goToPatient(userName : String) {

        if(radioAdmin.isChecked){

            database = FirebaseDatabase.getInstance().getReference("Admin").child(userName)
            database.get().addOnSuccessListener {
                if(it.exists()){
                    val password : String = findViewById<TextInputEditText>(R.id.textInputEditTextPassword).text.toString()

                    val passwordData : String = it.child("password").value.toString()

                    if(password.contentEquals(passwordData)){
                        val name : String = it.child("name").value.toString()
                        val surname : String = it.child("surname").value.toString()
                        val number : String = it.child("number").value.toString()

                        val intent = Intent(this,AdminActivity::class.java)
                        intent.putExtra("name",name)
                        intent.putExtra("surname",surname)
                        intent.putExtra("number",number)
                        intent.putExtra("userName",userName)
                        startActivity(intent)
                        Toast.makeText(this,"Login successful",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"Email and password are incorrect",Toast.LENGTH_LONG).show()
                    }
                    Toast.makeText(this,"Login as admin",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"username doesn't exist",Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this,"failed",Toast.LENGTH_LONG).show()
            }
        }
        if(radioDoctor.isChecked){

            database = FirebaseDatabase.getInstance().getReference("Doctor").child(userName)
            database.get().addOnSuccessListener {
                if(it.exists()){
                    val password : String = findViewById<TextInputEditText>(R.id.textInputEditTextPassword).text.toString()

                    val passwordData : String = it.child("password").value.toString()

                    if(password.contentEquals(passwordData)){
                        val name : String = it.child("name").value.toString()
                        val surname : String = it.child("surname").value.toString()
                        val number : String = it.child("phone").value.toString()

                        val intent = Intent(this,DoctorActivity::class.java)
                        intent.putExtra("name",name)
                        intent.putExtra("surname",surname)
                        intent.putExtra("number",number)
                        intent.putExtra("username",userName)
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
        if(radioPatient.isChecked){

            database = FirebaseDatabase.getInstance().getReference("Patient").child(userName)
            database.get().addOnSuccessListener {
                if(it.exists()){
                    val password : String = findViewById<TextInputEditText>(R.id.textInputEditTextPassword).text.toString()

                    val passwordData : String = it.child("password").value.toString()

                    if(password.contentEquals(passwordData)){

                        val name : String = it.child("name").value.toString()
                        val surname : String = it.child("surname").value.toString()
                        val number : String = it.child("phoneNumber").value.toString()

                        val intent = Intent(this,PatientActivity::class.java)
                        intent.putExtra("name",name)
                        intent.putExtra("surname",surname)
                        intent.putExtra("number",number)
                        intent.putExtra("userName",userName)
                        startActivity(intent)
                        Toast.makeText(this,"Login successful",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"Email and password are incorrect",Toast.LENGTH_LONG).show()
                    }
                    Toast.makeText(this,"Login as patient",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"username doesn't exist",Toast.LENGTH_LONG).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this,"failed",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun goToRegisterPage() {
        val intent = Intent(this,PatientRegisterActivity::class.java)
        startActivity(intent)
    }
}