package com.ivan.interbanking.service;

import com.ivan.interbanking.dtos.EmpresaDTO;
import com.ivan.interbanking.entity.EmpresaEntity;
import com.ivan.interbanking.entity.TransferenciaEntity;
import com.ivan.interbanking.repository.EmpresaRespository;
import com.ivan.interbanking.repository.TransferenciaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
@AllArgsConstructor
public class EmpresaService
{
	@Inject
	TransferenciaRepository transferenciaRepository;
	@Inject
	EmpresaRespository empresaRespository;

	private static final String CUIT_REGEX = "^\\d{11}$";

	public List<EmpresaDTO> empresasUltimoMes()
	{
		List<EmpresaEntity> empresaEntitiesList = empresaRespository.empresasAdheridasMes(System.currentTimeMillis());
		return empresaEntitiesList.stream()
				.map(EmpresaDTO::new)
				.toList();
	}

	public void adherirEmpresa(EmpresaDTO empresaDTO)
	{
		validarCuit(empresaDTO.getCuit());
		EmpresaEntity empresaEntity = new EmpresaEntity(empresaDTO);
		empresaRespository.persist(empresaEntity);
	}

	public List<EmpresaDTO> empresasActivasUltimoMes()
	{
		List<TransferenciaEntity> transferenciaEntityList = transferenciaRepository.transferenciasMes(System.currentTimeMillis());
		Map<EmpresaEntity, List<TransferenciaEntity>> empresaToTransferenciaMap =
				transferenciaEntityList.stream().collect(Collectors.groupingBy(TransferenciaEntity::getEmpresa));
		return empresaToTransferenciaMap.keySet().stream()
				.map(EmpresaDTO::new)
				.toList();
	}
	
	private void validarCuit(String cuitAValidar)
	{
		if (cuitAValidar == null || cuitAValidar.isBlank() || cuitAValidar.isEmpty())
		{
			throw new BadRequestException("El CUIT no puede ser vacío");
		}

		if (!cuitAValidar.matches(CUIT_REGEX))
		{
			throw new BadRequestException("El CUIT no es válido");
		}

		if (empresaRespository.findByCuit(cuitAValidar).isPresent())
		{
			throw new BadRequestException("La empresa con CUIT: " + cuitAValidar + " ya existe");
		}
	}
}
