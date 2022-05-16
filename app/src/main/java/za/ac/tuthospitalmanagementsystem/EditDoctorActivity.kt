package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditDoctorActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_doctor)

        val username :String = intent.getStringExtra("username").toString()
        val buttonEdit = findViewById<Button>(R.id.buttonEdit)

        buttonEdit.setOnClickListener {
            editDoctor(username)
        }
    }

    private fun editDoctor(username: String) {
        val availability :String = findViewById<Spinner>(R.id.spinnerAvailability).selectedItem.toString()
        val department :String = findViewById<Spinner>(R.id.spinnerDepartment).selectedItem.toString()
        val specialization :String = findViewById<Spinner>(R.id.spinnerSpecialization).selectedItem.toString()
        val address :String = findViewById<TextInputEditText>(R.id.textInputEditTextAddress).text.toString()
        val phone :String = findViewById<TextInputEditText>(R.id.textInputEditTextPhone).text.toString()
        val surname :String = findViewById<TextInputEditText>(R.id.textInputEditTextLastName).text.toString()
        val name :String = findViewById<TextInputEditText>(R.id.textInputEditTextFirstName).text.toString()

        database = FirebaseDatabase.getInstance().getReference("Doctor")
        var updateDoctor = mapOf<String,String>(
            "availability" to availability,
            "department" to department,
            "specialization" to specialization,
            "address" to address,
            "phone" to phone,
            "surname" to surname,
            "name" to name
        )
        database.child(username).updateChildren(updateDoctor)
        Toast.makeText(this,"Updated",Toast.LENGTH_SHORT)
        intent = Intent(this,AdminDoctorActivity::class.java)
        startActivity(intent)
    }
}