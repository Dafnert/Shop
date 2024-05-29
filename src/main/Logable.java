package main;

import java.sql.SQLException;

public interface Logable {
	boolean login(int user, String password) throws SQLException; //Definimos el metodo login con parametros de entrada que devuelve un boolean.
}
