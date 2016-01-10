package nerdlauncher.android.bignerdranch.com.photogallery;

import android.support.v4.app.Fragment;

/**
 * Created by Denis on 10.01.2016.
 */
public class PhotoPageActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFragment(){
        return new PhotoPageFragment();
    }
}
