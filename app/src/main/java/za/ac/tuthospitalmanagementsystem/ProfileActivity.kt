package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Update Prifile"
        val buttonEdit = findViewById<Button>(R.id.buttonEdit)

        buttonEdit.setOnClickListener {
            editProfile()
        }
    }

    private fun editProfile() {
        val username : String = intent.getStringExtra("username").toString()
        val textViewUsername = findViewById<TextView>(R.id.textViewUsername)
        val name = findViewById<EditText>(R.id.editTextViewName).text.toString()
        val surname = findViewById<EditText>(R.id.editTextViewSurname).text.toString()
        val address = findViewById<EditText>(R.id.editTextViewAddress).text.toString()
        val number = findViewById<EditText>(R.id.editTextViewNumber).text.toString()

        textViewUsername.text = username

        database = FirebaseDatabase.getInstance().getReference("Patient")
        var updatePatient = mapOf<String,String>(
            "name" to name,
            "surname" to surname,
            "address" to address,
            "phoneNumber" to number
        )
        database.child(username).updateChildren(updatePatient)
        Toast.makeText(this,"Updated", Toast.LENGTH_SHORT)
        intent = Intent(this,PatientActivity::class.java)
        startActivity(intent)
    }
}