package com.htueko.apod.ui.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.htueko.apod.databinding.ContentNetworkStateLayoutBinding
import com.htueko.apod.util.Logger

private val mTag: String = NetworkDialog::class.java.name

class NetworkDialog : DialogFragment() {

    private var _binding: ContentNetworkStateLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ContentNetworkStateLayoutBinding.inflate(inflater, container, false)
        Logger.debug(mTag, "onCreateView() called")
        Logger.debug(mTag, "onCreateView() private binding : $_binding")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.debug(mTag, "onViewCreated() called")
        binding.run {
            btnOpenNetworkSetting.setOnClickListener {
                Logger.debug(mTag, "onViewCreated() ${btnOpenNetworkSetting.text} pressed")
                val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                Logger.debug(mTag, "onViewCreated() Button Intent: $intent")
                startActivity(intent)
                Logger.debug(mTag, "onViewCreated() startActivity() called")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Logger.debug(mTag, "onStart() called")
        dialog?.setCancelable(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.debug(mTag, "onDestroy() called")
        _binding = null
        Logger.debug(mTag, "onDestroy() private binding : $_binding")
    }

}