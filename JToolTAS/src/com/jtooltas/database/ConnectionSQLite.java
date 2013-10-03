
package com.jtooltas.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionSQLite {

	private static volatile ConnectionSQLite instance = null;

	private Statement statement;
	private Connection connection;

	private boolean old_mode;
	

	private ConnectionSQLite() throws ClassNotFoundException {
		super();
		
		Class.forName( "org.sqlite.JDBC" );	
	}

	/**
	 * Singleton avec double check
	 * @return
	 * @throws ClassNotFoundException 
	 */
	public static ConnectionSQLite getInstance() throws ClassNotFoundException {

		if ( ConnectionSQLite.instance == null ) {

			synchronized( ConnectionSQLite.class ) {

				if ( ConnectionSQLite.instance == null )
					ConnectionSQLite.instance = new ConnectionSQLite();
			}
		}

		return ConnectionSQLite.instance;
	}

	/**
	 * Connecte la base de donn�es dont l'adresse est pass�e en param�tre.
	 * @param db_path l'adresse de la base de donn�es.
	 * @return true si connect�e
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean connect( String path ) throws SQLException {

		boolean connected = false;
		
		if ( ConnectionSQLite.instance != null ) {

			this.connection = DriverManager.getConnection( "jdbc:sqlite:" + path );
			this.old_mode = this.connection.getAutoCommit();
			this.statement = this.connection.createStatement();
			
			connected = true;
		}

		return connected;
	}
	
	public void begin() throws SQLException {
		
		this.old_mode = this.connection.getAutoCommit();
		this.connection.setAutoCommit( false );
	}
	
	public void end() throws SQLException {
		
		this.connection.commit();
		this.connection.setAutoCommit( this.old_mode );
	}
	
	public void updateQuery( String sql ) throws SQLException {
		
		this.statement.executeUpdate( sql );
	}
	
	public ResultSet selectQuery( String sql ) throws SQLException {
		
		return this.statement.executeQuery( sql );
	}
	
	public void close() throws SQLException {
		
		this.statement.close();
		this.connection.close();
	}
}
