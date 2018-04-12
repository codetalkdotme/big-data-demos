package me.codetalk.demo.dao.exception;

public class BaseDaoException extends RuntimeException {

    public BaseDaoException(String mesg) {
        super(mesg);
    }

    public BaseDaoException(String mesg, Throwable ex) {
        super(mesg, ex);
    }

    public BaseDaoException(Throwable ex) {
        super(ex);
    }

}
