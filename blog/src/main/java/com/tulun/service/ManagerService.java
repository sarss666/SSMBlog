package com.tulun.service;

import com.tulun.dao.ManagerMapper;
import com.tulun.model.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService {
    @Autowired
    private ManagerMapper managerMapper;

    public boolean login(Manager manager) {
        Manager m = managerMapper.selectManagerByNameAndPassword(manager);
        if (m == null || m.getId() == null) {
            return false;
        }
        return true;
    }
}
