package com.issever.cryptodiary.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.issever.cryptodiary.util.extensions.showSnackbar

abstract class BaseFragment<VB : ViewBinding,VM : BaseViewModel?> : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!
    protected val currentActivity: BaseActivity<*, *> by lazy { requireActivity() as BaseActivity<*, *> }

    protected open val viewModel: VM? = null
    protected abstract fun initViewBinding(): VB
    protected open fun init() {}
    open val progressBar : ProgressBar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = initViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel?.snackbarText?.observe(viewLifecycleOwner) {
            _binding?.root?.showSnackbar(it)
        }
        viewModel?.isLoading?.observe(viewLifecycleOwner) {
            progressBar?.isVisible = it
        }

        init()
    }

    override fun onDestroyView() {
        viewModel?.snackbarText?.removeObservers(viewLifecycleOwner)
        viewModel?.isLoading?.removeObservers(viewLifecycleOwner)
        super.onDestroyView()
        _binding = null
    }
}