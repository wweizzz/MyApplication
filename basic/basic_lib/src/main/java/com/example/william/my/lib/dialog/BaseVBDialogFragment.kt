package com.example.william.my.lib.dialog

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding

/**
 * onAttach -> onCreateDialog -> onCreateView -> onViewCreated -> onStart
 */
abstract class BaseVBDialogFragment<VB : ViewBinding?>(
    private val windowAnimationsRes: Int = 0
) : DialogFragment() {

    private var _binding: VB? = null
    protected val mBinding get() = _binding!!

    protected abstract fun getViewBinding(): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = getViewBinding()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.let { dialog ->
            //解决Dialog内存泄漏
            //X，显示过后的Dialog在Activity回到后台再重新进入后会重新显示
            //dialog.setOnDismissListener(null)
            //dialog.setOnCancelListener(null)

            dialog.window?.let { window ->
                setAttributes(window.attributes)
                window.attributes = window.attributes
                if (windowAnimationsRes > 0) {
                    window.setWindowAnimations(windowAnimationsRes)
                }
                //Android 5.0以上自定义Dialog时发现无法横向铺满屏幕
                window.decorView.setPadding(0, 0, 0, 0)
                window.setBackgroundDrawableResource(android.R.color.transparent)
            }
        }
        initView(view, savedInstanceState)
    }

    /**
     * 在此方法内初始化控件
     */
    open fun initView(view: View?, state: Bundle?) {

    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val transaction = manager.beginTransaction()
            //在每个add事务前增加一个remove事务，防止连续的add
            transaction.remove(this)
            // commit()方法换成了commitAllowingStateLoss()
            // 解决Can not perform this action after onSaveInstanceState with DialogFragment
            transaction.add(this, tag)
            transaction.commitAllowingStateLoss()
            // 解决java.lang.IllegalStateException: Fragment already added
            manager.executePendingTransactions()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected open fun setAttributes(params: WindowManager.LayoutParams) {
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.gravity = Gravity.CENTER
        params.dimAmount = 0.6f
    }
}