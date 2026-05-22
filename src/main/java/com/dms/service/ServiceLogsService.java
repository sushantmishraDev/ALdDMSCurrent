package com.dms.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dms.model.ServiceLogs;

@Service
public class ServiceLogsService {

    @PersistenceContext(unitName = "persistenceUnitDMS")
    private EntityManager em;

    @Transactional("transactionManager")
    public ServiceLogs save(ServiceLogs sl) {

        try {
            ServiceLogs serviceLogs = em.merge(sl);

            System.out.println(
                "Service log saved with ID: " + serviceLogs
            );

            return serviceLogs;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}