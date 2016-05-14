package com.example.version1;

import android.app.ActivityGroup;
import android.os.Bundle;

public class BaseActivity extends ActivityGroup {

	private int themes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		themes = new SkinSettingManager(this).getCurrentSkinRes();
		this.setTheme(themes);
		super.onCreate(savedInstanceState);

	}

}
