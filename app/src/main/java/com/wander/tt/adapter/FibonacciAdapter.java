package com.wander.tt.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.util.AsyncListUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wander on 2017/1/13.
 */
public class FibonacciAdapter extends RecyclerView.Adapter<FibonacciAdapter.FibonacciViewHolder> {
    int count;
    private Context context;
    private List<BigInteger> mList;
    private LoadListener mListener;
    private boolean isAsc = true;
    private Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                int num = msg.arg1;
                count += num;
                notifyItemRangeInserted(count - num, num);
                if (mListener != null) {
                    mListener.onLoadSuc();
                }
            }
            return false;
        }
    });
    private int MAX_NUM = 10;


    public void addDate(int num) {
        if (num < 1) {
            return;
        }
        if (mListener != null) {
            mListener.onStartLoad();
        }
        initFibonacci(num);

    }

    private void initFibonacci(final int num) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mList == null) {
                    mList = new LinkedList<>();
                    mList.add(BigInteger.valueOf(0));
                    mList.add(BigInteger.valueOf(1));
                }
                for (int i = 0; i < num; i++) {
                    BigInteger temp = mList.get(mList.size() - 1).add(mList.get(mList.size() - 2));
                    if (isAsc) {
                        mList.add(temp);
                    } else {
                        mList.add(0, temp);
                    }
                }
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = num;
                mHandler.sendMessage(msg);
            }
        }).start();


    }

    public FibonacciAdapter(Context context) {
        this.context = context;
        addDate(50);
    }

    @Override
    public FibonacciViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new FibonacciViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FibonacciViewHolder holder, int position) {
        BigInteger bigInteger = mList.get(position);
        String result = bigInteger.toString();
        result = getScientific(result, MAX_NUM);
        String index = String.valueOf(position);
        String text = index + "\t\t\t" + result;
        SpannableString spanString = new SpannableString(text);
        spanString.setSpan(new ForegroundColorSpan(Color.RED),
                0, index.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.mTextView.setText(spanString);


    }

    /**
     * 科学技术法
     *
     * @param bigString
     * @param maxLength 保留的最大位数
     * @return
     */
    private String getScientific(String bigString, int maxLength) {
        if (TextUtils.isEmpty(bigString) || bigString.length() < maxLength) {
            return bigString;
        }
        String s1 = bigString.substring(0, 1);
        String s2 = bigString.substring(1, 10);

        String scientific = s1 + "." + s2 + "E" + (bigString.length() - 1);
        return scientific;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public int getItemCount() {
        return count;
    }


    /**
     * 源码反转比我构造链表要牛逼一点，虽然原理一样
     */

    public void reverse() {
        isAsc = !isAsc;
        Collections.reverse(mList);
    }

    class FibonacciViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public FibonacciViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    public void setLoadingListener(LoadListener mListener) {
        this.mListener = mListener;
    }

    public interface LoadListener {
        void onStartLoad();

        void onLoadSuc();
    }

    public boolean isAsc() {
        return isAsc;
    }

    public void setAsc(boolean asc) {
        isAsc = asc;
    }
}
