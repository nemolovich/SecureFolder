package fr.nemolovich.apps.securefolder.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.AfterClass;
import org.junit.Test;

import fr.nemolovich.apps.securefolder.batch.BatchCommand;

public class TestBatchCommand {
	
	@Test
	public void nullCommand() {
		BatchCommand command=new BatchCommand(null);
		String res=command.execute();
		assertEquals(null, res);
	}
	
	@Test
	public void notExistingCommand() {
		BatchCommand command=new BatchCommand("notExist");
		String res=command.execute();
		assertEquals(null, res);
	}
	
	@Test(timeout=1000)
	public void customizedCommand() {
		BatchCommand command=new BatchCommand("ls -la");
		String res=command.execute();
		assertFalse(res==null);
	}
	
	@AfterClass
	public static void close() {
		if(!MainTestSuite.isExecutingAll()) {
			MainTestSuite.close();
		}
	}
}
