package com.baleit.footballmatchschedule.anim

class MyBounceInterpolator

(amplitude: Double, frequency: Double) : android.view.animation.Interpolator {

    internal var mAmplitude = 1.0

    internal var mFrequency = 10.0

    init {
        mAmplitude = amplitude
        mFrequency = frequency
    }

    override fun getInterpolation(time: Float): Float {
        var amplitude = mAmplitude
        if (amplitude == 0.0) {
            amplitude = 0.05
        }
        return (-1.0 * Math.pow(Math.E, -time / mAmplitude) * Math.cos(mFrequency * time) + 1).toFloat()
    }
}
