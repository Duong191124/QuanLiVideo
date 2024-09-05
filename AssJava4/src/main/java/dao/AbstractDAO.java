package dao;

import ultil.JpaUltil;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

public class AbstractDAO<T> {
    public static final EntityManager entityManager = JpaUltil.getEntityManager();

    @SuppressWarnings("deprecation")
    @Override
    protected void finalize() throws Throwable{
        entityManager.close();
        super.finalize();
    }

    public T findById(Class<T> classes , Integer id){
        return entityManager.find(classes, id);
    }

    public List<T> findAll(Class<T> classes, Boolean existIsActive){
        String entityName = classes.getSimpleName();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o FROM ").append(entityName).append(" o");
        if(existIsActive == true){
            sql.append(" WHERE isActive = 1");
        }
        TypedQuery<T> query = entityManager.createQuery(sql.toString(), classes);
        return query.getResultList();
    }

    public List<T> findAll(Class<T> classes, Boolean existIsActive, int pageNumber, int pageSize){
        String entityName = classes.getSimpleName();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o FROM ").append(entityName).append(" o");
        if(existIsActive == true){
            sql.append(" WHERE isActive = 1");
        }
        TypedQuery<T> query = entityManager.createQuery(sql.toString(), classes);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public T findOne(Class<T> classes, String sql, Object... params){
        TypedQuery<T> query = entityManager.createQuery(sql, classes);
        for(int i = 0; i < params.length; i++){
            query.setParameter(i, params[i]);
        }
        List<T> result = query.getResultList();
        if(result.isEmpty()){
            return null;
        }
        return result.get(0);
    }

    public List<T> findMany(Class<T> classes, String sql, Object... params){
        TypedQuery<T> query = entityManager.createQuery(sql, classes);
        for(int i = 0; i < params.length; i++){
            query.setParameter(i, params[i]);
        }
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> findManyByNativeQuery(String sql, Object... params){
        Query query = entityManager.createNativeQuery(sql);
        for(int i = 0; i < params.length; i++){
            query.setParameter(i, params[i]);
        }
        return query.getResultList();
    }

    public T create(T entity){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            return entity;
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public T update(T entity){
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();
            return entity;
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    public T delete(T entity){
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
            return entity;
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> callStored(String nameStored, Map<String, Object> params){
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery(nameStored);
        params.forEach((key, value) -> query.setParameter(key, value));
        return query.getResultList();
    }

}
