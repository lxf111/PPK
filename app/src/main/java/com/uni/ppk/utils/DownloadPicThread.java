package com.uni.ppk.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

/**
 * Author:zhn
 * Time:2019/7/22 0022 10:34
 * 下载图片并保存到相册
 */
public class DownloadPicThread implements Runnable {
    private Context context;
    private String filePath;
    private OnDownloadListener mListener;

    public DownloadPicThread(Context contexts, String filePaths,
                             OnDownloadListener listener) {
        this.context = contexts;
        this.filePath = filePaths;
        this.mListener = listener;
    }

    /**
     * 保存图片
     *
     * @param bm
     * @throws IOException
     */
    public void saveFile(Bitmap bm) throws IOException {
        File dirFile = new File(Environment.getExternalStorageDirectory().getPath());
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        String fileName = UUID.randomUUID().toString() + ".jpeg";
        File myCaptureFile = new File(Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        //把图片保存后声明这个广播事件通知系统相册有新图片到来
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(myCaptureFile);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    @Override
    public void run() {
        Bitmap bitmap = null;
        Log.e("TAG", "1");
        try {
            if (!TextUtils.isEmpty(filePath)) { //网络图片
                Log.e("TAG", "filePath="+filePath);
                // 对资源链接
//                URL url = new URL(filePath);
//                //打开输入流
//                InputStream inputStream = (InputStream) url.getContent();
//                BufferedInputStream bis = new BufferedInputStream(inputStream);
//                //标记其实位置，供reset参考
//                bis.mark(0);
//                BitmapFactory.Options opts = new BitmapFactory.Options();
//                //true,只是读图片大小，不申请bitmap内存
//                opts.inJustDecodeBounds = true;
//                BitmapFactory.decodeStream(bis, null, opts);
//                Log.e("TAG", "2="+2);
//                int size = (opts.outWidth * opts.outHeight);
//                if (size > 1024 * 1024 * 4) {
//                    int zoomRate = 2;
//                    //zommRate缩放比，根据情况自行设定，如果为2则缩放为原来的1/2，如果为1不缩放
//                    if (zoomRate <= 0) zoomRate = 1;
//                    opts.inSampleSize = zoomRate;
//                }
//                Log.e("TAG", "3="+3);
//                //设为false，这次不是预读取图片大小，而是返回申请内存，bitmap数据
//                opts.inJustDecodeBounds = false;
//                //缓冲输入流定位至头部，mark()
//                bis.reset();
//                bitmap = BitmapFactory.decodeStream(bis, null, opts);
//                inputStream.close();
//                bis.close();
                URL url = null;
                url = new URL(filePath);
                InputStream is = null;
                BufferedInputStream bis = null;
                is = url.openConnection().getInputStream();
                bis = new BufferedInputStream(is);
                bitmap = BitmapFactory.decodeStream(bis);
            }
            if (bitmap != null) {
                saveFile(bitmap);
                mListener.onDownloadSuccess();
            } else {
                mListener.onDownloadFailed();
            }
        } catch (Exception e) {
            e.printStackTrace();
            mListener.onDownloadFailed();
        }
    }

    //下载监听
    public interface OnDownloadListener {
        /**
         * 下载成功之后的文件
         */
        void onDownloadSuccess();

        /**
         * 下载异常信息
         */
        void onDownloadFailed();
    }

}