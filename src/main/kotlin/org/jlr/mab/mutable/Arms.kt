package org.jlr.mab.mutable

import kotlin.random.Random

class BernouilliArm(val probablity: Float,
                    private val random: Random = Random.Default) {

    fun draw(): Float {
        return if (random.nextFloat() > probablity) 0.0f
        else 1.0f
    }

}
