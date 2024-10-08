package com.example.william.my.module.demo.gift

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.LogUtils
import org.libpag.PAGScaleMode
import org.libpag.PAGView
import org.libpag.PAGView.PAGViewListener
import java.util.concurrent.LinkedBlockingQueue

class GiftLayout @JvmOverloads constructor(
    context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), PAGViewListener, Animator.AnimatorListener {
    private var isPlaying = false

    private val mPagView = PAGView(context, attrs, defStyleAttr)
    private val mSmallAnimView: SmallAnimView
    //private val mGiftMsgBodyQueue = LinkedBlockingQueue<CustomMsg<RoomGiftMsg>>()

    // temp
    private val mGiftMsgBodyQueue = LinkedBlockingQueue<String>()

    init {
        mPagView.visibility = GONE
        mPagView.setRepeatCount(1)
        mPagView.setScaleMode(PAGScaleMode.Zoom)
        mPagView.addListener(this)
        val mPagViewParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(mPagView, mPagViewParams)

        this.mSmallAnimView = SmallAnimView(context, attrs, defStyleAttr)
        mSmallAnimView.visibility = GONE
        mSmallAnimView.addListener(this)
        val mallAnimViewParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        mallAnimViewParams.topMargin = AdaptScreenUtils.pt2Px(60f)
        addView(mSmallAnimView, mallAnimViewParams)
    }

    //fun handlerGiftMsg(giftMsg: CustomMsg<RoomGiftMsg>) {
    //    LogUtils.e("handlerGiftMsg 收到消息")
    //    mGiftMsgBodyQueue.offer(giftMsg)
    //    notifyGiftMsg()
    //}

    private fun notifyGiftMsg() {
        LogUtils.e(
            "notifyGiftMsg 通知播放动画",
            "isPlaying : " + isPlaying + " mGiftMsgBodyQueue.size : " + mGiftMsgBodyQueue.size
        )
        if (!isPlaying && !mGiftMsgBodyQueue.isEmpty()) {
            val nextMessage = mGiftMsgBodyQueue.poll()
            //if (nextMessage is CustomMsg<RoomGiftMsg>) {
            //    when (nextMessage.data.getAnimationType()) {
            //        GiftAnimationType.BigAnim -> {
            //            startPagAnim(nextMessage)
            //        }

            //        GiftAnimationType.SmallAnim -> {
            //            startSmallAnim(nextMessage)
            //        }
            //    }
            //}
        }
    }

    //private fun startPagAnim(data: CustomMsg<RoomGiftMsg>) {
    //    LogUtils.e("startPagAnim 大动画")
    //    mPagView.setPath(data.data.getAnimationId().getFilePathByAnimId())
    //    mPagView.play()
    //}

    //private fun startSmallAnim(data: CustomMsg<RoomGiftMsg>) {
    //    LogUtils.e("startPagAnim 小动画")
    //    mSmallAnimView.setImageUrl(data.data.giftInfo.imageUrl)
    //    mSmallAnimView.play()
    //}

    override fun onAnimationStart(pagView: PAGView) {
        LogUtils.e("onAnimationStart")
        mPagView.visibility = VISIBLE
        isPlaying = true
    }

    override fun onAnimationEnd(pagView: PAGView) {
        LogUtils.e("onAnimationEnd")
        mPagView.visibility = GONE
        isPlaying = false
        notifyGiftMsg()
    }

    override fun onAnimationCancel(pagView: PAGView) {
    }

    override fun onAnimationRepeat(pagView: PAGView) {
    }

    override fun onAnimationUpdate(pagView: PAGView) {
    }

    override fun onAnimationStart(animation: Animator) {
        LogUtils.e("onAnimationStart")
        mSmallAnimView.visibility = VISIBLE
        isPlaying = true
    }

    override fun onAnimationEnd(animation: Animator) {
        LogUtils.e("onAnimationEnd")
        mSmallAnimView.visibility = GONE
        isPlaying = false
        notifyGiftMsg()
    }

    override fun onAnimationCancel(animation: Animator) {
    }

    override fun onAnimationRepeat(animation: Animator) {
    }
}
