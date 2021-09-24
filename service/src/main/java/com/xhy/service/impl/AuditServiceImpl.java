package com.xhy.service.impl;

import com.xhy.Mapper.AuditMapper;
import com.xhy.domain.audit;
import com.xhy.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    AuditMapper auditMapper;

    @Override
    public List<audit> findAll(String upname) {

        return auditMapper.findAll(upname);
    }

    @Override
    public Integer addAudit(audit audit) {

        return auditMapper.addAudit(audit);
    }

    @Override
    public Integer updateAudit(audit audit) {
        return auditMapper.updateAudit(audit);
    }

    @Override
    public Integer deleteAudit(int id) {
        return auditMapper.deleteAudit(id);
    }
}
