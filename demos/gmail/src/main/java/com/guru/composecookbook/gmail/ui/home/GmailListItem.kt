package com.guru.composecookbook.gmail.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.guru.composecookbook.data.DemoDataProvider
import com.guru.composecookbook.data.model.Tweet

@Composable
fun GmailListItem(item: Tweet, clickListener: (Tweet) -> Unit) {

  ConstraintLayout(
    modifier =
      Modifier.background(MaterialTheme.colorScheme.background)
        .fillMaxWidth()
        .clickable { clickListener(item) }
        .padding(horizontal = 12.dp, vertical = 4.dp)
  ) {
    var stared by remember(item.id) { mutableStateOf(false) }
    val (image, title, subtitle, source, starButton, time) = createRefs()
    createVerticalChain(title, subtitle, source, chainStyle = ChainStyle.Packed)

    Image(
      painter = painterResource(id = item.authorImageId),
      contentScale = ContentScale.Crop,
      contentDescription = null,
      modifier =
        Modifier.size(50.dp).clip(CircleShape).constrainAs(image) {
          linkTo(start = parent.start, end = title.start)
          top.linkTo(title.top)
        }
    )
    Text(
      text = item.author,
      style = MaterialTheme.typography.titleLarge,
      modifier =
        Modifier.constrainAs(title) {
          start.linkTo(image.end, 16.dp)
          width = Dimension.fillToConstraints
        }
    )
    Text(
      text = "5 sept",
      style = MaterialTheme.typography.titleLarge.copy(fontSize = 12.sp),
      modifier =
        Modifier.constrainAs(time) {
          end.linkTo(parent.end, margin = 8.dp)
          top.linkTo(title.top)
        }
    )
    Text(
      text = item.source,
      style = MaterialTheme.typography.titleLarge.copy(fontSize = 14.sp),
      modifier =
        Modifier.constrainAs(subtitle) {
          start.linkTo(image.end, 16.dp)
          width = Dimension.fillToConstraints
        }
    )
    Text(
      text = item.text,
      style = MaterialTheme.typography.bodySmall,
      maxLines = 2,
      modifier =
        Modifier.constrainAs(source) {
            linkTo(start = title.start, end = starButton.start)
            width = Dimension.fillToConstraints
          }
          .alpha(0.6f)
    )
    IconButton(
      onClick = { stared = !stared },
      modifier =
        Modifier.constrainAs(starButton) {
          linkTo(
            top = source.top,
            bottom = source.bottom,
          )
          linkTo(start = source.end, end = parent.end)
        }
    ) {
      Icon(
        imageVector = if (stared) Icons.Default.Star else Icons.Default.StarBorder,
        contentDescription = null,
        tint = if (stared) Color.Yellow else MaterialTheme.colorScheme.onSurface
      )
    }
  }
}

@Composable
fun GmailListActionItems(modifier: Modifier) {
  Row(horizontalArrangement = Arrangement.End, modifier = modifier) {
    IconButton(onClick = {}) {
      Icon(
        imageVector = Icons.Default.Delete,
        tint = MaterialTheme.colorScheme.onPrimary,
        contentDescription = null
      )
    }
    IconButton(onClick = {}) {
      Icon(
        imageVector = Icons.Default.AccountBox,
        tint = MaterialTheme.colorScheme.onPrimary,
        contentDescription = null
      )
    }
  }
}

@Composable
@Preview
fun PreviewGmailItem() {
  val item = DemoDataProvider.tweet
  GmailListItem(item = item) {}
}
