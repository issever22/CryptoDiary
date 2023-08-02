package com.issever.cryptodiary.ui.splash.fragments

import com.issever.cryptodiary.base.BaseFragment
import com.issever.cryptodiary.databinding.FragmentSplashBinding
import com.issever.cryptodiary.ui.main.MainActivity
import com.issever.cryptodiary.util.extensions.rotateCircularly
import com.issever.cryptodiary.util.extensions.startNewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, Nothing>() {

    override fun initViewBinding() = FragmentSplashBinding.inflate(layoutInflater)

    override fun init() {
        binding.appLogoImage.rotateCircularly(onAnimationEnd = {currentActivity.startNewActivity(MainActivity::class.java,true)})
    }
}