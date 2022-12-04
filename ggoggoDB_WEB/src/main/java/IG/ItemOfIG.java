package IG;

public class ItemOfIG implements Comparable<ItemOfIG>{
	
	final private int genreid;
	final private String genreName;
	private boolean Interest;
	private boolean Dirty;
	
	public ItemOfIG(int genreid, String genreName, boolean interest) {
		super();
		this.genreid = genreid;
		this.genreName = genreName;
		Interest = interest;
		Dirty = false;
	}
	
	public int getGenreid() {
		return genreid;
	}
	public String getGenreName() {
		return genreName;
	}
	
	public boolean isInterest() {
		return Interest;
	}
	public void setInterest(boolean interest) {
		if(Interest != interest) {
			Interest = interest;
			setDirty(true);
		}
	}
	
	public boolean isDirty() {
		return Dirty;
	}
	public void setDirty(boolean dirty) {
		Dirty = dirty;
	}

	@Override
	public int compareTo(ItemOfIG o) {
		return genreName.compareTo(o.getGenreName());
	}

} 
