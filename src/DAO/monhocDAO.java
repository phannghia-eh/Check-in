/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entities.MonHoc;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Phan Nghia
 */
public class monhocDAO {

    public static List<MonHoc> dsMH = layDSMH();

    public static List<MonHoc> layDSMH() {
        List<MonHoc> ds = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from MonHoc";
            Query query = session.createQuery(hql);
            ds = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }

        return ds;
    }

    public static MonHoc timKiem(String id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (MonHoc) session.get(MonHoc.class, id);
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return null;
    }

    public static boolean themMonHoc(MonHoc mh) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        if (sinhvienDAO.timKiem(mh.getTenMaMh()) != null) {
            return false;
        } else {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.save(mh);
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
    public static List<MonHoc> getDSMHTheoMaSV(String maSV){
        List<MonHoc> ds = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select mh from MonHoc mh, DiemDanh dd where mh.tenMaMh=dd.tenMaMh and dd.maSv=:maSV";
            Query query = session.createQuery(hql);
            query.setString("maSV", maSV);
            ds = query.list();
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }

        return ds;
    }
}
