package com.edusofter.educv.view;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import com.edusofter.educv.R;
import com.edusofter.educv.model.Education;
import com.edusofter.educv.model.Experience;
import com.edusofter.educv.persistence.DatabaseHelper;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class MainActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	public static final int FRAGMENT_PERSONAL = 0;

	private DrawerLayout drawer;
	private ListView drawerList;
	private String[] fragments_sections;
	private ShareActionProvider mShareActionProvider;
	private ActionBarDrawerToggle mDrawerToggle;
	private DatabaseHelper databaseHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		databaseHelper = getHelper();
		setUpViews();

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		setUpDrawer();

		if (savedInstanceState == null) {
			selectItem(0);
		}

	}

	private void setUpDrawer() {
		mDrawerToggle = new ActionBarDrawerToggle(this, drawer,
				R.drawable.ic_drawer, R.string.open_drawer,
				R.string.close_drawer) {
			@Override
			public void onDrawerClosed(View drawerView) {
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			@Override
			public void onDrawerOpened(View drawerView) {

				invalidateOptionsMenu();
			}
		};

		drawer.setDrawerListener(mDrawerToggle);
	}

	private void setUpViews() {
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);
		fragments_sections = getResources().getStringArray(
				R.array.fragments_sections);
		drawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.END);

		drawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, getFragments_sections()));
		drawerList.setOnItemClickListener(new DrawerItemClickListener());
	}

	public DatabaseHelper getDatabaseHelper() {
		return databaseHelper;
	}

	private void selectItem(int pos) {
		Fragment fragment = new DetailFragment();
		Bundle args = new Bundle();

		args.putString(DetailFragment.FRAGMENT_TITLE,
				getFragments_sections()[pos]);
		args.putInt(DetailFragment.FRAGMENT, pos);

		fragment.setArguments(args);
		getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();

		drawerList.setItemChecked(pos, true);

		setTitle(getFragments_sections()[pos]);
		drawer.closeDrawer(drawerList);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
			selectItem(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		// SET SHARE INTENT
		mShareActionProvider = (ShareActionProvider) menu.findItem(R.id.action_share).getActionProvider();
		setShareIntent();

		return super.onCreateOptionsMenu(menu);
	}

	private void setShareIntent() {
		if (mShareActionProvider != null) {
			Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
			shareIntent.setType("text/plain");
			shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
					getString(R.string.shareTitle));
			shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.shareBody));
			mShareActionProvider.setShareIntent(shareIntent);
		}
	}

	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_email:
			sendMailIntent();
			return true;
		case R.id.action_about:
			Intent intent = new Intent(this, ActivityAbout.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public void sendMailIntent() {
		Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto", "edumartinezber@gmail.com",null));
		intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.emailSendSubject));
		intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.emailSendBody));
		startActivity(Intent.createChooser(intent, getString(R.string.emailSend)));
	}

	/**
	 * Launches toast message
	 * 
	 * @param stringF
	 *            message to show
	 */
	@SuppressWarnings("unused")
	private void tosta(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	public String[] getFragments_sections() {
		return fragments_sections;
	}

	public static class DetailFragment extends Fragment {
		public static final String FRAGMENT_TITLE = "fragment_title";
		public static final String FRAGMENT = "fragment";
		public static final int PERSONAL = 0;
		public static final int FORMACION = 1;
		public static final int EXPERIENCIA = 2;
		public static final int ANDROID = 3;
		public static final int DESARROLLO = 4;
		public static final int INFORMATICA = 5;
		public static final int IDIOMAS = 6;
		public static final int OTRAFORMACION = 7;
		public static final int AFICIONES = 8;
		private static final String LANG_ES = "es";

		public DetailFragment() {

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			int fragment = getArguments().getInt(FRAGMENT);
			View rootView = null;
			String title = getArguments().getString(FRAGMENT_TITLE);
			getActivity().setTitle(title);
			switch (fragment) {
			case PERSONAL:
				rootView = inflater
						.inflate(R.layout.personal, container, false);
				setUpLaunchIntents(rootView);
				break;
			case FORMACION:
				rootView = loadEducation(inflater, container, rootView);
				break;
			case EXPERIENCIA:
				rootView = loadExperience(inflater, container, rootView);
				break;
			case ANDROID:
				rootView = inflater.inflate(R.layout.android, container, false);
				break;
			case DESARROLLO:
				rootView = inflater.inflate(R.layout.desarrollo, container,
						false);
				break;
			case INFORMATICA:
				rootView = inflater.inflate(R.layout.informatica, container,
						false);
				break;
			case IDIOMAS:
				rootView = inflater.inflate(R.layout.idiomas, container, false);
				break;
			case OTRAFORMACION:
				rootView = inflater.inflate(R.layout.otraformacion, container,
						false);
				break;
			case AFICIONES:
				rootView = inflater.inflate(R.layout.aficiones, container,
						false);
				((ImageView) rootView.findViewById(R.id.ImageViewVideo))
						.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent = new Intent(
										Intent.ACTION_VIEW,
										Uri.parse("http://www.youtube.com/watch?v=HylwBtWGOPo"));
								startActivity(intent);
							}
						});
				break;
			default:
				rootView = inflater.inflate(R.layout.fragment_detail,
						container, false);

				((TextView) rootView.findViewById(R.id.text_fragment_title))
						.setText(title);
			}

			return rootView;
		}

		private void setUpLaunchIntents(View rootView) {
			TextView email = (TextView) rootView.findViewById(R.id.textEmailInfo);
			
			email.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.fromParts("mailto", "edumartinezber@gmail.com",null));
					intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.emailSendSubject));
					intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.emailSendBody));
					startActivity(Intent.createChooser(intent, getString(R.string.emailSend)));
				}
			});
			TextView tfno = (TextView) rootView.findViewById(R.id.textTelefono);
			tfno.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					try{
					Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "+346067446090"));
					startActivity(intent);
					} catch (ActivityNotFoundException anf){
						Toast.makeText(v.getContext(), "No se pueden realizar llamadas telefónicas", Toast.LENGTH_SHORT).show();
					}
				}
			});
			
			ImageView linkedin = (ImageView) rootView.findViewById(R.id.imageLinkedin);
			linkedin.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse("http://www.linkedin.com/pub/eduardo-martínez/22/8b4/b34"));
					startActivity(i);
				}
			});
		}

		@SuppressWarnings("unchecked")
		private View loadEducation(LayoutInflater inflater,
				ViewGroup container, View rootView) {
			OrmLiteBaseActivity<DatabaseHelper> act = (OrmLiteBaseActivity<DatabaseHelper>) getActivity();
			try {
				rootView = inflater.inflate(R.layout.formacion, container,
						false);
				ListView list = (ListView) rootView
						.findViewById(R.id.listFormacion);
				String lang = Locale.getDefault().getLanguage();
				List<Education> edu;
				if (lang.equals(LANG_ES)) {
					edu = act.getHelper().getDao(Education.class)
							.queryBuilder().where()
							.eq(Education.LANG_COL, Education.LANG_ES).query();
				} else {
					edu = act.getHelper().getDao(Education.class)
							.queryBuilder().where()
							.eq(Education.LANG_COL, Education.LANG_EN).query();
				}
				list.setAdapter(new BaseListAdapter(act,
						R.layout.experience_list_item, edu));
			} catch (SQLException e) {
				Log.e(getClass().getName(), "Can't retrieve education", e);
			}
			return rootView;
		}

		@SuppressWarnings("unchecked")
		private View loadExperience(LayoutInflater inflater,
				ViewGroup container, View rootView) {
			OrmLiteBaseActivity<DatabaseHelper> act = (OrmLiteBaseActivity<DatabaseHelper>) getActivity();
			try {
				rootView = inflater.inflate(R.layout.formacion, container,
						false);
				ListView list = (ListView) rootView
						.findViewById(R.id.listFormacion);
				String lang = Locale.getDefault().getLanguage();
				List<Experience> edu;
				if (lang.equals(LANG_ES)) {
					edu = act.getHelper().getDao(Experience.class)
							.queryBuilder().where()
							.eq(Experience.LANG_COL, Experience.LANG_ES)
							.query();
				} else {
					edu = act.getHelper().getDao(Experience.class)
							.queryBuilder().where()
							.eq(Experience.LANG_COL, Experience.LANG_EN)
							.query();
				}
				list.setAdapter(new BaseListAdapter(act,
						R.layout.experience_list_item, edu));
			} catch (SQLException e) {
				Log.e(getClass().getName(), "Can't retrieve education", e);
			}
			return rootView;
		}
	}
}
