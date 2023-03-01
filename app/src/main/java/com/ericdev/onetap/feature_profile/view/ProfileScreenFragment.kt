package com.ericdev.onetap.feature_profile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ericdev.onetap.R
import com.ericdev.onetap.compose.ui.theme.OneTapTheme
import com.ericdev.onetap.compose.ui.theme.colorPrimary
import com.ericdev.onetap.feature_products.presentation.view.ProfileImage
import com.ericdev.onetap.feature_profile.composables.NavigationRowItem
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Suppress("DEPRECATION") // 🖕🏿
class ProfileScreenFragment : Fragment() {
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
                        modifier = Modifier.padding(top = 30.dp),
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(
                                        text = "Account",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontFamily = FontFamily.Monospace,
                                        style = MaterialTheme.typography.h6,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Start
                                    )
                                },
                                navigationIcon = {
                                    IconButton(
                                        onClick = { findNavController().navigate(R.id.action_profileScreenFragment_to_productsFragment)},
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .background(Color.White.copy(alpha = 0.24f))
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                },
                                actions = {
                                    IconButton(
                                        onClick = { /*TODO*/ },
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .background(Color.White.copy(alpha = 0.24f))
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = null
                                        )
                                    }
                                }
                            )
                        }
                    ) { paddingValues ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                                .padding(top = paddingValues.calculateTopPadding())
                        ) {
                            Spacer(modifier = Modifier.height(8.dp))
                            ProfileSection {
                                findNavController().navigate(R.id.action_profileScreenFragment_to_profileImageFragment)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = "Account")
                            Spacer(modifier = Modifier.height(8.dp))
                            ActionList()
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { /*TODO*/ },
                                modifier = Modifier.clip(CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Logout,
                                    contentDescription = "Log Out"
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "logout")
                            }

                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ProfileSection(
    userName: String,
    email: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            val link2 =
                "https://www.vibe.com/wp-content/uploads/2017/09/XXXTentacion-mugshot-orange-county-jail-1504911983-640x5601-1505432825.jpg?w=640&h=511&crop=1"
            ProfileImage(link = link2, onClick = { /*TODO*/ })
            Spacer(modifier = Modifier.width(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(verticalArrangement = Arrangement.Center) {
                    Text(
                        text = userName,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Monospace,
                        style = MaterialTheme.typography.subtitle2,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = email,
                        fontWeight = FontWeight.Light,
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_qr_code_2_24),
                        contentDescription = "Qr code"
                    )
                }
            }
        }
    }
}


@Composable
fun ActionList() {
    Column() {
        NavigationRowItem(text = "Orders", leadingIcon = Icons.Default.Article) {

        }
        NavigationRowItem(
            text = "Returns",
            leadingIcon = Icons.Default.RemoveShoppingCart
        ) {

        }
        NavigationRowItem(text = "Wishlist", leadingIcon = Icons.Default.FactCheck) {

        }
        NavigationRowItem(text = "Addresses", leadingIcon = Icons.Default.PinDrop) {

        }
        NavigationRowItem(text = "Payment", leadingIcon = Icons.Default.Payment) {

        }
        NavigationRowItem(text = "Wallet", leadingIcon = Icons.Default.Wallet) {

        }
    }
}

@Composable
fun ProfileSection(
    onClick: ()-> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val link2 =
            "https://www.vibe.com/wp-content/uploads/2017/09/XXXTentacion-mugshot-orange-county-jail-1504911983-640x5601-1505432825.jpg?w=640&h=511&crop=1"
        val borderColors =
            listOf(Color(0XFFE2AF89), Color(0XFFC99E59), Color(0XFF111111))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(link2)
                .crossfade(durationMillis = 250)
                .build(),
            placeholder = painterResource(R.drawable.ic_person),
            error = painterResource(R.drawable.ic_broken_image),
            fallback = painterResource(R.drawable.ic_person),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(end = 4.dp)
                .size(100.dp)
                .border(
                    width = (1).dp,
                    brush = Brush.linearGradient(
                        colors = borderColors,
                        tileMode = TileMode.Decal
                    ),
                    shape = CircleShape
                )
                .border(width = 2.dp, color = Color.White, shape = CircleShape)
                .clip(CircleShape)
                .clickable {
                    onClick()
                }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 3.dp)
            ) {
                Text(
                    text = "Hi, Manu",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = "abc@domain.com",
                    fontWeight = FontWeight.Light,
                    fontFamily = FontFamily.Monospace,
                    style = MaterialTheme.typography.body1
                )
            }
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.clip(CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_qr_code_2_24),
                    contentDescription = "Qr code",
                    modifier = Modifier.size(40.dp)
                )
            }
        }
    }
}
