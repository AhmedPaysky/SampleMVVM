package com.example.osama.ui.userdetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.osama.data.entities.UserModel
import com.example.osama.databinding.UserDetailFragmentBinding
import com.example.osama.utils.Resource
import com.example.osama.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import android.content.Intent

import android.content.BroadcastReceiver
import android.content.Context
import android.util.Log
import com.example.osama.background.CounterDownService
import android.content.IntentFilter
import java.lang.Exception

@SuppressLint("LogNotTimber")
@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    private val TAG = UserDetailFragment::class.java.name
    private val br: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateGUI(intent) // or whatever method used to update your GUI fields
        }
    }

    private var binding: UserDetailFragmentBinding by autoCleared()
    private val viewModel: UserDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let { viewModel.start(it) }
        setupObservers()
        activity?.startService(Intent(activity, CounterDownService::class.java))
        Log.i(TAG, "Started service")
    }


    private fun setupObservers() {
        viewModel.userModel.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    bindUser(it.data!!)
                    binding.progressBar.visibility = View.GONE
                    binding.userCl.visibility = View.VISIBLE
                }

                Resource.Status.ERROR ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.userCl.visibility = View.GONE
                }
            }
        })
    }

    private fun bindUser(userModel: UserModel) {
        binding.name.text = userModel.last_name
        binding.id.text = userModel.id.toString()
        binding.email.text = userModel.email
        Glide.with(binding.root)
            .load(userModel.avatar)
            .transform(CircleCrop())
            .into(binding.image)
    }

    override fun onResume() {
        super.onResume()
        activity?.registerReceiver(br, IntentFilter(CounterDownService.COUNTDOWN_BR))
        Log.i(TAG, "Registered broacast receiver")
    }

    override fun onPause() {
        super.onPause()
        activity?.unregisterReceiver(br)
        Log.i(TAG, "Unregistered broacast receiver")
    }

    override fun onStop() {
        try {
            activity?.unregisterReceiver(br)
        } catch (e: Exception) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop()
    }

    override fun onDestroy() {
        activity?.stopService(Intent(activity, CounterDownService::class.java))
        Log.i(TAG, "Stopped service")
        super.onDestroy()
    }

    private fun updateGUI(intent: Intent?) {
        if (intent?.extras != null) {
            val millisUntilFinished = intent.getLongExtra("countdown", -1)
            Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000)
            binding.counter.text = (millisUntilFinished / 1000).toString()
            if (millisUntilFinished.toInt() / 1000 == 1) activity?.onBackPressed()
        }
    }
}
