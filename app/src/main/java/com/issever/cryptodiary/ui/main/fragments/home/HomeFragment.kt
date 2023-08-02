package com.issever.cryptodiary.ui.main.fragments.home

import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.issever.cryptodiary.R
import com.issever.cryptodiary.base.BaseFragment
import com.issever.cryptodiary.databinding.FragmentHomeBinding
import com.issever.cryptodiary.ui.main.fragments.home.adapter.CoinListAdapter
import com.issever.cryptodiary.ui.main.fragments.home.adapter.FavoriteCoinListAdapter
import com.issever.cryptodiary.ui.main.fragments.home.adapter.TrendingCoinListAdapter
import com.issever.cryptodiary.ui.main.fragments.home.adapter.TrendingNftListAdapter
import com.issever.cryptodiary.ui.settings.SettingsActivity
import com.issever.cryptodiary.util.extensions.rotateCircularly
import com.issever.cryptodiary.util.extensions.startNewActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {


    override val viewModel: HomeViewModel by viewModels()
    override fun initViewBinding() = FragmentHomeBinding.inflate(layoutInflater)

    private val coinListAdapter = CoinListAdapter()
    private val favoriteCoinListAdapter = FavoriteCoinListAdapter()
    private val trendingCoinListAdapter = TrendingCoinListAdapter()
    private val trendingNftListAdapter = TrendingNftListAdapter()

    override fun init() {

        binding.homeAppIcon.rotateCircularly(12000, infinite = true)

        binding.coinListRecyclerView.adapter = coinListAdapter
        binding.favoriteCoinListRecyclerView.adapter = favoriteCoinListAdapter
        binding.trendingCoinListRecyclerView.adapter = trendingCoinListAdapter
        binding.trendingNftListRecyclerView.adapter = trendingNftListAdapter

        binding.homeSwipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshData()
        }

        binding.openSettingsButton.setOnClickListener {
            currentActivity.startNewActivity(SettingsActivity::class.java,true)
        }

        binding.homeReturnTopFAB.setOnClickListener {
            binding.homeNestedScrollView.smoothScrollTo(0, 0)
            binding.homeReturnTopFAB.hide()
        }

        binding.homeNestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            if (scrollY > oldScrollY) {
                binding.homeReturnTopFAB.show()
            }
            if (scrollY == 0) {
                binding.homeReturnTopFAB.hide()
            }
        })

        binding.homeSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                coinListAdapter.filter.filter(p0)
                favoriteCoinListAdapter.filter.filter(p0)
                trendingCoinListAdapter.filter.filter(p0)
                trendingNftListAdapter.filter.filter(p0)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                coinListAdapter.filter.filter(p0)
                favoriteCoinListAdapter.filter.filter(p0)
                trendingCoinListAdapter.filter.filter(p0)
                trendingNftListAdapter.filter.filter(p0)
                return false
            }
        })

        viewModel.favoriteCoinList.observe(viewLifecycleOwner) {
            binding.favoritesLayout.isVisible = it.isNotEmpty()
            favoriteCoinListAdapter.fullList = it
            favoriteCoinListAdapter.differ.submitList(it)
            binding.homeSwipeRefreshLayout.isRefreshing = false
        }

        viewModel.coinList.observe(viewLifecycleOwner) {
            binding.allCoinsTitle.isVisible = it.isNotEmpty()
            coinListAdapter.fullList = it
            coinListAdapter.differ.submitList(it)
            binding.homeSwipeRefreshLayout.isRefreshing = false
        }

        viewModel.trendingCoinList.observe(viewLifecycleOwner) {
            binding.trendingCoinsTitle.isVisible = it.isNotEmpty()
            trendingCoinListAdapter.fullList = it
            trendingCoinListAdapter.differ.submitList(it)
            binding.homeSwipeRefreshLayout.isRefreshing = false
        }

        viewModel.trendingNftList.observe(viewLifecycleOwner) {
            binding.trendingNftTitle.isVisible = it.isNotEmpty()
            trendingNftListAdapter.fullList = it
            trendingNftListAdapter.differ.submitList(it)
            binding.homeSwipeRefreshLayout.isRefreshing = false
        }

        coinListAdapter.setOnItemClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(it,it.id)
            )
        }

        favoriteCoinListAdapter.setOnItemClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(it,it.id)
            )
        }

        trendingCoinListAdapter.setOnItemClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(null,it.item.id)
            )
        }

        trendingNftListAdapter.setOnItemClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToNftDetailFragment(it.id)
            )
        }

        findNavController().addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment) {
                viewModel.refreshData()
            }
        }
    }
}