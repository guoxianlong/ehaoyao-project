package com.haoyao.goods.exception;

import org.springframework.dao.DataAccessException;

/**
 * Dao层Exception.
 * 
 * @author 
 * @update 
 */

public class DAOException extends DataAccessException {
    public DAOException(String message) {
	super(message);
    }

    public DAOException(String msg, Throwable ex) {
	super(msg, ex);
    }
}
