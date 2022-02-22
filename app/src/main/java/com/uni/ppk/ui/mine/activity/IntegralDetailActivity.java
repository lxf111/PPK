package com.uni.ppk.ui.mine.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.ppk.R;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.ui.mine.adapter.MyAccountAdapter;
import com.uni.ppk.ui.mine.bean.AccountListBean;
import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyItemDialogListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 积分明细
 */
public class IntegralDetailActivity extends BaseActivity {
    @BindView(R.id.tv_year)
    TextView tvYear;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.lv_account)
    ListView lvAccount;
    @BindView(R.id.tv_total_consume)
    TextView tvTotalConsume;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;

    private int mYear;//年份
    private int mMonth;//月份

    private List<String> mShowList = new ArrayList<>();//显示年份和月份的数据

    private MyAccountAdapter adapter;//账户明细适配器

    private AccountListBean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_account;
    }

    @Override
    protected void initData() {
        initTitle("积分明细");

        getData();
    }

    private void getData() {
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH) + 1;// Java月份从0开始算  

//        StyledDialogUtils.getInstance().loading(this);
//        BaseOkHttpClient.newBuilder().url(NetUrlUtils.MINE_MY_ACCOUNT_LIST)
//                .addParam("user_id", "" + MegaMartApplication.mPreferenceProvider.getUId())
//                .addParam("bal_month", "" + mMonth)
//                .addParam("bal_year", "" + mYear)
//                .post()
//                .json()
//                .build().enqueue(this, new BaseCallBack<String>() {
//            @Override
//            public void onSuccess(String result, String msg) {
//                StyledDialogUtils.getInstance().dismissLoading();
//                toast(msg);
//                mBean = JSONUtils.jsonString2Bean(result, AccountListBean.class);
//                adapter = new MyAccountAdapter(IntegralDetailActivity.this, mBean.getBill_list());
//                lvAccount.setAdapter(adapter);
//                llytNoData.setVisibility(View.GONE);
//                tvTotalConsume.setText("" + getString(R.string.account_total_consume) + mBean.getBal_total());
//            }
//
//            @Override
//            public void onError(int code, String msg) {
//                StyledDialogUtils.getInstance().dismissLoading();
//                toast(msg);
//                llytNoData.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                StyledDialogUtils.getInstance().dismissLoading();
//                toast("服务器异常");
//                llytNoData.setVisibility(View.VISIBLE);
//            }
//        });
    }

    @OnClick({R.id.tv_year, R.id.tv_month})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //选择年
            case R.id.tv_year:
                mShowList.clear();
                for (int i = 2; i >= 0; i--) {
                    mShowList.add("" + (mYear - i) + "年");
                }
                StyledDialog.init(IntegralDetailActivity.this);
                StyledDialog.buildBottomItemDialog(mShowList, "取消", new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        mYear = Integer.parseInt(mShowList.get(position).replace("年", ""));
                        getData();
                        tvYear.setText(mShowList.get(position).replace("年", ""));
                    }
                }).show();
                break;
            //选择月
            case R.id.tv_month:
                mShowList.clear();
                for (int i = 1; i <= 12; i++) {
                    mShowList.add(i + "月");
                }
                StyledDialog.init(IntegralDetailActivity.this);
                StyledDialog.buildBottomItemDialog(mShowList, "取消", new MyItemDialogListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        mMonth = Integer.parseInt(mShowList.get(position).replace("月", ""));
                        getData();
                        tvMonth.setText(mShowList.get(position).replace("月", ""));
                    }
                }).show();
                break;
        }
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtils.setStatusBarColor(this, R.color.theme);
        StatusBarUtils.setAndroidNativeLightStatusBar(this, false);
    }

}
