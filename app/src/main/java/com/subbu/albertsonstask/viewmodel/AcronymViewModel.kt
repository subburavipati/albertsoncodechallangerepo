package com.subbu.albertsonstask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subbu.albertsonstask.model.data.WordData
import com.subbu.albertsonstask.model.repository.AcronymRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AcronymViewModel @Inject constructor(
    private val repository: AcronymRepository
) : ViewModel() {

    private val _acronymEvent = MutableLiveData<AcronymEvent>()
    val acronymEvent: LiveData<AcronymEvent>
        get() = _acronymEvent

    fun getLfs(shortForm: String) {
        viewModelScope.launch {
            //_acronymEvent.postValue(AcronymEvent.Loading)
            repository.getLfs(shortForm).catch { e ->
                _acronymEvent.postValue(AcronymEvent.Failure(e.localizedMessage.orEmpty()))
            }
                .collect {
                    _acronymEvent.postValue(AcronymEvent.Success(it.firstOrNull()?.lfs?.map { lf ->
                        WordData(
                            lf.lf,
                            lf.freq,
                            lf.since
                        )
                    } ?: emptyList()))
                }
        }
    }

    sealed class AcronymEvent {
        class Success(val response: List<WordData>) : AcronymEvent()
        class Failure(val errorMessage: String) : AcronymEvent()
        object Loading : AcronymEvent()
    }
}