package com.example.hp.listadecontactos3.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.hp.listadecontactos3.Data.Contact
import com.example.hp.listadecontactos3.Data.ContactViewModel
import com.example.hp.listadecontactos3.R
import kotlinx.android.synthetic.main.activity_new_contact.*

class NewContactActivity : AppCompatActivity() {

    private lateinit var contactViewModel:ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)
        contactViewModel=ViewModelProviders.of(this).get(ContactViewModel::class.java)

    }
    fun addContact(view: View){
        if(nameInput.text.toString() !="" && phoneInput.text.toString()!=""&& emailInput.text.toString()!=""&&priorityInput.text.toString().toIntOrNull()!=null) {
            contactViewModel.insert(Contact(nameInput.text.toString(), phoneInput.text.toString(), emailInput.text.toString(), priorityInput.text.toString().toInt()))
            Toast.makeText(applicationContext,"Se ha agregado el contacto de manera exitosa", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(applicationContext,"No se ha podido agregar el contacto",Toast.LENGTH_SHORT).show()
        }
        nameInput.setText("")
        phoneInput.setText("")
        emailInput.setText("")

    }

    fun retrun(view: View){
        val intent: Intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }

}
