/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.infox.telas;

/**
 *
 * @author pallo
 */
import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils; // rs2xml.jar

public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    //método para adicionar clientes
    private void adicionar() {
        String sql = "INSERT INTO tbclientes(nomecli, endcli, emailcli, fonecli)"
                + "VALUES(?, ?, ?, ?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtClientesNome.getText());
            pst.setString(2, txtClientesEndereco.getText());
            pst.setString(3, txtClientesEmail.getText());
            pst.setString(4, txtClientesFone.getText());
           
            //validação dos campos obrigatórios
            if (txtClientesNome.getText().isEmpty() || txtClientesFone.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");

            } else {

                //a linha abaixo atualiza a tabela clientes com os dados do formulário
                // a estrutura abaixo é usada para confirmar a inserção dos dados na tabela
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve de apoio ao entendimento da lógica
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso!");
                    limparCampos();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //método para pesquisar clientes pelo nome com filtro
    private void pesquisar_cliente(){
        String sql = "select idcli as Id, nomecli as Nome, endcli as Endereço," +
"fonecli as Telefone, emailcli as Email from tbclientes where nomecli like ?";
        try {
            pst = conexao.prepareStatement(sql);
            // passando o conteúdo da caixa de pesquisa para o ?
            // atenção ao "%" - continuação da String sql
            pst.setString(1,txtClientesPesquisar.getText() + "%");
            rs = pst.executeQuery();
            // a linha abaixo usa a bilblioteca rs2xml.jar para preencher a tabela
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //método para setar os campos do formulário com o conteúdo da tabela
    public void setar_campos(){
        int setar = tblClientes.getSelectedRow();
        txtClientesId.setText(tblClientes.getModel().getValueAt(setar,0).toString());
        txtClientesNome.setText(tblClientes.getModel().getValueAt(setar,1).toString());
        txtClientesEndereco.setText(tblClientes.getModel().getValueAt(setar,2).toString());
        txtClientesFone.setText(tblClientes.getModel().getValueAt(setar,3).toString());
        txtClientesEmail.setText(tblClientes.getModel().getValueAt(setar,4).toString());
        //a linha abaixo desabilita o botão adicinar
        btnClientesCreate.setEnabled(false);
    }
    
    //método para alterar dados do cliente
    private void alterar(){
        String sql = "update tbclientes set nomecli=?, endcli=?, fonecli=?,"
                + "emailcli=? where idcli=?";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtClientesNome.getText());
            pst.setString(2, txtClientesEndereco.getText());
            pst.setString(3, txtClientesFone.getText());
            pst.setString(4, txtClientesEmail.getText());
            pst.setString(5, txtClientesId.getText());
            
            //validação dos campos obrigatórios
            if (txtClientesNome.getText().isEmpty() || txtClientesFone.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null,"Preencha todos os campos obrigatórios");

            } else {

                //a linha abaixo atualiza a tabela clientes com os dados do formulário
                // a estrutura abaixo é usada para confirmar a alteração dos dados na tabela
                int alterado = pst.executeUpdate();
                // a linha abaixo serve de apoio ao entendimento da lógica
                //System.out.println(adicionado);
                if (alterado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do cliente alterados com sucesso!");
                    limparCampos();
                    btnClientesCreate.setEnabled(true);
                }
            }
            
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //método responsável pela remoção de cliente
    private void remover(){
        // a estrutura abaixo confirma a remoção do cliente
        Object[] opcoes = {"Sim", "Não"};
        int confirma = JOptionPane.showOptionDialog(null, "Tem certeza que deseja remover este cliente?", "Atenção",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, opcoes, opcoes[1]);

       //System.out.println(confirma);
        if(confirma == JOptionPane.YES_OPTION){
            String sql = "delete from tbclientes where idcli=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtClientesId.getText());
                int apagado = pst.executeUpdate();
                if(apagado > 0){
                    JOptionPane.showMessageDialog(null,"Cliente removido com sucesso!");
                    limparCampos();
                    btnClientesCreate.setEnabled(true);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e);
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

        jLabel1 = new javax.swing.JLabel();
        btnClientesCreate = new javax.swing.JButton();
        btnClientesUpdate = new javax.swing.JButton();
        btnClientesDelete = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtClientesEndereco = new javax.swing.JTextField();
        txtClientesFone = new javax.swing.JTextField();
        txtClientesEmail = new javax.swing.JTextField();
        txtClientesNome = new javax.swing.JTextField();
        txtClientesPesquisar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        txtClientesId = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setPreferredSize(new java.awt.Dimension(640, 480));

        jLabel1.setText("*Campos obrigatórios");

        btnClientesCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icones/create.png"))); // NOI18N
        btnClientesCreate.setToolTipText("Adicionar");
        btnClientesCreate.setPreferredSize(new java.awt.Dimension(80, 80));
        btnClientesCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesCreateActionPerformed(evt);
            }
        });

        btnClientesUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icones/update.png"))); // NOI18N
        btnClientesUpdate.setToolTipText("Alterar");
        btnClientesUpdate.setPreferredSize(new java.awt.Dimension(80, 80));
        btnClientesUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesUpdateActionPerformed(evt);
            }
        });

        btnClientesDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icones/delete.png"))); // NOI18N
        btnClientesDelete.setToolTipText("Remover");
        btnClientesDelete.setPreferredSize(new java.awt.Dimension(80, 80));
        btnClientesDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesDeleteActionPerformed(evt);
            }
        });

        jLabel2.setText("*Nome");

        jLabel3.setText("Endereço");

        jLabel4.setText("*Telefone");

        jLabel5.setText("E-mail");

        txtClientesPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtClientesPesquisarKeyReleased(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icones/lupa.png"))); // NOI18N

        tblClientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id", "Nome", "Endereço", "Telefone", "Email"
            }
        ));
        tblClientes.setFocusable(false);
        tblClientes.getTableHeader().setReorderingAllowed(false);
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        jLabel8.setText("Id Cliente");

        txtClientesId.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 560, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtClientesNome)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtClientesEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel4)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtClientesFone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(txtClientesId, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(34, 34, 34)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtClientesEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnClientesCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(35, 35, 35)
                                        .addComponent(btnClientesUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(39, 39, 39)
                                        .addComponent(btnClientesDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(202, 202, 202))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtClientesPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(jLabel1)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtClientesPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel7)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtClientesId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtClientesNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtClientesEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtClientesFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtClientesEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnClientesUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClientesCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClientesDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        setBounds(0, 0, 640, 480);
    }// </editor-fold>//GEN-END:initComponents

    private void btnClientesCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesCreateActionPerformed
        // chamando o método adiconar cliente
        adicionar();
    }//GEN-LAST:event_btnClientesCreateActionPerformed

    // o evento abaixo é do tipo "enquanto for digitando"
    private void txtClientesPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClientesPesquisarKeyReleased
        // chamar o método pesquisar clientes
        pesquisar_cliente();
    }//GEN-LAST:event_txtClientesPesquisarKeyReleased

    //evento que será usado para setar os campos cliecando com o mouse na tabela
    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // chamando o método para setar os campos
        setar_campos();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnClientesUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesUpdateActionPerformed
        // chamando o método para alterar clientes
        alterar();
    }//GEN-LAST:event_btnClientesUpdateActionPerformed

    private void btnClientesDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesDeleteActionPerformed
        //chamando o método para remover um cliente
        remover();
    }//GEN-LAST:event_btnClientesDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClientesCreate;
    private javax.swing.JButton btnClientesDelete;
    private javax.swing.JButton btnClientesUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtClientesEmail;
    private javax.swing.JTextField txtClientesEndereco;
    private javax.swing.JTextField txtClientesFone;
    private javax.swing.JTextField txtClientesId;
    private javax.swing.JTextField txtClientesNome;
    private javax.swing.JTextField txtClientesPesquisar;
    // End of variables declaration//GEN-END:variables

    private void limparCampos() {
       txtClientesPesquisar.setText(null);
       txtClientesId.setText(null);
       txtClientesNome.setText(null);
       txtClientesEndereco.setText(null);
       txtClientesFone.setText(null);
       txtClientesEmail.setText(null);
       ((DefaultTableModel)tblClientes.getModel()).setRowCount(0);
    }
}
