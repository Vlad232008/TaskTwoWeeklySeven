package com.example.tasktwoweeklyseven.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tasktwoweeklyseven.databinding.ActivitySecondBinding
import com.example.tasktwoweeklyseven.presentation.activity.MainActivity.Companion.movieInfo
import com.squareup.picasso.Picasso

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        refreshActivity()
    }

    private fun refreshActivity() = with(binding) {

        val count = intent.getIntExtra("name", 0)
        val baseURl = movieInfo[count].imageurl

        nameHero.text = movieInfo[count].name
        realName.text = movieInfo[count].realname.toString()
        team.text = movieInfo[count].team.toString()
        year.text = movieInfo[count].firstappearance.toString()
        producer.text = movieInfo[count].createdby.toString()
        publisher.text = movieInfo[count].publisher.toString()
        bio.text = movieInfo[count].bio.toString()

        Picasso.get()
            .load(baseURl)
            .into(binding.imageView)
    }
}
