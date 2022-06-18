package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.util.*

class PatientActivity : AppCompatActivity() {
    private lateinit var toggle : ActionBarDrawerToggle
    //private val sdf : SimpleDateFormat("dd/MM/yyyy HH:mm")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val button_setAppointment = findViewById<Button>(R.id.buttonSetAppointment)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Patient"

        val name= intent.getStringExtra("name")
        val surname= intent.getStringExtra("surname")
        val number= intent.getStringExtra("number")
        val username : String= intent.getStringExtra("userName").toString()

        button_setAppointment.setOnClickListener {
            goToSetAppointment(username)
        }
        toggle  = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_navigation_drawer,R.string.close_navigation_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val nameTextView = findViewById<TextView>(R.id.textViewName)
        val usernameTextView = findViewById<TextView>(R.id.textViewUsername)
        val numberTextView = findViewById<TextView>(R.id.textViewUserNumber)
        val dateTextView = findViewById<TextView>(R.id.textViewTime)




        val date = Date()
        dateTextView.text = (date).toString()
        "$name $surname".also { nameTextView.text = it }
        usernameTextView.text = username
        numberTextView.text = number
        val nav_view = findViewById<NavigationView>(R.id.nav_view)
        nav_view.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.appointment-> goToAppointment(username)
                R.id.logout-> goToLogin()
                R.id.profile-> goToProfile(username)
            }
            true
        }
    }

    private fun goToSetAppointment(username: String) {
        val intent = Intent(this,SetAppointmentActivity::class.java)
        intent.putExtra("username",username)
        startActivity(intent)
    }

    private fun goToAppointment(username: String) {
        val intent = Intent(this,AppointmentActivity::class.java)
        intent.putExtra("username",username)
        startActivity(intent)
    }

    private fun goToLogin() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

    private fun goToProfile(username: String) {
        val intent = Intent(this,ProfileActivity::class.java)
        intent.putExtra("username",username)
        startActivity(intent)
    }
}