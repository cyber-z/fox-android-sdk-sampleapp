package jp.co.cyberz.sampleapp;


import jp.appAdForce.android.AdManager;
import jp.appAdForce.android.AnalyticsManager;
import jp.appAdForce.android.LtvManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.activity_main);
		
		/**
		 * 初回起動成果の計測処理
		 */
		AdManager ad = new AdManager(this);
		ad.sendConversion("default");
		
		Button tutorialBtn = (Button) findViewById(R.id.tutorial);
		tutorialBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				/**
				 * LTV成果の計測処理
				 */
				AdManager ad = new AdManager(MainActivity.this);
				LtvManager ltv = new LtvManager(ad);
				/*
				 * チュートリアル突破として発行されたLTV成果地点IDを指定します。
				 * 下記、引数100は例です。
				 */
				ltv.sendLtvConversion(100);
				
				/**
				 * アクセス解析 イベント計測
				 */
				AnalyticsManager.sendEvent(MainActivity.this, "チュートリアル突破", 0);
			}
		});
		
		Button purchaseBtn = (Button) findViewById(R.id.purchase);
		purchaseBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				/**
				 * LTV成果の計測処理
				 */
				AdManager ad = new AdManager(MainActivity.this);
				LtvManager ltv = new LtvManager(ad);
				/**
				 * 課金成果として発行されたLTV成果地点IDを指定します。
				 * 金額は100、通貨
				 * 下記、引数200は例です。
				 */
				ltv.addParam(LtvManager.URL_PARAM_PRICE, "10");
                ltv.addParam(LtvManager.URL_PARAM_CURRENCY, "USD");
                ltv.sendLtvConversion(200);
                AnalyticsManager.sendEvent(MainActivity.this,"アイテム課金", null, null, "12345", "", "GAME_ITEM", 10, 1, "USD");
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		/**
		 * アクセス解析
		 * アプリの起動計測の処理
		 */
		AnalyticsManager.sendStartSession(this);
		
		/**
		 * リエンゲージメント計測(URLスキームによる計測)
		 */
		AdManager ad = new AdManager(this);
		ad.sendReengagementConversion(getIntent());
	}

}
