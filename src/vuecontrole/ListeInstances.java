/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuecontrole;

import io.InstanceReader;
import io.exception.ReaderException;
import java.awt.Color;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import metier.RequetePlanning;
import modele.Instance;
import modele.Solution;
import modele.Tournee;

/**
 * @author Axelle
 */
public class ListeInstances extends javax.swing.JFrame {
    /**
     * Creates new form ListeInstances
     */
    
    private RequetePlanning requetePlanning;
    
  
    public ListeInstances() {
        initConnexion();
        initComponents();
        this.initialisationFenetre();
        this.remplirListeInstances();
    }
    
    private void initConnexion() {
        this.requetePlanning = requetePlanning.getInstance();
    }

    private void initialisationFenetre(){
        this.setVisible(true);
        this.setLocation(300, 300);
        this.setTitle("Gestion des instances");
        this.getContentPane().setBackground(Color.LIGHT_GRAY);
        listeInstancesSauv.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listeSolutions.addItem("Solution triviale");
        listeSolutions.addItem("Solution basique");
    }
       
    private void remplirListeInstances() {
        DefaultListModel list = new DefaultListModel();
        listeInstancesSauv.setModel(list);
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Deliver2iPU");
        final EntityManager em = emf.createEntityManager();
        try{
           Query query = em.createNamedQuery("Instance.findAll");
           List<Instance> listeObjInstance = query.getResultList();
           for(Instance i : listeObjInstance) {
               Query query2 = em.createNamedQuery("Tournee.getTourneeByInstanceId");
               query2.setParameter("id", i);
               List<Tournee> listeTournee = query2.getResultList();
               for(Tournee t : listeTournee){
                   i.getTournees().add(t);
               }
               list.addElement(i);
               listeInstancesSauv.setModel(list);
           }
        } 
        finally {
            if(em != null && em.isOpen()){
                em.close();
            }
            if(emf != null && emf.isOpen()){
                emf.close();
            }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        listeInstancesSauv = new javax.swing.JList<>();
        ajoutInstance = new javax.swing.JButton();
        listeSolutions = new javax.swing.JComboBox<>();
        ajoutSolution = new javax.swing.JButton();
        supprimerInstance = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        listeInstancesSauv.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(listeInstancesSauv);

        ajoutInstance.setText("Ajouter une instance");
        ajoutInstance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajoutInstanceActionPerformed(evt);
            }
        });

        ajoutSolution.setText("Tester une solution");
        ajoutSolution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ajoutSolutionActionPerformed(evt);
            }
        });

        supprimerInstance.setText("Supprimer une instance");
        supprimerInstance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supprimerInstanceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(listeSolutions, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ajoutInstance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ajoutSolution, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(supprimerInstance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ajoutInstance)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(supprimerInstance))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(listeSolutions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ajoutSolution))
                .addContainerGap(75, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ajoutInstanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ajoutInstanceActionPerformed
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            String chemin = chooser.getSelectedFile().getAbsolutePath();
            try{
                InstanceReader ir = new InstanceReader(chemin);
                this.requetePlanning.ajouterInstance(ir.readInstance());
            }      
            catch (ReaderException ex) {
                Logger.getLogger(ListeInstances.class.getName()).log(Level.SEVERE, null, ex);
            }            finally {
                this.remplirListeInstances();
            }
        }
    }//GEN-LAST:event_ajoutInstanceActionPerformed

    private void ajoutSolutionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ajoutSolutionActionPerformed
        // TODO add your handling code here:
        if(!listeInstancesSauv.isSelectionEmpty()){
            
            switch(listeSolutions.getItemAt(listeSolutions.getSelectedIndex())){
                 case "Solution basique":
                            final EntityManagerFactory emf1 =Persistence.createEntityManagerFactory("Deliver2iPU");
                            final EntityManager em1 = emf1.createEntityManager();
                            try{
                                final EntityTransaction et1 = em1.getTransaction();
                                try{
                                    et1.begin();

                                    Solution s = new Solution();
                                    Object obj1 = listeInstancesSauv.getSelectedValue();
                                    Instance i1 = (Instance)obj1;

                                    s.ajouterInstance(i1);

                                    s.solutionBasique(0);
                                    System.out.println(s);
                                    em1.persist(s);
                                    et1.commit();
                                    JOptionPane.showMessageDialog(rootPane, "Ajout le solution réussi.");
                                }
                                catch (Exception ex) {
                                    et1.rollback();
                                }
                            } 
                            finally {
                                if(em1 != null && em1.isOpen()){
                                    em1.close();
                                }
                                if(emf1 != null && emf1.isOpen()){
                                    emf1.close();
                                }
                            }
                        break;
                case "Solution triviale":
                    final EntityManagerFactory emf =Persistence.createEntityManagerFactory("Deliver2iPU");
                    final EntityManager em = emf.createEntityManager();
                    try{
                        final EntityTransaction et = em.getTransaction();
                        try{
                            et.begin();
                            
                            Solution s = new Solution();
                            Object obj = listeInstancesSauv.getSelectedValue();
                            Instance i = (Instance)obj;
                            
                            s.ajouterInstance(i);
                            
                            s.solutionTriviale(0);
                            em.persist(s);
                            et.commit();
                            JOptionPane.showMessageDialog(rootPane, "Ajout le solution réussi.");
                        }
                        catch (Exception ex) {
                            et.rollback();
                        }
                    } 
                    finally {
                        if(em != null && em.isOpen()){
                            em.close();
                        }
                        if(emf != null && emf.isOpen()){
                            emf.close();
                        }
                    }
                    break;
                   
            }
        
        } 
    }//GEN-LAST:event_ajoutSolutionActionPerformed

    private void supprimerInstanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supprimerInstanceActionPerformed
         if(!listeInstancesSauv.isSelectionEmpty()){
            final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Deliver2iPU");
            final EntityManager em = emf.createEntityManager();
            try{
                final EntityTransaction et = em.getTransaction();
                try{
                    et.begin();
                    Object obj = listeInstancesSauv.getSelectedValue();
                    Instance i = (Instance)obj;
                    Instance it = em.find(Instance.class, i.getId());
                    em.remove(it);
                    et.commit();
                    JOptionPane.showMessageDialog(rootPane, "Supression réussie.");
                }
                catch (Exception ex) {
                    et.rollback();
                }
            } 
            finally {
                if(em != null && em.isOpen()){
                    em.close();
                }
                if(emf != null && emf.isOpen()){
                    emf.close();
                    this.remplirListeInstances();
                }
            }
         }
    }//GEN-LAST:event_supprimerInstanceActionPerformed

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
            java.util.logging.Logger.getLogger(ListeInstances.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListeInstances.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListeInstances.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListeInstances.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListeInstances().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ajoutInstance;
    private javax.swing.JButton ajoutSolution;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listeInstancesSauv;
    private javax.swing.JComboBox<String> listeSolutions;
    private javax.swing.JButton supprimerInstance;
    // End of variables declaration//GEN-END:variables
}
