package com.issever.cryptodiary.ui.main

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.issever.cryptodiary.R
import com.issever.cryptodiary.base.BaseActivity
import com.issever.cryptodiary.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding,Nothing>() {

    override fun initViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    lateinit var navController: NavController

    override fun init() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainFragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
    }

}