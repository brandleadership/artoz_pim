package ch.screenconcept.artoz.publication;

import org.apache.log4j.Logger;

public class ArtozVelocityAttributeDescriptor extends GeneratedArtozVelocityAttributeDescriptor
{
	private static Logger log = Logger.getLogger( ArtozVelocityAttributeDescriptor.class.getName() );

	@Override
	public String getName()
	{
		return "Testname";
	}


	
	@Override
	public void setName(String name)
	{
		super.setName("test\n564");
	}



	@Override
	public String getDescription()
	{
		return "supertest";
	}
	
	

	
}
