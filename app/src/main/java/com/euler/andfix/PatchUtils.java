package com.euler.andfix;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;

import java.io.File;

/**
 * Created by Administrator on 2016/7/6.
 */
public class PatchUtils {
    private static final String TAG = "euler";
    private static final String ANDFIX_PATCHES = "/andfix_patches";
    private static final String DIR = "apatch";//补丁文件夹
    /**
     * patch manager
     */
    public static PatchManager mPatchManager;

    public static void loadPatch(Context context) {
        System.out.println("getApplicationContext()" + context);
        mPatchManager = new PatchManager(context);
        mPatchManager.init(getVersionName(context));
        mPatchManager.loadPatch();
        try {
            File dir = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/Android/data/" + context.getPackageName() + ANDFIX_PATCHES + "/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String loadPatchName = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/Android/data/" + context.getPackageName() + ANDFIX_PATCHES + "/" +
                    String.valueOf(getVersionCode(context))
                    + "_" +
                    FileUtils.getLoadPatchName(dir,
                            "apatch", String.valueOf(getVersionCode(context))) + ".apatch";
            Log.d("loadPatchName", loadPatchName);
            mPatchManager.addPatch(loadPatchName);
            Log.d(TAG, "apatch:" + loadPatchName + " added.");
            //复制且加载补丁成功后，删除下载的补丁
            File f = new File(context.getFilesDir(), DIR);
            if (f.exists()) {
                boolean result = new File(loadPatchName).delete();
                if (!result)
                    Log.e(TAG, loadPatchName + " delete fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static String getVersionName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}