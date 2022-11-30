package hr.hr.entity;

import java.io.Serializable;

public enum Command implements Serializable {
    READ,
    READ1,
    READ2,
    READ3,
    CREATE,
    CREATE2,
    CREATE3,
    UPDATE,
    UPDATE1,
    DELETE,
    DELETE1,
    DELETE2,
    DELETE3,
    EXIT,
    READPOSITION,
    CREATE1,
    READEMPLOYEE,
    READDEPARTMENT,
    RECEIVEDEPARTMENT,
    READPOSITIONUNIQUE,
    UPDATEEMPLOYEEDEPARTMENT,
    READSALARY,
}