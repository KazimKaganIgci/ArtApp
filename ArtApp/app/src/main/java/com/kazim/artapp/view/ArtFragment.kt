package com.kazim.artapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kazim.artapp.R
import com.kazim.artapp.adapter.ArtRecyclerAdapter
import com.kazim.artapp.databinding.ArtFragmentBinding
import com.kazim.artapp.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(
    val artRecyclerAdapter: ArtRecyclerAdapter
):Fragment(R.layout.art_fragment) {
    private var fragmentbinding:ArtFragmentBinding?= null
    lateinit var viewModel: ArtViewModel
    private var swipeCallBack =object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
             return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition =viewHolder.layoutPosition
            val selectedArt =artRecyclerAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]
        val binding =ArtFragmentBinding.bind(view)
        fragmentbinding=binding
        subscribeToObservers()
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.adapter=artRecyclerAdapter
        binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }
    }
    private fun subscribeToObservers(){
         viewModel.artList.observe(viewLifecycleOwner, Observer {
             artRecyclerAdapter.arts =it
         })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentbinding=null
    }

}