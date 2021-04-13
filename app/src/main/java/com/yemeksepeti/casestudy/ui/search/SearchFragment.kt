package com.yemeksepeti.casestudy.ui.search

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
import com.yemeksepeti.casestudy.ui.MainViewModel
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

            rvFilter.addItemDecoration(
                SpaceItemDecoration(
                    resources.getDimensionPixelSize(R.dimen.grid_margin),
                    false
                )
            )

            rvFilter.adapter = searchAdapter

            edtSearch.addTextChangedListener(DebouncingQueryTextListener(viewLifecycleOwner.lifecycle) {
                search(it.orEmpty())
            })

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
                        val action =
                            SearchFragmentDirections.actionSearchFragmentToSearchDetailFragment(
                                item.id ?: 0, item.title.orEmpty()
                            )
                        findNavController().navigate(action)
                    }
                }
                searchAdapter.submitData(it)
            }
        }
    }
}