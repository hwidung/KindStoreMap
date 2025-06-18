package com.konkuk.kindmap.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.konkuk.kindmap.R
import com.konkuk.kindmap.ui.theme.KindMapTheme
import kotlinx.coroutines.delay

@Composable
fun FAB(
    modifier: Modifier = Modifier,
    onMagazineClick: () -> Unit,
    onRankingClick: () -> Unit,
    onReviewClick: () -> Unit,
) {
    var showFABAll by remember { mutableStateOf(false) }

    var showReview by remember { mutableStateOf(false) }
    var showRanking by remember { mutableStateOf(false) }
    var showMagazine by remember { mutableStateOf(false) }

    // 순차 애니메이션
    LaunchedEffect(showFABAll) {
        if (showFABAll) {
            showReview = true
            delay(80)
            showRanking = true
            delay(80)
            showMagazine = true
        } else {
            showMagazine = false
            delay(50)
            showRanking = false
            delay(50)
            showReview = false
        }
    }

    val fabIcon = if (showFABAll) Icons.Default.Clear else Icons.Default.KeyboardArrowUp

    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.End,
        ) {
            AnimatedVisibility(
                visible = showReview,
                enter =
                    fadeIn(tween(200)) +
                        slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec = tween(300),
                        ),
                exit =
                    fadeOut(tween(150)) +
                        slideOutVertically(
                            targetOffsetY = { it / 2 },
                            animationSpec = tween(200),
                        ),
            ) {
                FABItem(label = "리뷰", iconRes = R.drawable.ic_review, onClick = onReviewClick)
            }

            AnimatedVisibility(
                visible = showRanking,
                enter =
                    fadeIn(tween(200)) +
                        slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec = tween(300),
                        ),
                exit =
                    fadeOut(tween(150)) +
                        slideOutVertically(
                            targetOffsetY = { it / 2 },
                            animationSpec = tween(200),
                        ),
            ) {
                FABItem(label = "랭킹", iconRes = R.drawable.ic_ranking, onClick = onRankingClick)
            }

            AnimatedVisibility(
                visible = showMagazine,
                enter =
                    fadeIn(tween(200)) +
                        slideInVertically(
                            initialOffsetY = { it / 2 },
                            animationSpec = tween(300),
                        ),
                exit =
                    fadeOut(tween(150)) +
                        slideOutVertically(
                            targetOffsetY = { it / 2 },
                            animationSpec = tween(200),
                        ),
            ) {
                FABItem(label = "매거진", iconRes = R.drawable.ic_magazine, onClick = onMagazineClick)
            }

            FloatingActionButton(
                onClick = { showFABAll = !showFABAll },
                shape = CircleShape,
                containerColor = KindMapTheme.colors.orange,
                elevation = FloatingActionButtonDefaults.elevation(),
            ) {
                Icon(
                    imageVector = fabIcon,
                    tint = KindMapTheme.colors.black,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
fun FABItem(
    label: String,
    iconRes: Int,
    onClick: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = label,
            color = KindMapTheme.colors.black,
            style = KindMapTheme.typography.body_r_14,
        )
        Spacer(modifier = Modifier.width(5.dp))
        FloatingActionButton(
            onClick = onClick,
            shape = CircleShape,
            containerColor = KindMapTheme.colors.white,
            elevation = FloatingActionButtonDefaults.elevation(),
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                tint = KindMapTheme.colors.black,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
            )
        }
    }
}
