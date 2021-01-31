package com.htueko.apod.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.htueko.apod.databinding.FragmentLocalBinding
import com.htueko.apod.ui.adapter.ApodAdapter
import com.htueko.apod.ui.viewmodel.LocalViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class LocalFragment : BaseFragment<FragmentLocalBinding, LocalViewModel>() {

    override val viewModel: LocalViewModel by viewModels()
    private val mAdapter = ApodAdapter() {
        val action = DetailFragmentDirections.actionGlobalDetailFragment(it)
        Navigation.findNavController(requireView()).navigate(action)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLocalBinding = FragmentLocalBinding.inflate(layoutInflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        updateData()
        observeData()
    }

    /**
     * when the internet connection is available
     */
    override fun onConnectionAvailable() {
        hideNetworkDialog()
    }

    /**
     * when the internet connection is not available
     */
    override fun onConnectionUnavailable() {
        showNetworkDialog()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            spanCount = 2
            adapter = mAdapter
        }
    }

    private fun updateData() {
        // get the local data set
        val data = getLocalData()
        // update the livedata at ViewModel
        viewModel.updateData(data)
    }

    /**
     * observe the change of the livedata from ViewModel and update the Adapter data set
     */
    private fun observeData() {
        viewModel.apodData.observe(viewLifecycleOwner, Observer {
            mAdapter.updateData(it)
        })
    }

}