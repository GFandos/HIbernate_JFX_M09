import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.Iterator;
import java.util.List;

/**
 * Created by 47989768s on 23/01/17.
 */
public class ManageVideogame {

    private static SessionFactory factory;
    public static void main(String[] args) {
        try{
            factory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        ManageVideogame ME = new ManageVideogame();

      /* Add few employee records in database */
        Integer v1 = ME.addVideogame("Crysis", 1, 15, 1);
        Integer v2 = ME.addVideogame("Dark Souls", 4, 20, 1);
        Integer v3 = ME.addVideogame("Resident Evil VII", 1, 60, 1);

      /* List down all the employees */
        ME.listVideogames();

      /* Update employee's records */
        ME.updateVideogames(v2, 25);

      /* Delete an employee from the database */
        ME.deleteVideogame(v1);

      /* List down new list of the employees */
        ME.listVideogames();
    }
    /* Method to CREATE an employee in the database */
    public Integer addVideogame(String title, int players, int price, int company){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer videogameID = null;
        try{
            tx = session.beginTransaction();
            Videogame videogame = new Videogame(title, players, price, company);
            videogameID = (Integer) session.save(videogame);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return videogameID;
    }
    /* Method to  READ all the employees */
    public List<Videogame> listVideogames( ){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        List videogames = null;
        try{
            tx = session.beginTransaction();
            videogames = session.createQuery("FROM Videogame").list();
            for (Iterator iterator = videogames.iterator(); iterator.hasNext();){
                Videogame videogame = (Videogame) iterator.next();
                System.out.print("  Title: " + videogame.getTitle());
                System.out.print("  Players: " + videogame.getPlayers());
                System.out.println("    Price: " + videogame.getPrice());
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }

        return videogames;
    }
    /* Method to UPDATE salary for an employee */
    public void updateVideogames(Integer videogameID, int price ){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Videogame videogame =
                    (Videogame)session.get(Videogame.class, videogameID);
            videogame.setPrice( price );
            session.update(videogame);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to DELETE an employee from the records */
    public void deleteVideogame(Integer videogameID){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            Videogame videogame =
                    (Videogame)session.get(Videogame.class, videogameID);
            session.delete(videogame);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public Boolean exists (Videogame v) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Query query = session.createQuery("select 1 from Videogame t where t.title = :key");
        query.setString("key", v.getTitle() );
        return (query.uniqueResult() != null);
    }


    /* Method to CREATE a company in the database */
    public Integer addCompany(String name){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        Integer companyID = null;
        try{
            tx = session.beginTransaction();
            Companies c = new Companies(name);
            companyID = (Integer) session.save(c);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return companyID;
    }

    public List<Companies> listCompanies( ){
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = null;
        List companies = null;
        try{
            tx = session.beginTransaction();
            companies = session.createQuery("FROM Companies").list();
            for (Iterator iterator = companies.iterator(); iterator.hasNext();){
                Companies c = (Companies) iterator.next();
                System.out.print("  Name: " + c.getName());
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }

        return companies;
    }

}
