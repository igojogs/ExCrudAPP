package br.igorms.crudapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LivroController implements Initializable {
    Connection com = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    @FXML
    private Button btnAtualizar;

    @FXML
    private Button btnDeletar;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnSalvar;

    @FXML
    private TableView<Livro> table;

    @FXML
    private TextField txtAno;

    @FXML
    private TextField txtAutor;

    @FXML
    private TextField txtTitulo;

    @FXML
    private TableColumn<Livro, Integer> colAno;

    @FXML
    private TableColumn<Livro, String> colAutor;

    @FXML
    private TableColumn<Livro, Integer> colId;

    @FXML
    private TableColumn<Livro, String> colTitulo;

    int id = 0;

    @FXML
    void getData(MouseEvent event) {
        Livro livro = table.getSelectionModel().getSelectedItem();
        id = livro.getId();
        txtTitulo.setText(livro.getTitulo());
        txtAutor.setText(livro.getAutor());
        txtAno.setText(String.valueOf(livro.getAno()));
        btnSalvar.setDisable(true);
    }

    @FXML
    void atualizarLivro(ActionEvent event) {
        String query = "update livros set Titulo = ?, Autor = ?, Ano = ? where id = ?";
        com = DBConnexion.getCon();
        try {
            st = com.prepareStatement(query);
            st.setString(1, txtTitulo.getText());
            st.setString(2, txtAutor.getText());
            st.setString(3, txtAno.getText());
            st.setInt(4, id);
            st.executeUpdate();
            clear();
            mostrarLivros();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void clear(){
        txtTitulo.setText(null);
        txtAutor.setText(null);
        txtAno.setText(null);
        btnSalvar.setDisable(false);
    }

    @FXML
    void deletarLivro(ActionEvent event) {
        String query = "delete from livros where id = ?";
        com = DBConnexion.getCon();
        try {
            st = com.prepareStatement(query);
            st.setInt(1, id);
            st.executeUpdate();
            clear();
            mostrarLivros();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void limparLivro(ActionEvent event) {
        clear();
    }

    @FXML
    void salvarLivro(ActionEvent event) {
        String query = "insert into livros(Titulo,Autor,Ano) values(?, ?, ?)";
        com = DBConnexion.getCon();
        try {
            st = com.prepareStatement(query);
            st.setString(1, txtTitulo.getText());
            st.setString(2, txtAutor.getText());
            st.setString(3, txtAno.getText());
            st.executeUpdate();
            clear();
            mostrarLivros();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mostrarLivros();
    }

    public ObservableList<Livro> getLivros(){
        ObservableList<Livro> livros = FXCollections.observableArrayList();

        String query = "select * from livros";
        com = DBConnexion.getCon();
        try {
            st = com.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()){
                Livro l = new Livro();
                l.setId(rs.getInt("id"));
                l.setTitulo(rs.getString("Titulo"));
                l.setAutor(rs.getString("Autor"));
                l.setAno(rs.getInt("Ano"));
                livros.add(l);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return livros;
    }

    public void mostrarLivros(){
        ObservableList<Livro> list = getLivros();
        table.setItems(list);
        colId.setCellValueFactory(new PropertyValueFactory<Livro, Integer>("id"));
        colAutor.setCellValueFactory(new PropertyValueFactory<Livro, String>("Autor"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<Livro, String>("Titulo"));
        colAno.setCellValueFactory(new PropertyValueFactory<Livro, Integer>("Ano"));
    }

}
