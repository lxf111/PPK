package com.uni.ppk.ui.home.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;

import com.uni.ppk.R;
import com.uni.ppk.adapter.AFinalRecyclerViewAdapter;
import com.uni.ppk.adapter.BaseRecyclerViewHolder;
import com.uni.ppk.ui.home.bean.BookTitleBean;
import com.uni.ppk.ui.home.bean.OrderClassifyBean;
import com.uni.ppk.widget.CustomRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2020/9/11
 * Time: 10:56
 */
public class BookTitleAdapter extends AFinalRecyclerViewAdapter<BookTitleBean> {

    public BookTitleAdapter(Activity ctx) {
        super(ctx);
    }

    @Override
    protected BaseRecyclerViewHolder onCreateCustomerViewHolder(ViewGroup parent, int viewType) {
        return new BookTitleViewHolder(mInflater.inflate(R.layout.item_book_title, parent, false));
    }

    @Override
    protected void onBindCustomerViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((BookTitleViewHolder) holder).setContent(getItem(position), position);
    }

    protected class BookTitleViewHolder extends BaseRecyclerViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.rlv_classify)
        CustomRecyclerView rlvClassify;

        public BookTitleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void setContent(BookTitleBean mBean, int position) {

            tvTitle.setText("" + mBean.getCname());

            rlvClassify.setLayoutManager(new GridLayoutManager(mContext, 4));
            OrderClassifyAdapter mClassifyAdapter = new OrderClassifyAdapter(mActivity);
            rlvClassify.setAdapter(mClassifyAdapter);
            mClassifyAdapter.setOnItemClickListener(new OnItemClickListener<OrderClassifyBean>() {
                @Override
                public void onItemClick(View view, int position, OrderClassifyBean model) {
//                    if (model.isSelect()) {
//                        model.setSelect(false);
//                    } else {
//                        for (int i = 0; i < mClassifyAdapter.getList().size(); i++) {
//                            mClassifyAdapter.getList().get(i).setSelect(false);
//                        }
//                        model.setSelect(true);
//                    }
//                    notifyDataSetChanged();
                    if (onClassifyItemCallback != null) {
                        onClassifyItemCallback.onClassifyCallback(model, position);
                    }
                }

                @Override
                public void onItemLongClick(View view, int position, OrderClassifyBean model) {

                }
            });
            mClassifyAdapter.refreshList(mBean.getData());
        }
    }

    private OnClassifyItemCallback onClassifyItemCallback;

    public void setOnClassifyItemCallback(OnClassifyItemCallback onClassifyItemCallback) {
        this.onClassifyItemCallback = onClassifyItemCallback;
    }

    public interface OnClassifyItemCallback {
        void onClassifyCallback(OrderClassifyBean model, int position);
    }

}
