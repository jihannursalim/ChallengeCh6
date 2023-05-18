package jihan.binar.challengechapter6.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jihan.binar.challengechapter6.model.Result
import jihan.binar.challengechapter6.model.ResponseMovie
import jihan.binar.challengechapter6.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel : ViewModel() {

    //Membuat sebuah variabel liveDataMovie dengan tipe MutableLiveData<List<PopularMovieItem>?> yang nantinya akan berisi data film populer.
    var liveDataMovie: MutableLiveData<List<Result>?> = MutableLiveData()

    // Membuat fungsi callMovie yang akan memanggil endpoint untuk mendapatkan data film populer dari server.
    fun callMovie() {
        //Menggunakan ApiClient untuk melakukan koneksi ke server dan memanggil method getMoviePopular().
        //Menggunakan enqueue() untuk menjalankan permintaan secara asynchronous dan memberikan Callback untuk menangani hasil respon.
        ApiClient.instance.getMoviePopular().enqueue(object : Callback<ResponseMovie> {
            //onResponse() akan dipanggil ketika respon dari server diterima dengan berhasil, dan akan memperbarui nilai liveDataMovie dengan hasil response dari server.
            override fun onResponse(
                call: Call<ResponseMovie>,
                response: Response<ResponseMovie>
            ) {
                if (response.isSuccessful) {
                    liveDataMovie.postValue(response.body()?.results)
                } else {
                    liveDataMovie.postValue(null)
                }
            }

            //onFailure() akan dipanggil ketika permintaan gagal karena alasan seperti jaringan atau kesalahan server, dan akan memperbarui nilai liveDataMovie menjadi null
            override fun onFailure(call: Call<ResponseMovie>, t: Throwable) {
                liveDataMovie.postValue(null)
            }
        })
    }
}
