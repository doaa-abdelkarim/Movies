package com.example.movies.presentation.details.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.movies.R
import com.example.movies.domain.entities.Review
import com.example.movies.presentation.common.CustomSubcomposeAsyncImage
import com.example.movies.ui.theme.boldSize20White
import com.example.movies.ui.theme.gray700
import com.example.movies.ui.theme.grayLight
import com.example.movies.ui.theme.regularSize14White

@Composable
fun CellReview(review: Review) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = gray700)
            .padding(all = dimensionResource(R.dimen.spacing_normal)),
    ) {
        CustomSubcomposeAsyncImage(
            modifier = Modifier
                .width(dimensionResource(R.dimen.view_size_88dp))
                .height(dimensionResource(R.dimen.view_size_88dp)),
            data = review.avatarPath,
            contentDescription = stringResource(R.string.reviewer_photo),
            error = {
                Box(
                    modifier = Modifier
                        .size(dimensionResource(R.dimen.view_size_88dp))
                        .clip(CircleShape)
                        .background(color = grayLight)
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(R.drawable.ic_placeholder_author),
                        contentDescription = stringResource(R.string.reviewer_photo),
                    )
                }
            }
        )
        Column {
            Text(
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.spacing_normal),
                    top = dimensionResource(R.dimen.spacing_small)
                ),
                text = review.username ?: "-",
                style = boldSize20White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.spacing_normal),
                    top = dimensionResource(R.dimen.spacing_normal)
                ),
                text = review.content ?: "-",
                style = regularSize14White
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun CellReviewPreview() {
    CellReview(
        review = Review(
            movieId = 1,
            reviewId = "1",
            username = "User name",
            content = "Content"
        )
    )
}