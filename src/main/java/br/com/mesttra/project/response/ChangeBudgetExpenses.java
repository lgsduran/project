package br.com.mesttra.project.response;

import javax.validation.constraints.NotNull;

public class ChangeBudgetExpenses {
	
	@NotNull(message = "Element must not be null.")
	public Double spentAmount;

}
