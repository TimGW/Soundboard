package com.mittylabs.soundboard

import android.graphics.Color
import kotlin.random.Random


object ColorUtil {
    private val random = Random(System.currentTimeMillis())

    fun generateRandomColor(): Int {
        // This is the base color which will be mixed with the generated one
        val baseColor: Int = Color.WHITE
        val baseRed: Int = Color.red(baseColor)
        val baseGreen: Int = Color.green(baseColor)
        val baseBlue: Int = Color.blue(baseColor)
        val red = (baseRed + random.nextInt(256)) / 2
        val green = (baseGreen + random.nextInt(256)) / 2
        val blue = (baseBlue + random.nextInt(256)) / 2
        return Color.rgb(red, green, blue)
    }
}