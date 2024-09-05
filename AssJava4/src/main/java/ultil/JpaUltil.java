package ultil;


import javax.persistence.*;

public class JpaUltil {
    private static EntityManagerFactory factory;

    public static EntityManager getEntityManager(){
        if(factory == null || factory.isOpen()){
            factory = Persistence.createEntityManagerFactory("AssJava4");
        }
        return factory.createEntityManager();
    }

    public static void Shutdown(){
        if(factory != null && factory.isOpen()){
            factory.close();
        }
        factory = null;
    }

}
