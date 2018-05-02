package com.example.fewwind.chaozhuofirst.ui;

import android.animation.Animator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

/**
 * Created by fewwind on 18-3-5.
 */

public class ScrollListener extends RecyclerView.OnScrollListener {
    private int lastVisibleItem = 0;
    private boolean isLoading = false;
    private View mView;
    private View mView2;

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    private float viewY;//控件距离coordinatorLayout底部距离
    private float viewY2;//控件距离coordinatorLayout底部距离
    private boolean isAnimate;//动画是否在进行
    private boolean isAnimate2;//动画是否在进行

    public void setView(View v){
        mView = v;
        viewY = 68;
    }
    public void setView2(View v){
        mView2 = v;
        viewY2 = 80;
    }

    @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);

    }


    @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int offset = recyclerView.computeVerticalScrollOffset();
        //if (dy > 0 && !isAnimate && mView.getVisibility() == View.VISIBLE) {
        //    hide(mView);
        //} else if (dy < 0 && !isAnimate && mView.getVisibility() == View.GONE) {
        //    show(mView);
        //}
        //
        //if (dy > 0 && !isAnimate2 && mView2.getVisibility() == View.VISIBLE) {
        //    hide2(mView2);
        //} else if (dy < 0 && !isAnimate2 && mView2.getVisibility() == View.GONE) {
        //    show2(mView2);
        //}
        mView.animate().translationY(-offset);
    }


    //
    //@Override public View focusSearch(View focused, int direction) {
    //    if (focused != null && focused.getId() == R.id.item_iv && direction == View.FOCUS_DOWN) {
    //        mParentRv.smoothScrollBy(0, getHeaderHeight());
    //        if (!PreferencesUtils.getBoolean(getContext(), KEY_GUAID_NEXT)) {
    //            PreferencesUtils.putBoolean(getContext(), KEY_GUAID_NEXT,true);
    //            floatAnim(false);
    //        }
    //    }
    //    if (focused != null && focused.getId() == R.id.item_iv_match &&
    //            direction == View.FOCUS_UP) {
    //        if (mRvSubject.indexOfChild(focused) == 0) {
    //            mParentRv.smoothScrollToPosition(0);
    //        }
    //    }
    //    return super.focusSearch(focused, direction);
    //}

     //if (dx != 0 || dy != 0) {
     //   final Rect tempRect = new Rect();
     //   nestedScrollingParent.offsetDescendantRectToMyCoords(child, tempRect);
     //   GridLayoutManager.LayoutParams layoutParams
     //           = (GridLayoutManager.LayoutParams) child.getLayoutParams();
     //   int margin = layoutParams.bottomMargin;
     //   int scrollHeight = child.getMeasuredHeight() + margin;
     //   int offsetVertical = parent.computeVerticalScrollOffset();
     //   Timber.e(dy + " * " + offsetVertical + " * " + parent.indexOfChild(child));
     //   if (parent.computeVerticalScrollExtent() > child.getTop()) {
     //       //nestedScrollingParent.scrollTo(0, 0);
     //   }
     //   final int[] scrollConsumed = new int[2];
     //   //nestedScrollingParent.onNestedPreScroll(parent, dx, dy, scrollConsumed);
     //   dx -= scrollConsumed[0];
     //   //dy += scrollConsumed[1];
     //   if (Math.abs(dy) % scrollHeight != 0) {
     //       if (dy > 0) { //按键向下。列表向上滚动
     //           dy = (int) (Math.floor(dy / scrollHeight) + 1) * scrollHeight;
     //       } else {
     //           dy = -(int) (Math.floor(Math.abs(dy) / scrollHeight) + 1) * scrollHeight;
     //       }
     //   }
     //   if (dy<0){
     //       Timber.w(offsetVertical+" *显示标题* "+scrollHeight*1.5);
     //       if (offsetVertical<scrollHeight*1.5){
     //           nestedScrollingParent.scrollTo(0,0);
     //           parent.smoothScrollToPosition(0);
     //       }
     //   } else {
     //       if (offsetVertical<scrollHeight){
     //           nestedScrollingParent.hideTop();
     //       }
     //   }


    //隐藏时的动画
    private void hide(final View view) {
        ViewPropertyAnimator animator = view.animate()
                .translationY(-viewY)
                .setInterpolator(INTERPOLATOR)
                .setDuration(200);

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
                .setDuration(200);
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

    //隐藏时的动画
    private void hide2(final View view) {
        ViewPropertyAnimator animator = view.animate()
                .translationY(-viewY2)
                .setInterpolator(INTERPOLATOR)
                .setDuration(200);

        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isAnimate2 = true;
            }


            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.GONE);
                isAnimate2 = false;
            }


            @Override
            public void onAnimationCancel(Animator animator) {
                show2(view);
            }


            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animator.start();
    }
    //显示时的动画
    private void show2(final View view) {
        ViewPropertyAnimator animator = view.animate()
                .translationY(0)
                .setInterpolator(INTERPOLATOR)
                .setDuration(200);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                view.setVisibility(View.VISIBLE);
                isAnimate2 = true;
            }


            @Override
            public void onAnimationEnd(Animator animator) {
                isAnimate2 = false;
            }


            @Override
            public void onAnimationCancel(Animator animator) {
                hide2(view);
            }


            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animator.start();
    }
}
