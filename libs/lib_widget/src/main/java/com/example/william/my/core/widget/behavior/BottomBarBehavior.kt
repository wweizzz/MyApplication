package com.example.william.my.core.widget.behavior

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

class BottomBarBehavior(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<View>(context, attrs) {

    private var viewY = 0f
    private var isAnimate = false

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int,
        @ViewCompat.NestedScrollType type: Int
    ): Boolean {
        if (child.visibility == View.VISIBLE && viewY == 0f) {
            // 获取控件距离父布局（coordinatorLayout）底部距离
            viewY = coordinatorLayout.height - child.y
        }
        return nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        // dy大于0是向上滚动 小于0是向下滚动
        // 判断的时候尽量不要判断是否大于等于或者小于等于0，否则可能会影响点击事件
        if (type == ViewCompat.TYPE_TOUCH) {
            if (dy > 10 && !isAnimate && child.visibility == View.VISIBLE) {
                hide(child)
            } else if (dy < 10 && !isAnimate && child.visibility == View.INVISIBLE) {
                show(child)
            }
        }
    }

    private fun hide(view: View) {
        val animator =
            view.animate().translationY(viewY).setInterpolator(FastOutSlowInInterpolator())
                .setDuration(500)
        animator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                isAnimate = true
            }

            override fun onAnimationEnd(animator: Animator) {
                view.visibility = View.INVISIBLE
                isAnimate = false
            }

            override fun onAnimationCancel(animator: Animator) {
                show(view)
            }

            override fun onAnimationRepeat(animator: Animator) {}
        })
        animator.start()
    }

    private fun show(view: View) {
        val animator = view.animate().translationY(0f).setInterpolator(FastOutSlowInInterpolator())
            .setDuration(500)
        animator.setListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {
                view.visibility = View.VISIBLE
                isAnimate = true
            }

            override fun onAnimationEnd(animator: Animator) {
                isAnimate = false
            }

            override fun onAnimationCancel(animator: Animator) {
                hide(view)
            }

            override fun onAnimationRepeat(animator: Animator) {}
        })
        animator.start()
    }
}