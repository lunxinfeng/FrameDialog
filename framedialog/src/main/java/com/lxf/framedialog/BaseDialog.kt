package com.lxf.framedialog

import android.app.DialogFragment
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.FrameLayout


abstract class BaseDialog : DialogFragment() {
    /**
     * dialog位置
     */
    private var mGravity = Gravity.CENTER
    /**
     * dialog是否可以触摸外部关闭
     */
    private var mCancelOnTouchOutSide = true
    /**
     * dialog是否可以点击返回键关闭
     */
    private var mCancelOnBack = true
    /**
     * dialog宽度占屏幕比率 0-1  负数表示不自定义
     */
    private var mWidth = -1.0f
    /**
     * dialog高度占屏幕比率 0-1  负数表示不自定义
     */
    private var mHeight = -1.0f
    /**
     * dialog进入动画
     */
    private var mAnimOnIn: Animation? = null
    /**
     * dialog退出动画
     */
    private var mAnimOnOut: Animation? = null
    /**
     * 对话框透明度 0-1
     */
    private var mAlpha = 1.0f

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = createView()

        view.alpha = mAlpha
        mAnimOnIn?.let { view.startAnimation(mAnimOnIn) }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isCustomSize()) {
            val dm = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(dm)
            val height = (dm.heightPixels * mHeight).toInt()
            val width = (dm.heightPixels * mWidth).toInt()
            view.layoutParams = FrameLayout.LayoutParams(width, height)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE,0)
    }

    protected abstract fun createView(): View

    private fun isCustomSize() = mWidth > 0 && mWidth <= 1 && mHeight > 0 && mHeight <= 1
}