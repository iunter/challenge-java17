package com.ivan.interbanking.controller;

import com.ivan.interbanking.dtos.EmpresaDTO;
import com.ivan.interbanking.service.EmpresaService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("empresa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class EmpresaController
{
	public static final String NUEVA_EMPRESA_EXAMPLE = "{\"cuit\":\"12345678901\", \"razonSocial\":\"ejemplo\"}";
	public static final String EMPRESAS_ADHERIDAS_MES_EXAMPLE = "[\n" +
			"    {\n" +
			"        \"id\": 1,\n" +
			"        \"cuit\": \"11111111111\",\n" +
			"        \"razonSocial\": \"empresa1\",\n" +
			"        \"fechaAdhesion\": \"2024-11-22\"\n" +
			"    },\n" +
			"    {\n" +
			"        \"id\": 3,\n" +
			"        \"cuit\": \"11111111113\",\n" +
			"        \"razonSocial\": \"empresa3\",\n" +
			"        \"fechaAdhesion\": \"2024-11-10\"\n" +
			"    }\n" +
			"]";
	public static final String EMPRESAS_ACTIVAS_MES_EXAMPLE = "[\n" +
			"    {\n" +
			"        \"id\": 2,\n" +
			"        \"cuit\": \"11111111112\",\n" +
			"        \"razonSocial\": \"empresa2\",\n" +
			"        \"fechaAdhesion\": \"2024-10-21\"\n" +
			"    }\n" +
			"]";

	@Inject
	private EmpresaService empresaService;

	@POST
	@Operation(summary = "Adhiere una empresa nueva", description = "El cuit debe ser un numero positivo de 11 cifras y " +
			"no debe existir previamente en la base de datos.\n" +
			"Se tomara la fecha del servidor como la fecha de adhesion.")
	@RequestBody(
			required = true,
			content = @Content(
					schema = @Schema(implementation = EmpresaDTO.class, required = true, requiredProperties = {"cuit", "razonSocial"}),
					examples = @ExampleObject(
							name = "Nueva empresa",
							description = "Adhiere la empresa nueva",
							value = NUEVA_EMPRESA_EXAMPLE
					)
			)
	)
	@APIResponses(
			value = {
					@APIResponse(
							name = "success",
							responseCode = "201"
					),
					@APIResponse(
							name = "Bad request",
							responseCode = "400",
							description = "Hay un error con los datos ingresados y se envia mensaje de error."
					)
			}
	)
	@Transactional
	public Response adherirEmpresa(EmpresaDTO empresaDTO)
	{
		empresaService.adherirEmpresa(empresaDTO);
		return Response.status(Response.Status.CREATED).build();
	}

	@GET
	@Operation(summary = "Obtener listado de empresas adheridas durante el ultimo mes")
	@APIResponses(
			value = {
					@APIResponse(
							name = "Empresas adheridas ultimo mes",
							responseCode = "200",
							content = @Content(
									mediaType = "application/json",
									schema = @Schema(
											type = SchemaType.ARRAY,
											example = EMPRESAS_ADHERIDAS_MES_EXAMPLE
									)
							)
					)
			}
	)
	public Response empresasUltimoMes()
	{
		return Response.ok(empresaService.empresasUltimoMes()).build();
	}

	@GET
	@Path("/activas")
	@Operation(summary = "Obtener listado de empresas que realizaron transferencias durante el ultimo mes")
	@APIResponses(
			value = {
					@APIResponse(
							name = "Empresas que realizaron transacciones el ultimo mes.",
							responseCode = "200",
							content = @Content(
									mediaType = "application/json",
									schema = @Schema(
											type = SchemaType.ARRAY,
											example = EMPRESAS_ACTIVAS_MES_EXAMPLE
									)
							)
					)
			}
	)
	public Response empresasActivasUltimoMes()
	{
		return Response.ok(empresaService.empresasActivasUltimoMes()).build();
	}

}
