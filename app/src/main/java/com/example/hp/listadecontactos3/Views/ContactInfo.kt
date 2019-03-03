package com.example.hp.listadecontactos3.Views

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.hp.listadecontactos3.R
import kotlinx.android.synthetic.main.activity_contact_info.*

class ContactInfo : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_info)
        val name=intent.getStringExtra("name")
        val phone=intent.getStringExtra("phone")
        val mail=intent.getStringExtra("mail")
        nameView.setText(name)
        phoneNumberView.setText(phone)
        emailView.setText(mail)

        phoneNumberView.setOnClickListener {
            val phoneIntent: Intent = Intent(Intent.ACTION_DIAL)
            phoneIntent.setData(Uri.parse("tel:${phoneNumberView.text}"))
            startActivity(phoneIntent)
        }
        //Codigo para enviar la informacion a un app para poder mandar un correo
        emailView.setOnClickListener {
            val to = arrayOf(mail)
            val mailIntent: Intent= Intent(Intent.ACTION_SEND)
            mailIntent.setData(Uri.parse("mailto:"))
            mailIntent.setType("text/plain")
            mailIntent.putExtra(Intent.EXTRA_EMAIL, to )
            mailIntent.putExtra(Intent.EXTRA_CC,"De")
            mailIntent.putExtra(Intent.EXTRA_TEXT,"Hola mi nombre es {su nombre aqui} y mi telefono es {su telefono aqui}")
            startActivity(mailIntent)

            try {
                startActivity(Intent.createChooser(mailIntent,"Send mail..."))
                finish()
            }catch (ex: android.content.ActivityNotFoundException){
                Toast.makeText(this,"there is no email client installed", Toast.LENGTH_SHORT).show()
            }
        }


    }
    fun retrun(view: View){
        val intent: Intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}
