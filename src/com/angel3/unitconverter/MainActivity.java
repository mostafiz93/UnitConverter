package com.angel3.unitconverter;

import android.os.Bundle;
import android.provider.Telephony.Sms.Conversations;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

//check 

public class MainActivity extends Activity {

	private String[] strConversionType;
	private static String[][] strAllCategory = {
			{ "Degree", "Radian", "Grad" }, { "Acre", "Hectare" },
			{ "Bit", "Byte" }, { "PoundPerInch", "gramPerCC" },
			{ "Ampere", "Gilbert" }, { "Calorie", "Jule" },
			{ "Dyne", "Newton" }, { "Inch", "Centimeter" },
			{ "Kilogram", "Pound" }, { "Horsepower", "Watt" },
			{ "Pascal", "Bar" }, { "MPH", "KMPH" },
			{ "Celsius", "Fahrenheit" },
			{ "Second", "minute", "Hour", "Day", "Month", "Year" },
			{ "Gallon", "CubicCentimeter" } };

	private final double co[][][] = {
			{ { 1, 0.0174532925, 1.111111111 },
					{ 57.2957795, 1, 63.661977237 }, { .9, 0.0157079632679, 1 } },
			{ { 1, 0.404686 }, { 2.47105, 1 } },
			{ { 1, .125 }, { 8, 1 } },
			{ { 1, 27.6799 }, { 0.036127298147753, 1 } },
			{ { 1, 1.25663705427 }, { 0.79577472, 1 } },
			{ { 1, 4.184 }, { 0.239005736, 1 } },
			{ { 1, .00001 }, { 100000, 1 } },
			{ { 1, 2.54 }, { 0.393701, 1 } },
			{ { 1, 2.20462 }, { 0.453592, 1 } },
			{ { 1, 745.699872 }, { 0.00134102209, 1 } },
			{ { 1, .00001 }, { 100000, 1 } },
			{ { 1, 0.621371 }, { 1.60934, 1 } },
			{ { 1, 0.404686 }, { 2.47105, 1 } },
			{
					{ 1, 0.0166666666666666666666, 0.00027777777777777777777,
							1.15741e-5, 3.8027e-7, 3.1689e-8 },
					{ 60, 1, 0.016666666666666666666666, 0.000694444,
							2.2816e-5, 1.9013e-6 },
					{ 3600, 60, 1, 0.0416667, 0.00136895, 0.00011408 },
					{ 86400, 1440, 24, 1, 0.0328549, 0.00273791 },
					{ 2.63e+6, 43829.1, 730.484, 30.4368, 1, 0.0833333 },
					{ 3.156e+7, 525949, 8765.81, 365.242, 12, 1 } },
			{ { 1, 3785.41178 }, { 0.000264172052, 1 } } };

	private static String[] strFrom = strAllCategory[0];
	private static String strConvertFrom, strConvertTo;
	private static int positionCategory, positionConvertFrom,
			positionConvertTo;
	private static double valueFrom, valueTo;
	private static EditText etFrom;

	private static ArrayAdapter<String> adapterConversionType, adapterFrom,
			adapterTo;
	private static Spinner spnConversionType, spnFrom, spnTo;

	TextView tvResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		strConversionType = getResources().getStringArray(
				R.array.strConversionType);
		adapterConversionType = new ArrayAdapter<String>(MainActivity.this,
				android.R.layout.simple_spinner_dropdown_item,
				strConversionType);
		spnConversionType = (Spinner) findViewById(R.id.spnSelectConversion);
		spnConversionType.setAdapter(adapterConversionType);

		spnConversionType.setOnItemSelectedListener(listenerConversionType);

		spnFrom = (Spinner) findViewById(R.id.spnFrom);
		spnTo = (Spinner) findViewById(R.id.spnTo);

		spnFrom.setOnItemSelectedListener(listenerFrom);
		spnTo.setOnItemSelectedListener(listenerTo);

		etFrom = (EditText) findViewById(R.id.etFrom);
		tvResult = (TextView) findViewById(R.id.tvResult);

	}

	OnItemSelectedListener listenerConversionType = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			// TODO Auto-generated method stub
			positionCategory = position;
			strFrom = strAllCategory[(int) id];

			adapterFrom = new ArrayAdapter<String>(MainActivity.this,
					android.R.layout.simple_dropdown_item_1line, strFrom);

			spnFrom.setAdapter(adapterFrom);

			adapterTo = new ArrayAdapter<String>(MainActivity.this,
					android.R.layout.simple_dropdown_item_1line, strFrom);
			spnTo.setAdapter(adapterTo);
			spnTo.setSelection(1);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	};

	OnItemSelectedListener listenerFrom = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			// TODO Auto-generated method stub
			positionConvertFrom = position;

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	};

	OnItemSelectedListener listenerTo = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			// TODO Auto-generated method stub
			positionConvertTo = position;

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	};

	public void swap(View v) {
		// Toast.makeText(getApplicationContext(),
		// positionConvertFrom+" "+positionConvertTo,
		// Toast.LENGTH_SHORT).show();

		spnFrom.setSelection(positionConvertTo);
		spnTo.setSelection(positionConvertFrom);
	}

	public void clear(View v) {
		etFrom.setText("");
		tvResult.setText("0.0");
		spnConversionType.setSelection(0);
	}

	public void convert(View v) {
		String temp = etFrom.getText().toString();
		if (temp.isEmpty())
			temp = "0.0";
		valueFrom = Double.parseDouble(temp);
		// Toast.makeText(getApplicationContext(),"="+ valueFrom,
		// Toast.LENGTH_SHORT).show();

		double x = co[positionCategory][positionConvertFrom][positionConvertTo];
		valueTo = valueFrom * x;

		if (positionCategory == 12) {
			// Toast.makeText(getApplicationContext(), "Temperature Received",
			// 1).show();
			if (positionConvertFrom == 0)
				valueTo = 32 + valueFrom * 9 / 5;
			else
				valueTo = (valueFrom - 32) * 5 / 9;
		}

		temp = String.format("%.4f", valueTo);
		tvResult.setText(temp);
	}

}
