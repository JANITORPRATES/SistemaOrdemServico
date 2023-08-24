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

public class TelaUsuario extends javax.swing.JInternalFrame {

    /**
     * Creates new form TelaUsuario
     */
    // usando a variavel conexao do DAL
    Connection conexao = null;

    /* criando variáveis especiais para conexão com o banco
    Prepared Statement e ResultSet são frameworks do pacote java.sql
    e servem para preparar e executar as instruções SQL */
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaUsuario() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    // método para consultar usuário
    private void consultar() {
        String sql = "select * from tbusuarios where iduser=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuarioId.getText());
            rs = pst.executeQuery();

            if (rs.next()) {
                txtUsuarioNome.setText(rs.getString(2));
                txtUsuarioFone.setText(rs.getString(3));
                txtUsuarioLogin.setText(rs.getString(4));
                txtUsuarioSenha.setText(rs.getString(5));
                // a linha abaixo se refere ao comboBox
                cboUsuarioPerfil.setSelectedItem(rs.getString(6));
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não cadastrado");
                limparCampos();

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //método para adicionar usuário
    private void adicionar() {
        String sql = "insert into tbusuarios(iduser, usuario, fone, login, senha, perfil)"
                + "values(?,?,?,?,?,?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuarioId.getText());
            pst.setString(2, txtUsuarioNome.getText());
            pst.setString(3, txtUsuarioFone.getText());
            pst.setString(4, txtUsuarioLogin.getText());
            pst.setString(5, txtUsuarioSenha.getText());
            pst.setString(6, cboUsuarioPerfil.getSelectedItem().toString());

            //validação dos campos obrigatórios
            if (txtUsuarioId.getText().isEmpty() || txtUsuarioSenha.getText().isEmpty()
                    || txtUsuarioNome.getText().isEmpty() || txtUsuarioLogin.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null,"Preencha todos os campos obrigatórios");

            } else {

                //a linha abaixo atualiza a tabela usuario com os dados do formulário
                // a estrutura abaixo é usada para confirmar a inserção dos dados na tabela
                int adicionado = pst.executeUpdate();
                // a linha abaixo serve de apoio ao entendimento da lógica
                //System.out.println(adicionado);
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Usuário adicionado com sucesso!");
                    limparCampos();
                    txtUsuarioId.setText(null);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //criando o método para alterar dados do usuário
    private void alterar(){
        String sql = "update tbusuarios set usuario=?, fone=?, login=?,"
                + "senha=?, perfil=? where iduser=?";
        
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuarioNome.getText());
            pst.setString(2, txtUsuarioFone.getText());
            pst.setString(3, txtUsuarioLogin.getText());
            pst.setString(4, txtUsuarioSenha.getText());
            pst.setString(5, cboUsuarioPerfil.getSelectedItem().toString());
            pst.setString(6, txtUsuarioId.getText());
            
            //validação dos campos obrigatórios
            if (txtUsuarioId.getText().isEmpty() || txtUsuarioSenha.getText().isEmpty()
                    || txtUsuarioNome.getText().isEmpty() || txtUsuarioLogin.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null,"Preencha todos os campos obrigatórios");

            } else {

                //a linha abaixo atualiza a tabela usuario com os dados do formulário
                // a estrutura abaixo é usada para confirmar a alteração dos dados na tabela
                int alterado = pst.executeUpdate();
                // a linha abaixo serve de apoio ao entendimento da lógica
                //System.out.println(adicionado);
                if (alterado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do usuário alterados com sucesso!");
                    limparCampos();
                    txtUsuarioId.setText(null);
                }
            }
            
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e);
        }
    }
    
    //método responsável pela remoção de usuários
    private void remover(){
        // a estrutura abaixo confirma a remoção do usuário
        Object[] opcoes = {"Sim", "Não"};
        int confirma = JOptionPane.showOptionDialog(null, "Tem certeza que deseja remover este usuário?", "Atenção",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, opcoes, opcoes[1]);

       //System.out.println(confirma);
        if(confirma == JOptionPane.YES_OPTION){
            String sql = "delete from tbusuarios where iduser=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtUsuarioId.getText());
                int apagado = pst.executeUpdate();
                if(apagado > 0){
                    JOptionPane.showMessageDialog(null,"Usuário removido com sucesso!");
                    limparCampos();
                    txtUsuarioId.setText(null);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cboUsuarioPerfil = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtUsuarioId = new javax.swing.JTextField();
        txtUsuarioNome = new javax.swing.JTextField();
        txtUsuarioLogin = new javax.swing.JTextField();
        txtUsuarioSenha = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtUsuarioFone = new javax.swing.JTextField();
        btnUsuarioCreate = new javax.swing.JButton();
        btnUsuarioRead = new javax.swing.JButton();
        btnUsuarioUpdate = new javax.swing.JButton();
        btnUsuarioDelete = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Usuários");
        setPreferredSize(new java.awt.Dimension(640, 480));

        jLabel1.setText("*id");

        jLabel2.setText("*Nome");

        jLabel3.setText("*Login");

        jLabel4.setText("*Senha");

        cboUsuarioPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "admin", "user" }));

        jLabel5.setText("*Perfil");

        jLabel6.setText("Fone");

        btnUsuarioCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icones/create.png"))); // NOI18N
        btnUsuarioCreate.setToolTipText("Adicionar");
        btnUsuarioCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuarioCreate.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuarioCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuarioCreateActionPerformed(evt);
            }
        });

        btnUsuarioRead.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icones/read.png"))); // NOI18N
        btnUsuarioRead.setToolTipText("Consultar");
        btnUsuarioRead.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuarioRead.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuarioRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuarioReadActionPerformed(evt);
            }
        });

        btnUsuarioUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icones/update.png"))); // NOI18N
        btnUsuarioUpdate.setToolTipText("Alterar");
        btnUsuarioUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuarioUpdate.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuarioUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuarioUpdateActionPerformed(evt);
            }
        });

        btnUsuarioDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icones/delete.png"))); // NOI18N
        btnUsuarioDelete.setToolTipText("Remover");
        btnUsuarioDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUsuarioDelete.setPreferredSize(new java.awt.Dimension(80, 80));
        btnUsuarioDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuarioDeleteActionPerformed(evt);
            }
        });

        jLabel7.setText("*Campos obrigatórios");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(24, 24, 24)
                        .addComponent(cboUsuarioPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtUsuarioId, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtUsuarioFone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(66, 66, 66)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(19, 19, 19)
                                        .addComponent(txtUsuarioLogin))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(19, 19, 19)
                                        .addComponent(txtUsuarioSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(txtUsuarioNome)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(btnUsuarioCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                                .addComponent(btnUsuarioRead, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(btnUsuarioUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(btnUsuarioDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(139, 139, 139))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtUsuarioId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtUsuarioNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtUsuarioLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtUsuarioFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtUsuarioSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboUsuarioPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(84, 84, 84)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnUsuarioCreate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUsuarioRead, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUsuarioUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUsuarioDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(151, 151, 151))
        );

        setBounds(0, 0, 640, 480);
    }// </editor-fold>//GEN-END:initComponents

    private void btnUsuarioReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuarioReadActionPerformed
        // chamando o método consultar
        consultar();
    }//GEN-LAST:event_btnUsuarioReadActionPerformed

    private void btnUsuarioCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuarioCreateActionPerformed
        // chamando o método adicionar
        adicionar();
    }//GEN-LAST:event_btnUsuarioCreateActionPerformed

    private void btnUsuarioUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuarioUpdateActionPerformed
        // chamando o método alterar
        alterar();
    }//GEN-LAST:event_btnUsuarioUpdateActionPerformed

    private void btnUsuarioDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuarioDeleteActionPerformed
        // chamando o método remover
        remover();
    }//GEN-LAST:event_btnUsuarioDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnUsuarioCreate;
    private javax.swing.JButton btnUsuarioDelete;
    private javax.swing.JButton btnUsuarioRead;
    private javax.swing.JButton btnUsuarioUpdate;
    private javax.swing.JComboBox<String> cboUsuarioPerfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtUsuarioFone;
    private javax.swing.JTextField txtUsuarioId;
    private javax.swing.JTextField txtUsuarioLogin;
    private javax.swing.JTextField txtUsuarioNome;
    private javax.swing.JTextField txtUsuarioSenha;
    // End of variables declaration//GEN-END:variables

    private void limparCampos() {

        // as linhas abaixo "limpam os campos"
        txtUsuarioNome.setText(null);
        txtUsuarioFone.setText(null);
        txtUsuarioLogin.setText(null);
        txtUsuarioSenha.setText(null);
        // a linha abaixo se refere ao comboBox
        //cboUsuarioPerfil.setSelectedItem(null);
    }
}
