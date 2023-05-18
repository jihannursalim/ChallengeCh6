package jihan.binar.challengechapter6.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import jihan.binar.challengechapter6.model.Result
import jihan.binar.challengechapter6.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val getfilm = arguments?.getSerializable("BUNDEL") as Result
        Glide.with(view)
            .load("https://image.tmdb.org/t/p/w500${getfilm.posterPath}")
            .into(binding.imgFilmImageDetail)
        binding.tvNamafilmdetail.text = getfilm.title
        binding.tvReleaseFilmDetail.text = "Release : ${getfilm.releaseDate}"
        binding.tvPopularityDetail.text = "Popularity : ${getfilm.popularity}"
        binding.tvSinopsisfilmdetail.text = """Overview:
            ${getfilm.overview}
        """.trimIndent()

        binding.btnCrashlytics.setOnClickListener {
            throw RuntimeException("Test Crash")
        }
    }
}