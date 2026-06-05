package br.edu.fatecpg.appfirebase.view

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import br.edu.fatecpg.appfirebase.databinding.DialogAdicionarFilmeBinding

class AdicionarFilmeDialog(
    private val context: Context,
    private val onSalvar: (titulo: String, link: String) -> Unit
) {
    fun show() {
        val binding = DialogAdicionarFilmeBinding.inflate(LayoutInflater.from(context))

        AlertDialog.Builder(context)
            .setTitle("Adicionar filme")
            .setView(binding.root)
            .setPositiveButton("Salvar") { _, _ ->
                val titulo = binding.etTitulo.text.toString().trim()
                val link = binding.etLink.text.toString().trim()

                when {
                    titulo.isEmpty() -> Toast.makeText(context, "Informe o título", Toast.LENGTH_SHORT).show()
                    link.isEmpty() -> Toast.makeText(context, "Informe o link do YouTube", Toast.LENGTH_SHORT).show()
                    else -> onSalvar(titulo, link)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
