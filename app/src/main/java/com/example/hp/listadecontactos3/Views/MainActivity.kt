package com.example.hp.listadecontactos3.Views

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hp.listadecontactos3.Adapters.ContactAdapter
import com.example.hp.listadecontactos3.Data.Contact
import com.example.hp.listadecontactos3.Data.ContactViewModel
import com.example.hp.listadecontactos3.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_CONTACT_REQUEST = 1
        const val EDIT_NOTE_REQUEST=2
    }

    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//Codigo para hacer funcionar el boton para agregar nuevos contactos
        buttonAddContact.setOnClickListener{
            startActivityForResult(
                Intent(this,NewContactActivity::class.java ),
                ADD_CONTACT_REQUEST
            )
        }
        recycler_view.layoutManager=LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        var adapter= ContactAdapter()

        recycler_view.adapter=adapter

        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel::class.java)

        contactViewModel.getAllContacts().observe(this, Observer<List<Contact>>{
            adapter.submitList(it)
        } )

        ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                contactViewModel.delete(adapter.getContactAt(viewHolder.adapterPosition)!!)
            }
        }
        ).attachToRecyclerView(recycler_view)
//Codigo para envira la informacion a la actividad con la informacion del cotacto
        adapter.setOnItemClickListener(object : ContactAdapter.OnItemClickListener{
            override fun onItemClick(contact: Contact) {
                var intent = Intent(baseContext, ContactInfo::class.java)
                intent.putExtra("name",contact.name)
                intent.putExtra("phone", contact.phoneNumber)
                intent.putExtra("mail", contact.eMail)

                startActivity(intent)
            }
        })
    }
//muestra el menu con la opcion de eliminar todosl los contactos
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_contacts -> {
                contactViewModel.deleteAllContacts()
                Toast.makeText(this, "Contactos Borrados", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
//Logica para descubrir de que actividad es que proviene la informacion
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_CONTACT_REQUEST && resultCode==Activity.RESULT_OK){
            val newContact = Contact(
                data!!.getStringExtra(NewContactActivity.EXTRA_NAME),
                data.getStringExtra(NewContactActivity.EXTRA_PHONE),
                data.getStringExtra(NewContactActivity.EXTRA_MAIL),
                data.getIntExtra(NewContactActivity.EXTRA_PRIORITY,1)
            )
            contactViewModel.insert(newContact)
            Toast.makeText(this, "Se agrego el contacto", Toast.LENGTH_SHORT).show()
        }
        else if (requestCode== EDIT_NOTE_REQUEST && resultCode==Activity.RESULT_OK){
            val id = data?.getIntExtra(NewContactActivity.EXTRA_ID,-1)

            if (id== -1){
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show()
            }
            val updateContact=Contact(
                data!!.getStringExtra(NewContactActivity.EXTRA_NAME),
                data.getStringExtra(NewContactActivity.EXTRA_PHONE),
                data.getStringExtra(NewContactActivity.EXTRA_MAIL),
                data.getIntExtra(NewContactActivity.EXTRA_PRIORITY,1)
            )
            updateContact.id=data.getIntExtra(NewContactActivity.EXTRA_ID,-1)
            contactViewModel.update(updateContact)
        }
        else{
            Toast.makeText(this, "Error al guardar!", Toast.LENGTH_SHORT).show()
        }
    }
}
