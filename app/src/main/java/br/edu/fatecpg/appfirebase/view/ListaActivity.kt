package br.edu.fatecpg.appfirebase.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.fatecpg.appfirebase.model.Filme
import br.edu.fatecpg.appfirebase.view.adapter.FilmeAdapter
import br.edu.fatecpg.appfirebase.viewmodel.FilmeViewModel
import com.google.firebase.auth.FirebaseAuth
import br.edu.fatecpg.appfirebase.databinding.ActivityListaBinding

class ListaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaBinding
    private lateinit var adapter: FilmeAdapter
    private val viewModel: FilmeViewModel by viewModels()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observarFilmes()

        binding.fabAdicionar.setOnClickListener {
            AdicionarFilmeDialog(this) { titulo, link ->
                val uid = auth.currentUser?.uid ?: return@AdicionarFilmeDialog
                val filme = Filme(
                    uid = uid,
                    titulo = titulo,
                    linkYoutube = link
                )
                viewModel.insert(filme)
            }.show()
        }

        binding.btnSair.setOnClickListener {
            auth.signOut()
            finish()
        }
    }

    private fun setupRecyclerView() {
        adapter = FilmeAdapter(
            onStatusChange = { filme, novoStatus ->
                viewModel.updateStatus(filme, novoStatus)
            },
            onPlayClick = { filme ->
                val youtubeId = extrairYoutubeId(filme.linkYoutube)
                val intent = Intent(this, TrailerActivity::class.java).apply {
                    putExtra("youtube_id", youtubeId)
                    putExtra("titulo", filme.titulo)
                }
                startActivity(intent)
            },
            onDeleteClick = { filme ->
                viewModel.delete(filme)
            }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun observarFilmes() {
        val uid = auth.currentUser?.uid ?: return
        viewModel.getAllByUser(uid).observe(this) { filmes ->
            if (filmes.isNullOrEmpty()) {
                binding.tvVazia.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.tvVazia.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                adapter.submitList(filmes)
            }
        }
    }

    private fun extrairYoutubeId(url: String): String {
        val patterns = listOf(
            Regex("v=([a-zA-Z0-9_-]{11})"),
            Regex("youtu\\.be/([a-zA-Z0-9_-]{11})"),
            Regex("embed/([a-zA-Z0-9_-]{11})")
        )
        for (pattern in patterns) {
            val match = pattern.find(url)
            if (match != null) return match.groupValues[1]
        }
        return url
    }
}