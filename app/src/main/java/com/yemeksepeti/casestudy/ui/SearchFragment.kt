package com.yemeksepeti.casestudy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.yemeksepeti.casestudy.R
import com.yemeksepeti.casestudy.databinding.SearchFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var binding: SearchFragmentBinding

    private val searchAdapter = SearchAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            rvFilter.adapter = searchAdapter
            rvFilter.addItemDecoration(
                SpaceItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.grid_margin),
                    false
                )
            )

            edtSearch.doAfterTextChanged {
                search(
                    it.toString()
                )
            }
        }

        lifecycleScope.launch {
            searchAdapter.loadStateFlow.collectLatest { loadState ->
                mainViewModel.loading(loadState.refresh is LoadState.Loading)
            }
        }
    }

    private fun search(query: String) {
        lifecycleScope.launch {
            mainViewModel.search(
                query
            ).collectLatest {
                binding.apply {
                    searchAdapter.goToDetail = { item ->
                        mainViewModel.movie(item.id)
                        findNavController().navigate(R.id.action_searchFragment_to_searchDetailFragment)
                    }
                }
                searchAdapter.submitData(it)
            }
        }
    }
}