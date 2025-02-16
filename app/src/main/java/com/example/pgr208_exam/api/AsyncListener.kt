package com.example.pgr208_exam.api

import com.example.pgr208_exam.db.AbstractDao

interface AsyncListener<T> {

    fun onFeaturesSuccess(collection: ArrayList<T>)
    fun onFeaturesError()
    fun showProgress(show: Boolean)

}