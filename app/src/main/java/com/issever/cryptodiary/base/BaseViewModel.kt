package com.issever.cryptodiary.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.issever.cryptodiary.data.model.SnackbarMessage
import com.issever.cryptodiary.util.Errors.COMMON_ERROR
import com.issever.cryptodiary.util.Resource
import com.issever.cryptodiary.util.SnackbarType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _snackbarText = MutableLiveData<SnackbarMessage>()
    val snackbarText: LiveData<SnackbarMessage>
        get() = _snackbarText


    fun showProgress() {
        _isLoading.postValue(true)
    }

    fun hideProgress() {
        _isLoading.postValue(false)
    }

    fun showSnackbar(snackbarMessage: SnackbarMessage) {
        _snackbarText.postValue(snackbarMessage)
    }

    fun <T> collectData(
        operation: suspend () -> Flow<Resource<T>>,
        successAction: ((T) -> Unit)? = null,
        errorAction: ((String) -> Unit)? = null,
        loadingAction: (() -> Unit)? = null,
        snackbarType: SnackbarType = SnackbarType.ERROR,
        snackBarAction: (() -> Unit)? = null,
        actionText: String? = null

    ) {
        viewModelScope.launch {
            operation().collect {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        hideProgress()
                        it.data?.let { it1 -> successAction?.invoke(it1) }
                    }

                    Resource.Status.ERROR -> {
                        hideProgress()
                        showSnackbar(
                            SnackbarMessage(
                                it.message,
                                snackbarType,
                                action = snackBarAction,
                                actionText = actionText
                            )
                        )
                        errorAction?.invoke(it.message ?: COMMON_ERROR)
                    }

                    Resource.Status.LOADING -> {
                        showProgress()
                        loadingAction?.invoke()
                    }
                }
            }
        }
    }

}
