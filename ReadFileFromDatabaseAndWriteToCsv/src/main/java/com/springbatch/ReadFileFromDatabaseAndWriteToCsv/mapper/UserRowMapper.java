package com.springbatch.ReadFileFromDatabaseAndWriteToCsv.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.springbatch.ReadFileFromDatabaseAndWriteToCsv.entity.Student;
@Component
public class UserRowMapper implements RowMapper<Student> {

	@Override
	public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Student st=new Student();
		st.setId(rs.getLong("id"));
		st.setName(rs.getString("name"));
		st.setDept(rs.getString("dept"));
		st.setPhoneNumber(rs.getLong("phoneNumber"));
		return st;
	}

}
