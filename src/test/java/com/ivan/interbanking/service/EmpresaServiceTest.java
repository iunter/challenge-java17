package com.ivan.interbanking.service;

import com.ivan.interbanking.dtos.EmpresaDTO;
import com.ivan.interbanking.entity.EmpresaEntity;
import com.ivan.interbanking.entity.TransferenciaEntity;
import com.ivan.interbanking.repository.EmpresaRespository;
import com.ivan.interbanking.repository.TransferenciaRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@QuarkusTest
public class EmpresaServiceTest
{
	@Mock
	TransferenciaRepository transferenciaRepository;

	@Mock
	EmpresaRespository empresaRespository;

	private EmpresaService empresaService;

	EmpresaDTO empresaDTO;

	@BeforeEach
	public void setup()
	{
		MockitoAnnotations.openMocks(this);
		empresaService = new EmpresaService(transferenciaRepository, empresaRespository);

		empresaDTO = EmpresaDTO.builder()
				.id(1l)
				.cuit("12345678901")
				.fechaAdhesion(LocalDate.now())
				.razonSocial("TEST")
				.build();
	}

	@Test
	public void empresasUltimoMes_success()
	{
		EmpresaEntity empresaEntity = EmpresaEntity.builder()
				.id(1l)
				.cuit("12345678901")
				.razonSocial("TEST")
				.fechaAdhesion(LocalDate.now())
				.build();

		Mockito.when(empresaRespository.empresasAdheridasMes(Mockito.anyLong()))
				.thenReturn(List.of(empresaEntity));

		EmpresaDTO empresaResponseDTO = empresaService.empresasUltimoMes().get(0);

		Assertions.assertEquals(empresaEntity.id, empresaResponseDTO.getId());
		Assertions.assertEquals(empresaEntity.cuit, empresaResponseDTO.getCuit());
		Assertions.assertEquals(empresaEntity.razonSocial, empresaResponseDTO.getRazonSocial());
		Assertions.assertEquals(empresaEntity.fechaAdhesion, empresaResponseDTO.getFechaAdhesion());
	}

	@Test
	public void empresasUltimoMes_empty()
	{
		Mockito.when(empresaRespository.empresasAdheridasMes(Mockito.anyLong()))
				.thenReturn(List.of());

		List<EmpresaDTO> empresaDTOList = empresaService.empresasUltimoMes();

		Assertions.assertTrue(empresaDTOList.isEmpty());
	}

	@Test
	public void adherirEmpresa_success()
	{
		Mockito.when(empresaRespository.findByCuit(empresaDTO.getCuit()))
				.thenReturn(Optional.empty());
		Mockito.doNothing().when(empresaRespository).persist(Mockito.any(EmpresaEntity.class));

		empresaService.adherirEmpresa(empresaDTO);

		ArgumentCaptor<EmpresaEntity> empresaEntityCaptor = ArgumentCaptor.forClass(EmpresaEntity.class);
		Mockito.verify(empresaRespository).persist(empresaEntityCaptor.capture());

		EmpresaEntity entityPersistido = empresaEntityCaptor.getValue();
		Assertions.assertEquals(empresaDTO.getCuit(), entityPersistido.cuit);
		Assertions.assertEquals(empresaDTO.getRazonSocial(), entityPersistido.razonSocial);
		Assertions.assertEquals(empresaDTO.getFechaAdhesion(), entityPersistido.fechaAdhesion);
	}

	@Test
	public void adherirEmpresa_cuitNull_BadRequestException()
	{
		EmpresaDTO empresaDTOCuitNull = EmpresaDTO.builder().build();

		Assertions.assertThrows(BadRequestException.class, () -> empresaService.adherirEmpresa(empresaDTOCuitNull));
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " ", "1111111111a", "1234567890", "123456789012", "-12345678901", "-1234567890"})
	public void adherirEmpresa_cuitsInvalidos_BadRequestException(String cuit)
	{
		EmpresaDTO empresaDTOCuitInvalido = EmpresaDTO.builder()
				.cuit(cuit)
				.build();

		Assertions.assertThrows(BadRequestException.class, () -> empresaService.adherirEmpresa(empresaDTOCuitInvalido));
	}

	@Test
	public void adherirEmpresa_cuitExistente_BadRequestException()
	{
		Mockito.when(empresaRespository.findByCuit(empresaDTO.getCuit()))
				.thenReturn(Optional.of(new EmpresaEntity()));

		Assertions.assertThrows(BadRequestException.class, () -> empresaService.adherirEmpresa(empresaDTO));
	}

	
	@Test
	public void empresasActivasUltimoMes_success()
	{
		EmpresaEntity empresaEntitySinTransferencias = EmpresaEntity.builder()
				.id(0l)
				.cuit("12345678900")
				.razonSocial("TEST0")
				.fechaAdhesion(LocalDate.now())
				.build();

		EmpresaEntity empresaEntity = EmpresaEntity.builder()
				.id(1l)
				.cuit("12345678901")
				.razonSocial("TEST")
				.fechaAdhesion(LocalDate.now())
				.build();

		EmpresaEntity empresaEntity2 = EmpresaEntity.builder()
				.id(2l)
				.cuit("12345678902")
				.razonSocial("TEST2")
				.fechaAdhesion(LocalDate.now())
				.build();

		TransferenciaEntity transferenciaEntity = TransferenciaEntity.builder()
				.id(1l)
				.empresa(empresaEntity)
				.build();

		TransferenciaEntity transferenciaEntity2 = TransferenciaEntity.builder()
				.id(2l)
				.empresa(empresaEntity)
				.build();

		TransferenciaEntity transferenciaEntity3 = TransferenciaEntity.builder()
				.id(3l)
				.empresa(empresaEntity2)
				.build();

		Mockito.when(transferenciaRepository.transferenciasMes(Mockito.anyLong()))
				.thenReturn(List.of(transferenciaEntity, transferenciaEntity2, transferenciaEntity3));

		List<EmpresaDTO> empresasActivas = empresaService.empresasActivasUltimoMes();

		Assertions.assertEquals(2, empresasActivas.size());

		Assertions.assertTrue(empresasActivas.stream().anyMatch(x -> x.getCuit().equals(empresaEntity.cuit)));
		Assertions.assertTrue(empresasActivas.stream().anyMatch(x -> x.getCuit().equals(empresaEntity2.cuit)));
		Assertions.assertFalse(empresasActivas.stream().anyMatch(x -> x.getCuit().equals(empresaEntitySinTransferencias.cuit)));
	}

	@Test
	public void empresasActivasUltimoMes_empty()
	{
		Mockito.when(transferenciaRepository.transferenciasMes(Mockito.anyLong()))
				.thenReturn(List.of());

		List<EmpresaDTO> empresaDTOList = empresaService.empresasActivasUltimoMes();

		Assertions.assertTrue(empresaDTOList.isEmpty());
	}
}
