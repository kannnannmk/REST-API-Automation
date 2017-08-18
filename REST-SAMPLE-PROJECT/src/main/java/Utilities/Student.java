package Utilities;


public class Student {
	
	private String name;
	private String age;
	
	public Student(String name, String age)
	{
		this.name = name;
		this.age = age;
	}

	
	public String getName()
	{
		return name;
	}
	
	public String getAge()
	{
		return age;
	}
	
	public boolean compare(Student otherStudent)
	{
		if(((this.getName().equals(otherStudent.getName()))
				&& (this.getAge().equals(otherStudent.getAge()))))
		{
			return true;
		}
		return false;
	}
	
	public String toString()
	{
		return "Name = " + name + " Age = " + age;
	}
}
