package jihan.binar.challengechapter6.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import jihan.binar.challengechapter6.R
import jihan.binar.challengechapter6.adapter.MovieAdapter
import jihan.binar.challengechapter6.databinding.FragmentHomeBinding
import jihan.binar.challengechapter6.viewmodel.MovieViewModel


class HomeFragment : Fragment() {


    lateinit var binding: FragmentHomeBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var sharedPreferences: SharedPreferences


    //mendefinisikan override function onCreate untuk melakukan beberapa konfigurasi sebelum fragment ditampilkan, seperti memanggil setHasOptionsMenu(true) untuk menampilkan menu pada toolbar.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    //mendefinisikan override function onCreateView untuk mengembalikan tampilan layout fragment.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    //mendefinisikan override function onViewCreated yang akan dipanggil setelah tampilan fragment dibuat, untuk melakukan inisialisasi UI dan logic pada tampilan.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //menetapkan toolbar yang ada pada layout sebagai ActionBar.
//        (activity as AppCompatActivity).setSupportActionBar(binding.tbHome)

        firebaseAuth = FirebaseAuth.getInstance()
        //mengecek apakah user sudah login atau belum, jika belum, maka akan diarahkan ke halaman login.
        if(firebaseAuth.currentUser == null){
            findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        //menginisialisasi instance dari SharedPreferences.
        //Dalam kode ini, requireContext() digunakan untuk mendapatkan konteks dari fragment saat ini dan kemudian digunakan untuk memanggil metode
        // getSharedPreferences() dengan parameter "dataUser" dan MODE_PRIVATE. Sehingga, objek SharedPreferences dengan nama "dataUser"
        // dan mode akses MODE_PRIVATE akan dibuat dan disimpan dalam variabel sharedPreferences.
        // dan mode akses MODE_PRIVATE akan dibuat dan disimpan dalam variabel sharedPreferences.
        sharedPreferences = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)

        // mengambil data user yang sudah login dari SharedPreferences.
        var getUser = sharedPreferences.getString("user", "")
        binding.textJudul.text = "Welcome, $getUser"

        //menambahkan event click pada tombol profil untuk menavigasikan ke halaman profil.
        binding.ivIcprofile.setOnClickListener {
            var addUser = sharedPreferences.edit()
            addUser.putString("user", getUser)
            addUser.apply()
            findNavController().navigate(R.id.action_homeFragment_to_profilFragment)
        }

        //menginisialisasi instance dari MovieViewModel.
        val viewModelFilm = ViewModelProvider(this).get(MovieViewModel::class.java)

        //memanggil method callMovie() yang akan memuat daftar film dari server menggunakan Retrofit.
        viewModelFilm.callMovie()

        //mengamati perubahan pada liveDataMovie pada MovieViewModel dan menampilkan daftar film pada RecyclerView jika ada perubahan pada data.
        viewModelFilm.liveDataMovie.observe(viewLifecycleOwner, Observer {

            //Jika it tidak null, maka RecyclerView rvListFilm akan diatur layout managernya menjadi LinearLayoutManager dengan orientasi vertical
            // dan diatur adapternya dengan data yang berasal dari it yang merupakan hasil dari API call. Jadi, ketika liveDataMovie pada ViewModel
            // berubah nilainya, maka RecyclerView akan secara otomatis diperbarui dengan data terbaru.
            if (it != null) {
                binding.rvListFilm.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                binding.rvListFilm.adapter = MovieAdapter(it)
            }
        })

    }

    override fun onStart() {
        super.onStart()

    }
}