package com.example.fewwind.chaozhuofirst.ui;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.fewwind.chaozhuofirst.MainActivity;
import com.example.fewwind.chaozhuofirst.R;
import com.example.fewwind.chaozhuofirst.bean.FileObject;
import com.example.fewwind.chaozhuofirst.utils.RxBus;
import com.orhanobut.logger.Logger;
import rx.functions.Action1;

public class SecondActivity extends AppCompatActivity {

    private TextView tv;
    private Button btn;
    private boolean isAnimate;//动画是否在进行
    private float viewY = 96;//控件距离coordinatorLayout底部距离
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private SeekBar mSb;
    LinearLayout.LayoutParams layoutParams;
    LinearLayout.LayoutParams layoutParams2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tv = (TextView) findViewById(R.id.tv);
        btn = (Button) findViewById(R.id.anim);
        mSb = (SeekBar) findViewById(R.id.pb);
        RxBus.getInstance().toObservable(FileObject.class).subscribe(new Action1<FileObject>() {
            @Override public void call(FileObject fileObject) {
                Logger.w("收到bus 事件--"+fileObject.toString());
            }
        });
        layoutParams = (LinearLayout.LayoutParams) tv.getLayoutParams();
        layoutParams2 = (LinearLayout.LayoutParams) btn.getLayoutParams();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                //if ( !isAnimate && tv.getVisibility() == View.VISIBLE) {
                //    hide(tv);
                //} else if (!isAnimate && tv.getVisibility() == View.GONE) {
                //    show(tv);
                //}

            }
        });
        mSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //layoutParams.bottomMargin = progress;
                //layoutParams.topMargin = -progress;
                //tv.setLayoutParams(layoutParams);
                //layoutParams2.bottomMargin = progress;
                //layoutParams2.topMargin = -progress;
                //btn.setLayoutParams(layoutParams2);
                ((View) tv.getParent()).scrollTo(0,progress*2);
            }


            @Override public void onStartTrackingTouch(SeekBar seekBar) {

            }


            @Override public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    //隐藏时的动画
    private void hide(final View view) {
        ViewPropertyAnimator animator = view.animate()
                .translationY(-viewY)
                .setInterpolator(INTERPOLATOR)
                .setDuration(2000);

        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isAnimate = true;
            }


            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.GONE);
                isAnimate = false;
            }


            @Override
            public void onAnimationCancel(Animator animator) {
                show(view);
            }


            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animator.start();
    }




    //显示时的动画
    private void show(final View view) {
        ViewPropertyAnimator animator = view.animate()
                .translationY(0)
                .setInterpolator(INTERPOLATOR)
                .setDuration(2000);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                view.setVisibility(View.VISIBLE);
                isAnimate = true;
            }


            @Override
            public void onAnimationEnd(Animator animator) {
                isAnimate = false;
            }


            @Override
            public void onAnimationCancel(Animator animator) {
                hide(view);
            }


            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animator.start();
    }

    @Override public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
