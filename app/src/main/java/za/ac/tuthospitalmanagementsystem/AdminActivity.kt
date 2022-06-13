package za.ac.tuthospitalmanagementsystem

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.FileOutputStream
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

class AdminActivity : AppCompatActivity() {
    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var database : DatabaseReference
    private var STORAGE_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val nameTextView = findViewById<TextView>(R.id.textViewName)
        val usernameTextView = findViewById<TextView>(R.id.textViewUsername)
        val numberTextView = findViewById<TextView>(R.id.textViewUserNumber)
        val dateTextView = findViewById<TextView>(R.id.textViewTime)

        val name= intent.getStringExtra("name")
        val surname= intent.getStringExtra("surname")
        val number= intent.getStringExtra("number")
        val username= intent.getStringExtra("userName")
        val date = Date()
        dateTextView.text = (date).toString()
        nameTextView.text = "$name $surname"
        usernameTextView.text = username
        numberTextView.text = number
        var drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Admin"
        toggle  = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_navigation_drawer,R.string.close_navigation_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        var nav_view = findViewById<NavigationView>(R.id.nav_view)
        nav_view.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.doctorRegister->{
                    goToDoctorRegister()
                }
                R.id.patients->{
                    goToPatients(name,surname,number,username)
                }
                R.id.doctors->{
                    goToDoctors()
                }
                R.id.appointment->{
                    goToAppointment(name,surname,number,username)
                }
                R.id.logout->{
                    goToLogin()
                }
                R.id.report-> {
                    saveReport()
                }
            }
            true
        }
    }

    private fun saveReport() {

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission,STORAGE_CODE)
            }else{
                savePDF()
            }
        }else{
            savePDF()
        }


    }

    private fun savePDF() {
        val name= intent.getStringExtra("name")
        val surname= intent.getStringExtra("surname")
        val mDoc  = Document()
        val wordDoc = XWPFDocument()
        val excelDoc = HSSFWorkbook()
        val mFilename = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
        val mFilePath = Environment.getExternalStorageDirectory().toString() + "/" + mFilename + ".pdf"
        val wordFilePath = Environment.getExternalStorageDirectory().toString() + "/" + mFilename + ".docx"
        val excelFilePath = Environment.getExternalStorageDirectory().toString() + "/" + mFilename + ".xls"
        val txtFilePath = Environment.getExternalStorageDirectory().toString() + "/" + mFilename + ".txt"
        var data = ""
        try{
            //word
            val para = wordDoc.createParagraph()
            val paraRun = para.createRun()
            //pdf
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
            mDoc.open()
            //excel
            val excelSheet = excelDoc.createSheet("Hospital Report")
            val excelRow = excelSheet.createRow(0)
            val excelCell = excelRow.createCell(0)

            excelCell.setCellValue("ertyu")
            excelDoc.write(FileOutputStream(excelFilePath))
            excelDoc.close()

            var sb = StringBuilder()
            database = FirebaseDatabase.getInstance().getReference("Appointment")
            database.get().addOnSuccessListener {

                for(i in it.children){


                    var availability = i.child("availability").value
                    var date = i.child("date").value
                    var disease = i.child("disease").value
                    var doctor : String = i.child("doctor").value.toString()
                    if(doctor.contentEquals("null")){
                        doctor = "Not yet assigned"
                    }
                    var patient = i.child("patient").value
                    var id = i.key

                    sb.append("Appointment number: $id\nPatient: $patient\nDoctor: $doctor\nAppointment Time: $availability\nDate: $date\nDisease: $disease\n_____________________________\n")

                }
                data = sb.toString()

                //create pdf
                mDoc.addAuthor(name + surname)
                mDoc.add(Paragraph("$data"))
                mDoc.close()

                //create word
                paraRun.setText(data)
                paraRun.fontSize = 18
                wordDoc.write(FileOutputStream(wordFilePath))
                wordDoc.close()

                //create txt file
                val writeIntoFile = FileOutputStream(txtFilePath)
                writeIntoFile.write(data.toByteArray())

                Toast.makeText(this,"$mFilename.pdf is successfully save",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this,"failed", Toast.LENGTH_LONG).show()
            }


        }catch (ex : Exception){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            STORAGE_CODE -> {
                if(grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    savePDF()
                }else{
                    Toast.makeText(this,"permission denied!",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goToDoctors() {
        val intent = Intent(this,AdminDoctorActivity::class.java)
        startActivity(intent)
    }

    private fun goToPatients(name: String?, surname: String?, number: String?, username: String?) {
        val intent = Intent(this,AdminPatientsActivity::class.java)
        intent.putExtra("name",name)
        intent.putExtra("surname",surname)
        intent.putExtra("number",number)
        intent.putExtra("userName",username)
        startActivity(intent)
    }

    private fun goToDoctorRegister() {
        val intent = Intent(this,DoctorRegisterActivity::class.java)
        startActivity(intent)
    }

    private fun goToAppointment(
        name: String?,
        surname: String?,
        number: String?,
        username: String?
    ) {
        val intent = Intent(this,AdminAppointmentActivity::class.java)
        intent.putExtra("name",name)
        intent.putExtra("surname",surname)
        intent.putExtra("number",number)
        intent.putExtra("userName",username)
        startActivity(intent)
    }

    private fun goToLogin() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
}