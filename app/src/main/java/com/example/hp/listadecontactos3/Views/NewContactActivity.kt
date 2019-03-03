package com.example.hp.listadecontactos3.Views

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.hp.listadecontactos3.R
import kotlinx.android.synthetic.main.activity_new_contact.*
//actividad que agrega nuevos contactos
class NewContactActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "com.example.hp.listadecontactos3.Views.EXTRA_ID"
        const val EXTRA_NAME = "com.example.hp.listadecontactos3.Views.EXTRA_NAME"
        const val EXTRA_PHONE = "com.example.hp.listadecontactos3.Views.EXTRA_PHONE"
        const val EXTRA_MAIL = "com.example.hp.listadecontactos3.Views.EXTRA_MAIL"
        const val EXTRA_PRIORITY = "com.example.hp.listadecontactos3.Views.EXTRA_PRIORITY"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        if (intent.hasExtra(EXTRA_ID)){
            title="Edit contact"
            nameInput.setText(intent.getStringExtra(EXTRA_NAME))
            phoneInput.setText(intent.getStringExtra(EXTRA_PHONE))
            emailInput.setText(intent.getStringExtra(EXTRA_MAIL))
            priorityInput.setText(intent.getIntExtra(EXTRA_PRIORITY,1))
        }
        else{
            title="New contact"
        }
    }
    //boton para guardar los cambios o el contacto nuevo
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_contact_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_contact -> {
                saveContact()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

//guarda la informacion y regresa a la actividad principal
    private fun saveContact() {
        if (nameInput.text.toString().trim().isBlank() || phoneInput.text.toString().trim().isBlank()||emailInput.text.toString().trim().isBlank()) {
            Toast.makeText(this, "No se puede agregar contacto sin informacion", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_NAME, nameInput.text.toString())
            putExtra(EXTRA_PHONE, phoneInput.text.toString())
            putExtra(EXTRA_MAIL, emailInput.text.toString())
            if (intent.getIntExtra(EXTRA_ID, -1) != -1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }
}
