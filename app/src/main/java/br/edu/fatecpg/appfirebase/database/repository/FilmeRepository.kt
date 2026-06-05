package br.edu.fatecpg.appfirebase.database.repository

import androidx.lifecycle.LiveData
import br.edu.fatecpg.appfirebase.database.dao.FilmeDao
import br.edu.fatecpg.appfirebase.model.Filme
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FilmeRepository(private val filmeDao: FilmeDao) {

    private val firestore = FirebaseFirestore.getInstance()
    private val filmesCollection = firestore.collection("filmes")

    fun getAllByUser(uid: String): LiveData<List<Filme>> {
        return filmeDao.getAllByUser(uid)
    }

    suspend fun insert(filme: Filme) {
        val docRef = filmesCollection.document()
        val filmeComId = filme.copy(firestoreId = docRef.id)
        docRef.set(filmeComId).await()
        filmeDao.insert(filmeComId)
    }

    suspend fun delete(filme: Filme) {
        filmeDao.delete(filme)
        if (filme.firestoreId.isNotEmpty()) {
            filmesCollection.document(filme.firestoreId).delete().await()
        }
    }

    suspend fun updateStatus(filme: Filme, novoStatus: String) {
        filmeDao.updateStatus(filme.id, novoStatus)
        if (filme.firestoreId.isNotEmpty()) {
            filmesCollection.document(filme.firestoreId).update("status", novoStatus).await()
        }
    }
}