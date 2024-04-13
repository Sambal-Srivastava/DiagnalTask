package com.diagnal.diagnaltask.viewmodel

import androidx.lifecycle.*
import com.diagnal.diagnaltask.data.model.MyResponse
import com.diagnal.diagnaltask.data.network.Resource
import com.diagnal.diagnaltask.data.repository.MainRepository
import com.diagnal.diagnaltask.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommonViewModel @Inject constructor(private val repository: MainRepository) : ViewModel(),
    DefaultLifecycleObserver {


    private val _loginMyResponse = SingleLiveEvent<Resource<MyResponse>>()
    val loginMyResponse: LiveData<Resource<MyResponse>> get() = _loginMyResponse
    var backPressedTime: Long = 0L

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        callApiLogin()
    }

    fun callApiLogin(pageN: String = "1") {
        _loginMyResponse.value = Resource.loading()
        viewModelScope.launch {
            _loginMyResponse.value = repository.callApiLogin(pageN)
        }
    }
}