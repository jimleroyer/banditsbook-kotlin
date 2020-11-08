package org.jlr.mab.mutable

/**
 * Contract for a MAB API.
 */
interface MultiArmedBandit {

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
    fun reset()

    /**
     * Select an arms on either an exploitation or exploratory basis.
     *
     * The exploratory mode will be selected based on a probability
     * of the provide epsilon parameter.
     *
     * On an exploitation mode, the default arm will be selected when
     * no arms were yet rewarded as the best one.
     *
     * @return The selected arm index.
     */
    fun selectArm(): Int

    /**
     * Rewards the selected arm with the provided float.
     *
     * The rewards will be aggregated to previous results
     * based on a running average.
     *
     * @param selectedArm The arm to reward.
     * @param reward The reward to award as a float.
     */
    fun update(selectedArm: Int, reward: Float)

}