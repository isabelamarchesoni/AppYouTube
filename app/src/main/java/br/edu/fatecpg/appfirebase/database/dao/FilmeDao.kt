package br.edu.fatecpg.appfirebase.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.edu.fatecpg.appfirebase.model.Filme

@Dao
interface FilmeDao {

    @Insert
    suspend fun insert(filme: Filme)

    @Delete
    suspend fun delete(filme: Filme)

    @Query("SELECT * FROM filmes WHERE uid = :uid ORDER BY titulo ASC")
    fun getAllByUser(uid: String): LiveData<List<Filme>>

    @Query("UPDATE filmes SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Int, status: String)
}