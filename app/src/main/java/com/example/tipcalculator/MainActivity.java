package com.example.tipcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	private MainViewModel mainViewModel;
	private EditText split;
	private EditText tipPercent;
	private TextView billTotal;
	private TextView tipTotal;
	private TextView splitTotal;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		billTotal = findViewById(R.id.bill_total);
		tipTotal = findViewById(R.id.tip_amount);
		splitTotal = findViewById(R.id.bill_total_split);
		tipPercent = this.findViewById(R.id.tip_percent);
		split = this.findViewById(R.id.split);

		//holy shit this is stupid
		mainViewModel = new ViewModelProvider(getViewModelStore(), ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(MainViewModel.class);

		this.<EditText>findViewById(R.id.bill_amount).addTextChangedListener(new TextWatcherAdapter() {
			@Override
			public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
				final double billAmount = charSequence.length() > 0
						? Double.parseDouble(charSequence.toString())
						: 0.0;
				mainViewModel.setBillAmount(billAmount);
				setTotals();
			}
		});
		split.addTextChangedListener(new TextWatcherAdapter() {
			@Override
			public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
				final int splitAmount = Math.max(1,
						charSequence.length() > 0
								? Integer.parseInt(charSequence.toString())
								: 1
				);
				mainViewModel.setSplit(splitAmount);
				setTotals();
			}
		});
		tipPercent.addTextChangedListener(new TextWatcherAdapter() {
			@Override
			public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2) {
				final double tipPercent = charSequence.length() > 0
						? Double.parseDouble(charSequence.toString())
						: 0.0;
				mainViewModel.setTipPercent(tipPercent);
				setTotals();
			}
		});

		findViewById(R.id.tip_subtract).setOnClickListener(this);
		findViewById(R.id.tip_add).setOnClickListener(this);
		findViewById(R.id.split_subtract).setOnClickListener(this);
		findViewById(R.id.split_add).setOnClickListener(this);
		findViewById(R.id.tip_10).setOnClickListener(this);
		findViewById(R.id.tip_15).setOnClickListener(this);
		findViewById(R.id.tip_18).setOnClickListener(this);
		findViewById(R.id.tip_20).setOnClickListener(this);
		findViewById(R.id.round_down).setOnClickListener(this);
		findViewById(R.id.round_up).setOnClickListener(this);
	}


	private void setTotals() {
		billTotal.setText(getString(R.string.currency_format, mainViewModel.getBillTotal()));
		tipTotal.setText(getString(R.string.currency_format, mainViewModel.getTip()));
		splitTotal.setText(getString(R.string.currency_format, mainViewModel.getSplitTotal()));
	}

	@Override
	public void onClick(final View view) {
		switch (view.getId()) {
			case R.id.tip_subtract:
				tipPercent.setText(getString(R.string.currency_format_input,
						Math.max(0.0, Math.ceil(mainViewModel.getTipPercent() - 1.0))));
				break;
			case R.id.tip_add:
				tipPercent.setText(getString(R.string.currency_format_input,
						Math.max(0.0, Math.floor(mainViewModel.getTipPercent() + 1.0))));
				break;
			case R.id.split_subtract:
				split.setText(String.valueOf(Math.max(1, mainViewModel.getSplit() - 1)));
				break;
			case R.id.split_add:
				split.setText(String.valueOf(mainViewModel.getSplit() + 1));
				break;
			case R.id.tip_10:
				tipPercent.setText(getString(R.string.currency_format_input, 10.00));
				break;
			case R.id.tip_15:
				tipPercent.setText(getString(R.string.currency_format_input, 15.00));
				break;
			case R.id.tip_18:
				tipPercent.setText(getString(R.string.currency_format_input, 18.00));
				break;
			case R.id.tip_20:
				tipPercent.setText(getString(R.string.currency_format_input, 20.00));
				break;
			case R.id.round_down:
				final double tipLower = ((Math.floor(mainViewModel.getBillTotal()) - mainViewModel.getBillAmount()) / mainViewModel.getBillAmount()) * 100.0;
				tipPercent.setText(getString(R.string.currency_format_input, tipLower));
				mainViewModel.setTipPercent(tipLower);
				//setTotals called again to account for more precise tip %
				setTotals();
				break;
			case R.id.round_up:
				final double tipUpper = ((Math.ceil(mainViewModel.getBillTotal()) - mainViewModel.getBillAmount()) / mainViewModel.getBillAmount()) * 100.0;
				tipPercent.setText(getString(R.string.currency_format_input, tipUpper));
				mainViewModel.setTipPercent(tipUpper);
				//setTotals called again to account for more precise tip %
				setTotals();
				break;
		}
	}
}