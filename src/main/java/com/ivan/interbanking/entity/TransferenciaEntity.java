package com.ivan.interbanking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "Transferencia")
@Entity(name = "Transferencia")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferenciaEntity
{
	// Panache requiere que los campos de los entities sean publicos.
	// Tras bambalinas igualmente se termina llamando a los getter y setters.
	// Documentacion de Quarkus https://es.quarkus.io/guides/hibernate-orm-panache#:~:text=Use%20public%20fields.%20Get,looks%20like%20field%20accessors.
	@Id
	@GeneratedValue(generator = "transferencia_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(
			name = "transferencia_seq",
			sequenceName = "transferencia_seq",
			allocationSize = 50
	)
	public Long id;
	public Integer importe;
	@ManyToOne
	@JoinColumn(name = "idEmpresa", nullable = false)
	// Getter necesario para poder hacer groupingBy()
	@Getter
	public EmpresaEntity empresa;
	public String cuentaDebito;
	public String cuentaCredito;
	public LocalDate fecha;
}
