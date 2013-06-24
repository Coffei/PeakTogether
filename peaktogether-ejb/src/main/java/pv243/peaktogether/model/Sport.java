package pv243.peaktogether.model;

/**
 * Created with IntelliJ IDEA.
 * Member: Coffei
 * Date: 28.4.13
 * Time: 9:31
 * Enum representing type of sport.
 */
public enum Sport {
    HIKING,
    BIKING,
    CLIMBING,
    VIAFERRATA,
    CANOEING,
    SKIING,
    CANYONING,
    SCOOTER,
    KAYAKING;
    

    @Override
    public String toString() {
        switch (this) {
            case BIKING: return "Biking";
            case CANOEING: return "Canoeing";
            case CANYONING: return "Canyoning";
            case CLIMBING: return "Climbing";
            case HIKING: return "Hiking";
            case KAYAKING: return "Kayaking";
            case SKIING: return "Skiing";
            case VIAFERRATA: return "Via-ferrata";
            case SCOOTER: return "Scooter";
            default: return null;
        }
    }
    //TODO: feel free to add more outdoor sports!
    //Warning: this enum is saved into DB as String, so be careful when changing the values!


}
