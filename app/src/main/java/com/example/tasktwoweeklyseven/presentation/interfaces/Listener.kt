package com.example.tasktwoweeklyseven.presentation.interfaces

import com.example.tasktwoweeklyseven.data.model.Movie

interface Listener {
    fun onClickItem(movie: MutableList<Movie>, position: Int)
}

