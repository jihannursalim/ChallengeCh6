package jihan.binar.challengechapter6.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import jihan.binar.challengechapter6.adapter.FavoriteAdapter
import jihan.binar.challengechapter6.databinding.FragmentFavoriteBinding
import jihan.binar.challengechapter6.network.AppDatabase
import jihan.binar.challengechapter6.network.FavoriteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favoriteDao: FavoriteDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi favoriteDao dari AppDatabase
        favoriteDao = AppDatabase.getInstance(requireContext()).favoriteDao()

        favoriteAdapter = FavoriteAdapter()
        binding.recyclerViewFavorites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteAdapter
        }

        loadFavorites()
    }

    private fun loadFavorites() {
        lifecycleScope.launch(Dispatchers.IO) {
            val favorites = favoriteDao.getAllFavorites()

            withContext(Dispatchers.Main) {
                if (favorites.isNotEmpty()) {
                    binding.textViewEmpty.visibility = View.GONE
                    binding.recyclerViewFavorites.visibility = View.VISIBLE
                    favoriteAdapter.submitList(favorites)
                } else {
                    binding.textViewEmpty.visibility = View.VISIBLE
                    binding.recyclerViewFavorites.visibility = View.GONE
                }
            }
        }
    }
}
