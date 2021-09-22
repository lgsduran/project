package br.com.mesttra.project.response;

import lombok.Value;

@Value
public class BudgetResponse {

	public int id;
	public double totalAmount;
	public double spentAmount;
	public String possibleDestinations;
	public String origem;

}
