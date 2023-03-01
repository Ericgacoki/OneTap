package com.ericdev.onetap.feature_profile.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ericdev.onetap.R
import com.ericdev.onetap.compose.ui.theme.OneTapTheme
import com.ericdev.onetap.compose.ui.theme.colorPrimary
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Suppress("DEPRECATION") // 🖕🏿
class ProfileImageFragment : Fragment() {

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
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val link2 =
                            "https://www.vibe.com/wp-content/uploads/2017/09/XXXTentacion-mugshot-orange-county-jail-1504911983-640x5601-1505432825.jpg?w=640&h=511&crop=1"

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(link2)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            placeholder = painterResource(R.drawable.ic_person),
                            error = painterResource(R.drawable.ic_broken_image),
                            fallback = painterResource(R.drawable.ic_person),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(4.dp)
                                .height(350.dp)
                                .fillMaxWidth()
                                .clip(MaterialTheme.shapes.large)
                        )
                    }
                }
            }
        }
    }
}