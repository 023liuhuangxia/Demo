package qf.com.day39_cameraandsensor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;
    private TextView mTextView;
    Uri imgUri;
    public static final int TAKE_PHOTO=1;    //拍照的请求码
    public static final int RESULT=2;    //拍照的请求码
    public static final int GET_PHOTO=3;    //拍照的请求码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();

        imgUri=Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    ,"mdzz2.jpg"));
    }



    private void findView() {
        mImageView = (ImageView) findViewById(R.id.mImageView);
        mTextView = (TextView) findViewById(R.id.mTextViewContent);

    }

    public void myClick(View view) {
        switch (view.getId()){
            case R.id.mBtnGetPhoto:
                getPhoto();
                break;
            case R.id.mBtnTakePhoto:
                takePhoto();
                break;
            case R.id.mBtnMakeEWM:
                break;
            case R.id.mBtnScannerEWM:
                break;
            case R.id.mBtnSensor:
                break;
        }



    }

    /**
     * 从图库中获取图片
     */
    private void getPhoto() {
        //这个Action的用途是 获取媒体资源(...)
        Intent intent=new Intent(Intent.ACTION_PICK);
        //设置类型(这一句句话的作用才是访问系统图片)
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        //打开页面
        startActivityForResult(intent,GET_PHOTO);

    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(null==data){
            return;
        }
        if(requestCode==TAKE_PHOTO){
            Log.e("---",imgUri.toString());
            startRESULT(imgUri);
        }
        if(requestCode==RESULT){//图片处理后的返回
            Bundle bundle=data.getExtras(); //返回的是一个Bundle
            Bitmap bitmap=bundle.getParcelable("data");
            //
            mImageView.setImageBitmap(bitmap);
            //若需要保存
           // bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream("")

        }
        if(requestCode==GET_PHOTO){
            startRESULT(data.getData());
        }



        super.onActivityResult(requestCode, resultCode, data);
    }

    //对选择的图片进行裁剪
    private void startRESULT(Uri uri) {
        //设置Action
        Intent intent=new Intent("com.android.camera.action.CROP");
        //设置请求数据的类型
        intent.setDataAndType(uri,"image/*");
        //设置是否需要裁剪
        intent.putExtra("crop","true");
        //设置裁剪的宽和高的比例
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //设置输出图片的宽和高
        intent.putExtra("outputX",300);
        intent.putExtra("outputY",300);
        //设置是否需要返回数据
        intent.putExtra("return-data",true);
        //打开页面开始裁剪了...
        startActivityForResult(intent,RESULT);

    }


    /**
     * 拍照的方法
     */
    public void takePhoto(){
        Intent intent=new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //设置照片的保存路径

        intent.putExtra(MediaStore.EXTRA_OUTPUT,imgUri);
        //
        startActivityForResult(intent,TAKE_PHOTO);



    }

    //摇一摇
    public void shakeOnSake(){
        //
        SensorManager mSensorManager= (SensorManager) this.getSystemService(SENSOR_SERVICE);
        //加速度传感器
        Sensor mSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //注册传感器的监听
        mSensorManager.registerListener(new SensorEventListener2() {

            @Override
            public void onFlushCompleted(Sensor sensor) {

            }

            @Override
            public void onSensorChanged(SensorEvent event) {


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        },mSensor,3);


    }
}
