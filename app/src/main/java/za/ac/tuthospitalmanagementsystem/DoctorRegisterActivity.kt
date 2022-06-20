package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DoctorRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_register)
        //val textViewBack = findViewById<TextView>(R.id.textViewBack)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Doctor Registration"
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        buttonRegister.setOnClickListener {
            val username : String= findViewById<TextInputEditText>(R.id.textInputEditTextEmail).text.toString()
            if(username.isNotEmpty()){
                registerDoctor(username)
                val intent = Intent(this,AdminActivity::class.java)
                startActivity(intent)
            }else{
                val usernameContainer = findViewById<TextInputLayout>(R.id.usernameContainer)
                usernameContainer.helperText = "enter the username"
            }
        }
    }

    private fun registerDoctor(username: String) {
        var record = true
        val password :String = findViewById<TextInputEditText>(R.id.textInputEditTextPassword).text.toString()
        val availability :String = findViewById<Spinner>(R.id.spinnerAvailability).selectedItem.toString()
        val department :String = findViewById<Spinner>(R.id.spinnerDepartment).selectedItem.toString()
        val specialization :String = findViewById<Spinner>(R.id.spinnerSpecialization).selectedItem.toString()
        val address :String = findViewById<TextInputEditText>(R.id.textInputEditTextAddress).text.toString()
        val gender :String = findViewById<Spinner>(R.id.spinnerGender).selectedItem.toString()
        val age :String = findViewById<TextInputEditText>(R.id.textInputEditTextAge).text.toString()
        val phone :String = findViewById<TextInputEditText>(R.id.textInputEditTextPhone).text.toString()
        val id :String = findViewById<TextInputEditText>(R.id.textInputEditTextId).text.toString()
        val surname :String = findViewById<TextInputEditText>(R.id.textInputEditTextLastName).text.toString()
        val name :String = findViewById<TextInputEditText>(R.id.textInputEditTextFirstName).text.toString()
        val role= "doctor"

        if(specialization.isEmpty()){
            record = false
            Toast.makeText(this,"Please select specialization",Toast.LENGTH_SHORT).show()
        }
        if(department == "Select doctor department"){
            record = false
            Toast.makeText(this,"Please select department",Toast.LENGTH_SHORT).show()
        }
        if(availability == "Select the time you are available"){
            record = false
            Toast.makeText(this,"Available time is needed",Toast.LENGTH_SHORT).show()
        }
        if(age.isEmpty()){
            record = false
            val ageContainer = findViewById<TextInputLayout>(R.id.ageContainer)
            ageContainer.helperText = "enter doctor's age"
        }
        if(address.isEmpty()){
            record = false
            val addressContainer = findViewById<TextInputLayout>(R.id.addressContainer)
            addressContainer.helperText = "enter the address"
        }
        if(gender == "Select gender"){
            record = false
            Toast.makeText(this,"please select a gender", Toast.LENGTH_SHORT).show()
        }
        if(password.isEmpty()){
            record = false
            val passwordContainer = findViewById<TextInputLayout>(R.id.passwordContainer)
            passwordContainer.helperText = "Invalid password"
        }
        if(id.length != 13){
            record = false
            val idContainer = findViewById<TextInputLayout>(R.id.idContainer)
            idContainer.helperText = "Invalid ID Number"
        }
        if(phone.length != 10){
            record = false
            val phoneContainer = findViewById<TextInputLayout>(R.id.phoneContainer)
            phoneContainer.helperText = "Invalid phone number"
        }
        if(name.isEmpty()){
            record = false
            val nameContainer = findViewById<TextInputLayout>(R.id.nameContainer)
            nameContainer.helperText = "enter the first name"
        }
        if(surname.isEmpty()){
            record = false
            val lastnameContainer = findViewById<TextInputLayout>(R.id.lastnameContainer)
            lastnameContainer.helperText = "enter the last name"
        }

        if(record){
            val database  = Firebase.database
            val myref = database.getReference("Doctor").child(username)

            myref.setValue(Doctor(name,surname,id,age,gender,phone,address,availability,department,specialization,password,role))
        }
    }
}