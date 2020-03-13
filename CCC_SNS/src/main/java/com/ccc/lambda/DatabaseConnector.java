package com.ccc.lambda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

class DatabaseConnector implements AutoCloseable {
	private Connection conn;
	
	public DatabaseConnector() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
        	this.conn = DriverManager.getConnection(AwsParams.DATABASE_URL, AwsParams.DATABASE_USERNAME, AwsParams.DATABASE_PASSWORD);
		} catch (SQLException|ClassNotFoundException ex) {
			throw new InternalError("Error while connecting to database", ex);
		}
	}
	
	public List<String> getSNSTopics(LogData logData) {
		String sql = this._makeSqlStatement(logData);
		
		try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
			try (ResultSet set = stmt.executeQuery()) {
				List<String> snsTopics = new LinkedList<String>();
				
				while (set.next()) {
					String current = set.getString(1);
					snsTopics.add(current);
				}
				
				return snsTopics;
			}
		} catch (SQLException ex) {
			throw new InternalError("Error while querying database", ex);
		}
	}
	
	private String _makeSqlStatement(LogData logData) {
		StringBuilder sql = new StringBuilder(
			 "SELECT ST.TopicArn " + 
			 "FROM SNSTopics ST " +
			 "INNER JOIN XRefSNSTopicTrigger XRST ON ST.SNSTopicId = XRST.SNSTopicId " +
			 "INNER JOIN Triggers T ON XRST.TriggerId = T.TriggerId " +
			 "WHERE "
		);
		List<String> subscriptionFilterList = logData.getSubscriptionFilters();
		
		for (int index = 0; index < subscriptionFilterList.size(); ++index) {
			sql.append("T.SubscriptionName = ");
			sql.append('\'');
			sql.append(subscriptionFilterList.get(index));
			sql.append('\'');
			
			if (subscriptionFilterList.size() - 1 == index) {
				sql.append(';');
			}
			else {
				sql.append(" OR ");
			}
		}
		
		return sql.toString();
	}
	
	@Override
	public void close() {
		try {
			this.conn.close();
		} catch (SQLException ex) {
			throw new InternalError("Error while closing connection to database");
		}
	}
}
