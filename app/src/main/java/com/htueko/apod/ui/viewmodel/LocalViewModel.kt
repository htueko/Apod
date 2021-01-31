package com.htueko.apod.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.htueko.apod.data.model.Apod
import kotlinx.coroutines.launch

class LocalViewModel : ViewModel() {

    private val _apodData: MutableLiveData<List<Apod>> = MutableLiveData()
    val apodData: LiveData<List<Apod>> = _apodData

    /**
     * sorted the [data] according to date and update the livedata value
     */
    fun updateData(data: List<Apod>) {
        // create an empty list
        val newList = mutableListOf<Apod>()
        // sorted the data by desc according to date
        val sortByDate = sortByDateDesc(data)
        // added to new list
        newList.addAll(sortByDate)
        // added to livedata (background thread)
        viewModelScope.launch {
            _apodData.postValue(newList)
        }
    }

    fun sortByDateDesc(data: List<Apod>) = data.sortedByDescending { it.date }

    /**
     * user selected item [apod]
     * local data set [data]
     * 1. remove the user selected item from local data set
     * 2. create new list and added user selected item at index 0
     * 3. added other item to the new list with sorted date desc
     */
    fun reorder(apod: Apod, data: List<Apod>) {
        // create new empty mutable list
        val newList = mutableListOf<Apod>()
        // convert to mutable list
        val before = data.toMutableList()
        // remove item from list
        before.remove(apod)
        // added item to new list
        newList.add(apod)
        // sorted the item according to date desc
        val sortedData = sortByDateDesc(before)
        // added all the other item to list
        sortedData.forEach { newList.add(it) }
        // added to livedata (background thread)
        viewModelScope.launch {
            _apodData.postValue(newList)
        }
    }

}
