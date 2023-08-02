package com.issever.cryptodiary.ui.main.fragments.detail.nft

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.issever.cryptodiary.base.BaseFragment
import com.issever.cryptodiary.databinding.FragmentNftDetailBinding
import com.issever.cryptodiary.ui.main.fragments.detail.nft.adapter.ExplorerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NftDetailFragment : BaseFragment<FragmentNftDetailBinding, NftDetailViewModel>() {

    override val viewModel: NftDetailViewModel by viewModels()

    private val args by navArgs<NftDetailFragmentArgs>()

    override fun initViewBinding() = FragmentNftDetailBinding.inflate(layoutInflater)

    private val explorerAdapter = ExplorerAdapter()

    override fun init() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        if (arguments != null) {
            viewModel.getNftDetail(args.id)
        }

        binding.nftDetailBackButton.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.nftDetail.observe(viewLifecycleOwner) { links ->
            binding.nftDetailLinkRecyclerView.adapter = explorerAdapter
            explorerAdapter.differ.submitList(links.explorers.filter { it.link.isNotEmpty() })
        }
    }

}