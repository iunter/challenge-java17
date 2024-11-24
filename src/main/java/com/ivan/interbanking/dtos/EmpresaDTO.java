package com.ivan.interbanking.dtos;

import com.ivan.interbanking.entity.EmpresaEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpresaDTO
{
	private Long id;
	private String cuit;
	private String razonSocial;
	private LocalDate fechaAdhesion;

	public EmpresaDTO(EmpresaEntity empresaEntity)
	{
		// Ver comentarios en EmpresaEntity
		this.id = empresaEntity.id;
		this.cuit = empresaEntity.cuit;
		this.razonSocial = empresaEntity.razonSocial;
		this.fechaAdhesion = empresaEntity.fechaAdhesion;
	}
}
