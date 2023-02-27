package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaFXMLController {
	@FXML
	private Label id;
	@FXML
	private Label nome;
	@FXML
	private TextField txtId;
	@FXML
	private TextField txtNome;

	@FXML
	private void initialize() {
	}

	@FXML
	public void actionMensagem(ActionEvent event) {
		id.setText(txtId.getText());
		nome.setText(txtNome.getText());
	}

	@FXML
	public void actionSQLSelect(ActionEvent event) {
		try {
			DBUtil db = DBUtil.getInstance();
			PreparedStatement ps = db.getConnection().prepareStatement("Select * from aluno");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getInt("idaluno") + "-" + rs.getString("nome"));
			}
		} catch (Exception e) {
			System.out.println("Erro: " + e.toString());
		}  
		initTable();

	}

	@FXML
	public void actionSQLInsert(ActionEvent event) {
		try {
			DBUtil db = DBUtil.getInstance();
			PreparedStatement ps = db.getConnection()
					.prepareStatement("Insert into aluno (idaluno , nome) values (?, ?)");
			ps.setInt(1, Integer.parseInt(txtId.getText()));
			ps.setString(2, txtNome.getText());
			ps.execute();
		} catch (Exception e) {
			System.out.println("Erro: " + e.toString());
		}
		initTable();
	}

	@FXML
	public void actionSQLUpdate(ActionEvent event) {
		try {
			DBUtil db = DBUtil.getInstance();
			PreparedStatement ps = db.getConnection().prepareStatement("update aluno set nome = ? where id = ?");
			ps.setString(1, txtNome.getText());
			ps.setInt(2, Integer.parseInt(txtId.getText()));
			ps.execute();
		} catch (Exception e) {
			System.out.println("Erro: " + e.toString());
		}
		initTable();
	}

	@FXML
	public void actionSQLDelete(ActionEvent event) {
		try {
			DBUtil db = DBUtil.getInstance();
			PreparedStatement ps = db.getConnection().prepareStatement("Delete from aluno where id = ?");
			ps.setInt(1, Integer.parseInt(txtId.getText()));
			ps.execute();
		} catch (Exception e) {
			System.out.println("Erro: " + e.toString());
		}
		initTable();
	}

	@FXML
	private TableView<Pessoa> tbView;
	@FXML
	private TableColumn<Pessoa, Integer> colId;
	@FXML
	private TableColumn<Pessoa, String> colNome;

    // private ObservableList<Pessoa> data = FXCollections.observableArrayList();
	 private ObservableList<Pessoa> listaPessoas;

	@FXML
	public void initTable() {
		colId.setCellValueFactory(new PropertyValueFactory<Pessoa, Integer>("id"));
		colNome.setCellValueFactory(new PropertyValueFactory<Pessoa, String>("nome"));

		        
		        listaPessoas = FXCollections.observableArrayList();
		        try {
		            DBUtil db = DBUtil.getInstance();
		            PreparedStatement ps = db.getConnection().prepareStatement("SELECT * FROM alunos");
		            ResultSet rs = ps.executeQuery();
		            while (rs.next()) {
		                Pessoa pessoa = new Pessoa(rs.getInt("id"), rs.getString("nome"));
		                listaPessoas.add(pessoa);
		            }
		        } catch (Exception e) {
		            System.out.println("Erro ao carregar dados da tabela: " + e.getMessage());
		        }

		        
		        tbView.setItems(listaPessoas);
		    }
	
}
