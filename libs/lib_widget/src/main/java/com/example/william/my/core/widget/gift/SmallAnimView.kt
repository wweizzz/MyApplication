package com.example.william.my.core.widget.gift

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.blankj.utilcode.util.ScreenUtils
import com.example.william.my.core.widget.databinding.AnimItemBinding

class SmallAnimView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var mListener: Animator.AnimatorListener? = null
    private val mBinding =
        AnimItemBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    fun setImageUrl(imageUrl: String?) {
        //mBinding.itemIvGift.loadImage(context, imageUrl)
    }

    fun addListener(listener: Animator.AnimatorListener?) {
        this.mListener = listener
    }

    fun play() {
        val animatorSet = AnimatorSet()
        val mTranslationIn = ObjectAnimator.ofFloat(
            this, "translationX", ScreenUtils.getScreenWidth().toFloat(), 0f
        )
        mTranslationIn.setDuration(1200)
        val mTranslationOut = ObjectAnimator.ofFloat(
            this, "translationX", 0f, -ScreenUtils.getScreenWidth().toFloat()
        )
        mTranslationOut.setDuration(1200)
        val mTranslationWait = ObjectAnimator.ofFloat(
            this, "translationX", 0f, 0f
        )
        mTranslationWait.setDuration(6000)
        animatorSet.playSequentially(mTranslationIn, mTranslationWait, mTranslationOut)
        animatorSet.addListener(mListener)
        animatorSet.start()
    }
}
