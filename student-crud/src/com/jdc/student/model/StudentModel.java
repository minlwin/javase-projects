package com.jdc.student.model;

import static com.jdc.student.model.StringsUtils.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jdc.student.model.Student.Gender;

public class StudentModel {

	private static final String FILE = "student.obj";
	
	private List<Student> list = new ArrayList<>();
	private static int count;
	
	private static StudentModel model;
	
	public static StudentModel getModel() {
		
		if(null == model) {
			model = new StudentModel();
		}
		
		return model;
	}
	
	private StudentModel() {
	}
	
	public void save(Student s) {
		
		// check fields
		if(isBlank(s.getName())) {
			throw new StudentAppException("Please enter Student name.");
		}
		
		if(isBlank(s.getPhone())) {
			throw new StudentAppException("Please enter Phone Number.");
		}
		
		if(s.getId() == 0) {
			// set id with counter
			s.setId(++ count);
			// add to list
			list.add(s);
		}
	}
	
	public List<Student> search(String gender, String name, String phone) {
		
		List<Student> raw = new ArrayList<>(list);
		
		Iterator<Student> itr = raw.iterator();
		
		while(itr.hasNext()) {
			Student s = itr.next();
			
			// gender not null and not empty and not equal All
			if(isNotBlank(gender) && !gender.equals("All")) {
				if(s.getGender() != Gender.valueOf(gender)) {
					itr.remove();
					continue;
				}
			}
			
			if(isNotBlank(name)) {
				if(!s.getName().toLowerCase().startsWith(name.toLowerCase())) {
					itr.remove();
					continue;
				}
			}
			
			if(isNotBlank(phone)) {
				if(!s.getPhone().startsWith(phone)) {
					itr.remove();
				}
			}
		}
		
		return raw;
	}
	
	public void delete(Student s) {
		list.remove(s);
	}

	@SuppressWarnings("unchecked")
	public void loadData() {

		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(FILE))){
			list = (List<Student>) input.readObject();
			
			Student lastStudent = list.get(list.size() - 1);
			count = lastStudent.getId();
		} catch (Exception e) {
			System.err.println("First time.");
		}
	}

	public void saveData() {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE))){
			out.writeObject(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
