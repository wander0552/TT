package com.wander.tt.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigInteger;
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
    private BigInteger MAX_NUM = BigInteger.valueOf((long) Math.pow(10, 10));


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
                    mList.add(temp);
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
        if (bigInteger.compareTo(MAX_NUM) > 1) {

        }
        String index = String.valueOf(position);
        String text = index + "\t" + bigInteger.doubleValue();
        SpannableString spanString = new SpannableString(text);
        spanString.setSpan(new ForegroundColorSpan(Color.RED),
                0, index.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.mTextView.setText(spanString);


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

    class FibonacciViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;

        public FibonacciViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    public void setmListener(LoadListener mListener) {
        this.mListener = mListener;
    }

    public interface LoadListener {
        void onStartLoad();

        void onLoadSuc();
    }
}
