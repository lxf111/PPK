package com.uni.ppk.ui.mine.fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uni.commoncore.utils.ImageUtils;
import com.uni.commoncore.utils.JSONUtils;
import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.commoncore.utils.StringUtils;
import com.uni.commoncore.widget.CircleImageView;
import com.uni.ppk.MyApplication;
import com.uni.ppk.R;
import com.uni.ppk.api.HttpUtils;
import com.uni.ppk.api.MyCallBack;
import com.uni.ppk.api.NetUrlUtils;
import com.uni.ppk.base.LazyBaseFragments;
import com.uni.ppk.pop.SharePopup;
import com.uni.ppk.pop.TipsPopup;
import com.uni.ppk.ui.home.activity.VipListActivity;
import com.uni.ppk.ui.mine.activity.AboutActivity;
import com.uni.ppk.ui.mine.activity.CommonToolActivity;
import com.uni.ppk.ui.mine.activity.FeedbackActivity;
import com.uni.ppk.ui.mine.activity.HelpActivity;
import com.uni.ppk.ui.mine.activity.MyCourseActivity;
import com.uni.ppk.ui.mine.activity.MyPublicActivity;
import com.uni.ppk.ui.mine.activity.MyRecommendActivity;
import com.uni.ppk.ui.mine.activity.MyServiceActivity;
import com.uni.ppk.ui.mine.activity.MyVipActivity;
import com.uni.ppk.ui.mine.activity.OrderActivity;
import com.uni.ppk.ui.mine.activity.PersonEditorActivity;
import com.uni.ppk.ui.mine.activity.SettingActivity;
import com.uni.ppk.ui.mine.activity.TicketActivity;
import com.uni.ppk.ui.mine.bean.PersonDataBean;
import com.uni.ppk.utils.LoginCheckUtils;
import com.uni.ppk.utils.TipsUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/5
 * Time: 9:14
 * 个人中心
 */
public class MineFragment extends LazyBaseFragments {

    @BindView(R.id.view_top)
    View viewTop;
    @BindView(R.id.llyt_top)
    LinearLayout llytTop;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_order)
    TextView tvOrder;
    @BindView(R.id.tv_community)
    TextView tvCommunity;
    @BindView(R.id.tv_public)
    TextView tvPublic;
    @BindView(R.id.tv_course)
    TextView tvCourse;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.tv_ticket)
    TextView tvTicket;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;
    @BindView(R.id.tv_help)
    TextView tvHelp;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.llyt_mine)
    LinearLayout llytMine;
    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.iv_vip)
    ImageView ivVip;

    private SharePopup mSharePopup;//分享弹窗
    private PersonDataBean mDataBean;

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_mine, null);
        return mRootView;
    }

    @Override
    public void initView() {
        viewTop.getLayoutParams().height = StatusBarUtils.getStatusBarHeight(mContext);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (LoginCheckUtils.checkUserIsLogin(mContext)) {
            getData();
        } else {
            ivHeader.setImageResource(R.mipmap.ic_default_header);
            tvName.setText("未登录");
            ivVip.setVisibility(View.GONE);
        }
    }

    //获取用户资料
    private void getData() {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", "" + MyApplication.mPreferenceProvider.getUId());
        HttpUtils.mineData(mContext, params, new MyCallBack() {
            @Override
            public void onSuccess(String response, String msg) {
                mDataBean = JSONUtils.jsonString2Bean(response, PersonDataBean.class);
                if (mDataBean != null) {
                    ImageUtils.getPic(NetUrlUtils.createPhotoUrl(mDataBean.getHead_img()), ivHeader, mContext, R.mipmap.ic_default_header);
                    tvName.setText("" + mDataBean.getUser_nickname());
                    LoginCheckUtils.updateUserInfo(mDataBean);
                    LoginCheckUtils.updateLevel(mDataBean.getUser_vip());
                    if (!StringUtils.isEmpty(mDataBean.getUser_vip()) && Double.parseDouble(mDataBean.getUser_vip()) > 0) {
                        ivVip.setVisibility(View.VISIBLE);
                    } else {
                        ivVip.setVisibility(View.GONE);
                    }
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

    @OnClick({R.id.tv_name, R.id.tv_info, R.id.tv_order, R.id.tv_tool, R.id.tv_community, R.id.tv_public, R.id.tv_course, R.id.tv_recommend, R.id.tv_vip, R.id.tv_ticket, R.id.tv_share, R.id.tv_about, R.id.tv_feedback, R.id.tv_help, R.id.tv_setting, R.id.iv_header})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //个人资料
            case R.id.tv_name:
            case R.id.tv_info:
            case R.id.iv_header:
                if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    return;
                }
                MyApplication.openActivity(mContext, PersonEditorActivity.class);
                break;
            //服务订单
            case R.id.tv_order:
                if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    return;
                }
                MyApplication.openActivity(mContext, OrderActivity.class);
                break;
            //我的咨询
            case R.id.tv_community:
                if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    return;
                }
                MyApplication.openActivity(mContext, MyServiceActivity.class);
                break;
            //常用工具
            case R.id.tv_tool:
                MyApplication.openActivity(mContext, CommonToolActivity.class);
                break;
            //我的发布
            case R.id.tv_public:
                if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    return;
                }
                MyApplication.openActivity(mContext, MyPublicActivity.class);
                break;
            //我的课程
            case R.id.tv_course:
                if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    return;
                }
                MyApplication.openActivity(mContext, MyCourseActivity.class);
                break;
            //我的推荐
            case R.id.tv_recommend:
                if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    return;
                }
                MyApplication.openActivity(mContext, MyRecommendActivity.class);
                break;
            //我的会员
            case R.id.tv_vip:
                if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    return;
                }
                if ("0".equals(MyApplication.mPreferenceProvider.getLevel())) {
                    TipsUtils.show(mContext, tvAbout, "温馨提示", "您未开通vip会员，确定开通吗？", new TipsPopup.OnTipsCallback() {
                        @Override
                        public void submit() {
                            MyApplication.openActivity(mContext, VipListActivity.class);
                        }

                        @Override
                        public void cancel() {

                        }
                    });
                    return;
                }
                MyApplication.openActivity(mContext, MyVipActivity.class);
                break;
            //我的发票
            case R.id.tv_ticket:
                if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    return;
                }
                MyApplication.openActivity(mContext, TicketActivity.class);
                break;
            //推荐给好友
            case R.id.tv_share:
                mSharePopup = new SharePopup(mContext);
                mSharePopup.showAtLocation(tvSetting, Gravity.BOTTOM, 0, 0);
                break;
            //关于诉问
            case R.id.tv_about:
//                getAbout();
                MyApplication.openActivity(mContext, AboutActivity.class);
                break;
            //意见反馈
            case R.id.tv_feedback:
                if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    return;
                }
                MyApplication.openActivity(mContext, FeedbackActivity.class);
                break;
            //帮助中心
            case R.id.tv_help:
                MyApplication.openActivity(mContext, HelpActivity.class);
                break;
            //设置
            case R.id.tv_setting:
                MyApplication.openActivity(mContext, SettingActivity.class);
                break;
        }
    }
}
