package urn.gojek.console.table;

import java.util.ArrayList;
import java.util.List;

public class Table {
	private String[] header;
	private List<String[]> rows;
	private int[] maxSpace;
	private Alignment contentAlignment;
	private Alignment headerAlignment;
	private int cellSpacing;

	public Table(String[] header) {
		if (header == null)
			throw new IllegalArgumentException("header Must Not Be Null");

		if (header.length == 0)
			throw new IllegalArgumentException("header Must Not Be Empty");

		cellSpacing = 3;

		// Instead Of Using The One In Param,Copy All The Its Items To Avoid Data
		// Manipulation By Reference
		this.header = new String[header.length];
		copy(header, this.header);
		maxSpace = new int[header.length];
		checkSpace(this.header);
		rows = new ArrayList<String[]>();
		contentAlignment = Alignment.LEFT;
		headerAlignment = Alignment.CENTER;
	}
	

	public void setContentAlignment(Alignment contentAlignment) {
		if (contentAlignment == null) {
			throw new IllegalArgumentException("Content Alignment Must Not Be Null");
		}
		this.contentAlignment = contentAlignment;
	}

	public void setHeaderAlignment(Alignment headerAlignment) {
		if (headerAlignment == null) {
			throw new IllegalArgumentException("Header Alignment Must Not Be Null");
		}
		this.headerAlignment = headerAlignment;

	}

	public void setCellSpacing(int cellSpacing) {
		if (cellSpacing < 0)
			throw new IllegalArgumentException("cellSpacing Should Be Equal Or Greater Than 0");

		this.cellSpacing = cellSpacing;
	}


	public void addRow(String[] row) {
		if (row == null) {
			throw new IllegalArgumentException("row Param Must Not Be Null");
		}

		if (row.length > header.length) {
			throw new IllegalArgumentException("row Param Size Must Be Less Than Or Equal With Header Size");
		}

		String[] copyRow = new String[row.length];
		copy(row, copyRow);
		rows.add(copyRow);
		checkSpace(copyRow);
	}

	public void clearContent()
	{
		if(rows.isEmpty())
			return;
		
		rows.clear();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < header.length; i++) {
			if (i > 0) {
				sb.append(headerAlignment.getText("", cellSpacing));
			}
			sb.append(headerAlignment.getText(header[i], maxSpace[i]));
		}
		
		for (String[] text : rows) {
			sb.append(String.format("%n"));
			for (int i = 0; i < text.length; i++) {

				if (i > 0) {
					sb.append(contentAlignment.getText("", cellSpacing));
				}
				sb.append(contentAlignment.getText(text[i] == null ? "" : text[i], maxSpace[i]));
			}
		}

		return sb.toString();
	}

	private void checkSpace(String[] text) {
		for (int i = 0; i < maxSpace.length; i++) {
			if (i == text.length) {
				break;
			}

			if(text[i] == null)
				continue;
			
			if (maxSpace[i] < text[i].length()) {
				maxSpace[i] = text[i].length();
			}
		}
	}

	private static <T> void copy(T[] from, T[] to) {
		System.arraycopy(from, 0, to, 0, to.length);
	}

}
