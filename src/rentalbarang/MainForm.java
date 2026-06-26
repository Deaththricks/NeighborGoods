/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package rentalbarang;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author ASUS TUF
 */
public class MainForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainForm.class.getName());
    private JButton notifButton;   // tombol 🔔
    private JLabel  notifBadge;   // label merah angka unread
    private Timer   notifTimer; 
    /**
     * Creates new form DashboardForm
     */
    public MainForm() {
        initComponents();
        buildNotifArea();  
        loadDefPannel();
        startNotifPolling();
    }
    
    private void buildNotifArea() {
        // Ambil container utama (contentPane sudah pakai GroupLayout dari NetBeans)
        // Cara paling aman tanpa menyentuh initComponents() adalah menambahkan
        // komponen ke panel header secara programatik menggunakan layered approach.
 
        // Buat tombol notif
        notifButton = new JButton("🔔");
        notifButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        notifButton.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
        notifButton.setFocusPainted(false);
        notifButton.setContentAreaFilled(false);
        notifButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        notifButton.setToolTipText("Notifikasi");
        notifButton.addActionListener(e -> notifButtonActionPerformed());
 
        // Badge merah (ditumpuk di atas tombol pakai JLayeredPane)
        notifBadge = new JLabel("0");
        notifBadge.setFont(new Font("Segoe UI", Font.BOLD, 9));
        notifBadge.setForeground(Color.WHITE);
        notifBadge.setBackground(Color.RED);
        notifBadge.setOpaque(true);
        notifBadge.setHorizontalAlignment(SwingConstants.CENTER);
        notifBadge.setBorder(BorderFactory.createEmptyBorder(1, 3, 1, 3));
        notifBadge.setVisible(false); // sembunyikan dulu kalau 0
 
        // ── Strategi penyisipan:
        //    Kita bungkus notifButton + badge ke dalam satu JPanel berlapis,
        //    lalu sisipkan ke contentPane menggunakan JLayeredPane overlay trick.
        //    Cara paling sederhana untuk NetBeans: tambahkan ke JLayeredPane
        //    yang sudah ada di JFrame.
 
        JLayeredPane layeredPane = getLayeredPane();
 
        // Posisi tombol notif: di sebelah kiri addItemButton
        // addItemButton ada di pojok kanan atas dengan lebar 30px
        // Kita letakkan notifButton 40px di kirinya
        notifButton.setBounds(290, 6, 30, 29);
        notifBadge.setBounds(308, 4, 16, 14);
 
        layeredPane.add(notifButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(notifBadge,  JLayeredPane.MODAL_LAYER);   // modal = di atas palette
 
        // Update badge pertama kali
        refreshNotifBadge();
    }
    // ═══════════════════════════════════════════════════════════════════════
    //  BAGIAN BARU 2 — Refresh badge (panggil ini dari mana saja)
    // ═══════════════════════════════════════════════════════════════════════
    public void refreshNotifBadge() {
        int unread = NotificationPannel.countUnread(UserSession.getUserId());
        if (unread > 0) {
            notifBadge.setText(unread > 99 ? "99+" : String.valueOf(unread));
            notifBadge.setVisible(true);
        } else {
            notifBadge.setVisible(false);
        }
    }
 
    // ═══════════════════════════════════════════════════════════════════════
    //  BAGIAN BARU 3 — Polling otomatis setiap 30 detik
    // ═══════════════════════════════════════════════════════════════════════
    private void startNotifPolling() {
        notifTimer = new Timer(30_000, e -> refreshNotifBadge());
        notifTimer.setRepeats(true);
        notifTimer.start();
    }
 
    // ═══════════════════════════════════════════════════════════════════════
    //  BAGIAN BARU 4 — Handler klik tombol notif
    // ═══════════════════════════════════════════════════════════════════════
    private void notifButtonActionPerformed() {
        NotificationPannel notifPanel = new NotificationPannel();
        mainPannel.removeAll();
        mainPannel.setLayout(new java.awt.BorderLayout());
        mainPannel.add(notifPanel, java.awt.BorderLayout.CENTER);
        mainPannel.revalidate();
        mainPannel.repaint();
 
        // Reset gaya semua nav-button
        resetAllNavButtons();
 
        // Aktifkan style notifButton
        notifButton.setForeground(Color.BLUE);
        notifButton.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
    }
 
    // ── Helper: reset style semua tombol navigasi ──────────────
    private void resetAllNavButtons() {
        homeButton.setBackground(Color.WHITE);
        homeButton.setForeground(Color.BLACK);
        homeButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
 
        searchButton.setBackground(Color.WHITE);
        searchButton.setForeground(Color.BLACK);
        searchButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
 
        profileButton.setBackground(Color.WHITE);
        profileButton.setForeground(Color.BLACK);
        profileButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
 
        addItemButton.setBackground(Color.WHITE);
        addItemButton.setForeground(Color.BLACK);
        addItemButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
 
        notifButton.setForeground(Color.BLACK);
        notifButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar1 = new javax.swing.JScrollBar();
        jLabel1 = new javax.swing.JLabel();
        title = new javax.swing.JLabel();
        homeButton = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        profileButton = new javax.swing.JButton();
        addItemButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        mainPannel = new javax.swing.JPanel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        title.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        title.setText("NeighborGoods");

        homeButton.setBackground(new java.awt.Color(51, 51, 255));
        homeButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        homeButton.setForeground(new java.awt.Color(255, 255, 255));
        homeButton.setText("Home");
        homeButton.setBorder(null);
        homeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        homeButton.setMargin(new java.awt.Insets(5, 14, 5, 14));
        homeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        homeButton.addActionListener(this::homeButtonActionPerformed);

        searchButton.setText("Search");
        searchButton.setBorder(null);
        searchButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        searchButton.setMargin(new java.awt.Insets(5, 14, 5, 14));
        searchButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        searchButton.addActionListener(this::searchButtonActionPerformed);

        profileButton.setText("Profile");
        profileButton.setBorder(null);
        profileButton.setMargin(new java.awt.Insets(5, 14, 5, 14));
        profileButton.addActionListener(this::profileButtonActionPerformed);

        addItemButton.setText("+");
        addItemButton.setBorder(null);
        addItemButton.addActionListener(this::addItemButtonActionPerformed);

        mainPannel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        mainPannel.setLayout(new java.awt.GridLayout(1, 3));
        jScrollPane1.setViewportView(mainPannel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(homeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addComponent(profileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(title)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(homeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(profileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadDefPannel(){
        
        DashboardPannel defaultPannel = new DashboardPannel();
        mainPannel.removeAll();
        mainPannel.setLayout(new java.awt.BorderLayout());
        mainPannel.add(defaultPannel, java.awt.BorderLayout.CENTER);
        mainPannel.revalidate();
        mainPannel.repaint();
        
        

    }
    public void refreshProfileUI() {
        // Since your profile page lives inside mainPannel, we force a clean panel swap
        // to let ProfilePannel's constructor pull fresh variables out of UserSession RAM
        mainPannel.removeAll();
        mainPannel.setLayout(new java.awt.BorderLayout());
        ProfilePannel freshProfileView = new ProfilePannel();
        mainPannel.add(freshProfileView, java.awt.BorderLayout.CENTER);
        
        // Tells Java Swing layout engine to reconstruct the layout tree structure
        mainPannel.revalidate();
        mainPannel.repaint();
    }
    private void profileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileButtonActionPerformed

        ProfilePannel ProfilePannelShow = new ProfilePannel();
            mainPannel.removeAll();
            mainPannel.setLayout(new java.awt.BorderLayout());
            mainPannel.add(ProfilePannelShow, java.awt.BorderLayout.CENTER);
            mainPannel.revalidate();
            mainPannel.repaint();

        profileButton.setBackground(java.awt.Color.BLUE);       
        profileButton.setForeground(java.awt.Color.WHITE);      
        profileButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); 

        addItemButton.setBackground(java.awt.Color.WHITE);    
        addItemButton.setForeground(java.awt.Color.BLACK);    
        addItemButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        
        homeButton.setBackground(java.awt.Color.WHITE);    
        homeButton.setForeground(java.awt.Color.BLACK);    
        homeButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));

        searchButton.setBackground(java.awt.Color.WHITE);    
        searchButton.setForeground(java.awt.Color.BLACK);    
        searchButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));

        
    }//GEN-LAST:event_profileButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        SearchPannel SearchPannellShow = new SearchPannel();
            mainPannel.removeAll();
            mainPannel.setLayout(new java.awt.BorderLayout());
            mainPannel.add(SearchPannellShow, java.awt.BorderLayout.CENTER);
            mainPannel.revalidate();
            mainPannel.repaint();

        searchButton.setBackground(java.awt.Color.BLUE);       
        searchButton.setForeground(java.awt.Color.WHITE);      
        searchButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); 

        addItemButton.setBackground(java.awt.Color.WHITE);    
        addItemButton.setForeground(java.awt.Color.BLACK);    
        addItemButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        
        homeButton.setBackground(java.awt.Color.WHITE);    
        homeButton.setForeground(java.awt.Color.BLACK);    
        homeButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));


        profileButton.setBackground(java.awt.Color.WHITE);    
        profileButton.setForeground(java.awt.Color.BLACK);    
        profileButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
    }//GEN-LAST:event_searchButtonActionPerformed

    private void homeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeButtonActionPerformed
        DashboardPannel defaultPannel = new DashboardPannel();
            mainPannel.removeAll();
            mainPannel.setLayout(new java.awt.BorderLayout());
            mainPannel.add(defaultPannel, java.awt.BorderLayout.CENTER);
            mainPannel.revalidate();
            mainPannel.repaint();

        homeButton.setBackground(java.awt.Color.BLUE);       
        homeButton.setForeground(java.awt.Color.WHITE);      
        homeButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12)); 

        addItemButton.setBackground(java.awt.Color.WHITE);    
        addItemButton.setForeground(java.awt.Color.BLACK);    
        addItemButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));


        searchButton.setBackground(java.awt.Color.WHITE);    
        searchButton.setForeground(java.awt.Color.BLACK);    
        searchButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));

        profileButton.setBackground(java.awt.Color.WHITE);    
        profileButton.setForeground(java.awt.Color.BLACK);    
        profileButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
    }//GEN-LAST:event_homeButtonActionPerformed

    private void addItemButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addItemButtonActionPerformed
        addItemPannel addItemPannelShow = new addItemPannel();
            mainPannel.removeAll();
            mainPannel.setLayout(new java.awt.BorderLayout());
            mainPannel.add(addItemPannelShow, java.awt.BorderLayout.CENTER);
            mainPannel.revalidate();
            mainPannel.repaint();
        
        addItemButton.setBackground(java.awt.Color.BLUE);       
        addItemButton.setForeground(java.awt.Color.WHITE);      
        addItemButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        



        homeButton.setBackground(java.awt.Color.WHITE);    
        homeButton.setForeground(java.awt.Color.BLACK);    
        homeButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));

        searchButton.setBackground(java.awt.Color.WHITE);    
        searchButton.setForeground(java.awt.Color.BLACK);    
        searchButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));

        profileButton.setBackground(java.awt.Color.WHITE);    
        profileButton.setForeground(java.awt.Color.BLACK);    
        profileButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
    }//GEN-LAST:event_addItemButtonActionPerformed

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MainForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addItemButton;
    private javax.swing.JButton homeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPannel;
    private javax.swing.JButton profileButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JLabel title;
    // End of variables declaration//GEN-END:variables
}
