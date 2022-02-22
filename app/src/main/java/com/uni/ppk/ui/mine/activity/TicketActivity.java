package com.uni.ppk.ui.mine.activity;

import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uni.commoncore.utils.InputCheckUtil;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.utils.ToastUtils;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.pop.VideoWarnPopup;
import com.uni.ppk.ui.mine.bean.TicketBean;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/16
 * Time: 11:14
 * 我的发票
 */
public class TicketActivity extends BaseActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.llyt_title)
    LinearLayout llytTitle;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_number)
    EditText edtNumber;
    @BindView(R.id.edt_address)
    EditText edtAddress;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_bank_name)
    EditText edtBankName;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ticket;
    }

    @Override
    protected void initData() {
        initTitle("我的发票");
        getData();
    }

    private void getData() {
        Map<String, Object> params = new HashMap<>();
        HttpUtils.getTicket(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                TicketBean bean = JSONUtils.jsonString2Bean(response, TicketBean.class);
                if (bean != null) {
                    edtName.setText("" + bean.getName());
                    edtNumber.setText("" + bean.getTax_num());
                    edtAddress.setText("" + bean.getAddress());
                    edtBankName.setText("" + bean.getAccount_bank());
                    edtEmail.setText("" + bean.getEmail());
                    edtPhone.setText("" + bean.getPhone());
                }
            }

            @Override
            public void onError(String msg, int code) {
            }

            @Override
            public void onFail(Call call, IOException e) {
            }
        });
    }

    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
        //立即申请
        submit();
    }

    private void submit() {
        String name = edtName.getText().toString().trim();
        String number = edtNumber.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String bankName = edtBankName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        if (StringUtils.isEmpty(name)) {
            ToastUtils.show(mContext, "请输入名称");
            return;
        }
//        if (StringUtils.isEmpty(number)) {
//            ToastUtils.show(mContext, "请输入纳税人识别号");
//            return;
//        }
        if (StringUtils.isEmpty(address)) {
            ToastUtils.show(mContext, "请输入地址");
            return;
        }
        if (StringUtils.isEmpty(phone)) {
            ToastUtils.show(mContext, "请输入手机号");
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(phone)) {
            ToastUtils.show(mContext, "请输入正确的手机号");
            return;
        }
        if (StringUtils.isEmpty(bankName)) {
            ToastUtils.show(mContext, "请输入开户行及账号");
            return;
        }
        if (StringUtils.isEmpty(email)) {
            ToastUtils.show(mContext, "请输入邮箱");
            return;
        }
        if (!InputCheckUtil.isEmail(email)) {
            ToastUtils.show(mContext, "请输入正确的邮箱");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("name", "" + name);
        params.put("tax_num", "" + number);
        params.put("address", "" + address);
        params.put("account_bank", "" + bankName);
        params.put("phone", "" + phone);
        params.put("email", "" + email);
        HttpUtils.applyTicket(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                ToastUtils.show(mContext, msg);
                VideoWarnPopup videoWarnPopup = new VideoWarnPopup(mContext);
                videoWarnPopup.setContent("您的发票申请已成功提交");
                videoWarnPopup.setTitle("提示");
                videoWarnPopup.showAtLocation(tvSubmit, Gravity.CENTER, 0, 0);
                videoWarnPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        finish();
                    }
                });
//                finish();
            }

            @Override
            public void onError(String msg, int code) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFail(Call call, IOException e) {
                ToastUtils.show(mContext, getString(R.string.service_error));
            }
        });
    }

}
