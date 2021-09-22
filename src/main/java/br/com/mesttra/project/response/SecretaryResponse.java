package br.com.mesttra.project.response;

import lombok.Value;

@Value
public class SecretaryResponse {

	public int id;
	public String secretaryName;
	public String folder;
	public int populationGrade;
	public boolean underInvestigation;

}
