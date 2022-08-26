package com.kazim.artapp.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.kazim.artapp.R
import com.kazim.artapp.databinding.ArtdetailsFragmentBinding
import com.kazim.artapp.util.Status
import com.kazim.artapp.viewmodel.ArtViewModel
import javax.inject.Inject


class ArtDetailsFragment @Inject constructor(
    val glide: RequestManager
):Fragment(R.layout.artdetails_fragment) {
    private var fragmentBinding:ArtdetailsFragmentBinding ?=null
    private lateinit var viewModel: ArtViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(requireActivity())[ArtViewModel::class.java]
        val binding=ArtdetailsFragmentBinding.bind(view)
        fragmentBinding=binding
        subcribeToObserves()

        binding.artImageD.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToArtImageFragment())
        }

       /* val callback =object :OnBackPressedCallback(true){
             override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)*/


        binding.artSaveButtonD.setOnClickListener {
            viewModel.makeArt(binding.artNameTextD.text.toString(),binding.artArtisttextD.text.toString(),
                binding.artYearTextD.text.toString())
        }

    }
    private fun subcribeToObserves(){
        viewModel.selectImageUrl.observe(viewLifecycleOwner, Observer {url->
            fragmentBinding?.let {
                glide.load(url).into(it.artImageD)
            }
        })
        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    Toast.makeText(requireContext(),"SUCCES",Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInstertArtMsg()

                }
              Status.ERROR ->{
                Toast.makeText(requireContext(),it.message ?:"Error",Toast.LENGTH_LONG).show()
            }
                Status.LOADING ->{

                }

            }

        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentBinding =null
    }
}