package com.ericdev.onetap.feature_products.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ericdev.onetap.R
import com.ericdev.onetap.compose.ui.theme.OneTapTheme
import com.ericdev.onetap.compose.ui.theme.colorInactive
import com.ericdev.onetap.compose.ui.theme.colorPrimary
import com.ericdev.onetap.compose.ui.theme.nunitoFontFamily
import com.ericdev.onetap.core.data.remote.dto.temp.TempProductDtoResultItem
import com.ericdev.onetap.feature_products.presentation.viewmodel.ProductsViewModel
import com.google.accompanist.pager.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize

@Suppress("DEPRECATION") // 🖕🏿
class ProductDetailsFragment : Fragment() {

    private val viewModel: ProductsViewModel by activityViewModels()

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
                OneTapTheme {

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
                    val favoriteProducts =
                        viewModel.favoriteProducts.collectAsState(initial = emptyList())
                    var itemCount by remember { mutableStateOf(1) }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    ) {
                        item {
                            val favorite =
                                favoriteProducts.value.any { fav -> fav.productId == product?.id }
                            Column(modifier = Modifier.fillMaxSize()) {

                                ProductImagesAndPrice(
                                    product = product,
                                    isFavorite = favorite,
                                    palette = palette.value,
                                    onFavoriteIconClick = {
                                        if (favorite) {
                                            viewModel.removeProductToFavorite(
                                                product?.id ?: 0
                                            )
                                        } else {
                                            viewModel.addProductToFavorite(
                                                product?.id ?: 0
                                            )
                                        }
                                    },
                                    provideCurrentImageUrl = {
                                        viewModel.resolveColorsFromUrl(it)
                                    }
                                )

                                Text(
                                    modifier = Modifier.padding(
                                        top = 8.dp,
                                        end = 8.dp,
                                        start = 12.dp
                                    ),
                                    text = product?.title ?: "N/A",
                                    maxLines = 1,
                                    style = MaterialTheme.typography.h5,
                                    fontWeight = FontWeight.SemiBold,
                                )

                                RatingBarCompose(
                                    modifier = Modifier.padding(
                                        vertical = 8.dp,
                                        horizontal = 12.dp
                                    ),
                                    value = product?.rating?.rate?.toFloat() ?: 0F,
                                    totalStars = 5
                                )

                                Text(
                                    modifier = Modifier.padding(
                                        start = 12.dp,
                                        end = 12.dp,
                                        bottom = 8.dp
                                    ),
                                    text = product?.description ?: "No description!",
                                    style = MaterialTheme.typography.body1,
                                    color = Color(0XFF4B4B4B)
                                )

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            top = 8.dp,
                                            bottom = 4.dp,
                                            start = 12.dp,
                                            end = 12.dp
                                        ),
                                    horizontalArrangement = SpaceBetween
                                ) {
                                    val colors = listOf(
                                        Color(0XFF9B6484),
                                        Color(0xFFFDA32F),
                                        Color(0XFF09090D)
                                    )
                                    val sizes = listOf("S", "M", "L")
                                    var selectedColor by remember { mutableStateOf(colors[0]) }
                                    var selectedSize by remember { mutableStateOf(sizes[0]) }

                                    LazyRow(verticalAlignment = CenterVertically) {
                                        if (colors.isNotEmpty())
                                            items(colors) { color ->
                                                SelectedColorIndicator(
                                                    selected = color == selectedColor,
                                                    color = color
                                                ) {
                                                    selectedColor = color
                                                }
                                            }
                                    }

                                    LazyRow(verticalAlignment = CenterVertically) {
                                        if (sizes.isNotEmpty())
                                            items(sizes) { size ->
                                                SelectedSizeIndicator(
                                                    size = size,
                                                    selected = size == selectedSize
                                                ) {
                                                    selectedSize = size
                                                }
                                            }
                                    }
                                }

                                Box(
                                    modifier = Modifier.padding(
                                        top = 16.dp,
                                        bottom = 32.dp,
                                        start = 12.dp
                                    )
                                ) {
                                    ItemCountIndicator(
                                        items = itemCount,
                                        onMinus = {
                                            if (itemCount > 1)
                                                itemCount -= 1
                                        },
                                        onPlus = {
                                            if (itemCount < 5)
                                                itemCount += 1
                                        }
                                    )
                                }

                                LazyVerticalGrid(
                                    modifier = Modifier
                                        .padding(top = 24.dp)
                                        .heightIn(max = 150.dp),
                                    columns = GridCells.Fixed(2)
                                ) {
                                    item {
                                        ComposeButton(text = "BUY NOW") {

                                        }
                                    }
                                    item {
                                        ComposeButton(text = "ADD TO CART", bordered = true) {

                                        }
                                    }
                                }
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

            // TODO: Get these links from the product's property in the new API
            val images = remember {
                mutableListOf(
                    product?.image, product?.image, product?.image
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

@Composable
fun SelectedColorIndicator(selected: Boolean = false, color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .clip(shape = RoundedCornerShape(2.dp))
            .size(if (selected) 34.dp else 30.dp)
            .background(color)
            .border(
                width = (0.25).dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(2.dp)
            )
            .border(width = 2.dp, color = color, shape = RoundedCornerShape(2.dp))
            .border(
                width = 4.dp,
                color = if (selected) Color.White else color,
                shape = RoundedCornerShape(2.dp)
            )
            .clickable {
                onClick()
            }
    )
}

@Composable
fun SelectedSizeIndicator(size: String, selected: Boolean, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick() }
            .clip(shape = RoundedCornerShape(2.dp))
            .size(if (selected) 34.dp else 30.dp)
            .background(Color(0XFFFBEAE9))
            .border(width = 1.dp, color = if (selected) Color(0XFF4F4F4F) else Color.Transparent)
    ) {
        Text(
            text = size,
            fontWeight = FontWeight.SemiBold,
            color = Color(0XFF4F4F4F)
        )
    }
}

@Composable
private fun ComposeButton(text: String, bordered: Boolean = false, onClick: () -> Unit) {
    Box(
        contentAlignment = Center,
        modifier = Modifier
            .padding(12.dp)
            .border(
                width = 1.dp,
                color = if (bordered) colorPrimary else Color.Transparent,
                shape = RoundedCornerShape(4.dp)
            )
            .clip(RoundedCornerShape(4.dp))
            .background(if (bordered) Color.White else colorPrimary)
            .height(48.dp)
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold,
            color = if (bordered) colorPrimary else Color.White
        )
    }
}

