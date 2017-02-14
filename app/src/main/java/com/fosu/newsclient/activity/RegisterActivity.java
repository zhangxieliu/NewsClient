package com.fosu.newsclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fosu.newsclient.R;
import com.fosu.newsclient.bean.User;
import com.fosu.newsclient.db.dao.UserDao;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Administrator on 2016/10/11.
 * 用户注册页面，主要用来处理用户注册请求，发送手机验证码
 */

public class RegisterActivity extends AppCompatActivity implements Validator.ValidationListener {
    private static final String TAG = "RegisterActivity";
    private static final String APPKEY = "17ccfa22b2003";
    private static final String APPSECRET = "4233a3ce5a09d48a703c057a49a3880e";

//    private static String APPKEY = "f3fc6baa9ac4";
//    private static String APPSECRET = "7f3dedcb36d92deebcb373af921d635a";

    @BindView(R.id.et_phone)
    @NotEmpty(message = "号码不能为空", trim = true)
    EditText etPhone;
    @BindView(R.id.btn_clear)
    Button btnClear;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.et_username)
    @NotEmpty(message = "用户名不能为空")
    EditText etUsername;
    @BindView(R.id.et_password)
    @Password(message = "密码不能为空，且不小于6位")
    EditText etPassword;
    @BindView(R.id.et_email)
    @Email(message = "邮箱格式不正确")
    EditText etEmail;
    @BindView(R.id.btn_eye)
    Button btnEye;
    @BindView(R.id.et_sms_code)
    @NotEmpty(trim = true, message = "请输入手机验证码")
    EditText etSmsCode;
    @BindView(R.id.btn_sms_code)
    Button btnSmsCode;
    private boolean isVisible = false;
    private EventHandler eventHandler;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0x001:
                    boolean isUse = (boolean) msg.obj;
                    if (isUse) {
                        Toast.makeText(RegisterActivity.this, "手机号已注册！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "发送验证码成功！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 0x002:
                    Toast.makeText(RegisterActivity.this, "验证成功！", Toast.LENGTH_SHORT).show();
                    String userName = etUsername.getText().toString().trim();
                    String userPassword = etPassword.getText().toString().trim();
                    String userEmail = etEmail.getText().toString().trim();
                    String phone = etPhone.getText().toString().trim();
                    User user = new User();
                    user.setUserName(userName);
                    user.setUserPassword(userPassword);
                    user.setUserEmail(userEmail);
                    user.setUserPhone(phone);
                    UserDao userDao = new UserDao(RegisterActivity.this);
                    boolean result = userDao.addUser(user);
                    if (result) {
                        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtra("user", user);
                        setResult(0x002, intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "注册失败，请重新注册！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 0x003:
                    String des = (String) msg.obj;
                    Toast.makeText(RegisterActivity.this, des, Toast.LENGTH_SHORT).show();
                    break;
                case 0x004:
                    Toast.makeText(RegisterActivity.this, "数据异常，请稍后再试！", Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    });
    private Validator validator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        initSMSSDK();
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    btnClear.setVisibility(View.GONE);
                    btnNext.setEnabled(false);
                    btnSmsCode.setEnabled(false);
                } else {
                    btnClear.setVisibility(View.VISIBLE);
                    btnNext.setEnabled(true);
                    btnSmsCode.setEnabled(true);
                }
            }
        });
    }

    private void initSMSSDK() {
        SMSSDK.initSDK(this, APPKEY, APPSECRET, true);
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        // 提交验证码成功
                        HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                        String country = (String) phoneMap.get("country");
                        String phone = (String) phoneMap.get("phone");
                        handler.sendEmptyMessage(0x002);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        // 获取验证码成功
                        boolean isUse = (boolean) data;
                        Message msg = Message.obtain();
                        msg.what = 0x001;
                        msg.obj = isUse;
                        handler.sendMessage(msg);
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        // 返回支持发送验证码的国家列表
                        ArrayList<HashMap<String, Object>> countrys = (ArrayList<HashMap<String, Object>>) data;
                    }
                } else {
                    // 出现错误
                    Throwable throwable = ((Throwable) data);
                    throwable.printStackTrace();
                    try {
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");//错误描述
//                        int status = object.optInt("status");//错误代码
                        Message msg = Message.obtain();
                        msg.what = 0x003;
                        msg.obj = des;
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        handler.sendEmptyMessage(0x004);
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler);
    }

    @OnClick({R.id.img_back, R.id.btn_clear, R.id.btn_next, R.id.btn_eye, R.id.btn_sms_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_clear:
                etPhone.setText("");
                break;
            case R.id.btn_next:
                validator.validate();
                break;
            case R.id.btn_eye:
                // 设置密码框的密码是否可见
                if (isVisible) {
                    isVisible = false;
                    btnEye.setBackgroundResource(R.drawable.eye_unenable);
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    isVisible = true;
                    btnEye.setBackgroundResource(R.drawable.eye_enable);
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;
            case R.id.btn_sms_code:
                validatePhone(etPhone.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    private void validatePhone(String phone) {
        // 获取短信目前支持的国家列表，在监听中返回
//        SMSSDK.getSupportedCountries();
        // 请求获取短信验证码，在监听中返回
        SMSSDK.getVerificationCode("86", phone);

        // OnSendMessageHandler的定义如下，这个Handler的用途是在发送短信之前，
        // 开发者自己执行一个操作，来根据电话号码判断是否需要发送短信
//        SMSSDK.getVerificationCode("86", phone, new OnSendMessageHandler() {
//            @Override
//            public boolean onSendMessage(String country, String phone) {
//                // 此方法在发送验证短信前被调用，传入参数为接收者号码
//                // 返回true表示此号码无须实际接收短信
//                LogUtil.i(TAG, "country:" + country + ", phone:" + phone);
//                return false;
//            }
//        });
    }


    @Override
    public void onValidationSucceeded() {
        String code = etSmsCode.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        SMSSDK.submitVerificationCode("86", phone, code);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // 展示输入框错误信息
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (eventHandler != null)
            SMSSDK.unregisterEventHandler(eventHandler);
    }
}
