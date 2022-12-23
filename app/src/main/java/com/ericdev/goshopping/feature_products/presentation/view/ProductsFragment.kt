package com.ericdev.goshopping.feature_products.presentation.view

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.ericdev.goshopping.R
import com.ericdev.goshopping.databinding.ErrorDialogBinding
import com.ericdev.goshopping.databinding.FragmentProductsBinding
import com.ericdev.goshopping.feature_products.presentation.viewmodel.ProductsViewModel
import com.ericdev.goshopping.util.Resource

class ProductsFragment : Fragment() {
    private val viewModel: ProductsViewModel by activityViewModels()

    lateinit var binding: FragmentProductsBinding

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(layoutInflater)

        requireActivity().window.statusBarColor = Color.parseColor("#E2615B")
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivImage.apply {
            clipToOutline = true
        }

        viewModel.tempProductsState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.tvHome.text = "Loading..."
                    //  TODO("Show loading gif")
                }
                is Resource.Success -> {
                    binding.tvHome.text = "Found ${resource.data?.size ?: 0} products"
                    binding.ivImage.let {
                        Glide.with(requireActivity())
                            .load(resource.data?.get(8)?.image)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(it)
                    }
                }
                is Resource.Error -> {
                    binding.tvHome.text = "Error!"
                    showErrorDialog(resource.message!!)
                }
            }
        }
    }

    private fun showErrorDialog(msg: String) {
        val dialogBinding = ErrorDialogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context)
        builder.setView(binding.root)

        val customDialog = AlertDialog.Builder(requireActivity(), 0).create()

        customDialog.apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setView(dialogBinding.root)
            setCancelable(false)
        }.show()

        customDialog.apply {
            setView(dialogBinding.root)
            setCancelable(false)
        }.show()

        dialogBinding.apply {
            tvErrorMessage.text = msg

            btnErrorCancel.setOnClickListener {
                customDialog.dismiss()
                // TODO: Show a "no data" image on the screen when the dialog exits on an Error state.
                //  Hide this image during Loading and Success states.
            }
            btnCancelRetry.setOnClickListener {
                viewModel.getTempProducts()
                customDialog.dismiss()
            }
        }
    }
}
