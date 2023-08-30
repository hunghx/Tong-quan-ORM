package ra.model.repository;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import ra.model.entity.Person;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository implements IPersonRepository{
    private static SessionFactory sessionFactory;
    private static EntityManager entityManager;
    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.config.xml")
                    .buildSessionFactory();
            entityManager = sessionFactory.createEntityManager();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Person> findAll() {
        List<Person> list = new ArrayList<>();
        String hql = "SELECT p FROM Person AS p";
        // sử dụng các phương thức của đối tượng 1 cách tự động
        TypedQuery<Person> type = entityManager.createQuery(hql, Person.class);
        // lấy về danh sách
        list = type.getResultList();
        return list;
    }

    @Override
    public Person findByID(Long id) {
        // sử dụng các phương thức của đối tượng 1 cách tự động
        TypedQuery<Person> type = entityManager.createQuery("SELECT p FROM Person AS p WHERE p.id=:id", Person.class);
        type.setParameter("id",id);
        // lấy về 1 đối tương
        Person p = type.getSingleResult();
        return p;
    }

    @Override
    public void save(Person p) {
        // Khởi tạo các đối tượng để quản lí giao dịch
        Session session = null;
        Transaction transaction = null;
        try {
            // khởi tạo session (phiên)
            session = sessionFactory.openSession();
            // bắt đầu 1 giao dịch
            transaction = session.beginTransaction();
            if(p.getId()==null){
                // chức năng thêm mới
                session.save(p);
            }else {
                // chức năng update (liên quan đến copy)
                // lấy đối tượng cũ cần sửa ra
                Person old = findByID(p.getId());
                if(p.getAvatar()==null){
                    p.setAvatar(old.getAvatar());
                }
                old.copy(p);
                session.saveOrUpdate(old);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.isActive();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void delete(Long id) {
        Session session =null;
        Transaction transaction = null;
        try{
            session = sessionFactory.openSession();
            transaction= session.beginTransaction();
            session.delete(findByID(id));
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            if(transaction!=null){
                transaction.isActive();
            }
        }finally {
            if (session!=null){
                session.close();
            }
        }
    }
}
