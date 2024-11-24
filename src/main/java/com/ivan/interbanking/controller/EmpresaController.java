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

@Path("empresa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class EmpresaController
{
	@Inject
	EmpresaService empresaService;

	@POST
	@Transactional
	public Response adherirEmpresa(EmpresaDTO empresaDTO)
	{
		empresaService.adherirEmpresa(empresaDTO);
		return Response.status(Response.Status.CREATED).build();
	}

	@GET
	public Response empresasUltimoMes()
	{
		return Response.ok(empresaService.empresasUltimoMes()).build();
	}

	@GET
	@Path("/activas")
	public Response empresasActivasUltimoMes()
	{
		return Response.ok(empresaService.empresasActivasUltimoMes()).build();
	}

}
