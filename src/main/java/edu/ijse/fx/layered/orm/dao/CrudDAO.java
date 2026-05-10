package edu.ijse.fx.layered.orm.dao;

import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {

    boolean save(T t) throws Exception;
    boolean update(T t) throws Exception;
    boolean delete(Integer id) throws Exception;
    T search(Integer id) throws Exception;
    ArrayList<T> getAll() throws Exception;

}
