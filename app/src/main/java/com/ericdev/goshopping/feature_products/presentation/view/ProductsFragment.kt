package com.ericdev.goshopping.feature_products.presentation.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ericdev.goshopping.databinding.FragmentProductsBinding
import com.ericdev.goshopping.feature_products.presentation.viewmodel.ProductsViewModel

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

        // return binding.root

        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                /*MaterialTheme {
                    // In Compose world
                    Text("Hello Compose!")
                }*/
                val list = remember {
                    mutableStateOf((1..200).toList())
                }
                LazyColumn(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                ) {
                    items(list.value) { count ->
                        Text(text = "$count", fontSize = 24.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivImage.apply {
            clipToOutline = true
        }

        binding.laySwipeToRefresh.setOnRefreshListener {
            viewModel.getTempProducts()
        }

        viewModel.tempProductsState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.tvHome.text = "Loading..."

                    if (!binding.laySwipeToRefresh.isRefreshing) {
                        //  TODO("Show loading gif")
                        binding.loadView.visibility = VISIBLE
                    }
                }
                is Resource.Success -> {
                    binding.laySwipeToRefresh.isRefreshing = false
                    binding.loadView.visibility = INVISIBLE

                    binding.tvHome.text = "Found ${resource.data?.size ?: 0} products"
                    binding.ivImage.let {
                        Glide.with(requireActivity())
                            .load(resource.data?.get(12)?.image)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(it)
                    }
                }
                is Resource.Error -> {
                    binding.laySwipeToRefresh.isRefreshing = false
                    binding.loadView.visibility = INVISIBLE

                    binding.tvHome.text = "Error!"
                    showErrorDialog(resource.message!!)
                }
            }
        }
    }*/

    /*  private fun showErrorDialog(msg: String) {
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
              val firstChar = msg[0].uppercaseChar().toString()
              tvErrorMessage.text = msg.replaceFirst(firstChar, firstChar, true)

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
      }*/
}

@Preview
@Composable
fun Prev() {
    Text(text = "Hello XML from Compose!")
}
