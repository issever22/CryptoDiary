package com.issever.cryptodiary.ui.main.fragments.detail.coin

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.issever.cryptodiary.base.BaseFragment
import com.issever.cryptodiary.databinding.FragmentCoinDetailBinding
import com.issever.cryptodiary.ui.main.fragments.detail.coin.adapter.LinkAdapter
import com.issever.cryptodiary.util.ChartHelper
import com.issever.cryptodiary.util.extensions.heartAnimation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailFragment : BaseFragment<FragmentCoinDetailBinding, CoinDetailViewModel>() {

    override val viewModel: CoinDetailViewModel by viewModels()

    private val args by navArgs<CoinDetailFragmentArgs>()

    override fun initViewBinding() = FragmentCoinDetailBinding.inflate(layoutInflater)

    private val linkAdapter = LinkAdapter()

    override fun init() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        if (arguments != null) {
            if (args.coin != null) {
                viewModel.coin.postValue(args.coin)
            } else {
                viewModel.getCoin(args.id)
            }
            viewModel.getCoinDetail(args.id)
            viewModel.getCoinChart(args.id)
            viewModel.isCoinExist(args.id)
        }

        binding.detailFavoriteButton.setOnClickListener {
            binding.detailFavoriteButton.heartAnimation(
                onSaveStart = {
                    viewModel.saveCoinToDb()
                },
                onDeleteStart = {
                    viewModel.deleteCoinFromDb()
                }
            )
        }

        binding.detailBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.coinChart.observe(viewLifecycleOwner) {
            ChartHelper.displayHistoricalLineChart(binding.lineChart, args.id, it.prices,resources,currentActivity)
        }

        viewModel.changeFavoriteImage.observe(viewLifecycleOwner) {
            if (it) {
                binding.detailFavoriteButton.heartAnimation()
            }
        }

        viewModel.coinDetail.observe(viewLifecycleOwner) { links ->
            binding.detailLinkRecyclerView.adapter = linkAdapter
            linkAdapter.differ.submitList(links.links.homepage.filter { it.isNotEmpty() })
        }
    }

}