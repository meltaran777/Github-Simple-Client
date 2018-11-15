package com.fil.github_client.helper.back_stack;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.fil.github_client.screens.repositories.RepositoriesFragment;

import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import timber.log.Timber;

public class BackStackManager implements Serializable {
    private final String LOG_TAG = BackStackManager.class.getSimpleName();

    private final LinkedList<BackStack> mBackStacks;

    private final BackStackListener mListener;

    private final BackStackFragmentManager mFragmentManager;


    public BackStackManager(@NonNull final BackStackListener listener,
                            @NonNull final FragmentManager fragmentManager) {
        mBackStacks = new LinkedList<>();
        mListener = listener;
        mFragmentManager = new BackStackFragmentManager(fragmentManager);
    }

    /**
     * When adding a new root fragment
     * IMPORTANT: Activity his holding the reference to the root.
     */
    public void addRootFragment(@NonNull final Fragment fragment,
                                final int layoutId) {

        if (!isAdded(fragment)) {
            Timber.tag(LOG_TAG).d("addRootFragment(): addRoot -> fragment = %s", fragment.getClass().getName());
            setTopFragmentPlaying(false);

            addRoot(fragment, layoutId);
            setTopFragmentPlaying(true);
        } else if (isAdded(fragment) && isCurrent(fragment)) {
            refreshCurrentRoot();
            Timber.tag(LOG_TAG).d("addRootFragment(): refreshCurrentRoot -> fragment = %s", fragment.getClass().getName());

            Deque<String> childBackStackList = getBackStackChildFragment(fragment);
            if (childBackStackList != null) {
                childBackStackList.clear();
                switchRoot(fragment);
                mFragmentManager.switchFragment(fragment);
                setTopFragmentPlaying(true);
            }
        } else {
            Timber.tag(LOG_TAG).d("addRootFragment(): switchRoot -> fragment = %s", fragment.getClass().getName());
            setTopFragmentPlaying(false);
            switchRoot(fragment);
            mFragmentManager.switchFragment(fragment);

            //Show ChildFragment if exist
            showBackStackChildFragment(fragment);
            setTopFragmentPlaying(true);
        }
    }

    /**
     * When activity is calling onBackPressed
     */
    public void onBackPressed() {
        final BackStack current = mBackStacks.peekLast();
        if(current != null) {
            String uuid = current.pop();
            if (uuid == null) {
                if (mBackStacks.size() > 1 || isChildExists())
                    setTopFragmentPlaying(false);
                removeRoot(current);
            } else {
                removeChild(current, uuid);
            }
            setTopFragmentPlaying(true);
        }
        else {
            Timber.tag(LOG_TAG).d("onBackPressed(): call onBackStackEmpty()");
            mListener.onBackStackEmpty();
        }
    }

    public boolean isChildExists() {
        final BackStack current = mBackStacks.peekLast();
        if(current == null || current.isEmpty())
            return false;
        else
            return true;
    }

    /**
     * Adding child fragment
     */
    public void addChildFragment(@NonNull final Fragment fragment, final int layoutId) {
        Timber.tag(LOG_TAG).d("addChildFragment(): %s", fragment.getClass().getSimpleName());
        setTopFragmentPlaying(false);

        //final String tag = UUID.randomUUID().toString();
        final String tag = fragment.getClass().getName();
        final BackStack backStack = mBackStacks.peekLast();

        //TODO: added check null to prevent crash
        if(backStack != null) {
            backStack.push(tag);
        } else {
            Timber.tag(LOG_TAG).e("addChildFragment(): mBackStacks.peekLast() == NULL!");
        }
        mFragmentManager.addChildFragment(fragment, layoutId, tag);
    }

    /**
     * Remove root
     */
    private void removeRoot(@NonNull final BackStack backStack) {
        Timber.tag(LOG_TAG).d("removeRoot(): %s", backStack.mRootFragment.getClass().getSimpleName());
        mBackStacks.remove(backStack);

        //After removing. Call close app listener if the backstack is empty
        if (mBackStacks.isEmpty()) {
            Timber.tag(LOG_TAG).d("removeRoot(): call onBackStackEmpty()");
            mListener.onBackStackEmpty();
        } else {
            //Change root since the old one is out
            BackStack newRoot = mBackStacks.peekLast();
            mFragmentManager.switchFragment(mFragmentManager.getFragmentManager().findFragmentByTag(newRoot.mRootFragment));
            mListener.onRootFragmentChangedBack(mFragmentManager.getFragmentManager().findFragmentByTag(newRoot.mRootFragment));

            //Show ChildFragment if exist
            showBackStackChildFragment(mFragmentManager.getFragmentManager().findFragmentByTag(newRoot.mRootFragment));
        }
    }

