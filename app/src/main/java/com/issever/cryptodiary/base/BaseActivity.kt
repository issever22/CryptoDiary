package com.issever.cryptodiary.base

import android.content.Context
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.issever.cryptodiary.data.localData.LocalData
import com.issever.cryptodiary.di.EntryPoint
import com.issever.cryptodiary.util.Constants.LocalData.FOLLOW_SYSTEM
import com.issever.cryptodiary.util.extensions.setupFullScreenMode
import com.issever.cryptodiary.util.extensions.showSnackbar
import com.issever.cryptodiary.util.extensions.updateLocale
import dagger.hilt.android.EntryPointAccessors

abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel?> : AppCompatActivity() {
    private var _binding: VB? = null
    val binding get() = _binding!!

    lateinit var localData: LocalData

    protected open val viewModel: VM? = null
    protected abstract fun initViewBinding(): VB
    protected open fun init() {}
    open val progressBar : ProgressBar? = null

    override fun attachBaseContext(newBase: Context) {
        val hiltEntryPoint = EntryPointAccessors.fromApplication(newBase, EntryPoint::class.java)
        localData = hiltEntryPoint.localData()
        val selectedLanguage = localData.getSelectedLanguage()
        val language =
            if (selectedLanguage == FOLLOW_SYSTEM) localData.getInitialLocale() else selectedLanguage
        val updatedContext = newBase.updateLocale(language)
        super.attachBaseContext(updatedContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = initViewBinding()
        setContentView(binding.root)
        setupFullScreenMode()

        viewModel?.snackbarText?.observe(this) {
            _binding?.root?.showSnackbar(it)
        }

        viewModel?.isLoading?.observe(this) {
            progressBar?.isVisible = it
        }

        init()
    }

    override fun onDestroy() {
        viewModel?.snackbarText?.removeObservers(this)
        viewModel?.isLoading?.removeObservers(this)
        super.onDestroy()
    }
}
