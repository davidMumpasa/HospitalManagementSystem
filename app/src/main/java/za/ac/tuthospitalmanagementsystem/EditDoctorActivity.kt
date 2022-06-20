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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditDoctorActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_doctor)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Update Doctor Details"
        val username :String = intent.getStringExtra("username").toString()
        val buttonEdit = findViewById<Button>(R.id.buttonEdit)
        val adminName = intent.getStringExtra("name").toString()
        val adminSurname= intent.getStringExtra("surname").toString()
        val adminNumber= intent.getStringExtra("number").toString()
        val adminUsername= intent.getStringExtra("userName").toString()
        buttonEdit.setOnClickListener {
            editDoctor(username,adminName,adminSurname,adminNumber,adminUsername)
        }
    }

    private fun editDoctor(username: String, adminName: String, adminSurname: String, adminNumber: String, adminUsername: String) {
        var record = true
        val availability :String = findViewById<Spinner>(R.id.spinnerAvailability).selectedItem.toString()
        val department :String = findViewById<Spinner>(R.id.spinnerDepartment).selectedItem.toString()
        val specialization :String = findViewById<Spinner>(R.id.spinnerSpecialization).selectedItem.toString()
        val address :String = findViewById<TextInputEditText>(R.id.textInputEditTextAddress).text.toString()
        val age :String = findViewById<TextInputEditText>(R.id.textInputEditTextAge).text.toString()
        val phone :String = findViewById<TextInputEditText>(R.id.textInputEditTextPhone).text.toString()


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
        if(phone.length != 10){
            record = false
            val phoneContainer = findViewById<TextInputLayout>(R.id.phoneContainer)
            phoneContainer.helperText = "Invalid phone number"
        }

        if(record){
            database = FirebaseDatabase.getInstance().getReference("Doctor")
            val updateDoctor = mapOf(
                "availability" to availability,
                "department" to department,
                "specialization" to specialization,
                "address" to address,
                "phone" to phone,
                "age" to age

            )
            database.child(username).updateChildren(updateDoctor)
            Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show()
            intent = Intent(this,AdminActivity::class.java)
            intent.putExtra("name",adminName)
            intent.putExtra("surname",adminSurname)
            intent.putExtra("number",adminNumber)
            intent.putExtra("userName",adminUsername)
            startActivity(intent)
        }
    }
}