    /**
     * Remove child
     */
    private void removeChild(@NonNull final BackStack backStack, @NonNull final String uuid) {
        Timber.tag(LOG_TAG).d("removeChild(): root = %s", backStack.mRootFragment.getClass().getSimpleName());
        mFragmentManager.popBackStack(uuid);
        mFragmentManager.switchFragment(mFragmentManager.getFragmentManager().findFragmentByTag(backStack.mRootFragment));
        showBackStackChildFragment(mFragmentManager.getFragmentManager().findFragmentByTag(backStack.mRootFragment));
    }

    /**
     * Adding root fragment
     */
    private void addRoot(@NonNull final Fragment fragment, final int layoutId) {
        mFragmentManager.addFragment(fragment, layoutId);
        //Create a new backstack and add it to the list
        final BackStack backStack = new BackStack(fragment);
        mBackStacks.offerLast(backStack);
    }

    /**
     * Switch root internally in the made up backstack
     */
    private void switchRoot(@NonNull final Fragment fragment) {
        for (int i = 0; i < mBackStacks.size(); i++) {
            BackStack backStack = mBackStacks.get(i);
            if (backStack.mRootFragment.equals(fragment.getClass().getName())) {
                mBackStacks.remove(i);
                mBackStacks.offerLast(backStack);
                break;
            }
        }
    }

    private void setTopFragmentPlaying(boolean isPlaying) {
/*        try {
            Fragment topFragment = null;
            if (mBackStacks.peekLast() != null)
                if (isChildExists()) {
                    String topUid = mBackStacks.peekLast().mStackItems.getFirst();
                    topFragment = mFragmentManager.getFragmentManager().findFragmentByTag(topUid);
                } else
                    topFragment = mBackStacks.peekLast().mRootFragment;
            if (topFragment != null && topFragment instanceof ToroContainerFragment && topFragment.isAdded()) {
                ((ToroContainerFragment) topFragment).setNeedVideosPlaying(isPlaying);
                //Log.d(LOG_TAG, "setTopFragmentPlaying() - state: " + isPlaying + ", fragment: " + topFragment.getClass().getSimpleName());
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }*/
    }

    /**
     * Let listener know to call onAddedExistingFragment
     */
    private void refreshCurrentRoot() {
        //mListener.onAddedExistingFragment();
    }

    /**
     * Convenience method
     */
    public boolean isAdded(@NonNull final Fragment fragment) {
        for (BackStack backStack : mBackStacks) {
            if (backStack.mRootFragment.equals(fragment.getClass().getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Convenience method
     */
    private boolean isCurrent(@NonNull final Fragment fragment) {
        final BackStack backStack = mBackStacks.peekLast();
        Timber.tag(LOG_TAG).d("isCurrent()->mBackStacks.peekLast()=" + mBackStacks.peekLast().mRootFragment.getClass().getName()
                + "\n" + "fragment=" + fragment.getClass().getName());
        Timber.tag(LOG_TAG).d("backStack.mRootFragment == fragment-> %s", (backStack.mRootFragment.equals(fragment.getClass().getName())));
        return backStack.mRootFragment.equals(fragment.getClass().getName());
    }

    /**
     * Convenience method
     */
    public boolean isFragmentExistsInBackStack(String fragmentClassName) {
        int count = 0;
        List<Fragment> fragments = mFragmentManager.getFragmentManager().getFragments();
        if(!fragments.isEmpty()) {
            for (Fragment fr : fragments) {
                if(fr.getClass().getSimpleName().equals(fragmentClassName)) {
                    count++;
                    break;
                }
            }
        }
        return count > 0;
    }

    /**
     * For showing back stack child fragments
     */
    private void showBackStackChildFragment(@NonNull final Fragment fragment) {
        for (BackStack backStack : mBackStacks) {
            if (backStack.mRootFragment.equals(fragment.getClass().getName())) {
                if (!backStack.isEmpty()) {
                    Deque<String> childBackStackList = backStack.mStackItems;
                    //Show ChildFragment
                    Fragment childFragment = mFragmentManager
                            .getFragmentManager()
                            .findFragmentByTag(childBackStackList.getFirst());
                    Timber.tag(LOG_TAG).d("showBackStackChildFragment(): %s", childFragment.getClass().getSimpleName());
                    mFragmentManager.switchFragment(childFragment);
                }
            }
        }
    }

    /**
     * Get back stack child fragments
     */
    private Deque<String> getBackStackChildFragment(@NonNull final Fragment fragment) {
        for (BackStack backStack : mBackStacks) {
            if (backStack.mRootFragment.equals(fragment.getClass().getName())) {
                if(!backStack.isEmpty()){
                    return backStack.mStackItems;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public void setFragmentManager(@NonNull final FragmentManager fragmentManager){
        mFragmentManager.setFragmentManager(fragmentManager);
    }

    public Fragment findFragment(Class clazz) {
        return mFragmentManager.getFragmentManager().findFragmentByTag(clazz.getName());
    }
}
