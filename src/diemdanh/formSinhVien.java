/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diemdanh;

import DAO.diemdanhDAO;
import DAO.monhocDAO;
import DAO.sinhvienDAO;
import static diemdanh.Login.userName;
import entities.DiemDanh;
import entities.MonHoc;
import entities.SinhVien;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Phan Nghia
 */
public class formSinhVien extends javax.swing.JFrame {

    /**
     * Creates new form sinhVien
     */
    
    public formSinhVien() {
        initComponents();

        new Thread() {
            public void run() {
                while (true) {
                    Calendar cal = new GregorianCalendar();

                    int hour = cal.get(Calendar.HOUR);
                    int min = cal.get(Calendar.MINUTE);
                    int sec = cal.get(Calendar.SECOND);
                    int AM_PM = cal.get(Calendar.AM_PM);
                    int day = cal.get(Calendar.DATE);
                    int month = cal.get(Calendar.MONTH + 1);
                    int year = cal.get(Calendar.YEAR);

                    String day_night = "";
                    if (AM_PM == 1) {
                        day_night = "PM";
                    } else {
                        day_night = "AM";
                    }
                    String time = day + "-" + month + "-" + year + " " + " " + hour + ":" + min + ":" + sec + " " + day_night;
                    Clock.setText(time);
                }
            }
        }.start();

        addInfoToBox();
        loadDanhSachMonHocDaDangKy();
        themDataDiemDanhVaoBang1();
        jButton1.setEnabled(false);
        checkTGianDiemDanh();
    }
    
    static String tenMaMHDiemDanh=null;
    static String tuan="tuan";
    
    private String checkTGianDiemDanh() {
        String kq=null;
        boolean chk=false;
        Calendar curTime = new GregorianCalendar();
        Date today = curTime.getTime();
        List<MonHoc> dsMHDaDangKy = monhocDAO.getDSMHTheoMaSV(userName);

        for (int i = 0; i < dsMHDaDangKy.size(); i++) {
            Calendar tmpCal;

            MonHoc tmpMH = dsMHDaDangKy.get(i);

            String gioBDFull = tmpMH.getGioBd();
            String gioKTFull = tmpMH.getGioKt();
            int gioBD = Integer.parseInt(gioBDFull.substring(0, 2));
            int phutBD = Integer.parseInt(gioBDFull.substring(3, 5));
            int gioKT = Integer.parseInt(gioKTFull.substring(0, 2));
            int phutKT = Integer.parseInt(gioKTFull.substring(3, 5));
            Date ngayBD = tmpMH.getNgayBd();
            Date ngayKT = tmpMH.getNgayKt();
            int thu = convertStringToThu(tmpMH.getThu());
            
            int AM_PM = curTime.get(Calendar.AM_PM);
            
            int gioHT = curTime.get(Calendar.HOUR);
            int phutHT = curTime.get(Calendar.MINUTE);
            if(gioHT==12){
                if(AM_PM==0)
                    gioHT+=24;
            }else{
                if(AM_PM==1)
                    gioHT+=12;
            }

            if (today.before(ngayKT) && today.after(ngayBD)){
                if(curTime.get(Calendar.DAY_OF_WEEK) == thu){
                    if(gioHT > gioBD && gioHT < gioKT){
                        jButton1.setEnabled(true);
                        kq= dsMHDaDangKy.get(i).getTenMaMh();
                        chk=true;
                    }
                    if(gioHT==gioBD&&phutHT>phutBD){
                        jButton1.setEnabled(true);
                        kq= dsMHDaDangKy.get(i).getTenMaMh();
                        chk=true;
                    }
                    if(gioHT==gioKT&&phutHT<phutKT){
                        jButton1.setEnabled(true);
                        kq= dsMHDaDangKy.get(i).getTenMaMh();
                        chk=true;
                    }
                }
            }
            //Get Tuần thứ ...
            if(chk){
                Calendar cal=Calendar.getInstance();
                cal.setTime(ngayBD);
                int totalDayToNow=curTime.get(Calendar.DAY_OF_YEAR);
                int totalDayToStartDay=cal.get(Calendar.DAY_OF_YEAR);
                int tuanThu=(totalDayToNow-totalDayToStartDay)/7+1;
                tuan+=tuanThu;
                chk=false;
            }
        }
        tenMaMHDiemDanh=kq;
        return kq;
    }
    int convertStringToThu(String value) {
        switch (value) {
            case "Thứ 2":
                return 2;
            case "Thứ 3":
                return 3;
            case "Thứ 4":
                return 4;
            case "Thứ 5":
                return 5;
            case "Thứ 6":
                return 6;
            case "Thứ 7":
                return 7;
            default:
                return 1;
        }
    }

    private void addInfoToBox() {
        //String us=userName;
        //userName = "1412000";
        SinhVien tmp = sinhvienDAO.timKiem(userName);
        jTextField2.setText(tmp.getTenSv());
        jTextField3.setText(tmp.getMaSv());
    }