@Composable
fun ItemCountIndicator(onMinus: () -> Unit, onPlus: () -> Unit, items: Int = 1, max: Int = 5) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(100))
            .width(150.dp)
            .background(Color(0XFFF6F6F6))
            .padding(4.dp)
    ) {
        Row(
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(contentAlignment = Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(36.dp)
                    .background(Color(0XFFFBEAE9))
                    .clickable {
                        onMinus()
                    }
            ) {
                Text(
                    text = "-",
                    fontSize = 24.sp,
                    color = if (items == 1) Color.LightGray else colorPrimary,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = "$items",
                fontSize = 24.sp,
                color = Color(0XFF6D6D6D),
                fontWeight = FontWeight.SemiBold
            )

            Box(contentAlignment = Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(36.dp)
                    .background(Color(0XFFFBEAE9))
                    .clickable {
                        onPlus()
                    }
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = "+",
                    color = if (items < max) colorPrimary else Color.LightGray,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun ItemCounterPreview() {
    OneTapTheme {
        ItemCountIndicator(
            items = 5,
            onMinus = {

            }, onPlus = {

            })
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeButtonPreview() {
    OneTapTheme {
        LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
            item {
                ComposeButton(text = "BUY NOW") {

                }
            }
            item {
                ComposeButton(text = "ADD TO CART", bordered = true) {

                }
            }
        })
    }
}

@Preview(showBackground = true)
@Composable
fun SelectedSizeIndicatorPreview() {
    OneTapTheme {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SelectedSizeIndicator(size = "S", selected = true) {}
            SelectedSizeIndicator(size = "M", selected = false) {}
            SelectedSizeIndicator(size = "L", selected = false) {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColorIndicatorPreview() {
    OneTapTheme {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SelectedColorIndicator(selected = true, color = Color.Red) {}
            SelectedColorIndicator(selected = false, color = Color.Green) {}
            SelectedColorIndicator(selected = false, color = Color.Blue) {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RatingBarPreview() {
    OneTapTheme {
        RatingBarCompose(value = 3.0F, totalStars = 5)
    }
}
