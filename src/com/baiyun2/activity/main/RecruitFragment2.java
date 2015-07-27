package com.baiyun2.activity.main;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baiyun2.activity.R;
import com.baiyun2.activity.webview.WebViewActiviry;
import com.baiyun2.activity.webview.WebViewActiviry2;
import com.baiyun2.base.BaseFragment;
import com.baiyun2.httputils.RecruitHttpUtils;
import com.baiyun2.vo.parcelable.RecruitPar;

public class RecruitFragment2 extends BaseFragment {
	private RecruitHttpUtils httpUtils;
	
	private LinearLayout llConsult;//右上角的查询按钮布局
	
	private ListView listView;
	private ListViewAdapter adapter;
	private List<RecruitPar> recruitPars = new ArrayList<RecruitPar>();
	
	public static RecruitFragment2 newInstance() {
		return new RecruitFragment2();
	}

	public RecruitFragment2() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		httpUtils = new RecruitHttpUtils(getActivity());
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((MainActivity)getActivity()).setLLConsultEnable(true);
	}

	@Override
	public int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.listview_common;
	}

	@Override
	public void createMyView(View rootView) {
		// TODO Auto-generated method stub
		initView(rootView);
		getNetData();
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		((MainActivity)getActivity()).setLoadingBarGone();
		((MainActivity)getActivity()).setLLConsultEnable(false);
	}
	
	private void initView(View rootView){
		listView = (ListView) rootView.findViewById(R.id.listview);
		adapter = new ListViewAdapter(getActivity(), recruitPars);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				RecruitPar recruitPar = recruitPars.get(position);
				Intent intent = new Intent(getActivity(), WebViewActiviry2.class);
				intent.putExtra(WebViewActiviry2.KEY_TITLE, recruitPar.getName());
				intent.putExtra(WebViewActiviry2.KEY_URL_LAST, recruitPar.getRecruitInforServiceUrl());
				startActivity(intent);
			}
		});
		
		llConsult = ((MainActivity)getActivity()).getLLConsult();
		llConsult.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				new KeFuManager(getActivity()).startChat();
				showLoadingDialog();
				(new RecruitHttpUtils(getActivity())).getRCUrl("3", new RecruitHttpUtils.OnGetRCUrlListener() {
					
					@Override
					public void onGetRCUrl(String url) {
						// TODO Auto-generated method stub
						closeLoadingDialog();
						if (url != null) {
							Intent intent = new Intent(getActivity(), WebViewActiviry.class);
							intent.putExtra(WebViewActiviry.KEY_WEB_VIEW_TYPE, WebViewActiviry.H_Consult);
							intent.putExtra(WebViewActiviry.KEY_CONTENT_URL, url);
							getActivity().startActivity(intent);
						}
					}
				});
			}
		});
	}
	
	private void getNetData(){
		((MainActivity)getActivity()).setLoadingBarVisible();
		httpUtils.getRecruitList(new RecruitHttpUtils.OnGetRecruitListListener() {
			
			@Override
			public void onRecruitList(List<RecruitPar> pars) {
				if (getActivity() != null) {
					((MainActivity)getActivity()).setLoadingBarGone();
				}
				if (pars != null) {
					recruitPars.addAll(pars);
					adapter.notifyDataSetChanged();
				}
			}
		});
	}

	private class ListViewAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private List<RecruitPar> recruitPars;

		public ListViewAdapter(Context context, List<RecruitPar> recruitPars) {
			inflater = LayoutInflater.from(context);
			this.recruitPars = recruitPars;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return recruitPars.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return recruitPars.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.list_item_life_guide, parent, false);
				holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			RecruitPar recruitPar = recruitPars.get(position);
			holder.tvTitle.setText(recruitPar.getName());
			return convertView;
		}

		public final class ViewHolder {
			public TextView tvTitle;
		}

	}

}
