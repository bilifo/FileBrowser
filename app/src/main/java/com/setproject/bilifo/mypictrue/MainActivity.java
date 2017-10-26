package com.setproject.bilifo.mypictrue;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import fragment.ListImages;
import fragment.ShowImages2;
import tools.PermissionsChecker;


public class MainActivity extends FragmentActivity {

    FragmentManager manager;

    ShowImages2 mShowImages;
    ListImages mListImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mPermissionsChecker = new PermissionsChecker(this);

        manager = getSupportFragmentManager();
        initView();
        initData();
        initListener();


    }

    private void initData() {

        manager.beginTransaction().replace(R.id.main_fragment_holder,
                new ShowImages2()).commit();
//		manager.beginTransaction().replace(R.id.main_fragment_holder,
//		new ListImages2()).commit();
    }

    private void initListener() {

    }

    private void initView() {

        mListImages = new ListImages();
        mShowImages = new ShowImages2();
//// 监听usb拔出广播
//        IntentFilter iFilter = new IntentFilter();
//        iFilter.addAction(Intent.ACTION_MEDIA_EJECT);
//        iFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
//        iFilter.addDataScheme("file");
//        UsbStateReceiver mUsbStateReceiver=new UsbStateReceiver(this.getBaseContext());
//        registerReceiver(mUsbStateReceiver, iFilter);
    }

    private static final int REQUEST_CODE = 0; // 请求码

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{

            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    private PermissionsChecker mPermissionsChecker; // 权限检测器


    @Override
    protected void onResume() {
        super.onResume();

        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }
}
