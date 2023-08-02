package com.issever.cryptodiary.ui.settings.fragments

import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.firebase.ui.auth.IdpResponse
import com.issever.cryptodiary.R
import com.issever.cryptodiary.base.BaseFragment
import com.issever.cryptodiary.data.model.SnackbarMessage
import com.issever.cryptodiary.databinding.FragmentSettingsBinding
import com.issever.cryptodiary.ui.main.MainActivity
import com.issever.cryptodiary.ui.settings.SettingsViewModel
import com.issever.cryptodiary.util.Constants.LocalData.DARK
import com.issever.cryptodiary.util.Constants.LocalData.ENGLISH
import com.issever.cryptodiary.util.Constants.LocalData.FOLLOW_SYSTEM
import com.issever.cryptodiary.util.Constants.LocalData.LIGHT
import com.issever.cryptodiary.util.Constants.LocalData.TURKISH
import com.issever.cryptodiary.util.Constants.Remote.MAIN_URL
import com.issever.cryptodiary.util.Errors.WENT_WRONG
import com.issever.cryptodiary.util.SnackbarType
import com.issever.cryptodiary.util.extensions.openGooglePlay
import com.issever.cryptodiary.util.extensions.openWebPage
import com.issever.cryptodiary.util.extensions.showChoiceDialog
import com.issever.cryptodiary.util.extensions.showConfirmationDialog
import com.issever.cryptodiary.util.extensions.slideInFromRight
import com.issever.cryptodiary.util.extensions.slideOutToLeft
import com.issever.cryptodiary.util.extensions.startNewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {

    override val viewModel: SettingsViewModel by viewModels()

    override fun initViewBinding() = FragmentSettingsBinding.inflate(layoutInflater)

    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.isSignedIn()
            viewModel.fetchAndSaveFavoritesFromFirebase()
            binding.settingsSignIn.slideOutToLeft(){
                binding.settingsSignOut.slideInFromRight()
            }
            viewModel.showSnackbar(SnackbarMessage(getString(R.string.successfully_sign_in), SnackbarType.SUCCESS))
        } else {
            val response = IdpResponse.fromResultIntent(result.data)
            viewModel.showSnackbar(SnackbarMessage(response?.error?.message?: WENT_WRONG, SnackbarType.ERROR))
        }
    }

    override fun init() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.settingsBackButton.setOnClickListener {
            currentActivity.startNewActivity(MainActivity::class.java, true)
        }

        binding.settingsTheme.setOnClickListener {
            currentActivity.showChoiceDialog(
                map = mapOf(
                    getString(R.string.dark) to DARK,
                    getString(R.string.light) to LIGHT,
                    getString(R.string.follow_system_default) to FOLLOW_SYSTEM
                ),
                selectedKey = viewModel.getSelectedTheme(),
                title = getString(R.string.choose_a_theme)
            ) { chosenTheme ->
                viewModel.setSelectedTheme(chosenTheme)
            }
        }

        binding.settingsLanguage.setOnClickListener {
            currentActivity.showChoiceDialog(
                map = mapOf(
                    getString(R.string.english) to ENGLISH,
                    getString(R.string.turkish) to TURKISH,
                    getString(R.string.follow_system_default) to FOLLOW_SYSTEM
                ),
                selectedKey = viewModel.getSelectedLanguage(),
                title = getString(R.string.choose_a_language)
            ) { chosenLanguage ->
                viewModel.setSelectedLanguage(chosenLanguage)
            }
        }


        binding.settingsOurApps.setOnClickListener {
            currentActivity.openGooglePlay()
        }

        binding.settingsContactUs.setOnClickListener {
            currentActivity.openWebPage(MAIN_URL)
        }

        binding.settingsSignIn.setOnClickListener {
            viewModel.signInWithGoogle()
        }

        binding.settingsSignOut.setOnClickListener {
            viewModel.signOut()
            binding.settingsSignOut.slideOutToLeft(){
                binding.settingsSignIn.slideInFromRight()
            }

        }

        binding.settingsClearCache.setOnClickListener {
            currentActivity.showConfirmationDialog(title = getString(R.string.clear_cache), message = getString(R.string.are_you_sure_you_want_to_clear_cache)){
                viewModel.clearAppCache()
            }
        }

        viewModel.authIntent.observe(viewLifecycleOwner) {
            it?.let {
                signInLauncher.launch(it)
            }
        }

        viewModel.isSigned.observe(viewLifecycleOwner){
            binding.settingsSignIn.isVisible = !it
            binding.settingsSignOut.isVisible = it
        }
    }
}