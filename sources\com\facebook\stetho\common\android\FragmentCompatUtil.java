package com.facebook.stetho.common.android;

import android.app.Activity;
import android.view.View;
import java.util.List;

public final class FragmentCompatUtil {
    private FragmentCompatUtil() {
    }

    public static Object findFragmentForView(View view) {
        Activity tryGetActivity = ViewUtil.tryGetActivity(view);
        if (tryGetActivity == null) {
            return null;
        }
        return findFragmentForViewInActivity(tryGetActivity, view);
    }

    private static Object findFragmentForViewInActivity(Activity activity, View view) {
        FragmentCompat supportLibInstance = FragmentCompat.getSupportLibInstance();
        if (supportLibInstance != null && supportLibInstance.getFragmentActivityClass().isInstance(activity)) {
            Object findFragmentForViewInActivity = findFragmentForViewInActivity(supportLibInstance, activity, view);
            if (findFragmentForViewInActivity != null) {
                return findFragmentForViewInActivity;
            }
        }
        FragmentCompat frameworkInstance = FragmentCompat.getFrameworkInstance();
        if (frameworkInstance != null) {
            Object findFragmentForViewInActivity2 = findFragmentForViewInActivity(frameworkInstance, activity, view);
            if (findFragmentForViewInActivity2 != null) {
                return findFragmentForViewInActivity2;
            }
        }
        return null;
    }

    private static Object findFragmentForViewInFragment(FragmentCompat fragmentCompat, Object obj, View view) {
        FragmentAccessor forFragment = fragmentCompat.forFragment();
        if (forFragment.getView(obj) == view) {
            return obj;
        }
        Object childFragmentManager = forFragment.getChildFragmentManager(obj);
        if (childFragmentManager != null) {
            return findFragmentForViewInFragmentManager(fragmentCompat, childFragmentManager, view);
        }
        return null;
    }

    private static Object findFragmentForViewInFragmentManager(FragmentCompat fragmentCompat, Object obj, View view) {
        List addedFragments = fragmentCompat.forFragmentManager().getAddedFragments(obj);
        if (addedFragments != null) {
            int size = addedFragments.size();
            for (int i = 0; i < size; i++) {
                Object findFragmentForViewInFragment = findFragmentForViewInFragment(fragmentCompat, addedFragments.get(i), view);
                if (findFragmentForViewInFragment != null) {
                    return findFragmentForViewInFragment;
                }
            }
        }
        return null;
    }

    public static boolean isDialogFragment(Object obj) {
        FragmentCompat supportLibInstance = FragmentCompat.getSupportLibInstance();
        if (supportLibInstance != null && supportLibInstance.getDialogFragmentClass().isInstance(obj)) {
            return true;
        }
        FragmentCompat frameworkInstance = FragmentCompat.getFrameworkInstance();
        if (frameworkInstance == null || !frameworkInstance.getDialogFragmentClass().isInstance(obj)) {
            return false;
        }
        return true;
    }

    private static Object findFragmentForViewInActivity(FragmentCompat fragmentCompat, Activity activity, View view) {
        Object fragmentManager = fragmentCompat.forFragmentActivity().getFragmentManager(activity);
        if (fragmentManager != null) {
            return findFragmentForViewInFragmentManager(fragmentCompat, fragmentManager, view);
        }
        return null;
    }
}
