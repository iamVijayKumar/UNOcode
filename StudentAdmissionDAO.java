package com.cts.unoadm.dao;

import java.util.ArrayList;
import java.util.List;

import com.cts.unoadm.exception.StudentAdmissionException;
import com.cts.unoadm.vo.StudentAdmission;

import com.cts.unoadm.util.*;
import java.sql.*;


public class StudentAdmissionDAO {

//Adding the details of admission of student	
	public boolean addStudentAdmissionDetails(List<StudentAdmission> stdAdmissions) throws StudentAdmissionException {
		boolean recordsAdded = false;
		Connection con=DBConnectionManager.getInstance().getConnection();
		PreparedStatement ps=null;
		try
		{
		    //Inserting the values of list of student admission into database
		    String query="insert into UNO_ADMISSION(ADMISSION_ID,STUDENT_CODE,DATE_OF_COUNSELING,DEPARTMENT_NAME,DATE_OF_ADMISSION,PREFER_COLLEGE_HOSTEL,FIRST_GRADUATE,MANAGER_APPROVAL,ADMISSION_FEE,TUITION_FEE,HOSTEL_FEE,TOTAL_COLLEGE_FEE,FINAL_STATUS_OF_ADMISSION) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		    for(StudentAdmission s:stdAdmissions)
		    {
		        ps=con.prepareStatement(query);
		        ps.setString(1,s.getAdmissionId());
		        ps.setString(2,s.getStudentCode());
		        ps.setDate(3,ApplicationUtil.convertUtilToSqlDate(s.getDateOfCounseling()));
		        ps.setString(4,s.getDepartmentName());
		        ps.setDate(5,ApplicationUtil.convertUtilToSqlDate(s.getDateOfAdmission()));
		        ps.setString(6,s.getPreferCollegeHostel());
		        ps.setString(7,s.getFirstGraduate());
		        ps.setString(8,s.getManagerApproval());
		        ps.setDouble(9,s.getAdmissionFee());
		        ps.setDouble(10,s.getTuitionFee());
		        ps.setDouble(11,s.getHostelFee());
		        ps.setDouble(12,s.getTotalCollegeFee());
		        ps.setString(13,s.getFinalStatusOfAdmission());
		        int i=ps.executeUpdate();
		        if(i>0)
		        {
		            recordsAdded=true;
		        }
		        else
		        {
		            recordsAdded=false;
		        }
		    }
		    
		}
		catch(SQLException e)
		{
		    try{
		        con.rollback();
		    }catch(Exception e1)
		    {
		        e1.printStackTrace();
		    }
		}catch(Exception e)
		{
		    e.printStackTrace();
		    //throw new StudentAdmissionException("Database Value Insertion Failed",e.getCause())
		    
		}
		finally{
		    try{
		        ps.close();
		        con.close();
		    }catch(Exception e)
		    {
		        e.printStackTrace();
		    }
		}
		
		return recordsAdded; 
	}

	public List<StudentAdmission> getAllStudentAdmissionDetails() throws StudentAdmissionException {
		
		List<StudentAdmission> stdAdmissions = new ArrayList<StudentAdmission>();

		//code here
		//Retrieval of all records from database
		String query="select * from UNO_ADMISSION";
		try(Connection con=DBConnectionManager.getInstance().getConnection();
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(query);)
		{
		    while(rs.next())
		    {
		        //Storing the values retrieved from database to Object
		        StudentAdmission obj=new StudentAdmission();
		        obj.setAdmissionId(rs.getString(1));
		        obj.setStudentCode(rs.getString(2));
		        obj.setDateOfCounseling(new java.util.Date(rs.getDate(3).getTime()));
		        obj.setDepartmentName(rs.getString(4));
		        obj.setDateOfAdmission(new java.util.Date(rs.getDate(5).getTime()));
		        obj.setPreferCollegeHostel(rs.getString(6));
		        obj.setFirstGraduate(rs.getString(7));
		        obj.setManagerApproval(rs.getString(8));
		        obj.setAdmissionFee(rs.getDouble(9));
		        obj.setTuitionFee(rs.getDouble(10));
		        obj.setHostelFee(rs.getDouble(11));
		        obj.setTotalCollegeFee(rs.getDouble(12));
		        obj.setFinalStatusOfAdmission(rs.getString(13));
		        
		        //Adding StudentAdmission object into arryalist
		        stdAdmissions.add(obj);
		        
		    }
		}catch(Exception e)
		{
		    e.printStackTrace();
		}
		
		return stdAdmissions;

	}
}