package com.xhy.service;

import com.xhy.domain.audit;

import java.util.List;

public interface AuditService {

    List<audit> findAll(String upname);
    Integer addAudit(audit audit);
    Integer updateAudit(audit audit);
    Integer deleteAudit(int id);
}
