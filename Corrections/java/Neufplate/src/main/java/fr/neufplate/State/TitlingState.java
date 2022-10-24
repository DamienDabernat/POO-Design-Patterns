package fr.neufplate.State;

import fr.neufplate.CorporateBsClient;
import fr.neufplate.Neufplate;

public class TitlingState extends State {

    public TitlingState(Neufplate neufplate) {
        super(neufplate);
        //this.onTitling();
    }

    @Override
    public String onTitling() {
        String title = CorporateBsClient.generateCorporateBs();
        this.neufplate.nft.title = title;
        this.neufplate.changeState(new MakingCollisionState(this.neufplate));
        return title;
    }

    @Override
    public String onMakingCollision() {
        throw new RuntimeException("You must generate a title before making collision");
        //return null;
    }

    @Override
    public String onGenerating() {
        return null;
    }
}