    private void themDataDiemDanhVaoBang1() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        List<DiemDanh> ds = diemdanhDAO.getDiemDanhTheoMaSV(userName);
        for (int i = 0; i < ds.size(); i++) {
            Object[] row = new Object[]{ds.get(i).getTenMaMh(),
                 ds.get(i).getTuan1(),
                 ds.get(i).getTuan2(),
                 ds.get(i).getTuan3(),
                 ds.get(i).getTuan4(),
                 ds.get(i).getTuan5(),
                 ds.get(i).getTuan6(),
                 ds.get(i).getTuan7(),
                 ds.get(i).getTuan8(),
                 ds.get(i).getTuan9(),
                 ds.get(i).getTuan10(),
                 ds.get(i).getTuan11(),
                 ds.get(i).getTuan12(),
                 ds.get(i).getTuan13(),
                 ds.get(i).getTuan14(),
                 ds.get(i).getTuan15()};
            model.addRow(row);
        }
    }
    
    private void loadDanhSachMonHocDaDangKy(){
        
        try {
            DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
            model.setRowCount(0);
        List<MonHoc> ds=monhocDAO.getDSMHTheoMaSV(userName);
        for(int i=0;i<ds.size();i++){
            Object[] row=new Object[]{i + 1,ds.get(i).getTenMaMh()
                    ,ds.get(i).getTenMh()
                    ,ds.get(i).getNgayBd()
                    ,ds.get(i).getNgayKt()
                    ,ds.get(i).getGioBd()
                    ,ds.get(i).getGioKt()
                    ,ds.get(i).getTenPh()
                    ,ds.get(i).getThu()};
            model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        Clock = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Họ và tên:");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Mã số sinh viên:");

        jTextField3.setEditable(false);

        jLabel4.setText("Mật khẩu:");

        jButton2.setText("<html>CẬP NHẬT <br> THÔNG TIN</html>");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Tên mã môn học", "Tên môn học", "Ngày bắt đầu", "Ngày kết thúc", "Giờ bắt đầu", "Giờ kết thúc", "Phòng học", "Thứ"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable2.getColumnModel().getColumn(1).setMinWidth(100);
            jTable2.getColumnModel().getColumn(2).setMinWidth(100);
            jTable2.getColumnModel().getColumn(3).setMinWidth(100);
            jTable2.getColumnModel().getColumn(4).setMinWidth(100);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2)
                            .addComponent(jTextField3)
                            .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
                        .addGap(49, 49, 49)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(34, 34, 34)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 90, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thông tin cá nhân", jPanel3);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        Clock.setBackground(new java.awt.Color(0, 0, 0));
        Clock.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        Clock.setForeground(new java.awt.Color(0, 165, 255));
        Clock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Clock.setText("7:45:31");

        jButton1.setText("ĐIỂM DANH");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã môn học", "Tuần 1", "Tuần 2", "Tuần 3", "Tuần 4", "Tuần 5", "Tuần 6", "Tuần 7", "Tuần 8", "Tuần 9", "Tuần 10", "Tuần 11", "Tuần 12", "Tuần 13", "Tuần 14", "Tuần 15"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setDoubleBuffered(true);
        jTable1.setEnabled(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(100);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(9).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(10).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(11).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(12).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(13).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(14).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(15).setMaxWidth(50);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
            .addComponent(Clock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(231, 231, 231))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(Clock)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Điểm danh", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        DiemDanh dd=diemdanhDAO.timKiemTheoMaMHVaMaSV(tenMaMHDiemDanh, userName);
        switch(tuan){
            case "tuan1": dd.setTuan1(true);break;
            case "tuan2": dd.setTuan2(true);break;
            case "tuan3": dd.setTuan3(true);break;
            case "tuan4": dd.setTuan4(true);break;
            case "tuan5": dd.setTuan5(true);break;
            case "tuan6": dd.setTuan6(true);break;
            case "tuan7": dd.setTuan7(true);break;
            case "tuan8": dd.setTuan8(true);break;
            case "tuan9": dd.setTuan9(true);break;
            case "tuan10": dd.setTuan10(true);break;
            case "tuan11": dd.setTuan11(true);break;
            case "tuan12": dd.setTuan12(true);break;
            case "tuan13": dd.setTuan13(true);break;
            case "tuan14": dd.setTuan14(true);break;
            case "tuan15": dd.setTuan15(true);break;
        }
        diemdanhDAO.capNhatDSDD(dd);
        themDataDiemDanhVaoBang1();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        SinhVien capNhatSV=new SinhVien();
        capNhatSV.setMaSv(jTextField3.getText());
        capNhatSV.setTenSv(jTextField2.getText());
        capNhatSV.setPassword(MD5.encryptMD5(jTextField4.getText()));
        if(sinhvienDAO.capNhatLanDau(capNhatSV)){
            JOptionPane.showMessageDialog(null, "Cập nhật thành công", "INFO", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "Cập nhật thất bại", "INFO", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(formSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formSinhVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formSinhVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Clock;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
