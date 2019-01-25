package com.amazingdata.ecare.ui.order;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseFragment;
import com.amazingdata.ecare.base.DialogListenerUtils;
import com.amazingdata.ecare.base.VMConnectedListenerUtils;
import com.amazingdata.ecare.databinding.FragmentOrderBinding;
import com.amazingdata.ecare.entity.ShoppingCartItem;
import com.amazingdata.ecare.utils.LinearSpacesItemDecoration;
import com.amazingdata.ecare.utils.ToastUtils;

/**
 * @author Xiong
 * @date 2019/1/18 - 14:42
 */
// 订单页面
public class OrderFragment extends BaseFragment<FragmentOrderBinding, OrderViewModel> {

    // 购物项列表的适配器
    private ShoppingCartListAdapter mAdapter;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_order;
    }

    @Override
    public int initVariableId() {
        return BR.orderViewModel;
    }

    @Override
    public void initData() {
        initAdapter();

        initViewUIChangeListener();

        // 初始化当前adapter中的数据
        viewModel.initAdapterData();
    }

    // 设置当前View视图UI变化的监听器
    private void initViewUIChangeListener() {
        // 设置假进度条的监听器
        viewModel.setProgressDialogListener(new DialogListenerUtils.ProgressDialogListener() {
            @Override
            public void show(String content) { // 进度条出现的回调监听
                showProgressDialog(content);
            }

            @Override
            public void dissmiss() { // 进度条小时的回调监听
                dismissProgressDialog();
                // 更新adapter的数据和结构
                onAdapterDatasChange();
            }
        });

        // 设置响应ViewModel变化的监听器
        viewModel.setOnViewChangeUI(new VMConnectedListenerUtils.onViewChangeUI() {
            @Override
            public void changeUI(Object o) {
                int mode = (int) o;
                switch (mode) {
                    case OrderViewModel.MODE_DELETE_CLICK:
                        ToastUtils.showShort("删除");
                        // 删除viewModel中的adapter观察者数据
                        viewModel.deleteData(mAdapter.getSelectedIndexList());
                        // adapter设置一个不选中
                        mAdapter.selectNone();
                        // 更新底部管理栏数据
                        viewModel.updateBottom(mAdapter.getSelectedIndexList());
                        break;
                    case OrderViewModel.MODE_PAY_CLICK:
                        ToastUtils.showShort("支付");
                        // ...
                        break;
                    case OrderViewModel.MODE_GETALL_CHECK:
                        // adapter设置全选
                        mAdapter.selectAll();
                        break;
                    case OrderViewModel.MODE_GETALL_UNCHECK:
                        // adapter设置一个不选
                        mAdapter.selectNone();
                        break;
                }
            }
        });
    }

    // 初始化adapter
    private void initAdapter() {
        mAdapter = new ShoppingCartListAdapter(getActivity(), viewModel.datas);
        // 设置对不可点击的不响应
        mAdapter.setResponseItemDisableClick(true);
        binding.fgorderOrderlistRv.setAdapter(mAdapter);
        // 设置item边距
        binding.fgorderOrderlistRv.addItemDecoration(new LinearSpacesItemDecoration(10));

        // 设置购物车条目改变的监听
        mAdapter.setOnCartItemChangeListener(new ShoppingCartListAdapter.onCartItemChangeListener() {
            @Override
            public void iconClick(ShoppingCartItem data) { // 点击icon图标的回调监听
                // 携带购物车条目数据跳转进药品详情界面
                ToastUtils.showShort(data.toString());
            }

            @Override
            public void numberChange(final int position) { // 购物车条目数目的回调监听
                // 当RecycleView在动态计算布局和当其在滑动时,不能直接
                // 对其adapter适配器中的数据和结构进行动态更新
                // 必须通过Handler将此消息添加到消息队列的末尾,等处理完后才处理adapter更新请求
                if (binding.fgorderOrderlistRv.isComputingLayout() ||
                        binding.fgorderOrderlistRv.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            // 更新底部操作栏
                            viewModel.updateBottom(mAdapter.getSelectedIndexList());
                            // 更新adapter数据和结构和重新设置监听事件
                            // (在删除RecycleView中的数据后,单单进行notifyItemChanged
                            // 只能更新item的数据而不能更新item的结构——比如监听等)
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                } else
                    onAdapterDatasChange();
            }

            @Override
            public void inputError(int position) { // 通过editText输入错误的回调监听
                ToastUtils.showShort("输入的数目不得小于1");
                onAdapterDatasChange();
            }

            @Override
            public void checkMode(boolean none) { // 选中项目为是否为空的回调监听
                // 设置底部管理栏的可见性
                viewModel.showManager.set(none ? View.INVISIBLE : View.VISIBLE);
                if (!none) // 如果有选中,则更新当前底部管理栏的响应数据
                    viewModel.updateBottom(mAdapter.getSelectedIndexList());
            }
        });
    }

    // 更新底部栏和adapter数据和结构
    private void onAdapterDatasChange() {
        viewModel.updateBottom(mAdapter.getSelectedIndexList());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
