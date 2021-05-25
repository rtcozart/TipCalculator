package com.example.tipcalculator;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
	private double billAmount;
	private double tipPercent;
	private int split;

	public MainViewModel() {
		this(0.0, 15.0, 1);
	}

	public MainViewModel(final double billAmount, final double tipPercent, final int split) {
		this.billAmount = billAmount;
		this.tipPercent = tipPercent;
		this.split = split;
	}

	public double getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(final double billAmount) {
		this.billAmount = billAmount;
	}

	public double getTipPercent() {
		return tipPercent;
	}

	public void setTipPercent(final double tipPercent) {
		this.tipPercent = tipPercent;
	}

	public int getSplit() {
		return split;
	}

	public void setSplit(final int split) {
		this.split = split;
	}

	public double getBillTotal() {
		return this.billAmount + this.getTip();
	}

	public double getSplitTotal() {
		return getBillTotal() / (double) split;
	}

	public double getTip() {
		return this.billAmount * (this.tipPercent * 0.01);
	}
}
