package com.example.collectit.screens.statistics

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.managers.CommonStatManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatViewModel @Inject constructor(private val manager: CommonStatManager): ViewModel() {
    private val _stat = MutableLiveData<Map<String, Int>>()
    val statistics: LiveData<Map<String, Int>> = _stat

    fun getStatistics() {
        viewModelScope.launch {
            Log.i("StatisticsViewModel", "Делаю запрос менеджеру статистики")
            val stat = manager.getStatistics()
            Log.i("StatisticsViewModel", "Статистика получена")
            _stat.value = stat
        }
    }
}