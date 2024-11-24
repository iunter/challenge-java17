package com.ivan.interbanking.entity;

import com.ivan.interbanking.dtos.EmpresaDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "Empresa")
@Table(name = "Empresa")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpresaEntity
{
	// Panache requiere que los campos de los entities sean publicos.
	// Tras bambalinas igualmente se termina llamando a los getter y setters
	// Documentacion de Quarkus https://es.quarkus.io/guides/hibernate-orm-panache#:~:text=Use%20public%20fields.%20Get,looks%20like%20field%20accessors.
	@Id
	@GeneratedValue(generator = "empresa_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(
			name = "empresa_seq",
			sequenceName = "empresa_seq",
			allocationSize = 50
	)
	public Long id;
	public String cuit;
	public String razonSocial;
	public LocalDate fechaAdhesion;

	public EmpresaEntity(EmpresaDTO empresaDTO)
	{
		this.id = empresaDTO.getId();
		this.cuit = empresaDTO.getCuit();
		this.razonSocial = empresaDTO.getRazonSocial();
		this.fechaAdhesion = empresaDTO.getFechaAdhesion();
	}
}
