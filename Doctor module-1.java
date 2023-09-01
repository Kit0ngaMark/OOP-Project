import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;
import net.proteanit.sql.DbUtils;

public class DoctorRecords extends javax.swing.JFrame {
    Connection con = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public DoctorRecords() {
        initComponents();
        con = Connect.ConnectDB();
        Get_Data();
    }

    private void Get_Data() {
        String sql = "SELECT DoctorID as 'Doctor ID', DoctorName as 'Doctor Name', FatherName as 'Father Name', Address, ContacNo as 'Contact No', Email as 'Email ID', Qualifications, Gender, BloodGroup as 'Blood Group', DateOfJoining as 'Joining Date' FROM Doctor ORDER BY DoctorName";
        try {
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {
        try {
            con = Connect.ConnectDB();
            int row = jTable1.getSelectedRow();
            String table_click = jTable1.getModel().getValueAt(row, 0).toString();
            String sql = "SELECT * FROM Doctor WHERE DoctorID = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, table_click);
            rs = pst.executeQuery();
            if (rs.next()) {
                this.dispose();
                Entry frm = new Entry();
frm.setVisible(true);
frm.setId(rs.getString("DoctorID"));
frm.setName(rs.getString("Doctorname"));
frm.setFname(rs.getString("Fathername"));
frm.setEmail(rs.getString("Email"));
frm.setQ(rs.getString("Qualifications"));
frm.setBloodGroup(rs.getString("BloodGroup"));
frm.setGender(rs.getString("Gender"));
frm.setJoiningDate(rs.getString("DateOfJoining"));
frm.setAddress(rs.getString("Address"));
frm.setContacNo(rs.getString("ContacNo"));
frm.btnUpdate.setEnabled(true);
frm.btnDelete.setEnabled(true);
frm.btnSave.setEnabled(false);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DoctorRecords.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new DoctorRecords().setVisible(true);
        });
    }
}
