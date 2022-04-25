package za.ac.tuthospitalmanagementsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val buttonEdit = findViewById<Button>(R.id.buttonEdit)

        buttonEdit.setOnClickListener {
            editProfile()
        }
    }

    private fun editProfile() {

    }
}