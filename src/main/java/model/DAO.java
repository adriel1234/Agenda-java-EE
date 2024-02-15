package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

public class DAO {

	/** Modulo conexão **/

	// parametros de conexao

	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "root";

	// Método url

	private Connection conectar() {
		Connection con = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);

			return con;
		} catch (Exception e) {
			System.err.println(e.getMessage());

			return null;
		}
	}

	/** CRUD CREATE **/

	public void inserirContato(JavaBeans contato) {
		String create = "insert into contatos (nome,fone,email) values(?,?,?)";

		try {
			// abrir conexao banco

			Connection con = conectar();

			// Preparara a query para ser executada no banco de dados
			PreparedStatement pst = (PreparedStatement) con.prepareStatement(create);

			// Substituir os parâmetros (?) pelo conteúdo das variáveis JavaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());

			// executar a query
			pst.executeUpdate();

			// encerrar a conexão com o banco
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/** CRUD READ **/

	public ArrayList<JavaBeans> listarContatos() {
		// criando um objeto para acessar a classe JavaBeans
		ArrayList<JavaBeans> contatos = new ArrayList<>();

		String read = "select * from contatos order by nome";

		try {
			Connection con = conectar();
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(read);
			ResultSet rs = pstm.executeQuery();

			// o laço abaixo será executado enquanto houver contatos
			while (rs.next()) {
				
				// variáveis de apoio que recebem os dados do banco
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);

				// populando o ArrayList
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}

			con.close();
			return contatos;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}

	}
	
	/** CRUD UPDATE **/
	
	public void selecionarContato(JavaBeans contato) {
		String read2 = "select * from contatos where idcon = ?";
		
		try {
			Connection con = conectar();
			PreparedStatement pst = (PreparedStatement) con.prepareStatement(read2);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				
				//Setar as variáveis JavaBeans
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
/** CRUD UPDATE **/
	
	public void editarContato(JavaBeans contato) {
		String read2 = "select * from contatos where idcon = ?";
		
		try {
			Connection con = conectar();
			PreparedStatement pst = (PreparedStatement) con.prepareStatement(read2);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				
				//Setar as variáveis JavaBeans
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//editar contato
	
	public void alterarContato(JavaBeans contato) {
		String update = "update contatos set nome=?,fone=?,email=? where idcon=?";
		
		try {
			Connection con = conectar();
			PreparedStatement pst = (PreparedStatement) con.prepareStatement(update);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			
			pst.executeUpdate();
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	/** CRUD DELETE **/
	public void deletarContato(JavaBeans contato) {
		String delete = "delete from contatos where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = (PreparedStatement) con.prepareStatement(delete);
			pst.setString(1, contato.getIdcon());
			
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
