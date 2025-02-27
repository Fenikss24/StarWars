package com.example.moviegalaxy.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviegalaxy.R
import com.example.moviegalaxy.databinding.MovieItemBinding
import com.example.moviegalaxy.domain.entities.Movie
import com.example.moviegalaxy.utils.loadImage

class MovieAdapter(
    private val dataCinema: List<Movie>,
    private val onClicked: (Movie) -> Unit = {}
) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {

    /// ВЫЗОВ НАПОЛНЕНИЯ КАРТОЧКИ
    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bind(dataCinema[position])
    }

    // ПРИВЯЗКА КАРТОЧКИ К АДАПТЕРУ
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding, onClicked)
    }

    // ЗА ПЕРЕДАЧУ СПИСКА ФИЛЬМОВ (РАЗМЕР СПИСКА КАРТОЧЕК)
    override fun getItemCount(): Int {
        return dataCinema.size
    }

    inner class MovieHolder(
        private val binding: MovieItemBinding,
        private val onClicked: (Movie) -> Unit = {}
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Movie) {
            binding.title.text = model.title
            binding.raiting.text = String.format("%.1f", model.vote_average)
            loadImage(
                itemView.context,
                URL_ADDRESS + model.poster_path,
                R.color.primaryColor,
                binding.poster
            )

            binding.poster.setOnClickListener() {
                onClicked(model)
            }
        }
    }

    companion object {
        private const val URL_ADDRESS = "https://image.tmdb.org/t/p/original"
    }
}