package com.example.nasaapi.ui.fragment.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nasaapi.base.BaseState
import com.example.nasaapi.databinding.FragmentListBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.HttpException
import java.net.UnknownHostException

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val viewModel: ListViewModel by viewModels()
    private lateinit var adapter: ListAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        // Observer
        viewModel.getState().observe(viewLifecycleOwner, { state ->
            when (state) {
                is BaseState.Error -> {
                    binding.fragmentListProgressBar.visibility = View.GONE
                    adapter.updateList(listOf())

                    val msg = when (state.dataError) {
                        is HttpException -> "Fatal Error: " + state.dataError.code().toString()
                        is UnknownHostException -> "No internet connection"
                        else -> "Generic Error"
                    }

                    // Show error message
                    MaterialAlertDialogBuilder(requireActivity())
                            .setTitle("Error")
                            .setMessage(msg)
                            .setPositiveButton("Retry") { dialog, which ->
                                // Respond to positive button press
                                viewModel.requestInformation()
                            }
                            .show()
                }
                is BaseState.Loading -> {
                    binding.fragmentListProgressBar.visibility = View.VISIBLE
                }
                is BaseState.Normal -> {
                    binding.fragmentListProgressBar.visibility = View.GONE
                    binding.fragmentListSwipeRefreshLayout.isRefreshing = false
                    adapter.updateList((state.data as ListState).picturesList)
                }
            }
        })

        // Set recycler view
        adapter = ListAdapter(listOf(), requireActivity())

        binding.fragmentListRecyclerView.apply {
            adapter = this@ListFragment.adapter
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
        }

        // Set Swipe Refresh Gesture
        binding.fragmentListSwipeRefreshLayout.setOnRefreshListener {
            adapter.updateList(listOf())
            viewModel.requestInformation()
        }

        // Request information and return it
        viewModel.requestInformation()

        return binding.root
    }
}