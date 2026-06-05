package br.edu.fatecpg.appfirebase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.edu.fatecpg.appfirebase.database.db.AppDatabase
import br.edu.fatecpg.appfirebase.database.repository.FilmeRepository
import br.edu.fatecpg.appfirebase.model.Filme
import kotlinx.coroutines.launch

class FilmeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FilmeRepository

    init {
        val dao = AppDatabase.getDatabase(application).filmeDao()
        repository = FilmeRepository(dao)
    }

    fun getAllByUser(uid: String): LiveData<List<Filme>> {
        return repository.getAllByUser(uid)
    }

    fun insert(filme: Filme) = viewModelScope.launch {
        repository.insert(filme)
    }

    fun delete(filme: Filme) = viewModelScope.launch {
        repository.delete(filme)
    }

    fun updateStatus(filme: Filme, novoStatus: String) = viewModelScope.launch {
        repository.updateStatus(filme, novoStatus)
    }
}