package com.issever.cryptodiary.ui.splash.fragments.onboarding

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.issever.cryptodiary.R
import com.issever.cryptodiary.base.BaseFragment
import com.issever.cryptodiary.data.model.OnBoardingEntity
import com.issever.cryptodiary.databinding.FragmentOnBoardingBinding
import com.issever.cryptodiary.ui.main.MainActivity
import com.issever.cryptodiary.ui.splash.fragments.onboarding.adapter.OnBoardingAdapter
import com.issever.cryptodiary.util.Constants.LocalData.ON_BOARDING_STATUS
import com.issever.cryptodiary.util.extensions.startNewActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment<FragmentOnBoardingBinding,Nothing>() {

    private var currentItemIndex = 0
    override val viewModel = null

    override fun initViewBinding() = FragmentOnBoardingBinding.inflate(layoutInflater)

    override fun init() {

        currentActivity.localData.setBooleanData(saveName = ON_BOARDING_STATUS, true)

        val onBoardingAdapter = OnBoardingAdapter()

        binding.vpOnBoarding.adapter = onBoardingAdapter

        onBoardingAdapter.differ.submitList(getOnBoardingList() as List<OnBoardingEntity>?)

        binding.dotsIndicator.attachTo(binding.vpOnBoarding)

        binding.vpOnBoarding.registerOnPageChangeCallback(object : OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                binding.btnPrevious.isGone = position == 0

                currentItemIndex = position

                if (currentItemIndex < 2) {
                    binding.btnNext.text = getString(R.string.next)
                    binding.btnNext.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(currentActivity, R.color.general_color))
                } else {
                    binding.btnNext.text = getString(R.string.done)
                    binding.btnNext.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(currentActivity, R.color.success_color))
                }
            }
        })

        binding.btnNext.setOnClickListener {
            if (currentItemIndex < 2) {
                binding.vpOnBoarding.setCurrentItem(currentItemIndex + 1, true)
            } else {
                currentActivity.startNewActivity(MainActivity::class.java, true)
            }
        }

        binding.btnPrevious.setOnClickListener {
            if (currentItemIndex != 0) {
                binding.vpOnBoarding.setCurrentItem(currentItemIndex - 1, true)
            }
        }

        binding.tvSkip.setOnClickListener {
            currentActivity.startNewActivity(MainActivity::class.java, true)
        }
    }

    private fun getOnBoardingList(): ArrayList<OnBoardingEntity> {

        val onBoarding1 = OnBoardingEntity(
            title = R.string.app_name,
            description = R.string.app_description,
            icon = R.drawable.on_boarding_1
        )

        val onBoarding2 = OnBoardingEntity(
            title = R.string.on_boarding_title_1,
            description = R.string.on_boarding_desc_1,
            icon = R.drawable.on_boarding_2
        )

        val onBoarding3 = OnBoardingEntity(
            title = R.string.on_boarding_title_2,
            description = R.string.on_boarding_desc_2,
            icon = R.drawable.on_boarding_3
        )

        return arrayListOf(onBoarding1, onBoarding2, onBoarding3)
    }


}