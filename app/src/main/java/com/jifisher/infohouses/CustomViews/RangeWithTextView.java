package com.jifisher.infohouses.CustomViews;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.appyvet.materialrangebar.RangeBar;
import com.jifisher.infohouses.MainActivity;
import com.jifisher.infohouses.R;

public class RangeWithTextView extends RelativeLayout implements RangeBar.OnRangeBarChangeListener {
    public AutoResizeTextView leftText;
    public AutoResizeTextView rightText;
    public RangeBar rangebar1;

    public boolean flagPrice = false;
    public boolean flagEndBuild = false;

    public RangeWithTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public RangeWithTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public RangeWithTextView(Context context) {
        super(context);
        initialize();
    }


    public void initialize() {
        LayoutInflater li = ((MainActivity) getContext()).getLayoutInflater();

        li.inflate(R.layout.view_range_with_text, this);
        this.rightText = findViewById(R.id.rightText);
        this.leftText = findViewById(R.id.leftText);
        this.rangebar1 = findViewById(R.id.rangebar1);
        rightText.setTextColor(Color.BLACK);
        leftText.setTextColor(Color.BLACK);
        setLeftPosition(12);
        setRigthPosition(89);
        rangebar1.setOnRangeBarChangeListener(this);
    }


    public void setMin(int min) {
        rangebar1.setTickEnd(Math.max(min + 3, rangebar1.getTickEnd()));
        rangebar1.setLeft(Math.max(min, rangebar1.getLeftIndex()));
        rangebar1.setTickStart(min);
    }

    public void setMax(int max) {
        flagPrice = max == 990;
        flagEndBuild = max == 40;
        rangebar1.setTickEnd(max);
    }

    public void setLeftPosition(int left) {
        rangebar1.setLeft(left);
        leftText.setText(left + "");
    }

    public void setRigthPosition(int right) {
        rangebar1.setRight(right);
        rightText.setText(right + "");
    }


    @Override
    public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
        leftPinIndex = Integer.parseInt(leftPinValue);
        rightPinIndex = Integer.parseInt(rightPinValue);
        if (!flagPrice && !flagEndBuild) {
            leftText.setText(leftPinValue);
            rightText.setText(rightPinValue);
        } else {
            if (flagEndBuild) {
                leftText.setText(leftPinIndex % 4 + 1 + "кв." + (leftPinIndex / 4 + 2015));
                rightText.setText(rightPinIndex % 4 + 1 + "кв." + (rightPinIndex / 4 + 2015));
            } else {
                leftPinIndex = leftPinIndex * 10000;
                rightPinIndex = rightPinIndex * 10000;

                leftText.setText((leftPinIndex >= 1000000) ? ((leftPinIndex / 10000) / 100f + "млн") : (leftPinIndex >= 1000) ? ((leftPinIndex / 1000) + "к") : (leftPinIndex + ""));
                rightText.setText((rightPinIndex >= 1000000) ? ((rightPinIndex / 10000) / 100f + "млн") : (rightPinIndex >= 1000) ? ((rightPinIndex / 1000) + "к") : (rightPinIndex + ""));
            }
        }
    }
}


