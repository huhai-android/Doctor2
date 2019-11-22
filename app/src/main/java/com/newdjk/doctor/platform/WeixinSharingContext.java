package com.newdjk.doctor.platform;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/12/27.
 */

public class WeixinSharingContext {

    public static OnNotShareAction mOnNotShareAction;

    private static final List<OnNotShareAction> mSharingCache = new ArrayList<>();

    public static void addItem(OnNotShareAction action) {
        mSharingCache.add(action);
    }

    public static void removeItem(OnNotShareAction action) {
        mSharingCache.remove(action);
    }

    public static OnNotShareAction findAction(Object obj) {
        OnNotShareAction result = null;
        for (OnNotShareAction action : mSharingCache) {
            if (obj == action.getObj()) {
                result = action;
                break;
            }
        }

        return result;
    }

    public enum ShareState {
        SUCCESS,
        CANCEL,
        NOT_FEEDBACK
    }
    public static abstract class OnNotShareAction implements Runnable {
        private WeakReference<Object> mTag;
        private ShareState shareState = ShareState.NOT_FEEDBACK;

        public OnNotShareAction(Object tag) {
            mTag = new WeakReference<>(tag);
        }

        public Object getObj() {
            if (null == mTag) {
                return null;
            } else {
                return mTag.get();
            }
        }

        @Override
        public final void run() {
            if (null == mTag || null == mTag.get()) {
                return;
            }

            onNotShare();
        }

        protected abstract void onNotShare();

        public void setShareState(ShareState shareState) {
            this.shareState = shareState;
        }

        public ShareState getShareState() {
            return shareState;
        }
    }
}
