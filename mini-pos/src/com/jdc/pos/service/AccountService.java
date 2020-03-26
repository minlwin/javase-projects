package com.jdc.pos.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jdc.pos.commons.Validations;
import com.jdc.pos.context.ConnectionManager;
import com.jdc.pos.context.PosException;
import com.jdc.pos.context.SqlHelper;
import com.jdc.pos.dto.Account;

public class AccountService {

	public Account login(String loginId, String password) {

		Validations.notEmptyInput(loginId, "Login ID");

		Validations.notEmptyInput(password, "Password");

		Account user = findById(loginId);

		if (null == user) {
			throw new PosException("Please check your Login ID.");
		}
		
		if(!user.getPassword().equals(password)) {
			throw new PosException("Please check your Password.");
		}

		return user;
	}

	private Account findById(String loginId) {
		
	
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(SqlHelper.sql("account.findById"))) {
			
			stmt.setString(1, loginId);
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				return getData(rs);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private Account getData(ResultSet rs) throws SQLException {
		Account a = new Account();
		a.setLoginId(rs.getString(1));
		a.setName(rs.getString(2));
		a.setPassword(rs.getString(3));
		a.setPhone(rs.getString(4));
		a.setEmail(rs.getString(5));
		return a;
	}

}
