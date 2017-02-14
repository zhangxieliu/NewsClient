package com.fosu.newsclient.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fosu.newsclient.R;
import com.fosu.newsclient.bean.User;
import com.fosu.newsclient.db.dao.UserDao;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Created by Administrator on 2016/10/8.
 * 用户登录页面，处理登录请求和跳转到注册页面请求
 */

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.layout_input)
    LinearLayout layoutInput;
    @BindView(R.id.tvLogin_Question)
    TextView tvLoginQuestion;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.imgWeixin)
    ImageView imgWeixin;
    @BindView(R.id.imgAlipay)
    ImageView imgAlipay;
    @BindView(R.id.layout_bottom_image)
    LinearLayout layoutBottomImage;
    @BindView(R.id.tv_weixin)
    TextView tvWeixin;
    @BindView(R.id.tv_alipay)
    TextView tvAlipay;
    @BindView(R.id.layout_bottom_tv)
    LinearLayout layoutBottomTv;
    @BindView(R.id.cb_remember)
    CheckBox cbRemember;
    @BindView(R.id.line_username)
    View lineUsername;
    @BindView(R.id.line_password)
    View linePassword;
    private SharedPreferences sp;
    private boolean isEmptyName;
    private boolean isEmptyPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initUser();
        initListener();
    }

    private void initUser() {
        sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        etUsername.setText(sp.getString("name", ""));
        etPassword.setText(sp.getString("password", ""));
        cbRemember.setChecked(sp.getBoolean("isChecked", false));
        if (sp.getBoolean("isChecked", false)) {
            btnLogin.setEnabled(true);
        }
    }

    /**
     * 初始化用户名和密码输入框的监听，当两个输入框都不为空时，才激活登录按钮
     */
    private void initListener() {
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim()))
                    isEmptyName = true;
                isEmptyName = false;
                setBtnLoginEnable();
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim()))
                    isEmptyPassword = true;
                isEmptyPassword = false;
                setBtnLoginEnable();
            }
        });
    }

    /**
     * 设置登录按钮是否激活
     */
    private void setBtnLoginEnable() {
        if (!isEmptyName && !isEmptyPassword) {
            btnLogin.setEnabled(true);
        } else {
            btnLogin.setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x001 && resultCode == 0x002) {
            User user = data.getParcelableExtra("user");
            etUsername.setText(user.getUserName());
            etPassword.setText(user.getUserPassword());
        }
    }

    @OnClick({R.id.tv_register, R.id.tvLogin_Question, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                // 这里跳转到注册界面
                startActivityForResult(new Intent("activity.register.news"), 0x001);
                break;
            case R.id.tvLogin_Question:
                // 这里跳转到登录遇到问题界面
                Toast.makeText(this, "暂时无法为您解决这个问题，请仔细查看用户名和密码是否正确，或者与管理员联系。", Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_login:
                // 这里进行登录请求认证
                String userName = etUsername.getText().toString().trim();
                String userPassword = etPassword.getText().toString().trim();
                User user = new User();
                user.setUserName(userName);
                user.setUserPassword(userPassword);
                UserDao dao = new UserDao(this);
                User result = dao.findUser(user);
                if (result != null) {
                    // 用户验证成功
                    if (cbRemember.isChecked()) {
                        sp.edit().putString("name", user.getUserName())
                                .putString("password", user.getUserPassword())
                                .putBoolean("isChecked", cbRemember.isChecked())
                                .apply();
                    } else {
                        sp.edit().clear().putString("name", user.getUserName()).apply();
                    }
                    Intent intent = new Intent("activity.news");
                    intent.putExtra("user", result);
                    startActivity(intent);
                    finish();
                } else {
                    // 用户验证失败
                    Toast.makeText(this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @OnTouch(value = {R.id.et_username, R.id.et_password})
    public boolean onTouch(View v) {
        switch (v.getId()) {
            case R.id.et_username:
                lineUsername.setBackgroundColor(0xFF1F81F8);
                linePassword.setBackgroundColor(0xffaba7a7);
                break;
            case R.id.et_password:
                linePassword.setBackgroundColor(0xFF1F81F8);
                lineUsername.setBackgroundColor(0xffaba7a7);
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("是否退出登录")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginActivity.this.finish();
                    }
                }).setNegativeButton("取消", null)
                .show();
    }
}
