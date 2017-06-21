package classesConsulta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import play.libs.ws.WSClient;
import responses.ConsultaResponse;

public class CONSULTAUNILAVRAS implements ConsultaMatricula {

	@Override
	public ConsultaResponse obterStatusMatricula(String mat, WSClient ws) {
		String driverName = "com.mysql.jdbc.Driver";
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String serverName = "localhost";
		String mydatabase = "webServiceTcc";
		String url = "jdbc:mysql://" + serverName + "/" + mydatabase;
		String username = "root";
		String password = "root";

		try {
			Connection con = DriverManager.getConnection(url, username, password);
			PreparedStatement stmt1 = con.prepareStatement("SELECT * FROM aluno_info WHERE matricula = 201134105");
			ResultSet rs1 = stmt1.executeQuery();
			while (rs1.next()) {
				return new ConsultaResponse(rs1.getString("nome"), rs1.getString("matricula"), rs1.getString("cpf"),
						rs1.getString("situacao_matricula"), "UNILAVRAS", rs1.getString("foto"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
