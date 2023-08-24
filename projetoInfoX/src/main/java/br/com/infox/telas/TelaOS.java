/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.infox.telas;

import java.sql.*;
import br.com.infox.dal.ModuloConexao;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author pallo
 */
public class TelaOS extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    // a linha abaixo cria uma variável para armazenar um texto 
    //de acordo com o radion button selecionado
    private String tipo;
    private final String OS = "Ordem de Serviço";
    private final String ORCAMENTO = "Orçamento";

    /**
     * Creates new form TelaOS
     */
    public TelaOS() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void pesquisar_cliente() {
        String sql = "select idcli as Id, nomecli as Nome, fonecli as Telefone from tbclientes where nomecli like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliPesquisar.getText() + "%");
            rs = pst.executeQuery();
            tblClintesOS.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
        }
    }

    private void setar_campos() {
        int setar = tblClintesOS.getSelectedRow();
        txtCliId.setText(tblClintesOS.getModel().getValueAt(setar, 0).toString());
    }

    //método para cadastrar uma OS
    private void emitir_os() {
        String sql = "insert into tbos(tipo, situacao, equipamento, defeito, "
                + "servico, tecnico, valor, idcli) values(?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOsSit.getSelectedItem().toString());
            pst.setString(3, txtOSEquipamento.getText());
            pst.setString(4, txtOSDefeito.getText());
            pst.setString(5, txtOSServico.getText());
            pst.setString(6, txtOSTecnico.getText());

            //validação campo Valor porque o banco de dados
            //gera erro se não tiver um valor decimal
            String valor = txtOSValor.getText().isEmpty() ? "0.00"
                    : txtOSValor.getText().replace(",", ".");
            pst.setString(7, valor);

            pst.setString(8, txtCliId.getText());

            //validação dos campos obrigatórios
            if (txtCliId.getText().isEmpty() || txtOSEquipamento.getText().isEmpty()
                    || txtOSDefeito.getText().isEmpty() || cboOsSit.getSelectedItem().equals(" ")) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");

            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "OS emitida com sucesso");

                    //recuperar o número da OS
                    recuperarOs();
                    
                    //limpar_campos();
                    btnOSAdicionar.setEnabled(false);
                    btnOSConsultar.setEnabled(false);
                    btnOSImprimir.setEnabled(true);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //método para pesquisar OS
    private void pesquisar_os() {
        //a linha abaixo cria uma caixa de entrada de dados
        String numeroOs = JOptionPane.showInputDialog("Numero da Ordem de Serviço");
        String sql = "select os, date_format(data_os, '%d/%m/%Y - %H:%I'), tipo, situacao, " +
"equipamento, defeito, servico, tecnico, valor, idcli from tbos where os= " + numeroOs;
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                txtOS.setText(rs.getString(1));
                txtData.setText(rs.getString(2));

                //setando os radios button
                String rbtTipo = rs.getString(3);
                if (rbtTipo.equals(OS)) {
                    rbtOS.setSelected(true);
                    tipo = OS;
                } else {
                    rbtOrcamento.setSelected(true);
                    tipo = ORCAMENTO;
                }

                cboOsSit.setSelectedItem(rs.getString(4));
                txtOSEquipamento.setText(rs.getString(5));
                txtOSDefeito.setText(rs.getString(6));
                txtOSServico.setText(rs.getString(7));
                txtOSTecnico.setText(rs.getString(8));
                txtOSValor.setText(rs.getString(9));
                txtCliId.setText(rs.getString(10));
                
                //evitando problems
                btnOSAdicionar.setEnabled(false);
                btnOSConsultar.setEnabled(false);
                txtCliPesquisar.setEditable(false);
                tblClintesOS.setVisible(false);
                
                //ativar demais botões
                btnOSAlterar.setEnabled(true);
                btnOSExcluir.setEnabled(true);
                btnOSImprimir.setEnabled(true);

            } else {
                JOptionPane.showMessageDialog(null, "Ordem de Serviço não cadastrada");
            }
        } catch (SQLSyntaxErrorException error) {
            JOptionPane.showMessageDialog(null, "Ordem de Serviço inválida!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    //metódo para alterar uma OS
    private void alterar_os() {
        String sql = "update tbos set tipo=?, situacao=?, equipamento=?, defeito=?, servico=?, tecnico=?, valor=? where os=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOsSit.getSelectedItem().toString());
            pst.setString(3, txtOSEquipamento.getText());
            pst.setString(4, txtOSDefeito.getText());
            pst.setString(5, txtOSServico.getText());
            pst.setString(6, txtOSTecnico.getText());

            //validação campo Valor porque o banco de dados
            //gera erro se não tiver um valor decimal
            String valor = txtOSValor.getText().isEmpty() ? "0.00"
                    : txtOSValor.getText().replace(",", ".");
            pst.setString(7, valor);

            pst.setString(8, txtOS.getText());

            //validação dos campos obrigatórios
            if (txtCliId.getText().isEmpty() || txtOSEquipamento.getText().isEmpty()
                    || txtOSDefeito.getText().isEmpty() || cboOsSit.getSelectedItem().equals(" ")) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");

            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de Serviço alterada com sucesso");

                    limpar_campos();
                    gerenciarBotoes();
                    
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    //método para excluir uma os
    private void excluir_os() {
        // a estrutura abaixo confirma a remoção do usuário
        Object[] opcoes = {"Sim", "Não"};
        int confirma = JOptionPane.showOptionDialog(null, "Tem certeza que deseja excluir esta OS?", "Atenção",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[1]);

        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbos where os=?";

            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtOS.getText());

                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "OS excluída com sucesso");
                    limpar_campos();
                    gerenciarBotoes();

                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    //método para imprimir uam os
    private void imprimir_os(){
        //imprimindo uma os
        Object[] opcoes = {"Sim", "Não"};
        int confirma = JOptionPane.showOptionDialog(null, "Confirma a impressão desta OS?", "Atenção",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, opcoes, opcoes[0]);

        if(confirma == JOptionPane.YES_OPTION){
            //emitindo relatório com o framework JasperReport
            try {
                //usando a classe HashMap para criar um filtro
                HashMap filtro = new HashMap();
                            //os é o nome do parâmetro criado no ireport
                filtro.put("os",Integer.parseInt(txtOS.getText()));
                
                //Usando a classe JasperPrint para preparar a impressão de um relatório
                JasperPrint print = JasperFillManager.fillReport("E:/CODIGOS/reports/os.jasper", filtro, conexao);
                
                //a linha abaixo exibe o relatório através da classe JasperViewer
                JasperViewer.viewReport(print, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtOS = new javax.swing.JTextField();
        txtData = new javax.swing.JTextField();
        rbtOrcamento = new javax.swing.JRadioButton();
        rbtOS = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cboOsSit = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtCliPesquisar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCliId = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClintesOS = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtOSDefeito = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtOSServico = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtOSEquipamento = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtOSTecnico = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtOSValor = new javax.swing.JTextField();
        btnOSAdicionar = new javax.swing.JButton();
        btnOSConsultar = new javax.swing.JButton();
        btnOSAlterar = new javax.swing.JButton();
        btnOSExcluir = new javax.swing.JButton();
        btnOSImprimir = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Ordem de Serviço");
        setPreferredSize(new java.awt.Dimension(640, 480));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Nº OS");

        jLabel2.setText("Data");

        txtOS.setEditable(false);

        txtData.setEditable(false);
        txtData.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        buttonGroup1.add(rbtOrcamento);
        rbtOrcamento.setText("Orçamento");
        rbtOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOrcamentoActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOS);
        rbtOS.setText("Ordem de Serviço");
        rbtOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(txtOS, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel2))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rbtOrcamento)
                        .addGap(18, 18, 18)
                        .addComponent(rbtOS)))
                .addContainerGap(9, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtOrcamento)
                    .addComponent(rbtOS))
                .addContainerGap())
        );

        jLabel3.setText("Situação");

        cboOsSit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Na bancada", "Entrega OK", "Orçamento REPROVADO", "Aguardando Aprovação", "Aguardando peças", "Abandonado pelo cliente", "Retornou" }));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icones/lupa16px.png"))); // NOI18N

        jLabel5.setText("*Id");

        txtCliId.setEnabled(false);
        txtCliId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCliIdActionPerformed(evt);
            }
        });

        tblClintesOS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Nome", "Telefone"
            }
        ));
        tblClintesOS.setFocusable(false);
        tblClintesOS.getTableHeader().setReorderingAllowed(false);
        tblClintesOS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClintesOSMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClintesOS);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCliPesquisar)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(23, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel6.setText("*Equipamento");

        jLabel7.setText("*Defeito");

        jLabel8.setText("Serviço");

        jLabel9.setText("Técnico");

        jLabel10.setText("Valor Total");

        btnOSAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icones/create.png"))); // NOI18N
        btnOSAdicionar.setToolTipText("Emitir OS");
        btnOSAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOSAdicionar.setPreferredSize(new java.awt.Dimension(60, 60));
        btnOSAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOSAdicionarActionPerformed(evt);
            }
        });

        btnOSConsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icones/read.png"))); // NOI18N
        btnOSConsultar.setToolTipText("Consultar");
        btnOSConsultar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOSConsultar.setPreferredSize(new java.awt.Dimension(60, 60));
        btnOSConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOSConsultarActionPerformed(evt);
            }
        });

        btnOSAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icones/update.png"))); // NOI18N
        btnOSAlterar.setToolTipText("Alterar");
        btnOSAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOSAlterar.setEnabled(false);
        btnOSAlterar.setPreferredSize(new java.awt.Dimension(60, 60));
        btnOSAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOSAlterarActionPerformed(evt);
            }
        });

        btnOSExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icones/delete.png"))); // NOI18N
        btnOSExcluir.setToolTipText("Remover");
        btnOSExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOSExcluir.setEnabled(false);
        btnOSExcluir.setPreferredSize(new java.awt.Dimension(60, 60));
        btnOSExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOSExcluirActionPerformed(evt);
            }
        });

        btnOSImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/icones/impressora.png"))); // NOI18N
        btnOSImprimir.setToolTipText("Imprimir OS");
        btnOSImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOSImprimir.setEnabled(false);
        btnOSImprimir.setPreferredSize(new java.awt.Dimension(60, 60));
        btnOSImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOSImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cboOsSit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel8)))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOSServico)
                            .addComponent(txtOSEquipamento)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtOSDefeito, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(txtOSTecnico)
                                            .addGap(18, 18, 18)
                                            .addComponent(jLabel10)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtOSValor, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnOSAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnOSConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnOSAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnOSExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnOSImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cboOsSit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtOSEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtOSDefeito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtOSServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtOSTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtOSValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOSAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOSConsultar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOSAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOSExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOSImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53))
        );

        setBounds(0, 0, 640, 480);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCliIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCliIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliIdActionPerformed

    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // chamando o metódo pesquisar_cliente
        pesquisar_cliente();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    private void tblClintesOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClintesOSMouseClicked
        // chamando o método setar campos
        setar_campos();
    }//GEN-LAST:event_tblClintesOSMouseClicked

    private void rbtOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOrcamentoActionPerformed
        // atribuindo um texto a variável tipo se selecionado
        tipo = ORCAMENTO;
    }//GEN-LAST:event_rbtOrcamentoActionPerformed

    private void rbtOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOSActionPerformed
        // a linha abaixo atribui um texto a variável tipo se o radio button 
        //estiver selecionado
        tipo = OS;
    }//GEN-LAST:event_rbtOSActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // ao abrir o form marcar o radio button Orçamento
        rbtOrcamento.setSelected(true);
        tipo = ORCAMENTO;
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnOSAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOSAdicionarActionPerformed
        // chamar o método emitir_os
        emitir_os();
    }//GEN-LAST:event_btnOSAdicionarActionPerformed

    private void btnOSConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOSConsultarActionPerformed
        // chamando o método pesquisar_os
        pesquisar_os();
    }//GEN-LAST:event_btnOSConsultarActionPerformed

    private void btnOSAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOSAlterarActionPerformed
        // chamando o método alterar_os
        alterar_os();
    }//GEN-LAST:event_btnOSAlterarActionPerformed

    private void btnOSExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOSExcluirActionPerformed
        // chamando o método para excluir uma OS
        excluir_os();
    }//GEN-LAST:event_btnOSExcluirActionPerformed

    private void btnOSImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOSImprimirActionPerformed
        // chamando o método para imprimir uma OS
        imprimir_os();
    }//GEN-LAST:event_btnOSImprimirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOSAdicionar;
    private javax.swing.JButton btnOSAlterar;
    private javax.swing.JButton btnOSConsultar;
    private javax.swing.JButton btnOSExcluir;
    private javax.swing.JButton btnOSImprimir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboOsSit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbtOS;
    private javax.swing.JRadioButton rbtOrcamento;
    private javax.swing.JTable tblClintesOS;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtOS;
    private javax.swing.JTextField txtOSDefeito;
    private javax.swing.JTextField txtOSEquipamento;
    private javax.swing.JTextField txtOSServico;
    private javax.swing.JTextField txtOSTecnico;
    private javax.swing.JTextField txtOSValor;
    // End of variables declaration//GEN-END:variables

    
    //recuperar número OS
    private void recuperarOs(){
        String sql = "select max(os) from tbos";
        
        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if(rs.next()){
                txtOS.setText(rs.getString(1));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        
    }
    
    
    private void limpar_campos() {
        txtOS.setText(null);
        txtData.setText(null);
        txtOSEquipamento.setText(null);
        txtOSDefeito.setText(null);
        txtOSServico.setText(null);
        txtOSTecnico.setText(null);
        txtOSValor.setText(null);
        txtCliId.setText(null);
        txtCliPesquisar.setText(null);
        ((DefaultTableModel) tblClintesOS.getModel()).setRowCount(0);
        cboOsSit.setSelectedItem(" ");

    }

    private void gerenciarBotoes() {
        //habilitar os objetos
        btnOSAdicionar.setEnabled(true);
        btnOSConsultar.setEnabled(true);
        txtCliPesquisar.setEnabled(true);
        tblClintesOS.setVisible(true);
        
        //desabilita os botões
        btnOSAlterar.setEnabled(false);
        btnOSExcluir.setEnabled(false);
        btnOSImprimir.setEnabled(false);
        
    }
}
