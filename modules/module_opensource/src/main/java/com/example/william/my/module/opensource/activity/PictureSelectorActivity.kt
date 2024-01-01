package com.example.william.my.module.opensource.activity

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.lib.activity.BaseVBActivity
import com.example.william.my.module.opensource.adapter.PictureSelectorAdapter
import com.example.william.my.module.opensource.databinding.OpenActivityPictureSelectorBinding
import com.example.william.my.module.opensource.engine.GlideEngine
import com.luck.picture.lib.basic.PictureSelector
import com.luck.picture.lib.config.SelectMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnExternalPreviewEventListener
import com.luck.picture.lib.interfaces.OnResultCallbackListener

/**
 * https://github.com/LuckSiege/PictureSelector
 */
@Route(path = RouterPath.Opensource.PictureSelector)
class PictureSelectorActivity : BaseVBActivity<OpenActivityPictureSelectorBinding>() {

    private lateinit var mAdapter: PictureSelectorAdapter

    override fun getViewBinding(): OpenActivityPictureSelectorBinding {
        return OpenActivityPictureSelectorBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()

        val manager = GridLayoutManager(this, 4)
        mBinding.recyclerView.layoutManager = manager

        mAdapter = PictureSelectorAdapter()
        mBinding.recyclerView.adapter = mAdapter

        mAdapter.setOnItemClickListener(object : PictureSelectorAdapter.OnItemClickListener {
            override fun openPicture() {
                PictureSelector.create(this@PictureSelectorActivity)
                    .openGallery(SelectMimeType.ofImage())
                    .setImageEngine(GlideEngine.createGlideEngine())
                    .forResult(object : OnResultCallbackListener<LocalMedia?> {
                        override fun onResult(result: ArrayList<LocalMedia?>) {

                            val oldSize: Int = mAdapter.data.size
                            val count = if (result.size == mAdapter.maxSelect) {
                                oldSize + 1
                            } else {
                                oldSize
                            }
                            mAdapter.notifyItemRangeRemoved(0, count)
                            mAdapter.data.clear()

                            mAdapter.data.addAll(result)
                            mAdapter.notifyItemRangeInserted(0, result.size)
                        }

                        override fun onCancel() {

                        }
                    })
            }

            override fun onItemClick(v: View, position: Int) {
                PictureSelector.create(this@PictureSelectorActivity)
                    .openPreview()
                    .setImageEngine(GlideEngine.createGlideEngine())
                    .setExternalPreviewEventListener(object : OnExternalPreviewEventListener {
                        override fun onPreviewDelete(position: Int) {
                            mAdapter.remove(position)
                            mAdapter.notifyItemRemoved(position)
                        }

                        override fun onLongPressDownload(
                            context: Context?,
                            media: LocalMedia?
                        ): Boolean {
                            return false
                        }
                    })
                    .startActivityPreview(position, true, mAdapter.data)
            }
        })
    }
}