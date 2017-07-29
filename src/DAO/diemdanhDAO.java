/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import entities.DiemDanh;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Phan Nghia
 */
public class diemdanhDAO {
    public static List<DiemDanh> dsCTMH=layDSCTMH();
    
    public static List<DiemDanh> layDSCTMH(){
        List<DiemDanh> ds = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from DiemDanh";
            Query query = session.createQuery(hql);
            ds = query.list();
        } catch (Exception ex) {
            System.err.println(ex);
        } finally {
            session.close();
        }
        return ds;
    }
    static public DiemDanh timKiem(Integer STT) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return (DiemDanh) session.get(DiemDanh.class, STT);
        } catch (HibernateException e) {
            System.err.println(e);
        } finally{
            session.close();
        }
        return null;
    }
    static public DiemDanh timKiemTheoMaMHVaMaSV(String maMH, String maSV){
        DiemDanh kq=null;
        Session session=HibernateUtil.getSessionFactory().openSession();
        try {
            String hql="from DiemDanh s where s.tenMaMh=:maMH and s.maSv=:maSV";
            Query query=session.createQuery(hql);
            query.setString("maMH", maMH);
            query.setString("maSV", maSV);
            kq=(DiemDanh)query.list().get(0);
        } catch (Exception e) {
            System.out.println(e);
        }finally{
            session.close();
        }
        return kq;
    }
    public static boolean isExist(DiemDanh ctmh){
        List<String> dsmaSV=null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String tmptenMaMH=ctmh.getTenMaMh();
            String tmpMaSV=ctmh.getMaSv();
            String hql = "select s.maSv from DiemDanh s where s.tenMaMh=:tmptenMaMH and s.maSv=:tmpMaSV";
            Query query = session.createQuery(hql);
            query.setString("tmptenMaMH", tmptenMaMH);
            query.setString("tmpMaSV", tmpMaSV);
            dsmaSV=query.list();
        } catch (Exception e) {
            System.err.println(e);
        }finally {
            session.close();
        }
        return (dsmaSV.size()>0);
    }
    
    public static boolean themDiemDanh(DiemDanh ctMH){
        Session session=HibernateUtil.getSessionFactory().openSession();
        if(isExist(ctMH)){
            return false;
        }else{
            Transaction transaction=null;
            try {
                transaction=session.beginTransaction();
                session.save(ctMH);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                System.out.println(e);
            }finally{
                session.close();
            }
        }
        return true;
    }
    public static List<DiemDanh> getDiemDanhTheoTenMaMH(String tenMaMH){
        List<DiemDanh> ds=null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select distinct s from DiemDanh s where s.tenMaMh=:tenmaMH";
            Query query = session.createQuery(hql);
            query.setString("tenmaMH", tenMaMH);
            ds=query.list();
        } catch (Exception e) {
            System.err.println(e);
        }finally {
            session.close();
        }
        return ds;
    }
    public static List<DiemDanh> getDiemDanhTheoMaSV(String maSV){
        List<DiemDanh> ds=null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select distinct s from DiemDanh s where s.maSv=:maSV";
            Query query = session.createQuery(hql);
            query.setString("maSV", maSV);
            ds=query.list();
        } catch (Exception e) {
            System.err.println(e);
        }finally {
            session.close();
        }
        return ds;
    }
    
    public static boolean capNhatDSDD(DiemDanh dd){
        Session session=HibernateUtil.getSessionFactory().openSession();
        if (timKiem(dd.getStt())!=null) {
            Transaction transaction=null;
            try {
                transaction=session.beginTransaction();
                session.update(dd);
                transaction.commit();
            } catch (Exception e) {
                System.out.println(e);
                transaction.rollback();
            }finally{
                session.close();
            }
        } else {
            return false;
        }
        return true;
    }
}
