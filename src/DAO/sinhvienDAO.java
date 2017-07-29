/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entities.SinhVien;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Phan Nghia
 */
public class sinhvienDAO {
    public static List<SinhVien> listSV = layDSSV();
    public static List<SinhVien> layDSSV() {
        List<SinhVien> ds = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from SinhVien";
            Query query = session.createQuery(hql);
            ds = query.list();
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return ds;
    }

    static public SinhVien timKiem(String id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (SinhVien) session.get(SinhVien.class, id);
        } catch (HibernateException e) {
            System.err.println(e);
        } finally{
            session.close();
        }
        return null;
    }
    
    public static boolean capNhatLanDau(SinhVien sv){
        Session session=HibernateUtil.getSessionFactory().openSession();
        session.flush();
        Transaction transaction=null;
        try {
            session.flush();
            transaction=session.beginTransaction();
            session.update(sv);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
            transaction.rollback();
        }finally{
            session.close();
        }
        return true;
    }
    
    public static boolean themSinhVien(SinhVien sv) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        if (sinhvienDAO.timKiem(sv.getMaSv()) != null) {
            return false;
        } else {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(sv);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                System.out.println(e);
            } finally {
                session.close();
            }
        }

        return true;
    }
}
