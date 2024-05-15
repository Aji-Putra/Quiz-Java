
package Quis;
import Quis.Koneksi;
import java.sql.Connection; // Untuk koneksi ke database
import java.sql.PreparedStatement; // Untuk membuat statement SQL yang telah dipreparasi
import java.sql.SQLException; // Untuk menangani pengecualian yang berkaitan dengan SQL
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;



public class Main extends javax.swing.JFrame {
    
    private void tampilkanbuku(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Menghapus data yang ada sebelumnya dari tabel
            
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = Koneksi.getConnection(); // Mendapatkan koneksi
            String sql = "SELECT * FROM book";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            // Menambahkan setiap baris data ke dalam model tabel
            while (rs.next()) {
                Object[] row = {
                    rs.getString("judul"),
                    rs.getString("penulis"),
                    rs.getString("harga"),
                    rs.getString("stok")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal menutup koneksi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    private void hapusbuku() {
    // Mendapatkan indeks baris terpilih
    int selectedRow = Tabel.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Mendapatkan nilai ID dari baris terpilih
    String judul = Tabel.getValueAt(selectedRow, 0).toString(); // Mengambil nilai dari kolom pertama (judul)
    
    // Membuat koneksi
    Connection conn = null;
    PreparedStatement pst = null;
    
    try {
        // Menyiapkan query SQL untuk menghapus data
        String sql = "DELETE FROM book WHERE judul = ?";
        
        // Mendapatkan koneksi
        conn = Koneksi.getConnection();
        
        // Membuat statement PreparedStatement
        pst = conn.prepareStatement(sql);
        
        // Mengatur parameter judul untuk query
        pst.setString(1, judul);
        
        // Menjalankan perintah hapus
        int result = pst.executeUpdate();
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
            // Refresh tabel setelah menghapus data
            tampilkanbuku(Tabel);
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        // Menutup koneksi dan statement
        try {
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menutup koneksi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
    
    private void editData() {
    // Mendapatkan indeks baris terpilih
    int selectedRow = Tabel.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih baris yang ingin diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Mendapatkan nilai dari setiap kolom pada baris terpilih
    String judul = Tjudul.getText();
    String penulis = Tpenulis.getText();
    String harga = Tharga.getText();
    String stok = Tstok.getText();
    
    // Membuat koneksi
    Connection conn = null;
    PreparedStatement pst = null;
    
    try {
        // Menyiapkan query SQL untuk mengupdate data
        String sql = "UPDATE book SET penulis = ?, harga = ?, stok = ? WHERE judul = ?";
        
        // Mendapatkan koneksi
        conn = Koneksi.getConnection();
        
        // Membuat statement PreparedStatement
        pst = conn.prepareStatement(sql);
        
        // Mengatur parameter untuk query
        pst.setString(1, penulis);
        pst.setString(2, harga);
        pst.setString(3, stok);
        pst.setString(4, judul);
        
        // Menjalankan perintah update
        int result = pst.executeUpdate();
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Data berhasil diperbarui.");
            // Refresh tabel setelah mengedit data
            tampilkanbuku(Tabel);
        } else {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Gagal memperbarui data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } finally {
        // Menutup koneksi dan statement
        try {
            if (pst != null) pst.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menutup koneksi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
    private void cariBuku(String judul) {
     DefaultTableModel model = (DefaultTableModel) Tabel.getModel();
        model.setRowCount(0); // Menghapus data yang ada sebelumnya dari tabel
    
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
    
        try {
            conn = Koneksi.getConnection(); // Mendapatkan koneksi
            String sql = "SELECT * FROM book WHERE judul LIKE ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + judul + "%"); // Menggunakan placeholder untuk mencocokkan judul yang sebagian cocok
            rs = pst.executeQuery();
    
            // Menambahkan setiap baris data yang cocok ke dalam model tabel
            while (rs.next()) {
                Object[] row = {
                    rs.getString("judul"),
                    rs.getString("penulis"),
                    rs.getString("harga"),
                    rs.getString("stok")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Gagal menutup koneksi: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
}
    
    

    public Main() {
        initComponents();
        tampilkanbuku(Tabel);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Tjudul = new javax.swing.JTextField();
        Tpenulis = new javax.swing.JTextField();
        Tharga = new javax.swing.JTextField();
        Tstok = new javax.swing.JTextField();
        TSimpan = new javax.swing.JButton();
        THapus = new javax.swing.JButton();
        TEdit = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        Tcarijudul = new javax.swing.JTextField();
        TCari = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabel = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Judul");

        jLabel2.setText("Penulis");

        jLabel3.setText("Harga");

        jLabel4.setText("Stok");

        TSimpan.setText("Tambah");
        TSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TSimpanActionPerformed(evt);
            }
        });

        THapus.setText("Hapus");
        THapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                THapusActionPerformed(evt);
            }
        });

        TEdit.setText("Edit");
        TEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TEditActionPerformed(evt);
            }
        });

        jLabel5.setText("BUKU");

        Tcarijudul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TcarijudulActionPerformed(evt);
            }
        });

        TCari.setText("Cari");
        TCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(155, 155, 155)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Tcarijudul, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                    .addComponent(Tjudul)
                    .addComponent(Tpenulis)
                    .addComponent(Tharga)
                    .addComponent(Tstok))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(THapus)
                            .addComponent(TSimpan)
                            .addComponent(TEdit)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(TCari)))
                .addGap(29, 29, 29))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(Tjudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TSimpan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(Tpenulis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(THapus))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Tharga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TEdit))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(Tstok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Tcarijudul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TCari))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        Tabel.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Judul", "Penulis", "Harga", "Stok"
            }
        ));
        jScrollPane2.setViewportView(Tabel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(108, 108, 108)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(113, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(228, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TSimpanActionPerformed
    try {
    String sql = "INSERT INTO book (judul, penulis, harga, stok) VALUES (?, ?, ?, ?)";
    Connection conn = Koneksi.getConnection(); 
    PreparedStatement pst = conn.prepareStatement(sql);
    pst.setString(1, Tjudul.getText());
    pst.setString(2, Tpenulis.getText());
    pst.setString(3, Tharga.getText());
    pst.setString(4, Tstok.getText());
    pst.executeUpdate();
    JOptionPane.showMessageDialog(null, "Penyimpanan Data Berhasil");
} catch (SQLException e) {
    JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage());
} finally {
    // Tutup koneksi di sini jika diperlukan
}        // TODO add your handling code here:
    }//GEN-LAST:event_TSimpanActionPerformed

    private void THapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_THapusActionPerformed
        // TODO add your handling code here:
        hapusbuku();
    }//GEN-LAST:event_THapusActionPerformed

    private void TEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TEditActionPerformed
            // TODO add your handling code here:
       editData();
    }//GEN-LAST:event_TEditActionPerformed

    private void TcarijudulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TcarijudulActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TcarijudulActionPerformed

    private void TCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TCariActionPerformed
        // TODO add your handling code here:
        String judul = Tjudul.getText(); // Mengambil nilai judul dari JTextField TCariJudul
        cariBuku(judul);
    }//GEN-LAST:event_TCariActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton TCari;
    private javax.swing.JButton TEdit;
    private javax.swing.JButton THapus;
    private javax.swing.JButton TSimpan;
    private javax.swing.JTable Tabel;
    private javax.swing.JTextField Tcarijudul;
    private javax.swing.JTextField Tharga;
    private javax.swing.JTextField Tjudul;
    private javax.swing.JTextField Tpenulis;
    private javax.swing.JTextField Tstok;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
