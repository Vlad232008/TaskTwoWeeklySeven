package com.example.tasktwoweeklyseven.presentation.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasktwoweeklyseven.data.model.Movie
import com.example.tasktwoweeklyseven.data.retrofit.ApiService
import com.example.tasktwoweeklyseven.data.retrofit.Repository
import com.example.tasktwoweeklyseven.databinding.ActivityMainBinding
import com.example.tasktwoweeklyseven.presentation.adapter.MovieAdapter
import com.example.tasktwoweeklyseven.presentation.interfaces.Listener
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), Listener {

    private lateinit var apiService: ApiService
    private lateinit var binding: ActivityMainBinding
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter: MovieAdapter
    private lateinit var defPref: SharedPreferences
    private lateinit var json:List<Movie>
    companion object{
        var movieInfo = listOf<Movie>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        defPref = PreferenceManager.getDefaultSharedPreferences(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiService = Repository.retrofitService
        binding.recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager
        if (openJson()) initRcView()
        else getAllMovieList()
    }

    private fun getAllMovieList() {
        apiService.getMovieList().enqueue(object : Callback<MutableList<Movie>> {
            override fun onFailure(call: Call<MutableList<Movie>>, t: Throwable) {
            }
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<MutableList<Movie>>,
                response: Response<MutableList<Movie>>
            ) {
                adapter = MovieAdapter(response.body() as MutableList<Movie>, this@MainActivity)
                adapter.notifyDataSetChanged()
                binding.recyclerView.adapter = adapter
                movieInfo = response.body()!!
                saveJson()
            }
        })
    }

    private fun saveJson(){
        val gson = Gson()
        val editor = defPref.edit()
        editor.putString("Log", gson.toJson(movieInfo)).apply()
    }

    private fun openJson() :Boolean{
        val dataFromSharedPrefs = defPref.getString("Log", null)
        val gson = Gson()
        if (dataFromSharedPrefs != null) {
            json = gson.fromJson(
                dataFromSharedPrefs,
                Array<Movie>::class.java
            ).asList()
            return true
        }
        return false
    }

    private fun initRcView(){
        adapter = MovieAdapter(json as MutableList<Movie>, this@MainActivity)
        binding.recyclerView.adapter = adapter
    }

    override fun onClickItem(movie: MutableList<Movie>, position: Int) {
        val intentMovie = Intent(this, SecondActivity::class.java)
        intentMovie.putExtra("name",position)
        startActivity(intentMovie)
    }
}





