package com.example.william.my.lib.bottomSheetDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.example.william.my.lib.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * onAttach -> onCreateDialog -> onCreateView -> onViewCreated -> onStart
 */
abstract class BaseVBBottomSheetDialogFragment<VB : ViewBinding?>(
    private val windowAnimationsRes: Int = 0
) : BottomSheetDialogFragment() {

    private var _binding: VB? = null
    protected val mBinding get() = _binding!!

    protected var behavior: BottomSheetBehavior<FrameLayout>? = null

    protected abstract fun getViewBinding(): VB

    /**
     * 在Fragment中，IProvider会在Fragment的生命周期方法onCreateView中被初始化。
     * 这是因为Fragment的onCreateView方法是在Fragment被创建并添加到视图层次结构中时被调用的。
     * 因此，当ARouter导航到一个Fragment时，IProvider会被初始化并提供页面所需的数据。
     * 然而，在DialogFragment中，IProvider并不会在DialogFragment的生命周期方法onCreateView中被初始化。
     * 这是因为DialogFragment的onCreateView方法是在DialogFragment被创建并显示出来时被调用的。
     * 因此，当ARouter导航到一个DialogFragment时，IProvider并不会被初始化，因为DialogFragment还没有被显示出来。
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)

        ARouter.getInstance().inject(this)
    }

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

        initDialog()
        initView(view, savedInstanceState)

        initViewModel()
        observeViewModel()
    }

    private fun initDialog() {
        dialog?.let { dialog ->
            //解决Dialog内存泄漏
            //X，显示过后的Dialog在Activity回到后台再重新进入后会重新显示
            //dialog.setOnDismissListener(null)
            //dialog.setOnCancelListener(null)

            behavior = (dialog as BottomSheetDialog).behavior
            dialog.dismissWithAnimation = true

            val root: ConstraintLayout? = dialog.findViewById(R.id.root)
            setBottomSheetParams(root?.layoutParams)

            dialog.window?.let { window ->
                setAttributes(window.attributes)
                if (windowAnimationsRes > 0) {
                    window.setWindowAnimations(windowAnimationsRes)
                }
                //Android 5.0以上自定义Dialog时发现无法横向铺满屏幕
                window.decorView.setPadding(0, 0, 0, 0)
                window.setBackgroundDrawableResource(android.R.color.transparent)
            }
        }
    }

    /**
     * 在此方法内初始化控件
     */
    open fun initView(view: View?, state: Bundle?) {

    }

    /**
     * 在此方法内初始化ViewModel
     */
    open fun initViewModel() {

    }

    /**
     * 在此方法内监听ViewModel
     */
    open fun observeViewModel() {

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
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.dimAmount = 0f
    }

    protected open fun setBottomSheetParams(params: ViewGroup.LayoutParams?) {

    }
}