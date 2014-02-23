package fr.nemolovich.apps.securefolder.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
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
	
	@Test(timeout=10000)
	public void customizedCommand() {
		BatchCommand command=new BatchCommand(MainTestSuite.CUSTOMIZED_COMMAND);
		String res=command.execute();
		assertFalse(res==null);
	}

	@BeforeClass
	public static void init() throws IOException {
		System.out
				.println("[INFO] ------------------------------------------------------------------------");
		System.out.println("[INFO] Tests for: "+TestBatchCommand.class.getName());
		System.out
				.println("[INFO] ------------------------------------------------------------------------");
	}
	
	@AfterClass
	public static void close() {
		if(!MainTestSuite.isExecutingAll()) {
			MainTestSuite.close();
		}
	}
}
