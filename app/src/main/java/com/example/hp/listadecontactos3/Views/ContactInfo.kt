package com.example.hp.listadecontactos3.Views

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.hp.listadecontactos3.Data.Contact
import com.example.hp.listadecontactos3.Data.ContactViewModel
import com.example.hp.listadecontactos3.R
import kotlinx.android.synthetic.main.activity_contact_info.*

class ContactInfo : AppCompatActivity() {
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_info)
        contactViewModel= ViewModelProviders.of(this).get(ContactViewModel::class.java)
        val itemName=intent.getStringExtra("name")
        val itemPhone= intent.getStringExtra("number")
        val itemMail=intent.getStringExtra("mail")
        nameView.text=itemName
        phoneNumberView.text=itemPhone
        emailView.text=itemMail

        phoneNumberView.setOnClickListener {
            val phoneIntent: Intent = Intent(Intent.ACTION_DIAL)
            phoneIntent.setData(Uri.parse("tel:${phoneNumberView.text}"))
            startActivity(phoneIntent)
        }
        //Codigo para enviar la informacion a un app para poder mandar un correo
        emailView.setOnClickListener {
            val to = arrayOf(itemMail)
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
    fun edit(view:View){
        val intent: Intent = Intent(this,MainActivity::class.java)
        intent.putExtra("currentName",nameView.text)
        intent.putExtra("currentTel",phoneNumberView.text)
        intent.putExtra("currentMail",emailView.text)
        intent.putExtra("item",intent.getIntExtra("itemToGet",0))
        startActivity(intent)
    }
}
