package com.amazingdata.ecare.ui.order;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseBindingRVAdapter;
import com.amazingdata.ecare.databinding.ShoppingcartItemBinding;
import com.amazingdata.ecare.entity.ShoppingCartItem;

import java.util.ArrayList;

/**
 * @author Xiong
 * @date 2019/1/25 - 10:56
 */
// 购物车条目列表adapter适配器
public class ShoppingCartListAdapter extends BaseBindingRVAdapter<ShoppingCartItem, ShoppingcartItemBinding, ViewDataBinding, ViewDataBinding> {

    private onCartItemChangeListener mOnCartItemChangeListener;

    public void setOnCartItemChangeListener(onCartItemChangeListener onCartItemChangeListener) {
        this.mOnCartItemChangeListener = onCartItemChangeListener;
    }

    // 自定义的监听购物条目数据(状态)改变的接口
    public interface onCartItemChangeListener {
        // 点击当前条目的图标
        void iconClick(ShoppingCartItem data);

        // 当前条目的数目改变
        void numberChange(int position);

        // 通过editText输入的数据有误
        void inputError(int position);

        // 改变当前条目的选中状态
        void checkMode(boolean none);
    }

    public ShoppingCartListAdapter(Context mContext, ObservableArrayList<ShoppingCartItem> mItems) {
        super(mContext, mItems);
    }

    @Override
    protected int getHeaderLayoutResId() {
        return 0;
    }

    @Override
    protected int getItemLayoutResId() {
        return R.layout.shoppingcart_item;
    }

    @Override
    protected int getFooterLayoutResId() {
        return 0;
    }

    @Override
    protected void onBindHeader(ViewDataBinding binding) {

    }

    // 设置全选
    public void selectAll() {
        if (null != selectedIndexList) {
            if (selectedIndexList.size() == mItems.size())
                return;
            else {
                selectedIndexList.clear();
                for (int i = 0; i < mItems.size(); i++)
                    selectedIndexList.add(i);
                // 更新adapter的数据和结构
                notifyDataSetChanged();
            }
        } else {
            selectedIndexList = new ArrayList<>();
            for (int i = 0; i < mItems.size(); i++)
                selectedIndexList.add(i);
            notifyDataSetChanged();
        }
    }

    // 设置全不选
    public void selectNone() {
        if (null == selectedIndexList || selectedIndexList.size() == 0)
            return;
        else {
            selectedIndexList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    protected void onBindItem(final ShoppingcartItemBinding binding, final ShoppingCartItem item, final int position) {
        // 绑定数据和XML——binding对象
        binding.setVariable(BR.shoppingCartItem, item);
        binding.executePendingBindings();

        // 设置当前条目的选中状态
        if (null != selectedIndexList)
            binding.itemcartChooseCb.setChecked(selectedIndexList.contains(position));

        // 对当前条目设置选中状态改变设置的监听器
        binding.itemcartChooseCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (null != mOnCartItemChangeListener) {
                    // 初始化选中index列表
                    if (null == selectedIndexList)
                        selectedIndexList = new ArrayList<>();
                    if (isChecked) { // 如果被选中
                        if (!selectedIndexList.contains(position)) // 意外排除,正常不会包含
                            selectedIndexList.add(position);
                    } else { // 如果不被选中
                        if (selectedIndexList.contains(position))
                            // remove对象或者index,因为对象和index都是int,所以有些不同
                            selectedIndexList.remove(selectedIndexList.indexOf(position));
                    }
                    if (selectedIndexList.isEmpty()) // 如果当前选中列表为空
                        mOnCartItemChangeListener.checkMode(true);
                    else // 如果当前选中诶表不为空
                        mOnCartItemChangeListener.checkMode(false);
                }
            }
        });

        // 对当前条目图标设置点击监听
        binding.itemcartIconIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnCartItemChangeListener)
                    mOnCartItemChangeListener.iconClick(item);
            }
        });

        // 对当前条目的减号设置点击监听
        binding.itemcartMinusIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnCartItemChangeListener) {
                    // 判断当前数目是否为1
                    boolean isOnly = item.getGoodsNum() == 1;
                    if (!isOnly) { // 如果不为1,则设置数量-1,并设置监听
                        item.setGoodsNum(item.getGoodsNum() - 1);
                        mOnCartItemChangeListener.numberChange(position);
                    }
                }
            }
        });

        // 对当前条目的加号设置点击监听
        binding.itemcartAddIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnCartItemChangeListener) {
                    item.setGoodsNum(item.getGoodsNum() + 1);
                    mOnCartItemChangeListener.numberChange(position);
                }
            }
        });

        // 对当前条目的editText输入设置数据变化监听
        binding.itemcartInputnumEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 如果当前editText焦点没有被获取(editText没被选中)
                if (!binding.itemcartInputnumEt.isFocusable())
                    return;
                // 如果当前的数据为空
                if (TextUtils.isEmpty(s.toString()))
                    return;
                int newNum = Integer.parseInt(String.valueOf(s));
                // 如果当前的数据与原本数据一样(因为加减号的点击也会触发该监听)
                // 通过判断数据是否与原本一样来过滤
                if (newNum == item.getGoodsNum())
                    return;
                if (newNum <= 0) { // 如果数据小于0无效,设置输入错误的监听
                    if (null != mOnCartItemChangeListener)
                        mOnCartItemChangeListener.inputError(position);
                } else { // 如果数据有效,更新当前条目的数据并设置数据改变监听
                    if (null != mOnCartItemChangeListener) {
                        item.setGoodsNum(newNum);
                        mOnCartItemChangeListener.numberChange(position);
                    }
                }
            }
        });
    }

    @Override
    protected void onBindFooter(ViewDataBinding binding) {

    }
}
