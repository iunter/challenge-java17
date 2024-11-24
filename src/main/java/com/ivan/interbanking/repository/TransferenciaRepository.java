package com.ivan.interbanking.repository;

import com.ivan.interbanking.entity.TransferenciaEntity;
import com.ivan.interbanking.utils.FechasUtils;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class TransferenciaRepository implements PanacheRepository<TransferenciaEntity>
{
	public List<TransferenciaEntity> transferenciasMes(Long fechaEnMilis)
	{
		LocalDate unMesAtras = FechasUtils.restarUnMes(fechaEnMilis);
		return find("fecha >= ?1", unMesAtras).list();
	}
}
