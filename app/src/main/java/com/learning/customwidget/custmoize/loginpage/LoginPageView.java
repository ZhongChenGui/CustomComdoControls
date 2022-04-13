package com.learning.customwidget.custmoize.loginpage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.learning.customwidget.App;
import com.learning.customwidget.R;

import java.lang.reflect.Field;

public class LoginPageView extends FrameLayout {

    //手机号码的规则
    public static final String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
    private static final String TAG = "LoginPageView";

    private final int SIZE_DEFAULT_VERITY_CODE = 6;
    private int mVerifyCodeSize;
    private int mMainColor;
    private OnLoginPageActionListener mActionListener;
    private LoginKeyboardView mNumKeyBoard;
    private EditText mVerifyCodeInputBox;
    private EditText mPhoneNumberInp;

    private boolean isVerifyCode = false;
    private boolean isPhoneNum = false;
    private boolean isAgreementOk = false;
    private TextView mVerifyCodeBtn;
    private TextView mLoginBtn;
    private CheckBox mCheckBox;

    public LoginPageView(@NonNull Context context) {
        this(context, null);
    }

    public LoginPageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginPageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 初始化属性
        initAttrs(context, attrs);
        // 初始化view
        initView(context);
        // 禁止弹起键盘
        disableEditFocus2Keyboard();
        // 处理事件
        initEvents();
    }

    private int totalTime = 60 * 1000;
    private int dTime = 1000;
    private int resetTime = totalTime - dTime;

    /**
     * 开始计时
     */
    private void startCountDown() {
        Handler handler = App.getHandler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                resetTime -= dTime;
                if (resetTime > 0) {
                    handler.postDelayed(this, dTime);
                    mVerifyCodeBtn.setText("(" + resetTime / 1000 + "秒)");
                    mVerifyCodeBtn.setEnabled(false);
                } else {
                    mVerifyCodeBtn.setText("获取验证码");
                    mVerifyCodeBtn.setEnabled(true);
                }
            }
        });
    }

    private void disableEditFocus2Keyboard() {
        mVerifyCodeInputBox.setShowSoftInputOnFocus(false);
        mPhoneNumberInp.setShowSoftInputOnFocus(false);
    }

    private void initEvents() {

        mNumKeyBoard.setOnKeyPressListener(new LoginKeyboardView.OnKeyPressListener() {
            @Override
            public void onNumberPress(int value) {
                EditText editText = getFocusEdt();
                if (editText != null) {
                    Editable text = editText.getText();
                    int index = editText.getSelectionEnd();
                    text.insert(index, String.valueOf(value));
                }
            }

            @Override
            public void onBackPress() {
                EditText editText = getFocusEdt();
                if (editText != null) {
                    Editable text = editText.getText();
                    int index = editText.getSelectionEnd();
                    if (index > 0) {
                        text.delete(index - 1, index);
                    }
                }
            }
        });
        mPhoneNumberInp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                isPhoneNum = length == 11 && s.toString().matches(REGEX_MOBILE_EXACT);
                updateBtnStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mVerifyCodeInputBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isVerifyCode = s.length() == mVerifyCodeSize;
                updateBtnStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isAgreementOk = isChecked;
                updateBtnStatus();
            }
        });
        mVerifyCodeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActionListener != null) {
                    String phoneNum = mPhoneNumberInp.getText().toString().trim();
                    mActionListener.onSendVerifyCodeClick(phoneNum);
                    startCountDown();
                } else {
                    throw new IllegalArgumentException("your need setOnClickListener");
                }
            }
        });
    }

    private EditText getFocusEdt() {
        View view = this.findFocus();
        if (view instanceof EditText) {
            return ((EditText) view);
        }
        return null;
    }

    private void initView(@NonNull Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.login_page_view, this);
        mCheckBox = view.findViewById(R.id.sure_check_box);
        mVerifyCodeInputBox = view.findViewById(R.id.verify_code_input_box);
        mVerifyCodeBtn = view.findViewById(R.id.get_verify_code);
        mNumKeyBoard = view.findViewById(R.id.num_key_board);
        mPhoneNumberInp = view.findViewById(R.id.phone_number_input_box);
        mLoginBtn = view.findViewById(R.id.login_btn);
        if (mMainColor != -1) {
            mCheckBox.setTextColor(mMainColor);
        }
        if (mVerifyCodeSize != SIZE_DEFAULT_VERITY_CODE) {
            mVerifyCodeInputBox.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mVerifyCodeSize)});
        }
        disableCopyAndPaste(mVerifyCodeInputBox);
        disableCopyAndPaste(mPhoneNumberInp);
        // 更新按钮
        updateBtnStatus();
    }

    private void updateBtnStatus() {
        mVerifyCodeBtn.setEnabled(isPhoneNum);
        mLoginBtn.setEnabled(isPhoneNum && isAgreementOk && isVerifyCode);
    }


    private void initAttrs(@NonNull Context context, @Nullable AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LoginPageView);
        mVerifyCodeSize = ta.getInt(R.styleable.LoginPageView_verifyCodeSize, SIZE_DEFAULT_VERITY_CODE);
        mMainColor = ta.getResourceId(R.styleable.LoginPageView_mainColor, -1);
        ta.recycle();
    }

    // 定义接口事件

    public void setOnLoginPageActionListener(OnLoginPageActionListener listener) {
        this.mActionListener = listener;
    }

    public interface OnLoginPageActionListener {
        void onOpenAgreement();

        void onConfirmClick(String verifyCode, String phoneNum);

        void onSendVerifyCodeClick(String phoneNum);
    }

    /**
     * 禁用复制粘贴
     *
     * @param editText e
     */
    @SuppressLint("ClickableViewAccessibility")
    public void disableCopyAndPaste(final EditText editText) {
        try {
            if (editText == null) {
                return;
            }

            editText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
            editText.setLongClickable(false);
            editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        // setInsertionDisabled when user touches the view
                        setInsertionDisabled(editText);
                    }

                    return false;
                }
            });
            editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setInsertionDisabled(EditText editText) {
        try {
            Field editorField = TextView.class.getDeclaredField("mEditor");
            editorField.setAccessible(true);
            Object editorObject = editorField.get(editText);

            // if this view supports insertion handles
            Class editorClass = Class.forName("android.widget.Editor");
            Field mInsertionControllerEnabledField = editorClass.getDeclaredField("mInsertionControllerEnabled");
            mInsertionControllerEnabledField.setAccessible(true);
            mInsertionControllerEnabledField.set(editorObject, false);

            // if this view supports selection handles
            Field mSelectionControllerEnabledField = editorClass.getDeclaredField("mSelectionControllerEnabled");
            mSelectionControllerEnabledField.setAccessible(true);
            mSelectionControllerEnabledField.set(editorObject, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
