package com.yyds.gdhome

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {
    lateinit var behavior: BottomSheetBehavior<RelativeLayout>
    var halfExpandedRatio = 0.3f
    var peekRatio = 0.1f
    var mSlideOffset = 0f
    var lastNewState = BottomSheetBehavior.STATE_EXPANDED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        behavior = BottomSheetBehavior.from(findViewById(R.id.fraBottomSheet))
        behavior.isHideable = false
        behavior.peekHeight = (getScreenHeight() * peekRatio).toInt()
        behavior.halfExpandedRatio = halfExpandedRatio
        behavior.state = BottomSheetBehavior.STATE_EXPANDED

        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //3 STATE_EXPANDED 4  STATE_COLLAPSED 5 STATE_HIDDEN
                Log.d("Swt", "newState" + newState)
                if (newState == BottomSheetBehavior.STATE_SETTLING) {
                    if (lastNewState == BottomSheetBehavior.STATE_EXPANDED) {
                        if (mSlideOffset < 1f) {
                            behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                            lastNewState = BottomSheetBehavior.STATE_HALF_EXPANDED
                        }
                        return
                    }

                    if (lastNewState == BottomSheetBehavior.STATE_HALF_EXPANDED) {
                        if (mSlideOffset > halfExpandedRatio) {
                            behavior.state = BottomSheetBehavior.STATE_EXPANDED
                            lastNewState = BottomSheetBehavior.STATE_EXPANDED

                        }
                        if (mSlideOffset < halfExpandedRatio) {
                            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                            lastNewState = BottomSheetBehavior.STATE_COLLAPSED
                        }
                        return
                    }
                    if (lastNewState == BottomSheetBehavior.STATE_COLLAPSED && mSlideOffset > peekRatio) {
                        behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                        lastNewState = BottomSheetBehavior.STATE_HALF_EXPANDED
                        return
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("Swt", "slideOffset" + slideOffset)
                mSlideOffset = slideOffset
            }

        })
    }

    private fun getScreenHeight(): Int {
        val metric = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metric)
        return metric.heightPixels
    }

    fun testClick(view: View) {
        Toast.makeText(this, "我响应了", Toast.LENGTH_SHORT).show()
    }
}