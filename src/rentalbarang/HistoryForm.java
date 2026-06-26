/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rentalbarang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class HistoryForm extends javax.swing.JFrame {

    public HistoryForm() {
        initComponents();
        this.setTitle("Riwayat Peminjaman");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        loadHistory();
    }

    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();
        titleLabel = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();

        titleLabel.setText("Riwayat Peminjaman Barang");
        titleLabel.setFont(new java.awt.Font("Segoe UI", 1, 16));

        historyTable.setModel(new DefaultTableModel(
            new Object[]{"ID", "Nama Barang", "Mulai", "Selesai", "Durasi (Hari)", "Total Harga", "Status"},
            0
        ));
        historyTable.setEnabled(false);
        jScrollPane1.setViewportView(historyTable);

        closeButton.setText("Tutup");
        closeButton.addActionListener(evt -> dispose());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                    .addComponent(closeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeButton)
                .addContainerGap())
        );
        pack();
    }

    private void loadHistory() {
        DefaultTableModel model = (DefaultTableModel) historyTable.getModel();
        model.setRowCount(0); // clear existing rows

        String sql = "SELECT t.transaction_id, i.item_name, t.rent_start, t.rent_end, " +
                     "t.rent_duration, t.rent_price, t.transaction_status " +
                     "FROM transaction t " +
                     "JOIN item i ON t.item_id = i.item_id " +
                     "WHERE t.renter_id = ? " +
                     "ORDER BY t.rent_start DESC";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, UserSession.getUserId());

            try (ResultSet rs = pstmt.executeQuery()) {
                boolean hasData = false;
                while (rs.next()) {
                    hasData = true;
                    model.addRow(new Object[]{
                        rs.getInt("transaction_id"),
                        rs.getString("item_name"),
                        rs.getString("rent_start"),
                        rs.getString("rent_end"),
                        rs.getInt("rent_duration") + " hari",
                        "Rp " + String.format("%,.0f", rs.getDouble("rent_price")),
                        rs.getString("transaction_status")
                    });
                }
                if (!hasData) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Belum ada riwayat peminjaman.");
                }
            }

        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal memuat history: " + e.getMessage());
        }
    }

    private javax.swing.JTable historyTable;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton closeButton;
}
