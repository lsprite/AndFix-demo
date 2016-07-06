
##
坑多，可以弃用了

###使用小结

使用方式

>apkpatch -f 2.apk -t 1.apk -o . -k keystore.jks -p 4434881751 -a nemo -e 4434881751


1）首先添加依赖
```
compile 'com.alipay.euler:andfix:0.4.0@aar'
```

2）http://www.cnblogs.com/xiaomoxian/p/5265920.html

3) https://github.com/alibaba/AndFix

#### http://blog.csdn.net/qxs965266509/article/details/49802429
1.在自定义Application中初始化，为了更早的修复应用中的bug。
```
public class MainApplication extends Application {
    public PatchManager mPatchManager;
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化patch管理类
        mPatchManager = new PatchManager(this);
        // 初始化patch版本
        mPatchManager.init("1.0");
        // 加载已经添加到PatchManager中的patch
        mPatchManager.loadPatch();
    }
}
```

2.如果有新的补丁需要修复，下载完成后，进行以下操作

//添加patch，只需指定patch的路径即可，补丁会立即生效
```
mPatchManager.addPatch(path);
```

3当apk版本升级，需要把之前patch文件的删除，需要以下操作
```
//删除所有已加载的patch文件
mPatchManager.removeAllPatch();
```
