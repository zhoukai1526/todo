package com.iwintrue.todoapplication.permission;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.iwintrue.todoapplication.R;
import com.jakewharton.rxbinding.view.RxView;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action1;

public class PermissionAty extends AppCompatActivity {

    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_aty);

        rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    rxPermissions
                            .requestEach(Manifest.permission.ACCESS_FINE_LOCATION
                                    ,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe(new Action1<Permission>() {
                                @Override
                                public void call(Permission permission) {
                                    Log.i("main", "permission:>>>>>> " + permission);
                                }
                            });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();




        RxView.clicks(findViewById(R.id.btn))
                // Ask for permissions when button is clicked
                .compose(rxPermissions.ensureEach(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_COARSE_LOCATION))
                .subscribe(new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {

                        Log.i("main", "Permission result " + permission.name);
//                                   if (permission.get(0).granted) {
//                                        Toast.makeText(PermissionAty.this,"授权成功",Toast.LENGTH_SHORT).show();
//                                   } else if (permission.shouldShowRequestPermissionRationale) {
//                                       // Denied permission without ask never again
//                                       Toast.makeText(PermissionAty.this,
//                                               "Denied permission without ask never again",
//                                               Toast.LENGTH_SHORT).show();
//                                   } else {
//                                       // Denied permission with ask never again
//                                       // Need to go to the settings
//                                       Toast.makeText(PermissionAty.this,
//                                               "Permission denied, can't enable the camera",
//                                               Toast.LENGTH_SHORT).show();
//                                   }
//                               }
                    }
                });
    }



    @Override
    protected void onStop() {
        super.onStop();
    }



}
