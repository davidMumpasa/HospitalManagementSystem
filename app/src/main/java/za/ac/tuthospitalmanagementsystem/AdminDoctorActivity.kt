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
import java.lang.StringBuilder

class AdminDoctorActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_doctor)
        loadingDoctor()

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        val name = intent.getStringExtra("name").toString()
        val surname= intent.getStringExtra("surname").toString()
        val number= intent.getStringExtra("number").toString()
        val username= intent.getStringExtra("userName").toString()
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Doctors"
        val buttonEdit = findViewById<Button>(R.id.buttonEdit)
        buttonEdit.setOnClickListener {
            goToDoctorEdit(name,surname,number,username)
        }
        val buttonDelete = findViewById<Button>(R.id.buttonDelete)

        buttonDelete.setOnClickListener {
            val editTextDoctorId = findViewById<EditText>(R.id.editTextDoctorId)

            val doctorEmail : String = editTextDoctorId.text.toString()
            if(doctorEmail.isNotEmpty()){
                deleteRecord(doctorEmail)
                editTextDoctorId.setText("")
            }else{
                Toast.makeText(this,"Enter patient ID",Toast.LENGTH_LONG).show()
            }
            loadingDoctor()
        }

    }

    private fun goToDoctorEdit(name: String, surname: String, number: String, username: String) {
        val editTextDoctorId = findViewById<EditText>(R.id.editTextDoctorId).text.toString()

        intent = Intent(this,EditDoctorActivity::class.java)
        intent.putExtra("username",editTextDoctorId)
        intent.putExtra("name",name)
        intent.putExtra("surname",surname)
        intent.putExtra("number",number)
        intent.putExtra("userName",username)
        startActivity(intent)
    }

    private fun loadingDoctor() {
        val textViewDoctors = findViewById<TextView>(R.id.textViewDoctors)

        database = FirebaseDatabase.getInstance().getReference("Doctor")
        database.get().addOnSuccessListener {
            var sb = StringBuilder()
            for(i in it.children){

                var name = i.child("name").value
                var surname = i.child("surname").value
                var gender = i.child("gender").value
                var age = i.child("age").value
                var phone = i.child("phone").value
                var department = i.child("department").value
                var specialization = i.child("specialization").value
                var password = i.child("password").value
                var role = i.child("role").value
                var id = i.key

                sb.append("Username $id\nName $name\nSurname $surname\nGender $gender\n_____________________________\n")
            }
            textViewDoctors.text = sb
        }.addOnFailureListener {
            Toast.makeText(this,"failed", Toast.LENGTH_LONG).show()
        }
    }
    private fun deleteRecord(patientEmail: String) {

        database = FirebaseDatabase.getInstance().getReference("Doctor").child(patientEmail)
        database.removeValue().addOnSuccessListener {
            Toast.makeText(this,"Doctor removed",Toast.LENGTH_LONG).show()
        }
    }
}