package test.ojig.Model;

public class Local_Model {
    private String Local_Pk;
    private String Game;
    private String Count;
    private String Max;
    public Local_Model(String local_pk, String game, String count, String max) {
        Local_Pk = local_pk;
        Game = game;
        Count = count;
        Max = max;
    }
    public String getLocal_Pk() {
        return Local_Pk;
    }

    public String getGame() {
        return Game;
    }

    public String getCount() {
        return Count;
    }

    public String getMax(){
        return Max;
    }

}
