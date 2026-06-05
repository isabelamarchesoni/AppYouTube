package br.edu.fatecpg.appfirebase.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.edu.fatecpg.appfirebase.databinding.ItemFilmeBinding
import br.edu.fatecpg.appfirebase.model.Filme
import br.edu.fatecpg.appfirebase.R

class FilmeAdapter(
    private val onStatusChange: (Filme, String) -> Unit,
    private val onPlayClick: (Filme) -> Unit,
    private val onDeleteClick: (Filme) -> Unit
) : ListAdapter<Filme, FilmeAdapter.FilmeViewHolder>(DiffCallback()) {

    inner class FilmeViewHolder(private val binding: ItemFilmeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(filme: Filme) {
            binding.tvTitulo.text = filme.titulo
            binding.tvLink.text = filme.linkYoutube

            val jaAssistiu = filme.status == "JA_ASSISTI"
            binding.checkAssistido.setOnCheckedChangeListener(null)
            binding.checkAssistido.isChecked = jaAssistiu
            atualizarBadge(jaAssistiu)

            binding.checkAssistido.setOnCheckedChangeListener { _, isChecked ->
                val novoStatus = if (isChecked) "JA_ASSISTI" else "PRECISO_ASSISTIR"
                atualizarBadge(isChecked)
                onStatusChange(filme, novoStatus)
            }

            binding.btnPlay.setOnClickListener { onPlayClick(filme) }
            binding.btnDeletar.setOnClickListener { onDeleteClick(filme) }
        }

        private fun atualizarBadge(assistido: Boolean) {
            if (assistido) {
                binding.tvStatus.text = "✓ Já assisti"
                binding.tvStatus.setBackgroundResource(R.drawable.bg_status_assistido)
            } else {
                binding.tvStatus.text = "● Preciso assistir"
                binding.tvStatus.setBackgroundResource(R.drawable.bg_status_pendentes)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmeViewHolder {
        val binding = ItemFilmeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Filme>() {
        override fun areItemsTheSame(oldItem: Filme, newItem: Filme) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Filme, newItem: Filme) = oldItem == newItem
    }
}