/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entities.GiaoVu;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Phan Nghia
 */
public class giaovuDAO {

    public static List<GiaoVu> dsGV = layDSGV();

    public static List<GiaoVu> layDSGV() {
        List<GiaoVu> ds = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select * from GiaoVu";
            Query query = session.createQuery(hql);
            ds = query.list();
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return ds;
    }

    public static GiaoVu timKiem(String id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (GiaoVu) session.get(GiaoVu.class, id);
        } catch (HibernateException e) {
            System.err.println(e);
        } finally {
            session.close();
        }
        return null;
    }
}
