package io.agileinfra.trading.platform.account.model;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AccountDto {
	private String id;
	private String owner;
	private double cash;
	private List<AssetDto> assets = Lists.newArrayList();
	private List<AccountOrderDto> orders = Lists.newArrayList();

	public void cashIn(float amount) {
		cash += amount;
	}

	public void cashOut(float amount) {
		cash -= amount;
	}

	public double getValuation() {
		final Double assetsValuation = assets.stream().map(asset -> asset.getPrice() * asset.getQuantity()).reduce(0d, Double::sum);
		final Double ordersValuation = orders.stream().map(order -> order.getPrice() * order.getQuantity()).reduce(0d, Double::sum);
		return Double.sum(Double.sum(cash, assetsValuation), ordersValuation);
	}
}
