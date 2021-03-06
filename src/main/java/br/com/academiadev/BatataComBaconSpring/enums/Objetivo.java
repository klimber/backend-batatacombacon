package br.com.academiadev.BatataComBaconSpring.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Objetivo implements EnumInterface {

	PERDIDO("Perdido"), ENCONTRADO("Encontrado"), DOACAO("Doação");

	private String descricao;
}