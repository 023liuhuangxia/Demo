package qf.com.day39_cameraandsensor;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main2Activity extends AppCompatActivity {

    private Toolbar mToolBar;
    private FloatingActionButton mFab;
    Snackbar mSnackbar;
    private CoordinatorLayout mCoordinatorLayout;
    LayoutInflater layoutInflater;

    //定义用户名和密码输入正确性与否的标记
    private boolean flag_username = true;
    private boolean flag_pwd = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        findView();
        setSupportActionBar(mToolBar);


    }

    private void findView() {
        layoutInflater= (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        mToolBar = (Toolbar) findViewById(R.id.mToolBar);
        mFab = (FloatingActionButton) findViewById(R.id.mFab);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.mCoordinatorLayout);

    }

    public void fabOnClick(View view) {
      //  mSnackbar.setText("点击了一下");
       // mSnackbar.setDuration(5000);
        mSnackbar.make(mCoordinatorLayout,"注册会有更多精彩",5000)
                .setAction("注册", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View dialogLayout=layoutInflater.inflate(R.layout.dialog_main,null);
                        final TextInputLayout layout_userName= (TextInputLayout) dialogLayout.findViewById(R.id.layout_userName);
                        final TextInputLayout layout_userPassword= (TextInputLayout) dialogLayout.findViewById(R.id.layout_password);
                        EditText editText_userName= (EditText) dialogLayout.findViewById(R.id.textUserName);
                        final EditText editText_password= (EditText) dialogLayout.findViewById(R.id.textPassword);

                        /**
                         * 用户名输入框改变的回调
                         */
                        editText_userName.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(!isChinese(s.toString())){
                                    layout_userName.setErrorEnabled(true);
                                    layout_userName.setError("用户名必须是中文！");
                                    flag_username=false;
                                }
                                else {
                                    if(s.length()>4){
                                        layout_userName.setErrorEnabled(true);
                                        layout_userName.setError("文字不能超过4个！");
                                        flag_username=false;
                                    }
                                    else {
                                        layout_userName.setErrorEnabled(false);
                                        flag_username = true;
                                    }
                                }


                            }
                        });

                        /**
                         * 密码输入框改变的回调
                         */
                        editText_password.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                if(!isPwd(s.toString())){
                                    layout_userPassword.setErrorEnabled(true);
                                    layout_userPassword.setError("密码必须是6位数字、字母！");
                                    flag_pwd=false;
                                }
                                else {
                                    layout_userPassword.setErrorEnabled(false);
                                    flag_pwd=true;

                                }

                            }
                        });

                        AlertDialog.Builder builder=new AlertDialog.Builder(Main2Activity.this);
                        builder.setIcon(R.drawable.ic_yh);
                        builder.setTitle("用户注册");
                        builder.setView(dialogLayout);
                        builder.setNegativeButton("取消",null);
                        builder.setPositiveButton("注册", null);
                        builder.show();
                    }
                })
                .show();



    }

    private boolean isChinese(String str) {
        String regexp = "^[\u4e00-\u9fa5]+";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    private boolean isPwd(String str) {
        String regexp = "^[0-9a-zA-Z]{6}$";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
