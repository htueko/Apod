package com.htueko.apod.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.htueko.apod.data.model.Apod
import com.htueko.apod.ui.view.NetworkDialog
import com.htueko.apod.util.ConnectionState
import com.htueko.apod.util.getJsonDataFromAsset
import com.htueko.apod.util.observeConnectivityAsFlow
import com.htueko.apod.util.shareWhileObserved
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

@ExperimentalCoroutinesApi
abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    companion object {
        private const val TAG_NETWORK_DIALOG = "network_dialog"
    }

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    protected abstract val viewModel: VM

    private var networkDialog: NetworkDialog? = null

    private lateinit var connectionState: Flow<ConnectionState>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        observeConnectionState()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        networkDialog?.dismiss()
        networkDialog = null
    }

    protected abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    fun showNetworkDialog() {
        if (networkDialog == null) networkDialog = NetworkDialog()
        networkDialog?.let {
            if (!it.isVisible) {
                it.show(requireActivity().supportFragmentManager, TAG_NETWORK_DIALOG)
            }
        }
    }

    fun hideNetworkDialog() {
        networkDialog?.let {
            if (it.isVisible) {
                it.dismiss()
            }
        }
    }

    fun getApplicationContext(): Context = requireActivity().applicationContext

    private fun observeConnectionState() {
        connectionState = getApplicationContext()
            .observeConnectivityAsFlow()
            .shareWhileObserved(viewLifecycleOwner.lifecycleScope)
            .also { flow ->
                flow.asLiveData().observe(viewLifecycleOwner) { state ->
                    when (state) {
                        ConnectionState.Available -> onConnectionAvailable()
                        ConnectionState.Unavailable -> onConnectionUnavailable()
                    }
                }
            }
    }

    abstract fun onConnectionAvailable()
    abstract fun onConnectionUnavailable()

    suspend fun isConnected(): Boolean {
        return this::connectionState.isInitialized && connectionState.first<ConnectionState>() is ConnectionState.Available
    }

    fun getLocalData(): List<Apod> {
        val jsonString = getJsonDataFromAsset(requireContext().applicationContext, "nasa.json")
        return Gson().fromJson(jsonString, object : TypeToken<List<Apod>>() {}.type)
    }

}