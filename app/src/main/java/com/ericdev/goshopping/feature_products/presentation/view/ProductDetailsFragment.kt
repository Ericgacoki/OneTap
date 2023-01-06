package com.ericdev.goshopping.feature_products.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ericdev.goshopping.R
import com.ericdev.goshopping.compose.ui.theme.GoShoppingTheme
import com.ericdev.goshopping.compose.ui.theme.colorInactive
import com.ericdev.goshopping.compose.ui.theme.colorPrimary
import com.ericdev.goshopping.compose.ui.theme.nunitoFontFamily
import com.ericdev.goshopping.feature_products.data.remote.dto.temp.TempProductDtoResultItem
import com.ericdev.goshopping.feature_products.presentation.viewmodel.ProductsViewModel
import com.google.accompanist.pager.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import timber.log.Timber

@Suppress("DEPRECATION") // 🖕🏿
class ProductDetailsFragment : Fragment() {

    private val viewModel: ProductsViewModel by activityViewModels()

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        requireActivity().window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        return ComposeView(requireContext()).apply {

            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                GoShoppingTheme {

                    val palette = viewModel.currentPalette.collectAsState()
                    val systemUiController = rememberSystemUiController()
                    val useDarkIcons = true

                    DisposableEffect(systemUiController, useDarkIcons) {
                        systemUiController.setStatusBarColor(
                            color = Color.Transparent,
                            darkIcons = useDarkIcons
                        )


                        onDispose {}
                    }

                    val product = viewModel.selectedProductOnNavigation

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        stickyHeader {
                            ProductImagesAndPrice(product = product,
                                palette = palette.value,
                                onFavoriteIconClick = {

                                },
                                provideCurrentImageUrl = {
                                    viewModel.resolveColorsFromUrl(it)

                                    Timber.e("CALLING VIEW MODEL")
                                }
                            )
                        }

                        item {
                            Column(modifier = Modifier.fillMaxSize()) {
                                Text(
                                    modifier = Modifier.padding(
                                        top = 8.dp,
                                        end = 8.dp,
                                        start = 16.dp
                                    ),
                                    text = product?.title ?: "N/A",
                                    maxLines = 1,
                                    style = MaterialTheme.typography.h5,
                                    fontWeight = FontWeight.SemiBold,
                                )

                                RatingBarCompose(
                                    modifier = Modifier.padding(
                                        vertical = 8.dp,
                                        horizontal = 16.dp
                                    ),
                                    value = product?.rating?.rate?.toFloat() ?: 0F,
                                    totalStars = 5
                                )

                                Text(
                                    modifier = Modifier.padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        bottom = 8.dp
                                    ),
                                    text = product?.description ?: "No description!",
                                    style = MaterialTheme.typography.body1,
                                    color = Color(0XFF4B4B4B)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductImagesAndPrice(
    product: TempProductDtoResultItem?,
    isFavorite: Boolean = false,
    palette: Palette?,
    onFavoriteIconClick: () -> Unit,
    provideCurrentImageUrl: (String?) -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(Color.White)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // TODO: Get these links from a product's property in the new API
            val images = remember {
                mutableListOf(
                    product?.image, product?.image, product?.image,
                    /*"https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
                    "https://fakestoreapi.com/img/61U7T1koQqL._AC_SX679_.jpg"*/
                )
            }
            val pagerState = rememberPagerState()

            LaunchedEffect(key1 = pagerState.currentPage, block = {
                provideCurrentImageUrl(images[pagerState.currentPage])
            })

            ProductImagesViewPager(
                pagerState = pagerState,
                images = images.toList()
            )

            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // TODO: Decide which Swatch to pick
                val swatchRGB = palette.let { it?.darkMutedSwatch?.rgb }
                // val swatchTextRGB = palette.let { it?.dominantSwatch?.bodyTextColor }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100))
                        .background(
                            color = if (swatchRGB != null) Color(swatchRGB).copy(alpha = .24F) else
                                colorPrimary.copy(alpha = .24F)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$ ${product?.price.toString()}",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.h6,
                        maxLines = 1,
                        color = if (swatchRGB != null) Color(swatchRGB) else colorPrimary,
                        fontWeight = FontWeight.Bold,
                        fontFamily = nunitoFontFamily,
                    )
                }

                Icon(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .size(20.dp)
                        .clickable(
                            enabled = true,
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(
                                bounded = false,
                                radius = 24.dp,
                                color = colorPrimary
                            )
                        ) {
                            onFavoriteIconClick()
                        },
                    painter = painterResource(id = R.drawable.ic_fav_not_filled),
                    tint = if (isFavorite) colorPrimary else Color(0XFFD7D7D7),
                    contentDescription = null,
                )
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp),
                activeColor = colorPrimary,
                inactiveColor = colorInactive
            )
        }
    }
}

@Composable
fun RatingBarCompose(modifier: Modifier = Modifier, value: Float, totalStars: Int) {
    RatingBar(
        modifier = modifier,
        value = value,
        config = RatingBarConfig()
            .activeColor(Color(0XFFFFC700))
            .hideInactiveStars(false)
            .inactiveColor(Color(0XFFD9D9D9))
            .inactiveBorderColor(Color.Blue)
            .stepSize(StepSize.HALF)
            .numStars(totalStars)
            .isIndicator(true)
            .size(16.dp)
            .padding(4.dp)
            .style(RatingBarStyle.Normal),
        onValueChange = {},
        onRatingChanged = {}
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun ProductImagesViewPager(
    pagerState: PagerState,
    images: List<String?>
) {
    HorizontalPager(
        modifier = Modifier
            .padding(top = 24.dp)
            .fillMaxWidth()
            .padding(8.dp),
        count = images.size,
        state = pagerState
    ) { currentPage ->
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(images[currentPage])
                .crossfade(durationMillis = 250)
                .build(),
            placeholder = painterResource(R.drawable.ic_bag_icon_large),
            error = painterResource(R.drawable.ic_broken_image),
            fallback = painterResource(R.drawable.ic_bag_icon_large),
            contentDescription = null,
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RatingBarPreview() {
    GoShoppingTheme {
        RatingBarCompose(value = 3.0F, totalStars = 5)
    }
}
