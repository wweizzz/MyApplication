package com.example.william.my.module.demo.activity3

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.william.my.basic.basic_module.activity.BasicImageActivity
import com.example.william.my.basic.basic_module.router.path.RouterPath
import com.example.william.my.basic.basic_module.router.service.ImageUtilsService
import java.io.File

@Suppress("deprecation")
@Route(path = RouterPath.Demo.Crop)
class CropActivity : BasicImageActivity() {

    private val mItems = arrayOf("图库", "拍照", "拍照")

    private var sourceUri: Uri? = null
    private var destinationUri: Uri? = null

    override fun onImageClick(view: View) {
        super.onImageClick(view)

        showDialog()
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this@CropActivity)
        builder.setItems(mItems) { _, i ->
            when (i) {
                0 -> {
                    val intentFromAlbum = Intent(Intent.ACTION_PICK)
                    intentFromAlbum.type = "image/*"
                    startActivityForResult(intentFromAlbum, ACTION_PICK)
                }

                1 -> {
                    val intentFromCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intentFromCamera, ACTION_IMAGE_CAPTURE)
                }

                2 -> {
                    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    val file =
                        File(externalCacheDir.toString() + File.separator + System.currentTimeMillis() + ".jpg")
                    sourceUri =
                        FileProvider.getUriForFile(
                            this@CropActivity,
                            "$packageName.fileProvider",
                            file
                        )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, sourceUri)
                    startActivityForResult(takePictureIntent, ACTION_IMAGE_CAPTURE_FUll)
                }
            }
        }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                ACTION_PICK -> if (data != null && data.data != null) {
                    val source = data.data
                    val file =
                        File(externalCacheDir.toString() + File.separator + System.currentTimeMillis() + ".jpg")
                    val destination = Uri.fromFile(file)
                    toCropFromUri(source, destination)
                }

                ACTION_IMAGE_CAPTURE -> if (data != null && data.extras != null) {
                    val bitmap = data.extras!!["data"] as Bitmap?
                    val source = saveBitmap2Uri(bitmap)
                    val file =
                        File(externalCacheDir.toString() + File.separator + System.currentTimeMillis() + ".jpg")
                    val destination = Uri.fromFile(file)
                    toCropFromUri(source, destination)
                }

                ACTION_IMAGE_CAPTURE_FUll -> if (sourceUri != null) {
                    val file =
                        File(externalCacheDir.toString() + File.separator + System.currentTimeMillis() + ".jpg")
                    val destination = Uri.fromFile(file)
                    toCropFromUri(sourceUri, destination)
                }

                ACTION_CROP ->                     /*
                     * 设置 MediaStore.EXTRA_OUTPUT false，不返回data数据，通过Uri获取数据
                     */try {
                    val bitmap: Bitmap
                    //if (data != null && data.getExtras() != null) {
                    //	bitmap = (Bitmap) data.getExtras().get("data");
                    //} else {
                    //	bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(image_crop_uri));
                    //}
                    bitmap = BitmapFactory.decodeStream(
                        contentResolver.openInputStream(
                            destinationUri!!
                        )
                    )
                    mBinding.basicsImage.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun toCropFromUri(source: Uri?, destination: Uri) {
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(source, "image/*")

        //添加权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true")

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 1024)
        intent.putExtra("outputY", 1024)

        /*
         * 是否通过intent传递截获的图片，此方法返回的图片只能是小图片
         * 故只保存图片Uri，调用时将Uri转换为Bitmap，此方法还可解决MIUI系统不能return data的问题
         */intent.putExtra("return-data", false)
        destinationUri = destination //保存指定Uri

        //necessary!
        //将URI指向相应的file:///... , 需要使用Uri.fromFile(file)生成
        intent.putExtra(MediaStore.EXTRA_OUTPUT, destination)
        startActivityForResult(intent, ACTION_CROP)
    }

    private fun saveBitmap2Uri(bitmap: Bitmap?): Uri {
        val file =
            File(externalCacheDir.toString() + File.separator + System.currentTimeMillis() + ".jpg")
        val service = ARouter.getInstance().build(RouterPath.Service.ImageUtilsService)
            .navigation() as ImageUtilsService
        val successful = service.save(bitmap!!, file, Bitmap.CompressFormat.JPEG)
        return FileProvider.getUriForFile(this@CropActivity, "$packageName.fileProvider", file)
    }

    companion object {
        private const val ACTION_PICK = 0
        private const val ACTION_IMAGE_CAPTURE = 1
        private const val ACTION_IMAGE_CAPTURE_FUll = 2
        private const val ACTION_CROP = 3
    }
}