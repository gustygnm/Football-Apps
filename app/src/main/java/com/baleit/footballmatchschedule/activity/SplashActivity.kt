package com.baleit.footballmatchschedule.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.baleit.footballmatchschedule.R
import com.baleit.footballmatchschedule.anim.MyBounceInterpolator


class SplashActivity : Activity() {

    var color: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        anim()
        val thread = object : Thread() {
            override fun run() {
                try {
                    Thread.sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
        thread.start()
    }

    fun anim() {
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)
        val animationDuration = 2.50 * 1000
        myAnim.duration = animationDuration.toLong()

        // Use custom animation interpolator to achieve the bounce effect
        val interpolator = MyBounceInterpolator(0.20, 20.00)

        myAnim.interpolator = interpolator

         var imageLogo: ImageView
        // Animate the button
        imageLogo = findViewById(R.id.imgLogo)
        imageLogo.startAnimation(myAnim)
    }

    override fun onBackPressed() {
        //
    }
}
