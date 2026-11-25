package com.youarelaunched.challenge.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.youarelaunched.challenge.R
import com.youarelaunched.challenge.network.models.NetworkCategory
import com.youarelaunched.challenge.network.models.NetworkTag
import com.youarelaunched.challenge.network.models.NetworkVendor
import com.youarelaunched.challenge.ui.theme.Colors
import com.youarelaunched.challenge.ui.theme.OpenSansFamily

@Composable
fun VendorCard(
    modifier: Modifier,
    vendor: NetworkVendor
) {
    Column(
        modifier
            .fillMaxWidth()
            .background(Colors.White)
    ) {
        Box(
            Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .clip(RoundedCornerShape(10.dp)),
                model = vendor.coverPhoto.mediaUrl,
                contentScale = ContentScale.FillWidth,
                contentDescription = "Image of a ${vendor.shopType}"
            )
            BookmarkBadge(
                modifier = Modifier.align(Alignment.TopEnd),
                status = vendor.favorite
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .absolutePadding(left = 8.dp, bottom = 8.dp)
                    .background(
                        color = Colors.White.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(vertical = 4.dp, horizontal = 8.dp),
            ) {
                Text(
                    fontFamily = OpenSansFamily,
                    letterSpacing = (-0.4).sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Colors.TextGrey,
                    text = vendor.areaServed
                )
            }
        }
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            fontFamily = OpenSansFamily,
            letterSpacing = (-0.4).sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Colors.TextGrey,
            text = vendor.companyName
        )

        Categories(vendor.categories ?: emptyList())

        Spacer(Modifier.height(8.dp))

        Tags(vendor.tags ?: emptyList())
    }
}

@Composable
private fun BookmarkBadge(modifier: Modifier, status: Boolean) {
    if(status) {
        Box(
            modifier = modifier
                .absolutePadding(right = 10.dp, top = 10.dp)
                .background(color = Colors.BookmarkGreen, shape = RoundedCornerShape(50))
                .padding(6.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.bookmark_filled),
                tint = Colors.White,
                contentDescription = "Bookmark Icon"
            )
        }
    } else {
        Box(
            modifier = modifier
                .absolutePadding(right = 10.dp, top = 10.dp)
                .background(color = Colors.White, shape = RoundedCornerShape(50))
                .padding(6.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.bookmark_border),
                tint = Colors.BookmarkGreen,
                contentDescription = "Bookmark Icon"
            )
        }
    }
}

@Composable
private fun Categories(categories: List<NetworkCategory>) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        categories.forEach { category ->
            Row(verticalAlignment = Alignment.CenterVertically) {

                AsyncImage(
                    modifier = Modifier.size(22.dp),
                    model = category.image.mediaUrl,
                    contentDescription = "Image of a ${category.name}"
                )

                Spacer(Modifier.width(6.dp))

                Text(
                    fontFamily = OpenSansFamily,
                    letterSpacing = (-0.4).sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Colors.TextGrey,
                    text = category.name
                )
            }
        }
    }
}

@Composable
private fun Tags(tags: List<NetworkTag>) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.forEach { tag ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(4.dp),
                    painter = painterResource(R.drawable.circle_filled),
                    tint = Colors.TextGreySecondary,
                    contentDescription = "Dot"
                )

                Spacer(Modifier.width(2.dp))

                Text(
                    fontFamily = OpenSansFamily,
                    letterSpacing = (-0.4).sp,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Colors.TextGreySecondary,
                    text = tag.name
                )
            }
        }
    }
}