package com.xhy.Mapper;

import com.xhy.domain.audit;

import java.util.List;

public interface AuditMapper {
    List<audit> findAll(String upname);
    Integer addAudit(audit audit);
    Integer updateAudit(audit audit);
    Integer deleteAudit(int id);
}
