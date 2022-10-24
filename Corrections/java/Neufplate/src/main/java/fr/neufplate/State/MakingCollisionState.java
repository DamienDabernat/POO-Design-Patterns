package fr.neufplate.State;

import fr.neufplate.Event.EventType;
import fr.neufplate.Event.MessageToSendRepository;
import fr.neufplate.Neufplate;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;

public class MakingCollisionState extends State {

    public MakingCollisionState(Neufplate neufplate) {
        super(neufplate);
        //this.onMakingCollision();
    }

    @Override
    public String onTitling() {
        return null;
    }

    @Override
    public String onMakingCollision() {
        Map.Entry<Long,String> pair = makeCollision(this.neufplate.nft.title);
        this.neufplate.nft.setHash(pair.getValue());
        this.neufplate.nft.nonce = pair.getKey();

        this.neufplate.events.notify(EventType.COLLIDED, this.neufplate.user,
                MessageToSendRepository.sendOwnershipCertificate(this.neufplate.user, this.neufplate.nft));

        this.neufplate.changeState(new GeneratingState(this.neufplate));
        return pair.getValue();
    }

    @Override
    public String onGenerating() {
        return null;
    }

    private Map.Entry<Long,String> makeCollision(String value) {
        Long nonce = 0L;
        String hash = sha1(nonce + "#" + value);

        String prefix = String.join("", Collections.nCopies(this.neufplate.settings.level, "0"));

        while (!hash.startsWith(prefix)) {
            nonce++;
            hash = sha1(nonce + "#" + value);
        }

        return new AbstractMap.SimpleEntry<>(nonce, hash);
    }

    static String sha1(String input){
        String sha1 = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(input.getBytes(StandardCharsets.UTF_8));
            sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return sha1;
    }
}
