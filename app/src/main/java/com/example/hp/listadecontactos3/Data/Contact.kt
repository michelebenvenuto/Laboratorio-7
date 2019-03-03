package com.example.hp.listadecontactos3.Data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob
//clase que guarda la informacion de cada contacto
@Entity (tableName = "contacts_table")
class Contact(
    var name:String,
    var phoneNumber: String,
    var eMail:String,
    var priority: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id:Int=0

}