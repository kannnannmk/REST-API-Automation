package Utilities;

import org.testng.Assert;

public class AdvancedAssertions extends Assert {
	
	public static void AssertStudentsAreEqual(Student student1, Student student2, String message){
		
		if(!student1.getName().equals(student2.getName()) && student1.getAge().equals(student2.getAge())){
			
			throw new AssertionError(message+" Expected Name: "+student1.getName()+" Actual Name"+student2.getName());
			
		}
		
	}
	
public static void AssertPartiallyEquals(Student student1, Student student2, String message){
		
		if(!student1.getName().contains(student2.getName()) && student1.getAge().contains(student2.getAge())){
			
			throw new AssertionError(message+" Expected Name: "+student1.getName()+" Actual Name"+student2.getName());
			
		}
		
	}

}
