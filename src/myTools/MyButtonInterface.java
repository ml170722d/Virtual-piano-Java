package myTools;

public interface MyButtonInterface {

	public abstract int getButtonMidi();
	public abstract char getButtonSymbol();
	public abstract String getButtonNote();
	
	public abstract void removeListeners();
	public abstract void setListeners();
	
}
