package br.com.mesttra.project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Folder {

	HEALTH("HEALTH"), EDUCATION("EDUCATION"), SPORTS("SPORTS"), INFRASTRUCTURE("INFRASTRUCTURE");

	private final String value;

}
