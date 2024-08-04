package com.example.booktracker.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Composable
fun rememberVisibility(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "visibility",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(20f, 26.208f)
                quadToRelative(2.958f, 0f, 5f, -2.041f)
                quadToRelative(2.042f, -2.042f, 2.042f, -5f)
                quadToRelative(0f, -2.959f, -2.042f, -5f)
                quadToRelative(-2.042f, -2.042f, -5f, -2.042f)
                reflectiveQuadToRelative(-5f, 2.042f)
                quadToRelative(-2.042f, 2.041f, -2.042f, 5f)
                quadToRelative(0f, 2.958f, 2.042f, 5f)
                quadToRelative(2.042f, 2.041f, 5f, 2.041f)
                close()
                moveToRelative(0f, -2.5f)
                quadToRelative(-1.917f, 0f, -3.229f, -1.312f)
                quadToRelative(-1.313f, -1.313f, -1.313f, -3.229f)
                quadToRelative(0f, -1.917f, 1.313f, -3.229f)
                quadToRelative(1.312f, -1.313f, 3.229f, -1.313f)
                reflectiveQuadToRelative(3.229f, 1.313f)
                quadToRelative(1.313f, 1.312f, 1.313f, 3.229f)
                quadToRelative(0f, 1.916f, -1.313f, 3.229f)
                quadToRelative(-1.312f, 1.312f, -3.229f, 1.312f)
                close()
                moveToRelative(0f, 7.75f)
                quadToRelative(-5.667f, 0f, -10.312f, -3.041f)
                quadToRelative(-4.646f, -3.042f, -7.146f, -8.042f)
                quadToRelative(-0.125f, -0.25f, -0.188f, -0.563f)
                quadToRelative(-0.062f, -0.312f, -0.062f, -0.645f)
                quadToRelative(0f, -0.334f, 0.062f, -0.646f)
                quadToRelative(0.063f, -0.313f, 0.188f, -0.563f)
                quadToRelative(2.5f, -5f, 7.146f, -8.041f)
                quadTo(14.333f, 6.875f, 20f, 6.875f)
                reflectiveQuadToRelative(10.312f, 3.042f)
                quadToRelative(4.646f, 3.041f, 7.146f, 8.041f)
                quadToRelative(0.125f, 0.25f, 0.209f, 0.563f)
                quadToRelative(0.083f, 0.312f, 0.083f, 0.646f)
                quadToRelative(0f, 0.333f, -0.083f, 0.645f)
                quadToRelative(-0.084f, 0.313f, -0.209f, 0.563f)
                quadToRelative(-2.5f, 5f, -7.146f, 8.042f)
                quadTo(25.667f, 31.458f, 20f, 31.458f)
                close()
            }
        }.build()
    }
}
@Composable
fun rememberVisibilityOff(): ImageVector {
    return remember {
        ImageVector.Builder(
            name = "visibility_off",
            defaultWidth = 40.0.dp,
            defaultHeight = 40.0.dp,
            viewportWidth = 40.0f,
            viewportHeight = 40.0f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1f,
                stroke = null,
                strokeAlpha = 1f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(31.5f, 27.667f)
                lineToRelative(-5.25f, -5.25f)
                quadToRelative(0.375f, -0.667f, 0.583f, -1.521f)
                quadToRelative(0.209f, -0.854f, 0.209f, -1.729f)
                quadToRelative(0f, -2.959f, -2.042f, -5f)
                quadToRelative(-2.042f, -2.042f, -5f, -2.042f)
                quadToRelative(-0.875f, 0f, -1.708f, 0.187f)
                quadToRelative(-0.834f, 0.188f, -1.542f, 0.605f)
                lineToRelative(-4.417f, -4.459f)
                quadToRelative(1.5f, -0.666f, 3.625f, -1.125f)
                quadToRelative(2.125f, -0.458f, 4.167f, -0.458f)
                quadToRelative(5.625f, 0f, 10.271f, 3.021f)
                quadToRelative(4.646f, 3.021f, 7.104f, 8.062f)
                quadToRelative(0.125f, 0.25f, 0.188f, 0.563f)
                quadToRelative(0.062f, 0.312f, 0.062f, 0.646f)
                quadToRelative(0f, 0.333f, -0.062f, 0.666f)
                quadToRelative(-0.063f, 0.334f, -0.188f, 0.542f)
                quadToRelative(-1.042f, 2.208f, -2.562f, 4.021f)
                quadToRelative(-1.521f, 1.812f, -3.438f, 3.271f)
                close()
                moveToRelative(1.083f, 8.458f)
                lineToRelative(-5.916f, -5.833f)
                quadToRelative(-1.417f, 0.541f, -3.146f, 0.854f)
                quadToRelative(-1.729f, 0.312f, -3.521f, 0.312f)
                quadToRelative(-5.708f, 0f, -10.375f, -3.02f)
                quadToRelative(-4.667f, -3.021f, -7.125f, -8.063f)
                quadToRelative(-0.125f, -0.292f, -0.167f, -0.583f)
                quadToRelative(-0.041f, -0.292f, -0.041f, -0.625f)
                quadToRelative(0f, -0.334f, 0.062f, -0.667f)
                quadToRelative(0.063f, -0.333f, 0.146f, -0.583f)
                quadToRelative(0.875f, -1.792f, 2.167f, -3.479f)
                quadToRelative(1.291f, -1.688f, 2.958f, -3.146f)
                lineTo(3.583f, 7.167f)
                quadToRelative(-0.416f, -0.375f, -0.416f, -0.917f)
                reflectiveQuadToRelative(0.416f, -0.917f)
                quadToRelative(0.375f, -0.375f, 0.917f, -0.375f)
                reflectiveQuadToRelative(0.958f, 0.375f)
                lineToRelative(29f, 29f)
                quadToRelative(0.334f, 0.375f, 0.334f, 0.875f)
                reflectiveQuadToRelative(-0.375f, 0.917f)
                quadToRelative(-0.375f, 0.417f, -0.917f, 0.417f)
                reflectiveQuadToRelative(-0.917f, -0.417f)
                close()
                moveTo(20f, 26.208f)
                quadToRelative(0.542f, 0f, 1.167f, -0.083f)
                reflectiveQuadToRelative(1.083f, -0.292f)
                lineToRelative(-8.917f, -8.875f)
                quadToRelative(-0.208f, 0.5f, -0.291f, 1.063f)
                quadToRelative(-0.084f, 0.562f, -0.084f, 1.146f)
                quadToRelative(0f, 2.958f, 2.063f, 5f)
                quadToRelative(2.062f, 2.041f, 4.979f, 2.041f)
                close()
                moveToRelative(4.333f, -5.708f)
                lineToRelative(-5.666f, -5.667f)
                quadToRelative(2.5f, -0.791f, 4.521f, 1.063f)
                quadToRelative(2.02f, 1.854f, 1.145f, 4.604f)
                close()
            }
        }.build()
    }
}