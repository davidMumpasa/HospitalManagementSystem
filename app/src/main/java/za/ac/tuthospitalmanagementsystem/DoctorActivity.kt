package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.util.*

class DoctorActivity : AppCompatActivity() {
    lateinit var toggle : ActionBarDrawerToggle
    //private val sdf : SimpleDateFormat('dd/MM/yyyy HH:mm')
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor)

        var drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)

        val nameTextView = findViewById<TextView>(R.id.textViewName)
        val usernameTextView = findViewById<TextView>(R.id.textViewUsername)
        val numberTextView = findViewById<TextView>(R.id.textViewUserNumber)
        val dateTextView = findViewById<TextView>(R.id.textViewTime)

        val date = Date()
        dateTextView.text = (date).toString()

        val name: String = intent.getStringExtra("name").toString()
        val surname: String= intent.getStringExtra("surname").toString()
        val number: String= intent.getStringExtra("number").toString()
        val username: String= intent.getStringExtra("username").toString()

        nameTextView.text = "$name $surname"
        usernameTextView.text = username
        numberTextView.text = number

        toggle  = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_navigation_drawer,R.string.close_navigation_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        var nav_view = findViewById<NavigationView>(R.id.nav_view)
        nav_view.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.appointment->{
                    goToAppointment(username,name,surname,number)
                }
                R.id.patientRecord->{
                    goToPatientRecord()
                }
                R.id.patientViewRecord->{
                    goToView()
                }
                R.id.logout->{
                    goToLogin()
                }
            }
            true
        }
    }

    private fun goToView() {
        val intent = Intent(this,RecordActivity::class.java)
        startActivity(intent)
    }

    private fun goToPatientRecord() {
        val intent = Intent(this,PatientRecordActivity::class.java)
        startActivity(intent)
    }
    private fun goToAppointment(username: String, name: String, surname: String, number: String) {
        val intent = Intent(this,DoctorAppointmentsActivity::class.java)
        intent.putExtra("username",username)
        intent.putExtra("name",name)
        intent.putExtra("surname",surname)
        intent.putExtra("number",number)
        startActivity(intent)
    }

    private fun goToLogin() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
}