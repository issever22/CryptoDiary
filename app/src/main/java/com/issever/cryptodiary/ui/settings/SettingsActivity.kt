package com.issever.cryptodiary.ui.settings

import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.issever.cryptodiary.R
import com.issever.cryptodiary.base.BaseActivity
import com.issever.cryptodiary.databinding.ActivitySettingsBinding
import com.issever.cryptodiary.ui.main.MainActivity
import com.issever.cryptodiary.util.extensions.startNewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : BaseActivity<ActivitySettingsBinding,SettingsViewModel>() {

    override fun initViewBinding() = ActivitySettingsBinding.inflate(layoutInflater)

    override val viewModel: SettingsViewModel by viewModels()

    lateinit var navController: NavController

    override fun init() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.settingsFragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                startNewActivity(MainActivity::class.java, true)
            }
        })
    }

}