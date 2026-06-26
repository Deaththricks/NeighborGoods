/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rentalbarang;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

/**
 * NotificationPannel
 * Menampilkan daftar notifikasi masuk untuk pemilik barang (owner).
 * Notif muncul ketika ada user lain yang menyewa barang milik user yang login.
 */
public class NotificationPannel extends JPanel {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(NotificationPannel.class.getName());

    public NotificationPannel() {
        setLayout(new BorderLayout());
        buildUI();
    }

    private void buildUI() {
        // ── Header ──────────────────────────────────────────────
        JLabel header = new JLabel("🔔  Notifikasi Masuk");
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBorder(BorderFactory.createEmptyBorder(12, 14, 8, 14));
        add(header, BorderLayout.NORTH);

        // ── Scroll list ─────────────────────────────────────────
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));

        loadNotifications(listPanel);

        JScrollPane scroll = new JScrollPane(listPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        add(scroll, BorderLayout.CENTER);

        // ── Tombol "Tandai semua sudah dibaca" ──────────────────
        JButton markAllBtn = new JButton("✔  Tandai Semua Dibaca");
        markAllBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        markAllBtn.setBackground(new Color(51, 51, 255));
        markAllBtn.setForeground(Color.WHITE);
        markAllBtn.setFocusPainted(false);
        markAllBtn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        markAllBtn.addActionListener(e -> {
            markAllAsRead();
            listPanel.removeAll();
            loadNotifications(listPanel);
            listPanel.revalidate();
            listPanel.repaint();

            // Perbarui badge di MainForm
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window instanceof MainForm) {
                ((MainForm) window).refreshNotifBadge();
            }
        });

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBorder(BorderFactory.createEmptyBorder(4, 10, 8, 10));
        footer.add(markAllBtn);
        add(footer, BorderLayout.SOUTH);
    }

    // ── Query notifikasi milik user yang sedang login ────────────
    private void loadNotifications(JPanel listPanel) {
        int ownerId = UserSession.getUserId();

        String sql = """
                SELECT n.notif_id,
                       n.notif_message,
                       n.is_read,
                       n.created_at,
                       u.user_name   AS renter_name,
                       i.item_name
                FROM   notification n
                JOIN   users u ON u.user_id = n.renter_id
                JOIN   item  i ON i.item_id = n.item_id
                WHERE  n.owner_id = ?
                ORDER  BY n.created_at DESC
                """;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ownerId);

            try (ResultSet rs = ps.executeQuery()) {
                boolean hasData = false;

                while (rs.next()) {
                    hasData = true;
                    int    notifId  = rs.getInt("notif_id");
                    String message  = rs.getString("notif_message");
                    boolean isRead  = rs.getInt("is_read") == 1;
                    String  time    = rs.getString("created_at");

                    listPanel.add(buildNotifCard(notifId, message, isRead, time));
                    listPanel.add(Box.createVerticalStrut(6));
                }

                if (!hasData) {
                    JLabel empty = new JLabel("Belum ada notifikasi.");
                    empty.setFont(new Font("Segoe UI", Font.ITALIC, 13));
                    empty.setForeground(Color.GRAY);
                    empty.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 0));
                    listPanel.add(empty);
                }
            }

        } catch (SQLException e) {
            logger.severe("Gagal load notifikasi: " + e.getMessage());
            JLabel errLabel = new JLabel("Gagal memuat notifikasi.");
            errLabel.setForeground(Color.RED);
            listPanel.add(errLabel);
        }
    }

    // ── Kartu satu notifikasi ────────────────────────────────────
    private JPanel buildNotifCard(int notifId, String message, boolean isRead, String time) {
        JPanel card = new JPanel(new BorderLayout(8, 4));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        card.setBackground(isRead ? Color.WHITE : new Color(235, 240, 255));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // Ikon lonceng / centang
        JLabel icon = new JLabel(isRead ? "✔" : "🔔");
        icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        card.add(icon, BorderLayout.WEST);

        // Teks pesan
        JLabel msgLabel = new JLabel("<html>" + message + "</html>");
        msgLabel.setFont(new Font("Segoe UI", isRead ? Font.PLAIN : Font.BOLD, 12));
        card.add(msgLabel, BorderLayout.CENTER);

        // Waktu
        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        timeLabel.setForeground(Color.GRAY);
        card.add(timeLabel, BorderLayout.EAST);

        // Klik kartu → tandai dibaca
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (!isRead) {
                    markOneAsRead(notifId);
                    card.setBackground(Color.WHITE);
                    icon.setText("✔");
                    msgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    card.revalidate();
                    card.repaint();

                    Window window = SwingUtilities.getWindowAncestor(card);
                    if (window instanceof MainForm) {
                        ((MainForm) window).refreshNotifBadge();
                    }
                }
            }
        });

        return card;
    }

    // ── Tandai satu notif sebagai dibaca ────────────────────────
    private void markOneAsRead(int notifId) {
        String sql = "UPDATE notification SET is_read = 1 WHERE notif_id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, notifId);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Gagal mark notif: " + e.getMessage());
        }
    }

    // ── Tandai SEMUA notif user ini sebagai dibaca ──────────────
    private void markAllAsRead() {
        String sql = "UPDATE notification SET is_read = 1 WHERE owner_id = ? AND is_read = 0";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, UserSession.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.severe("Gagal mark all notif: " + e.getMessage());
        }
    }

    // ── Util: hitung notif belum dibaca (dipanggil MainForm) ────
    public static int countUnread(int ownerId) {
        String sql = "SELECT COUNT(*) FROM notification WHERE owner_id = ? AND is_read = 0";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ownerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            java.util.logging.Logger.getLogger(NotificationPannel.class.getName())
                    .severe("countUnread error: " + e.getMessage());
        }
        return 0;
    }
}