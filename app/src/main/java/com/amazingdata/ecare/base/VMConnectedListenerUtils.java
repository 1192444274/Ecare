package com.amazingdata.ecare.base;

/**
 * @author Xiong
 * @date 2019/1/23 - 14:18
 */
public class VMConnectedListenerUtils {
    public interface ListItemListener<D> {
        void onItemClick(D d);
    }

    public interface onViewChangeUI {
        void changeUI(Object o);
    }
}
