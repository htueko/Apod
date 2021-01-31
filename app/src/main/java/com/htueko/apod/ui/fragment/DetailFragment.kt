package com.htueko.apod.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.htueko.apod.databinding.FragmentDetailBinding
import com.htueko.apod.ui.MainActivity
import com.htueko.apod.ui.adapter.ApodViewPagerAdapter
import com.htueko.apod.ui.viewmodel.LocalViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class DetailFragment : BaseFragment<FragmentDetailBinding, LocalViewModel>() {

    override val viewModel: LocalViewModel by viewModels()

    // arguments received
    private val args: DetailFragmentArgs by navArgs()
    private val mAdapter: ApodViewPagerAdapter = ApodViewPagerAdapter()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailBinding {
        // hide the appbar and bottom appbar when this fragment views is created
        (activity as MainActivity).toggleNavView(false)
        return FragmentDetailBinding.inflate(layoutInflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    override fun onStart() {
        super.onStart()
        updateData()
        observeData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // show the appbar and bottom appbar after this views from fragment is destroy
        (activity as MainActivity).toggleNavView(true)
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

    private fun setupViewPager() {
        binding.viewPager.apply {
            adapter = mAdapter
        }
    }

    private fun updateData() {
        // get the local data set
        val data = getLocalData()
        // reorder the data at ViewModel
        viewModel.reorder(args.apod, data)
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