package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.apache.commons.math3.util.IntegerSequence as IntegerSequence1

class PatientRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_register)
        val textViewBack = findViewById<TextView>(R.id.textViewBack)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Admin"
        buttonRegister.setOnClickListener {
            //val query = "insert into Patient values()"
            registerPatient()
        }
        textViewBack.setOnClickListener {
            goToLoginPage()
        }
    }

    private fun registerPatient() {
        var record = true
        val firstName = findViewById<TextInputEditText>(R.id.textInputEditTextFirstName).text.toString()
        val lastName = findViewById<TextInputEditText>(R.id.textInputEditTextLastName).text.toString()
        val patientId = findViewById<TextInputEditText>(R.id.textInputEditTextId).text.toString()
        val phoneNumber = findViewById<TextInputEditText>(R.id.textInputEditTextPhone).text.toString()
        val email = findViewById<TextInputEditText>(R.id.textInputEditTextEmail).text.toString()
        val address = findViewById<TextInputEditText>(R.id.textInputEditTextAddress).text.toString()
        val gender = findViewById<Spinner>(R.id.spinnerGender).selectedItem.toString()
        val password = findViewById<TextInputEditText>(R.id.textInputEditTextPassword).text.toString()
        val role = "patient"


        if(firstName.isEmpty()){
            record = false
            val nameContainer = findViewById<TextInputLayout>(R.id.nameContainer)
            nameContainer.helperText = "enter the first name"
        }
        if(lastName.isEmpty()){
            record = false
            val lastnameContainer = findViewById<TextInputLayout>(R.id.lastnameContainer)
            lastnameContainer.helperText = "enter the last name"
        }
        if(patientId.length != 13){
            record = false
            val idContainer = findViewById<TextInputLayout>(R.id.idContainer)
            idContainer.helperText = "Invalid ID Number"
        }
        if(phoneNumber.length != 10){
            record = false
            val phoneContainer = findViewById<TextInputLayout>(R.id.phoneContainer)
            phoneContainer.helperText = "Invalid phone number"
        }
        if(email.isEmpty()){
            record = false
            val usernameContainer = findViewById<TextInputLayout>(R.id.usernameContainer)
            usernameContainer.helperText = "enter the username"
        }
        if(address.isEmpty()){
            record = false
            val addressContainer = findViewById<TextInputLayout>(R.id.addressContainer)
            addressContainer.helperText = "enter the address"
        }
        if(gender == "Select gender"){
            record = false
            Toast.makeText(this,"please select a gender",Toast.LENGTH_SHORT).show()
        }
        if(password.isEmpty()){
            record = false
            val passwordContainer = findViewById<TextInputLayout>(R.id.passwordContainer)
            passwordContainer.helperText = "Invalid password"
        }
        if(record){
            val database  = Firebase.database
            val myref = database.getReference("Patient").child(email)

            myref.setValue(Patient(patientId,firstName,lastName,phoneNumber,address,gender,password,role))

            goToLoginPage()
        }
    }

    private fun goToLoginPage() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
}