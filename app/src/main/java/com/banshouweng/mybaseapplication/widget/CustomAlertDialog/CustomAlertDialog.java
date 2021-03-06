package com.banshouweng.mybaseapplication.widget.CustomAlertDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.banshouweng.mybaseapplication.R;


/**
 * 《一个Android工程的从零开始》
 *
 * @author 半寿翁
 * @博客：
 * @CSDN http://blog.csdn.net/u010513377/article/details/74455960
 * @简书 http://www.jianshu.com/p/1410051701fe
 */
public class CustomAlertDialog {

    /**
     * CANCEL_ID    取消按键的ID
     * CONFIRM_ID   确认按键的ID
     */
    public static final int CANCEL_ID = R.id.dialog_cancel;
    public static final int CONFIRM_ID = R.id.dialog_confirm;

    public static final String NO_NET_TAG = "no_net_tag";

    /**
     * 上下文信息
     */
    private Context context;
    /**
     * 弹窗识别标签：如果同一个页面需要调用多个弹窗的时候，根据tag去判断点击事件
     */
    private String tag;
    /**
     * 弹窗页面点击事件回调接口
     */
    private OnDialogClickListener listener;
    /**
     * 提示弹窗
     */
    private AlertDialog myDialog;

    /**
     * dialogTitle      ： 弹窗标题
     * dialogContent    ： 弹窗信息
     * dialogCancel     ： 弹窗取消键
     * dialogConfirm    ： 弹窗确认键
     */
    private TextView dialogTitle, dialogContent, dialogCancel, dialogConfirm;
    /**
     * 弹窗取消键与确认键的中线
     */
    private View dialogMiddleLine;
    /**
     * title      ： 标题
     * content    ： 信息
     * cancel     ： 取消键文本
     * confirm    ： 确认键文本
     */
    private String title, content, cancel, confirm;
    /**
     * 是否只显示确认键（只显示一个按键）
     */
    private boolean isOnlyMakeSure = false;
    /**
     * 是否只显示确认键（只显示一个按键）
     */
    private boolean isTouchOutsideCanDismiss = true;

    private DialogInterface.OnKeyListener onKeyListener;

    /**
     * 设置标题
     *
     * @param title 标题
     */
    protected void setTitle(String title) {
        this.title = title;
    }

    /**
     * 设置信息
     *
     * @param content 信息
     */
    protected void setContent(String content) {
        this.content = content;
    }

    /**
     * 设置取消按键文字
     *
     * @param cancel 取消按键文字
     */
    protected void setCancel(String cancel) {
        this.cancel = cancel;
    }

    /**
     * 设置确认按键文字
     *
     * @param confirm 确认按键文字
     */
    protected void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    /**
     * 只显示确认键
     */
    protected void onlyMakeSure() {
        isOnlyMakeSure = true;
    }

    /**
     * 构造提示弹窗
     *
     * @param context  上下文
     * @param tag      弹窗识别标签
     * @param listener 弹窗点击事件
     */
    protected CustomAlertDialog(Context context, String tag, OnDialogClickListener listener) {
        this.context = context;
        this.tag = tag;
        this.listener = listener;
    }

    void setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        this.onKeyListener = onKeyListener;
    }

    /**
     * 初始化弹窗
     */
    private void initDialog() {
        // 创建弹窗
        myDialog = new AlertDialog.Builder(context).create();
        myDialog.show();

        // 获取弹窗布局
        myDialog.getWindow().setContentView(R.layout.alert_dialog);

        myDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return false;
            }
        });
        myDialog.setOnKeyListener(onKeyListener != null ? onKeyListener : new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return false;
            }
        });

        // 初始化取消键
        dialogCancel = (TextView) myDialog.getWindow().findViewById(R.id.dialog_cancel);
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                if (listener != null) {
                    listener.onClick(tag, v);
                }
            }
        });

        // 初始化确认键
        dialogConfirm = (TextView) myDialog.getWindow().findViewById(R.id.dialog_confirm);
        dialogConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                if (listener != null) {
                    listener.onClick(tag, v);
                }
            }
        });

        // 初始化内容控件
        dialogContent = (TextView) myDialog.getWindow().findViewById(R.id.dialog_content);
        // 初始化标题控件
        dialogTitle = (TextView) myDialog.getWindow().findViewById(R.id.dialog_title);
        // 初始化中线控件
        dialogMiddleLine = myDialog.getWindow().findViewById(R.id.dialog_middle_line);
    }

    /**
     * 弹窗外点击不允许弹窗消失
     */
    void touchOutside() {
        isTouchOutsideCanDismiss = false;
    }

    /**
     * 展示方法
     */
    protected void show() {
        initDialog();
        myDialog.setCanceledOnTouchOutside(isTouchOutsideCanDismiss);
        if (! TextUtils.isEmpty(title)) {
            dialogTitle.setText(title);
        }
        if (! TextUtils.isEmpty(content)) {
            dialogContent.setText(content);
            dialogContent.setVisibility(View.VISIBLE);
        }
        if (! TextUtils.isEmpty(cancel)) {
            dialogCancel.setText(cancel);
        }
        if (! TextUtils.isEmpty(confirm)) {
            dialogConfirm.setText(confirm);
        }
        if (isOnlyMakeSure) {
            dialogMiddleLine.setVisibility(View.GONE);
            dialogCancel.setVisibility(View.GONE);
        }
    }

    public void dismiss() {
        if (myDialog != null) {
            myDialog.dismiss();
        }
    }
}
