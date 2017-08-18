package InterviewProject.cmpute.io;

import Utilities.Student;

public class CustomAssert {

	public static void AssertStudentsAreEqual(Student student1, Student student2, String message)
	{
		if(!student1.compare(student2))
		{
			throw new AssertionError(message + " Excepted: " + student2.toString() + " Actual: " + student1.toString());
		}
	}
}
	 
	

