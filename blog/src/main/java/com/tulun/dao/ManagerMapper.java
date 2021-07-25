package com.tulun.dao;

import com.tulun.model.Manager;

public interface ManagerMapper {
    Manager selectManagerByNameAndPassword(Manager manager);
}
