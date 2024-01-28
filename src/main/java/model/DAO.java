package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
	
	/** Modulo conexão **/
	
	//parametros de conexao
	
	private String driver="com.mysql.jdbc.Driver";
	private String url="jdbc:mysql://localhost:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	private String user ="root";
	private String password="root";
	
	// Método url
	
	private Connection conectar() {
		Connection con = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			
			return con;
		}catch (Exception e) {
			System.err.println(e.getMessage());
			
			return null;
		}
	}
	
	//teste conexao
	
//	public void testeConexa() {
//		try {
//			Connection con = conectar();
//			System.out.println(con);
//			con.close();
//		}catch (Exception e) {
//			System.err.println(e.getMessage());
//		}
//		
//	}
	

}
