package org.jlr.mab.mutable

import org.jlr.tail
import kotlin.math.exp
import kotlin.random.Random

class Softmax(protected val numArms: Int,
              protected val temperature: Float,
              protected val random: Random = Random.Default) : MultiArmedBandit {

    protected var selectionCountArms = IntArray(numArms)
    protected var rewardMeans = FloatArray(numArms)

    /**
     * Resets this mutable Epsilon/Greedy MAB instance.
     *
     * The internal metrics will be reset, although the number of arms
     * and epsilon remains the same.
     *
     * The Random instance is not reset and hence different results
     * will be achieved even by passing identical results to this
     * MAB after a [reset].
     */
    override fun reset() {
        selectionCountArms = IntArray(numArms)
        rewardMeans = FloatArray(numArms)
    }

    /**
     * Select an arms on either an exploitation or exploratory basis.
     *
     * The exploratory mode will be selected based on a probability
     * of the provide temperature parameter.
     *
     * On an exploitation mode, the default arm will be selected when
     * no arms were yet rewarded as the best one.
     *
     * @return The selected arm index.
     */
    override fun selectArm(): Int {
        val sum = rewardMeans.fold(0f) { sum, r -> sum + exp(r / temperature) }
        val probabilities: List<Float> = rewardMeans.map { exp(it / temperature) / sum }
        return categoricalDraw(0f, 0, probabilities, Random.nextFloat())
    }

    /**
     * Rewards the selected arm with the provided float.
     *
     * The rewards will be aggregated to previous results
     * based on a running average.
     *
     * @param selectedArm The arm to reward.
     * @param reward The reward to award as a float.
     */
    override fun update(selectedArm: Int, reward: Float) {
        if (selectedArm < 0 || selectedArm >= numArms) {
            throw IllegalArgumentException("The selected arm is outbound to the $numArms registered number of arms.")
        }

        val countArm = selectionCountArms[selectedArm] + 1
        selectionCountArms[selectedArm] = countArm

        val oldReward = rewardMeans[selectedArm]
        val newReward = ((countArm - 1) / countArm.toFloat()) * oldReward + (1 / countArm.toFloat()) * reward
        rewardMeans[selectedArm] = newReward
    }

    private tailrec fun categoricalDraw(cumProb: Float, currentIdx: Int, probabilities: List<Float>, z: Float): Int {
        return when {
            probabilities.size == 1 -> {
                currentIdx
            }
            cumProb + probabilities.first() > z -> {
                currentIdx
            }
            else -> {
                categoricalDraw(cumProb + probabilities.first(), currentIdx + 1, probabilities.tail(), z)
            }
        }
    }

}