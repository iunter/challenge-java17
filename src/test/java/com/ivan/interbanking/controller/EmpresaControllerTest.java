package com.ivan.interbanking.controller;

import com.ivan.interbanking.dtos.EmpresaDTO;
import com.ivan.interbanking.service.EmpresaService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

@QuarkusTest
public class EmpresaControllerTest
{
	private EmpresaController empresaController;

	@Mock
	EmpresaService empresaService;

	private EmpresaDTO empresaDTO;

	@BeforeEach
	public void setup()
	{
		MockitoAnnotations.openMocks(this);
		empresaController = new EmpresaController(empresaService);

		empresaDTO = EmpresaDTO.builder()
				.id(1l)
				.cuit("TEST")
				.fechaAdhesion(LocalDate.now())
				.razonSocial("TEST")
				.build();
	}

	@Test
	public void adherirEmpresa_success()
	{
		Mockito.doNothing().when(empresaService).adherirEmpresa(Mockito.any(EmpresaDTO.class));

		empresaController.adherirEmpresa(empresaDTO);

		Mockito.verify(empresaService).adherirEmpresa(empresaDTO);
	}

	@Test
	public void empresasUltimoMes_success()
	{

		Mockito.when(empresaService.empresasUltimoMes()).thenReturn(List.of(empresaDTO));

		Response response = empresaController.empresasUltimoMes();

		EmpresaDTO empresaDTOResponse = (EmpresaDTO) response.readEntity(List.class).get(0);

		Assertions.assertEquals(empresaDTO.getId(), empresaDTOResponse.getId());
		Assertions.assertEquals(empresaDTO.getCuit(), empresaDTOResponse.getCuit());
		Assertions.assertEquals(empresaDTO.getFechaAdhesion(), empresaDTOResponse.getFechaAdhesion());
		Assertions.assertEquals(empresaDTO.getRazonSocial(), empresaDTOResponse.getRazonSocial());
	}

	@Test
	public void empresasActivasUltimoMes_success()
	{
		Mockito.when(empresaService.empresasActivasUltimoMes()).thenReturn(List.of(empresaDTO));

		Response response = empresaController.empresasActivasUltimoMes();

		EmpresaDTO empresaDTOResponse = (EmpresaDTO) response.readEntity(List.class).get(0);

		Assertions.assertEquals(empresaDTO.getId(), empresaDTOResponse.getId());
		Assertions.assertEquals(empresaDTO.getCuit(), empresaDTOResponse.getCuit());
		Assertions.assertEquals(empresaDTO.getFechaAdhesion(), empresaDTOResponse.getFechaAdhesion());
		Assertions.assertEquals(empresaDTO.getRazonSocial(), empresaDTOResponse.getRazonSocial());
	}
}
