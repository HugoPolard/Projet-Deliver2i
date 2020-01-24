/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vuecontrole;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import modele.Instance;
import modele.Shift;
import modele.Solution;
import modele.Tournee;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
/**
 *
 * @author Axelle
 */
public class AffichageSolution extends javax.swing.JFrame {

    /**
     * Creates new form AffichageSolution
     */
    public AffichageSolution() {
        initComponents();
        remplirListeSolution();
        
    }

     private IntervalCategoryDataset getCategoryDataset(Solution s) {
     
         TaskSeries serie = new TaskSeries("Tournées");
         
        System.out.println(s.getShifts());
        
        Date datedebut = new Date(s.getInstance().getTournees().get(0).getDebut().getTime() - 60000);
        Date datefin = new Date(86400000);
        int j =0;
        for(Shift shift : s.getShifts()){
            String name = "shift" + j;
              final Task t = new Task(name, datedebut,  datefin);
                  shift.trier();
              for(Tournee tournee : shift.getTournees()){
                  final Task st = new Task(name, tournee.getDebut(), tournee.getFin());
                  st.setPercentComplete(1.0);
                  t.addSubtask(st);
                  int index = shift.getTournees().indexOf(tournee);
                  if(index != 0){
                      if(!tournee.getDebut().equals(shift.getTournees().get(index).getFin())) {
                          final Task st2 = new Task(name, new Date (shift.getTournees().get(index-1).getFin().getTime() +1), new Date (tournee.getDebut().getTime() - 1));
                          t.addSubtask(st2);
                      }
                  }

              }
              Date dateFinMinimum = new Date(shift.getTournees().get(0).getDebut().getTime() + shift.getSolution().getInstance().getDureeMinimale()*60000);
              if(shift.getTournees().get(shift.getTournees().size()-1).getFin().before(dateFinMinimum) ){
                  final Task st3 = new Task(name, shift.getTournees().get(shift.getTournees().size()-1).getFin(), dateFinMinimum);
                      t.addSubtask(st3);
              }

             serie.add(t);
              j++;
          }

        final TaskSeriesCollection dataset = new TaskSeriesCollection();
        dataset.add(serie);
        System.out.println();
        return dataset;
     }
     
     private void remplirListeSolution() {
        DefaultListModel list = new DefaultListModel();
        listeSolution.setModel(list);
        final EntityManagerFactory emf =Persistence.createEntityManagerFactory("Deliver2iPU");
        final EntityManager em = emf.createEntityManager();
        try{
            
           Query query = em.createNamedQuery("Solution.findAll");
           List<Solution> listeObjSolution = query.getResultList();
           
           for(Solution s : listeObjSolution) {
               
               Query query2 = em.createNamedQuery("Shift.getShiftBySolutionId");
               query2.setParameter("id", s);
               List<Shift> listeShift = query2.getResultList();
               
               for(Shift shift : listeShift){
                   
                    s.getShifts().add(shift);
               }
               //System.out.println(s.getShifts());
               list.addElement(s);
               listeSolution.setModel(list);
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listeSolution = new javax.swing.JList<>();
        afficherSolution = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1150, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 713, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(listeSolution);

        afficherSolution.setText("Afficher");
        afficherSolution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                afficherSolutionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(afficherSolution, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
                .addGap(32, 32, 32)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(afficherSolution)))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void afficherSolutionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_afficherSolutionActionPerformed
        // TODO add your handling code here:
        if(!listeSolution.isSelectionEmpty()){
            Object obj = listeSolution.getSelectedValue();
            Solution s = (Solution)obj;
            
            final IntervalCategoryDataset dataset = getCategoryDataset(s);

            // create the chart...
                final JFreeChart chart = ChartFactory.createGanttChart(
                "",  // chart title
                "Task",              // domain axis label
                "Date",              // range axis label
                dataset,             // data
                false,                // include legend
                true,                // tooltips
                false                // urls
            );
            final CategoryPlot plot = (CategoryPlot) chart.getPlot();
      //      plot.getDomainAxis().setMaxCategoryLabelWidthRatio(10.0f);
            final CategoryItemRenderer renderer = plot.getRenderer();
            renderer.setSeriesPaint(0, Color.blue);


            final ChartPanel chartPanel = new ChartPanel(chart);

            chartPanel.setPreferredSize(new java.awt.Dimension(jPanel1.getWidth(), jPanel1.getHeight()));
            jPanel1.removeAll();
            jPanel1.setLayout(new FlowLayout(FlowLayout.LEFT));
            jPanel1.add(chartPanel);
            
            this.revalidate();
        }
    }//GEN-LAST:event_afficherSolutionActionPerformed

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
            java.util.logging.Logger.getLogger(AffichageSolution.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AffichageSolution.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AffichageSolution.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AffichageSolution.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AffichageSolution().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton afficherSolution;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listeSolution;
    // End of variables declaration//GEN-END:variables
}