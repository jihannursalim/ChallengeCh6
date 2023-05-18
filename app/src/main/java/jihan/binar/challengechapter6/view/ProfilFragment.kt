package jihan.binar.challengechapter6.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import jihan.binar.challengechapter6.R
import jihan.binar.challengechapter6.databinding.FragmentProfilBinding


class ProfilFragment : Fragment() {

    lateinit var binding: FragmentProfilBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences("dataUser", Context.MODE_PRIVATE)
        var getUser = sharedPreferences.getString("user", "")
        binding.edtUserNameProfile.setText(getUser)

        var getNama = sharedPreferences.getString("nama", "")
        binding.edtNameProfile.setText(getNama)

        var getTgl = sharedPreferences.getString("tgl", "")
        binding.edtTanggalLahirProfile.setText(getTgl)

        var getAlamat = sharedPreferences.getString("alamat", "")
        binding.edtAlamat.setText(getAlamat)

        binding.btnUpdate.setOnClickListener {
            var getUsername = binding.edtUserNameProfile.text.toString()
            var getNamaLengkap = binding.edtNameProfile.text.toString()
            var getTglLahir = binding.edtTanggalLahirProfile.text.toString()
            var getAlamat = binding.edtAlamat.text.toString()
            var addUser = sharedPreferences.edit()
            addUser.putString("user", getUsername)
            addUser.putString("nama", getNamaLengkap)
            addUser.putString("tgl", getTglLahir)
            addUser.putString("alamat", getAlamat)
            addUser.apply()

            Toast.makeText(context, "Update Berhasil", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_profilFragment_to_homeFragment)
        }

        binding.btnLogout.setOnClickListener {
            firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()
            var addUser = sharedPreferences.edit()
            addUser.remove("nama")
            addUser.remove("tgl")
            addUser.remove("alamat")
            addUser.commit()
            Toast.makeText(context, "Keluar Berhasil", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_profilFragment_to_loginFragment)
        }

    }

    private fun signout() {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()
    }
}