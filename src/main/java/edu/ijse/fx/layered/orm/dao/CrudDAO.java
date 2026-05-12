package edu.ijse.fx.layered.orm.dao;

import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {

    boolean save(T t) throws Exception;
    boolean update(T t) throws Exception;
    boolean delete(String id) throws Exception;
    T search(String id) throws Exception;
    ArrayList<T> getAll() throws Exception;

}
