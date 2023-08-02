package com.issever.cryptodiary.ui.splash

import android.annotation.SuppressLint
import androidx.navigation.fragment.NavHostFragment
import com.issever.cryptodiary.R
import com.issever.cryptodiary.base.BaseActivity
import com.issever.cryptodiary.databinding.ActivitySplashBinding
import com.issever.cryptodiary.util.Constants.LocalData.ON_BOARDING_STATUS
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, Nothing>() {

    override fun initViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun init() {
        val onBoardingStatus =
            localData.getBooleanData(saveName = ON_BOARDING_STATUS, false)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.splashFragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.splash_nav_graph)

        if (onBoardingStatus) {
            navGraph.setStartDestination(R.id.splashFragment)
            navController.graph = navGraph
        } else {
            navGraph.setStartDestination(R.id.onBoardingFragment)
            navController.graph = navGraph
        }
    }


}