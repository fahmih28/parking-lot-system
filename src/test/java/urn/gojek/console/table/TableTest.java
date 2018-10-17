package urn.gojek.console.table;

import org.junit.Test;

public class TableTest {
	@Test(expected=IllegalArgumentException.class)
	public void nullParamHeaderTableTest()
	{
		new Table(null);
	}	
	
	@Test(expected=IllegalArgumentException.class)
	public void emptyArrayParamHeaderTableTest()
	{
		new Table(new String[] {});
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setContentAlignmentParamNullTest()
	{
		Table table = new Table(new String[] {"No","Name"});
		table.setContentAlignment(Alignment.RIGHT);
		table.setContentAlignment(null);
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void setHeaderAlignmentParamNullTest()
	{
		Table table = new Table(new String[] {"No","Name"});
		table.setHeaderAlignment(Alignment.RIGHT);
		table.setHeaderAlignment(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setCellSpacingLessThanZeroTest()
	{
		Table table = new Table(new String[] {"No","Name"});
		table.setCellSpacing(3);
		table.setCellSpacing(-1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void addRowNullTest()
	{
		Table table = new Table(new String[] {"No","Name"});
		table.addRow(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void addRowMoreThanHeaderSizeTest()
	{
		Table table = new Table(new String[] {"No","Name"});
		table.addRow(new String[] {null,""});
		table.addRow(new String[] {""});
		table.setContentAlignment(Alignment.RIGHT);
		table.toString();
		table.addRow(new String[] {null,"",""});
	}
	
}
