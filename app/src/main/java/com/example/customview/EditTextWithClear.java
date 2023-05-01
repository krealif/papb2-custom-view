package com.example.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

public class EditTextWithClear extends AppCompatEditText {

    Drawable clearButtonIcon;

    // define clear button
    private void init() {
        clearButtonIcon = ResourcesCompat.getDrawable(getResources(),
                R.drawable.baseline_clear_opaque_24, null);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (getCompoundDrawablesRelative()[2] != null) {

                    boolean isButtonClicked = false;
                    // LTR
                    if (getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
                        final float clearButtonStartPosition = (getWidth()-getPaddingEnd()-clearButtonIcon.getIntrinsicWidth());
                        if (event.getX() > clearButtonStartPosition) {
                            isButtonClicked = true;
                        }
                    // RTL
                    } else {
                        final float clearButtonEndPosition = getPaddingEnd()+clearButtonIcon.getIntrinsicWidth();
                        if (event.getX() < clearButtonEndPosition) {
                            isButtonClicked = true;
                        }
                    }

                    if (isButtonClicked) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            clearButtonIcon = ResourcesCompat.getDrawable(getResources(),
                                    R.drawable.baseline_clear_black_24, null);
                            showClearButton();
                        }

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            clearButtonIcon = ResourcesCompat.getDrawable(getResources(),
                                    R.drawable.baseline_clear_opaque_24, null);
                            showClearButton();
                            getText().clear();
                            hideClearButton();
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }

    public EditTextWithClear(@NonNull Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, clearButtonIcon, null);
    }

    private void hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
    }
}
