package com.bmshamsnahid.callerappbeta;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class About_Us extends Activity{

	TextView tv_one_hosting_about_us, tv_two_hosting_about_us, 
	tv_three_hosting_about_us, tv_four_hosting_about_us, tv_five_hosting_about_us;
	ImageView iv_company_logo_hosting_about_us;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us);
		initialize();
	}

	private void initialize() {
		image_view_initialize();
		button_initialize();
		text_view_initialize();
		font_initialize();
	}

	private void font_initialize() {
		try {
			/////////Trying custom font/////////////////////////////////////////////////////
			Typeface font = Typeface.createFromAsset(getAssets(), "G-Unit.ttf");
			Typeface font_chunk_five = Typeface.createFromAsset(getAssets(), "Chunkfive.otf");
			Typeface font_star_jedi = Typeface.createFromAsset(getAssets(), "Starjedi.ttf");
			Typeface font_rough_simple = Typeface.createFromAsset(getAssets(), "Rough_Simple.ttf");
			Typeface font_pink_t_shirt = Typeface.createFromAsset(getAssets(), "Pink_t_shirt.ttf");
			
			
			tv_one_hosting_about_us.setTypeface(font_chunk_five);
			tv_two_hosting_about_us.setTypeface(font_chunk_five);
			tv_three_hosting_about_us.setTypeface(font_chunk_five);
			tv_four_hosting_about_us.setTypeface(font_chunk_five);
			tv_five_hosting_about_us.setTypeface(font_chunk_five);
			
		} catch(Exception e) {
			Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
		}
	}

	private void text_view_initialize() {
		tv_one_hosting_about_us = (TextView) findViewById(R.id.text_view_one_hosting_about_us);
		tv_two_hosting_about_us = (TextView) findViewById(R.id.text_view_two_hosting_about_us);
		tv_three_hosting_about_us = (TextView) findViewById(R.id.text_view_three_hosting_about_us);
		tv_four_hosting_about_us = (TextView) findViewById(R.id.text_view_four_hosting_about_us);
		tv_five_hosting_about_us = (TextView) findViewById(R.id.text_view_five_hosting_about_us);
	}

	private void button_initialize() {
		
	}

	private void image_view_initialize() {
		iv_company_logo_hosting_about_us = (ImageView) findViewById(R.id.image_view_company_logo_hosting_about_us);
	}
}
