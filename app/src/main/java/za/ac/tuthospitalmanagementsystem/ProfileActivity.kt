package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Update Profile"
        val buttonEdit = findViewById<Button>(R.id.buttonEdit)

        val name= intent.getStringExtra("name").toString()
        val surname= intent.getStringExtra("surname").toString()
        val username : String = intent.getStringExtra("userName").toString()

        val textViewUsername = findViewById<TextView>(R.id.textViewUsername)

        textViewUsername.text = username
        buttonEdit.setOnClickListener {
            editProfile(username,name,surname)
        }
    }

    private fun editProfile(username: String, name: String, surname: String) {
        var record = true
        val address = findViewById<TextInputEditText>(R.id.textInputEditTextAdd).text.toString()
        val number = findViewById<TextInputEditText>(R.id.textInputEditPhone).text.toString()

        if(address.isEmpty()){
            record = false
            val addContainer = findViewById<TextInputLayout>(R.id.addContainer)
            addContainer.helperText = "enter the address"
        }
        if(10 != number.length){
            record = false
            val phoContainer = findViewById<TextInputLayout>(R.id.phoContainer)
            phoContainer.helperText = "Invalid phone number"
        }
        if(record){
            database = FirebaseDatabase.getInstance().getReference("Patient")
            val updatePatient = mapOf(
                "address" to address,
                "phoneNumber" to number
            )
            database.child(username).updateChildren(updatePatient)
            Toast.makeText(this,"Updated", Toast.LENGTH_SHORT).show()
            intent = Intent(this,PatientActivity::class.java)
            intent.putExtra("userName",username)
            intent.putExtra("name",name)
            intent.putExtra("surname",surname)
            intent.putExtra("number",number)
            startActivity(intent)
        }
    }
}