package pv243.peaktogether.model;

/**
 * Created with IntelliJ IDEA.
 * Member: Coffei
 * Date: 28.4.13
 * Time: 12:13
 * To change this template use File | Settings | File Templates.
 */
public enum LocationType {
    START,
    END,
    CHECKPOINT;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if (this == START) return  "Start";
		else if (this  == END) return "End";
		else return "Checkpoint";
	}
    
    
 
}




