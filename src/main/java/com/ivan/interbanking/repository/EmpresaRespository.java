package com.ivan.interbanking.repository;

import com.ivan.interbanking.entity.EmpresaEntity;
import com.ivan.interbanking.utils.FechasUtils;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EmpresaRespository implements PanacheRepository<EmpresaEntity>
{
	public List<EmpresaEntity> empresasAdheridasMes(Long fechaEnMilis)
	{
		LocalDate unMesAtras = FechasUtils.restarUnMes(fechaEnMilis);
		return find("fechaAdhesion >= ?1", unMesAtras).list();
	}

	public Optional<EmpresaEntity> findByCuit(String cuit)
	{
		return find("cuit = ?1", cuit).singleResultOptional();
	}
}
