package com.example.moviegalaxy.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviegalaxy.databinding.HomeFragmentBinding
import com.example.moviegalaxy.domain.entities.Movie
import com.example.moviegalaxy.utils.subscribe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe(viewModel.listPopularMoviesLiveData) {
            setAdapter(it)
        }
        subscribe(viewModel.isLoadingLiveData) {
            if (it) showProgressBar()
            else hideProgressBar()
        }
        binding.searchView.addTextChangedListener {
            viewModel.filter(it.toString())
        }
    }


    private fun setAdapter(listMovie: List<Movie>) {
        val adapter = MovieAdapter(listMovie) {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it.id)
            findNavController().navigate(action)
        }

        binding.recyclerView.layoutManager =
            GridLayoutManager(
                requireContext(), 2,
                LinearLayoutManager.VERTICAL,
                false
            )
        binding.recyclerView.adapter = adapter
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}