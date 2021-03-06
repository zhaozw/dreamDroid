package net.reichholf.dreamdroid.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import net.reichholf.dreamdroid.R;
import net.reichholf.dreamdroid.fragment.abs.AbstractHttpListFragment;
import net.reichholf.dreamdroid.helpers.ExtendedHashMap;
import net.reichholf.dreamdroid.helpers.enigma2.Event;
import net.reichholf.dreamdroid.helpers.enigma2.Service;
import net.reichholf.dreamdroid.loader.AsyncFavListLoader;
import net.reichholf.dreamdroid.loader.LoaderResult;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by Stephan on 09.11.2014.
 */
public class PickServiceFragment extends AbstractHttpListFragment{
	public ExtendedHashMap mCurrentBouquet;
	public static final String KEY_BOUQUET = "bouquet";



	@Override
	public void onCreate(Bundle savedInstanceState) {
		mReload = true;
		super.onCreate(savedInstanceState);
		ExtendedHashMap up = new ExtendedHashMap();
		up.put(Service.KEY_REFERENCE, AsyncFavListLoader.REF_FAVS);
		up.put(Service.KEY_NAME, getString(R.string.services));
		mCurrentBouquet = up;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAdapter = new SimpleAdapter(getActionBarActivity(), mMapList, android.R.layout.simple_list_item_1,
				new String[] { Event.KEY_SERVICE_NAME }, new int[] { android.R.id.text1 });
		setListAdapter(mAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		mCurrentBouquet = mMapList.get(position);
		Intent data = new Intent();
		data.putExtra(KEY_BOUQUET, (Parcelable) mCurrentBouquet);
		finish(Activity.RESULT_OK, data);
	}

	@Override
	public ArrayList<NameValuePair> getHttpParams(int loader) {
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("bRef", mCurrentBouquet.getString(Service.KEY_REFERENCE)));
		return params;
	}

	@Override
	public Loader<LoaderResult<ArrayList<ExtendedHashMap>>> onCreateLoader(int i, Bundle args) {
		return new AsyncFavListLoader(getActionBarActivity(), args);
	}

}
