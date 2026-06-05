package br.edu.fatecpg.appfirebase.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filmes")
data class Filme(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val firestoreId: String = "",
    val uid: String = "",
    val titulo: String = "",
    val linkYoutube: String = "",
    var status: String = "PRECISO_ASSISTIR"
)