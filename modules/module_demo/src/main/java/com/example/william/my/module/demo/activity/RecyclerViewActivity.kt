package com.example.william.my.module.demo.activity

import android.content.res.Resources
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.lib.recyclerview.itemdecoration.RItemDecorationSpacing
import com.example.william.my.module.demo.R
import com.example.william.my.module.demo.adapter.RecyclerAdapter
import com.example.william.my.module.demo.databinding.DemoActivityRecyclerViewBinding

/**
 * LayoutManager -> Adapter -> ItemDecoration -> OnScrollListener
 */
@Route(path = RouterPath.Demo.RecyclerView)
class RecyclerViewActivity : BaseVBActivity<DemoActivityRecyclerViewBinding>(),
    RecyclerAdapter.OnItemClickListener {

    override fun getViewBinding(): DemoActivityRecyclerViewBinding {
        return DemoActivityRecyclerViewBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()

        initRecyclerView()
    }

    private fun initRecyclerView() {
        //保持固定的大小，提高性能
        mBinding.recycleView.setHasFixedSize(true)

        //线性布局管理器
        val mLinearLayoutManager = LinearLayoutManager(this)
        mLinearLayoutManager.orientation = RecyclerView.VERTICAL

        //网格布局管理器
        val mGridLayoutManager = GridLayoutManager(this, 4)
        mGridLayoutManager.orientation = RecyclerView.VERTICAL

        //瀑布流布局管理
        val mStaggeredGridLayoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        mStaggeredGridLayoutManager.gapStrategy =
            StaggeredGridLayoutManager.GAP_HANDLING_NONE

        //充满屏幕的网格布局
        //val mFullyGridLayoutManager = FullyGridLayoutManager(this, 4)
        //mFullyGridLayoutManager.orientation = RecyclerView.VERTICAL

        //设置布局管理器
        mBinding.recycleView.layoutManager = mGridLayoutManager
        //设置item添加和移除的动画
        mBinding.recycleView.itemAnimator = DefaultItemAnimator()

        //设置分割线
        //mBinding.recycleView.addItemDecoration(
        //    RItemDecorationDivider(this, dp2px(8f))
        //)

        //mBinding.recycleView.addItemDecoration(
        //    RItemDecorationTop(dp2px(8f))
        //)

        //mBinding.recycleView.addItemDecoration(
        //    RItemDecorationStartEnd(dp2px(8f))
        //)

        //mBinding.recycleView.addItemDecoration(
        //    RItemDecorationBottom(dp2px(8f), true)
        //)

        //mBinding.recycleView.addItemDecoration(
        //    RItemDecorationBottom(dp2px(8f), false)
        //)

        mBinding.recycleView.addItemDecoration(
            RItemDecorationSpacing(spacing = dp2px(20f), bottom = dp2px(48f))
        )

        /*
         * LinearSnapHelper,PagerSnapHelper 使RecyclerView 像ViewPager一样的效果，每次只能滑动一页
         * LinearSnapHelper 支持快速滑动
         * PagerSnapHelper 限制一次只能滑动一页，不能快速滑动
         */
        //LinearSnapHelper linearSnapHelper = new LinearSnapHelper()
        //linearSnapHelper.attachToRecyclerView(mBinding.recycleView)

        //PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        //pagerSnapHelper.attachToRecyclerView(mBinding.recycleView);

        val mController = LayoutAnimationController(
            AnimationUtils.loadAnimation(this, R.anim.demo_anim_recycler_item_left)
        )

        //显示顺序：ORDER_NORMAL 顺序，ORDER_REVERSE 倒序，ORDER_RANDOM 随机
        mController.order = LayoutAnimationController.ORDER_NORMAL
        //显示间隔
        mController.delay = 0.2f

        mBinding.recycleView.layoutAnimation = mController

        val mData: MutableList<String> = ArrayList()
        for (i in 1..59) {
            mData.add("POSITION $i")
        }

        val mRecyclerAdapter = RecyclerAdapter(mData)

        //设置唯一标识符，需要在setAdapter之前调用
        mRecyclerAdapter.setHasStableIds(true)
        mBinding.recycleView.adapter = mRecyclerAdapter

        //设置点击事件
        mRecyclerAdapter.setOnItemClickListener(this)

        //设置ViewCacheExtension缓存
        //mBinding.recycleView.setViewCacheExtension(new RecyclerCacheExtension())
    }

    override fun onItemClick(adapter: RecyclerAdapter, view: View, position: Int) {
        adapter.notifyItemChanged(position, "Payload")
    }

    private fun dp2px(dpValue: Float): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}