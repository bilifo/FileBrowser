package adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个是用于fragment的viewpager的adapter
 * @author PanJunLong
 *
 */
public class ComPagerAdapter extends FragmentPagerAdapter {
	public ComPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	private List<Fragment> mFragments = new ArrayList();
	private List<String> mTitles = new ArrayList();

	@Override
	public Fragment getItem(int position) {
		return mFragments.get(position);
	}

	@Override
	public int getCount() {
		return mFragments.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return mTitles.get(position);
	}

	/**
	 * ���
	 * 
	 * @param fragment
	 * @param title
	 */
	public ComPagerAdapter addFragment(Fragment fragment, String title) {
		mFragments.add(fragment);
		mTitles.add(title);
		return this;
	}

}
