package urn.gojek.console.table;

public enum Alignment {
	CENTER,LEFT,RIGHT;
	
	public String getText(String text,int size)
	{
		StringBuilder ret = new StringBuilder(text);
		if(this.equals(Alignment.CENTER))
		{
			int sizeRemain = size-ret.length();
			int leftSize = sizeRemain/2;
			for(int i = 0;i < leftSize;i++)
			{
				ret.insert(0, ' ');
			}
			
			while(ret.length() < size)
			{
				ret.append(' ');
			}
			
		}
		else if(this.equals(Alignment.LEFT)) {
			while(ret.length() < size)
			{
				ret.append(' ');
			}
		}
		else
		{
			while(ret.length() < size)
			{
				ret.insert(0,' ');
			}
		}
		return ret.toString();
	}
}
