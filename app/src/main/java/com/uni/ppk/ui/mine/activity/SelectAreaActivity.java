package com.uni.ppk.ui.mine.activity;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.uni.commoncore.utils.StatusBarUtils;
import com.uni.ppk.R;
import com.uni.ppk.base.BaseActivity;
import com.uni.ppk.ui.mine.adapter.SelectAreaAdapter;
import com.uni.ppk.ui.mine.bean.SelectAreaBean;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/6/17
 * Time: 9:32
 * 选择区或者州
 */
public class SelectAreaActivity extends BaseActivity {
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.lv_area)
    ListView lvArea;

    private SelectAreaAdapter mAdapter;

    private String mSearch = "";//搜索的地址或者邮编

    private int mType = 1;//1区 2州

    private SelectAreaBean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_area;
    }

    @Override
    protected void initData() {

        mType = getIntent().getIntExtra("type", 1);

        if (mType == 1) {
            initTitle("" + getString(R.string.select_area));
        } else {
            initTitle("" + getString(R.string.select_state));
        }
        getData();

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mSearch = v.getText().toString().trim();
                    getData();
//                    KeyBoardUtils.hideKeyboard(edtSearch);
                    return true;
                }
                return false;
            }
        });
    }

    private void getData() {
//        StyledDialogUtils.getInstance().loading(this);
//        BaseOkHttpClient.newBuilder().url(NetUrlUtils.ADDRESS_AREA)
//                .addParam("search_keyword", "" + mSearch)
//                .addParam("area_level", "" + mType)
//                .post()
//                .json()
//                .build().enqueue(this, new BaseCallBack<String>() {
//            @Override
//            public void onSuccess(String result, String msg) {
//                StyledDialogUtils.getInstance().dismissLoading();
//                mBean = JSONUtils.jsonString2Bean(result, SelectAreaBean.class);
//                mAdapter = new SelectAreaAdapter(SelectAreaActivity.this, mBean.getArea_info());
//                lvArea.setAdapter(mAdapter);
//                lvArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Intent intent = new Intent();
//                        intent.putExtra("bean", mBean.getArea_info().get(position));
//                        setResult(RESULT_OK, intent);
//                        finish();
//                    }
//                });
//            }
//
//            @Override
//            public void onError(int code, String msg) {
//                StyledDialogUtils.getInstance().dismissLoading();
//                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                StyledDialogUtils.getInstance().dismissLoading();
//                Toast.makeText(mContext, "服务器异常", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
        StatusBarUtils.setStatusBarColor(this, R.color.theme);
        StatusBarUtils.setAndroidNativeLightStatusBar(this, false);
    }
}
