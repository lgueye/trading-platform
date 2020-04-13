package io.agileinfra.trading.platform.account.model;

import com.google.common.collect.Sets;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode(of = {"instrument"})
public class AssetDto {
	private String instrument;
	private double price;
	private double quantity;
	private Set<String> orders = Sets.newHashSet();

}
