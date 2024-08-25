package com.example.booktracker.presentation.component.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Visibility: ImageVector
    get() {
        if (_Visibility != null) {
            return _Visibility!!
        }
        _Visibility = ImageVector.Builder(
            name = "Visibility",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(480f, 640f)
                quadToRelative(75f, 0f, 127.5f, -52.5f)
                reflectiveQuadTo(660f, 460f)
                quadToRelative(0f, -75f, -52.5f, -127.5f)
                reflectiveQuadTo(480f, 280f)
                quadToRelative(-75f, 0f, -127.5f, 52.5f)
                reflectiveQuadTo(300f, 460f)
                quadToRelative(0f, 75f, 52.5f, 127.5f)
                reflectiveQuadTo(480f, 640f)
                close()
                moveToRelative(0f, -72f)
                quadToRelative(-45f, 0f, -76.5f, -31.5f)
                reflectiveQuadTo(372f, 460f)
                quadToRelative(0f, -45f, 31.5f, -76.5f)
                reflectiveQuadTo(480f, 352f)
                quadToRelative(45f, 0f, 76.5f, 31.5f)
                reflectiveQuadTo(588f, 460f)
                quadToRelative(0f, 45f, -31.5f, 76.5f)
                reflectiveQuadTo(480f, 568f)
                close()
                moveToRelative(0f, 192f)
                quadToRelative(-134f, 0f, -244.5f, -72f)
                reflectiveQuadTo(61f, 498f)
                quadToRelative(-5f, -9f, -7.5f, -18.5f)
                reflectiveQuadTo(51f, 460f)
                quadToRelative(0f, -10f, 2.5f, -19.5f)
                reflectiveQuadTo(61f, 422f)
                quadToRelative(64f, -118f, 174.5f, -190f)
                reflectiveQuadTo(480f, 160f)
                quadToRelative(134f, 0f, 244.5f, 72f)
                reflectiveQuadTo(899f, 422f)
                quadToRelative(5f, 9f, 7.5f, 18.5f)
                reflectiveQuadTo(909f, 460f)
                quadToRelative(0f, 10f, -2.5f, 19.5f)
                reflectiveQuadTo(899f, 498f)
                quadToRelative(-64f, 118f, -174.5f, 190f)
                reflectiveQuadTo(480f, 760f)
                close()
                moveToRelative(0f, -300f)
                close()
                moveToRelative(0f, 220f)
                quadToRelative(113f, 0f, 207.5f, -59.5f)
                reflectiveQuadTo(832f, 460f)
                quadToRelative(-50f, -101f, -144.5f, -160.5f)
                reflectiveQuadTo(480f, 240f)
                quadToRelative(-113f, 0f, -207.5f, 59.5f)
                reflectiveQuadTo(128f, 460f)
                quadToRelative(50f, 101f, 144.5f, 160.5f)
                reflectiveQuadTo(480f, 680f)
                close()
            }
        }.build()
        return _Visibility!!
    }

private var _Visibility: ImageVector? = null
