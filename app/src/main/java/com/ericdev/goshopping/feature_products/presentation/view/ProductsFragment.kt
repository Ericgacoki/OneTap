package com.ericdev.goshopping.feature_products.presentation.view

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ericdev.goshopping.R
import com.ericdev.goshopping.compose.ui.theme.GoShoppingTheme
import com.ericdev.goshopping.compose.ui.theme.colorInactive
import com.ericdev.goshopping.compose.ui.theme.colorPrimary
import com.ericdev.goshopping.compose.ui.theme.nunitoFontFamily
import com.ericdev.goshopping.core.data.remote.dto.temp.Rating
import com.ericdev.goshopping.core.data.remote.dto.temp.TempProductDtoResultItem
import com.ericdev.goshopping.feature_products.presentation.viewmodel.ProductsViewModel
import com.ericdev.goshopping.util.Resource
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.compose.ui.graphics.Color as ComposeColor

@Suppress("DEPRECATION")
class ProductsFragment : Fragment() {
    private val viewModel: ProductsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().window.statusBarColor = Color.parseColor("#E2615B")
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE

        return ComposeView(requireContext()).apply {

            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                GoShoppingTheme {

                    val systemUiController = rememberSystemUiController()
                    val useDarkIcons = false

                    DisposableEffect(systemUiController, useDarkIcons) {
                        systemUiController.setStatusBarColor(
                            color = colorPrimary,
                            darkIcons = useDarkIcons
                        )

                        onDispose {}
                    }

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        backgroundColor = ComposeColor(0xFFF7F7F7),
                        topBar = {
                            AnimatedVisibility(visible = true) {
                                Row(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = CenterVertically,
                                    horizontalArrangement = SpaceBetween
                                ) {
                                    SearchBarCompose(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(100))
                                            .fillMaxWidth(.80F)
                                            .background(ComposeColor.White),
                                        onSearchParamChange = {
                                            // TODO("Update value in viewModel")
                                        },
                                        onSearchClick = {
                                            // TODO("Perform search event")
                                        })

                                    // TODO: Replace link with actual user image link
                                    val link1 =
                                        "https://upload.wikimedia.org/wikipedia/commons/thumb/3/38/XXXTENTACION_mugshot_12_28_2016.jpg/800px-XXXTENTACION_mugshot_12_28_2016.jpg"
                                    val link2 =
                                        "https://www.vibe.com/wp-content/uploads/2017/09/XXXTentacion-mugshot-orange-county-jail-1504911983-640x5601-1505432825.jpg?w=640&h=511&crop=1"
                                    ProfileImage(link2) {
                                        // TODO: Navigate to User profile
                                    }
                                }
                            }
                        },
                        content = {
                            val productsState = viewModel.tempProductsStateFlow.collectAsState()

                            when (productsState.value) {
                                is Resource.Loading -> {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Center
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_bag_icon),
                                            contentDescription = null
                                        )
                                        CircularProgressIndicator()
                                    }
                                }
                                is Resource.Success -> {
                                    val featuredProducts: ArrayList<TempProductDtoResultItem> =
                                        arrayListOf()
                                    for (i in 1..3) {
                                        featuredProducts.add(productsState.value.data!!.random())
                                    }
                                    // TODO: Remove this demo after implementing the NEW API
                                    data class DemoCategory(val id: Int, val title: String)

                                    val demoCategories = listOf(
                                        DemoCategory(0, "All"),
                                        DemoCategory(12, "Men"),
                                        DemoCategory(1, "Women"),
                                        DemoCategory(2, "Kids"),
                                        DemoCategory(3, "Jewelery"),
                                        DemoCategory(4, "Electronics"),
                                        DemoCategory(5, "Household"),
                                        DemoCategory(6, "Jewelery 2"),
                                        DemoCategory(7, "Electronics 2"),
                                        DemoCategory(8, "Household 2"),
                                        DemoCategory(9, "Jewelery 2"),
                                        DemoCategory(10, "Electronics 2"),
                                        DemoCategory(11, "Household 2"),
                                    )
                                    var demoSelectedCategoryId by remember {
                                        mutableStateOf(0)
                                    }

                                    Column(
                                        modifier = Modifier
                                            .padding(it)
                                            .fillMaxSize()
                                    ) {
                                        LazyVerticalGrid(
                                            modifier = Modifier.fillMaxSize(),
                                            columns = GridCells.Fixed(2),
                                            state = rememberLazyGridState()
                                        ) {
                                            item(span = { GridItemSpan(2) }) {
                                                FeaturedProductsViewPager(featuredProducts){ product ->
                                                    viewModel.selectedProductOnNavigation = product
                                                    findNavController().navigate(R.id.action_productsFragment_to_productDetailsFragment)
                                                }
                                            }

                                            item(span = { GridItemSpan(2) }) {
                                                LazyRow(
                                                    modifier = Modifier
                                                        .padding(8.dp)
                                                        .fillMaxWidth()
                                                ) {
                                                    items(demoCategories) { category ->
                                                        ProductCategory(
                                                            selected = demoSelectedCategoryId == category.id,
                                                            icon = null,
                                                            title = category.title
                                                        ) {
                                                            if (demoSelectedCategoryId != category.id) {
                                                                demoSelectedCategoryId = category.id
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            items(
                                                productsState.value.data ?: emptyList()
                                            ) { product ->

                                                var tempFav by remember { mutableStateOf(false) }

                                                ProductItem(
                                                    product = product,
                                                    isFavorite = tempFav,
                                                    onFavoriteIconClick = {
                                                        // TODO: Add or Remove from favorites
                                                        tempFav = tempFav.not()
                                                    }) {
                                                    viewModel.selectedProductOnNavigation = product
                                                    findNavController().navigate(R.id.action_productsFragment_to_productDetailsFragment)
                                                }
                                            }
                                            item(span = { GridItemSpan(2) }) {
                                                Spacer(modifier = Modifier.height(50.dp))
                                            }
                                        }
                                    }
                                }
                                is Resource.Error -> {
                                    Box(
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .fillMaxSize(),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        Column(
                                            horizontalAlignment = CenterHorizontally
                                        ) {
                                            val firstChar =
                                                productsState.value.message!![0].uppercaseChar()
                                                    .toString()

                                            Text(
                                                text = productsState.value.message!!.replaceFirst(
                                                    firstChar, firstChar, true
                                                ),
                                                color = ComposeColor.Gray,
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.h6
                                            )

                                            Spacer(modifier = Modifier.height(12.dp))

                                            Button(onClick = {
                                                viewModel.getTempProducts()
                                            }) {
                                                Text(
                                                    text = "RETRY",
                                                    style = MaterialTheme.typography.button
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FeaturedProductsViewPager(
    featuredProducts: ArrayList<TempProductDtoResultItem>,
    onNavigate: (product: TempProductDtoResultItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        val pagerState = rememberPagerState()

        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            count = featuredProducts.size,
            state = pagerState
        ) { currentPage ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(featuredProducts[currentPage].image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_bag_icon),
                error = painterResource(R.drawable.ic_broken_image),
                fallback = painterResource(R.drawable.ic_bag_icon_large),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(12))
                    .fillMaxWidth()
                    .height(180.dp)
                    .aspectRatio(1F)
                    .clickable {
                       onNavigate(featuredProducts[currentPage])
                    }
            )
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(top = 16.dp),
            activeColor = colorPrimary,
            inactiveColor = colorInactive
        )
    }
}

@Composable
fun SearchBarCompose(
    modifier: Modifier = Modifier
        .clip(RoundedCornerShape(100))
        // .fillMaxWidth()
        .background(
            if (isSystemInDarkTheme())
                ComposeColor.White.copy(alpha = .24F) else
                ComposeColor.Black.copy(alpha = .24F)
        ),
    hint: String = "Search",
    onSearchParamChange: (String) -> Unit,
    onSearchClick: (String) -> Unit
) {
    Box(modifier = modifier.height(54.dp)) {
        var searchParam: String by remember { mutableStateOf("") }

        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        TextField(
            value = searchParam,
            onValueChange = { newValue ->
                searchParam = if (newValue.trim().isNotEmpty()) newValue else ""
                onSearchParamChange(newValue)
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester = focusRequester),
            singleLine = true,
            placeholder = {
                Text(text = hint)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = androidx.compose.ui.graphics.Color.Transparent,
                focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
                unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
            ), keyboardOptions = KeyboardOptions(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClick(searchParam)
                    focusManager.clearFocus()
                }
            ),
            trailingIcon = {
                Row {
                    AnimatedVisibility(visible = searchParam.trim().isNotEmpty()) {
                        IconButton(onClick = {
                            focusManager.clearFocus()
                            searchParam = ""
                            onSearchParamChange(searchParam)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = null
                            )
                        }
                    }

                    IconButton(onClick = {
                        onSearchClick(searchParam)
                        focusManager.clearFocus()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun ProfileImage(link: String?, onClick: () -> Unit) {
    // TODO: Get these colors from the image
    val borderColors =
        listOf(ComposeColor(0XFFE2AF89), ComposeColor(0XFFC99E59), ComposeColor(0XFF111111))

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(link)
            .crossfade(durationMillis = 250)
            .build(),
        placeholder = painterResource(R.drawable.ic_person),
        error = painterResource(R.drawable.ic_broken_image),
        fallback = painterResource(R.drawable.ic_person),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(end = 4.dp)
            .size(48.dp)
            .border(
                width = (1).dp,
                brush = Brush.linearGradient(
                    colors = borderColors,
                    tileMode = TileMode.Decal
                ),
                shape = CircleShape
            )
            .border(width = 2.dp, color = ComposeColor.White, shape = CircleShape)
            .clip(CircleShape)
            .clickable {
                onClick()
            }
    )
}

@Composable
fun ProductCategory(selected: Boolean, icon: Any?, title: String, onClick: () -> Unit) {
    Box(contentAlignment = Center, modifier = Modifier.padding(horizontal = 4.dp)) {
        Column(horizontalAlignment = CenterHorizontally) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(icon)
                    .crossfade(durationMillis = 250)
                    .build(),
                placeholder = painterResource(R.drawable.ic_bag_icon),
                error = painterResource(R.drawable.ic_broken_image),
                fallback = painterResource(R.drawable.ic_person),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                colorFilter = ColorFilter.tint(
                    if (selected) colorPrimary else
                        ComposeColor(0XFF6D6D6D)
                ),
                modifier = Modifier
                    .size(46.dp)
                    .border(
                        width = (1).dp,
                        color = if (selected) colorPrimary else ComposeColor(0xCCFBEAE9),
                        shape = CircleShape
                    )
                    .border(width = 2.dp, color = ComposeColor.White, shape = CircleShape)
                    .clip(CircleShape)
                    .background(if (selected) ComposeColor(0XFFFBEAE9) else ComposeColor(0XFFD9D9D9))
                    .clickable {
                        onClick()
                    }
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = title.take(12) + if (title.length > 12) "..." else "",
                color = if (selected) colorPrimary else ComposeColor.Gray,
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
            )
        }
    }
}

@Composable
fun ProductItem(
    product: TempProductDtoResultItem,
    isFavorite: Boolean,
    onFavoriteIconClick: () -> Unit,
    onProductClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(ComposeColor.White)
            .fillMaxSize()
            .clickable {
                onProductClick()
            },
        contentAlignment = Center
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.image)
                    .crossfade(durationMillis = 250)
                    .build(),
                placeholder = painterResource(R.drawable.ic_bag_icon_large),
                error = painterResource(R.drawable.ic_broken_image),
                fallback = painterResource(R.drawable.ic_bag_icon_large),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier.height(170.dp)
            )

            Text(
                modifier = Modifier
                    .padding(start = 4.dp, top = 16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                text = product.title ?: "Product Name",
                style = MaterialTheme.typography.body1,
                maxLines = 1,
                fontWeight = FontWeight.SemiBold,
                fontFamily = nunitoFontFamily,
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 2.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = ComposeColor.Gray,
                text = "3 Colors",
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                fontFamily = nunitoFontFamily
            )

            Row(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .fillMaxWidth(),
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100))
                        .background(ComposeColor(0XFFF6F6F6))
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    contentAlignment = Center
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 2.dp, horizontal = 4.dp),
                        text = "$ ${product.price.toString()}",
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        fontFamily = nunitoFontFamily,
                    )
                }

                Icon(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
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
                    tint = if (isFavorite) colorPrimary else ComposeColor(0XFFD7D7D7),
                    contentDescription = null,
                )
            }
        }
    }
}

// TODO: Remove previews before production
@Preview
@Composable
fun ProductItemPreview() {
    val dummyProduct = TempProductDtoResultItem(
        0,
        "Back Pack Pro",
        125.00,
        "Bag description here...",
        "Men",
        "link",
        Rating(4.0, 100)
    )

    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        item {
            ProductItem(product = dummyProduct, isFavorite = true, onFavoriteIconClick = {}) {}
        }
        item {
            ProductItem(product = dummyProduct, isFavorite = false, onFavoriteIconClick = {}) {

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryPrev() {
    GoShoppingTheme {
        LazyRow(horizontalArrangement = Arrangement.Start) {
            item {
                ProductCategory(selected = false, icon = null, title = "Men") {

                }
            }

            item {
                ProductCategory(selected = true, icon = null, title = "Jewelery") {

                }
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun ProfileImagePrev() {
    GoShoppingTheme {
        ProfileImage("") {

        }
    }
}
